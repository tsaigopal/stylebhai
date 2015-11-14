package com.wacaw.stylebhai.widget.swt;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.DateTime;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Widget;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;

import com.wacaw.stylebhai.config.WidgetConfig;
import com.wacaw.stylebhai.core.AbstractScreen;
import com.wacaw.stylebhai.core.StylerException;
import com.wacaw.stylebhai.event.EventHandler;
import com.wacaw.stylebhai.event.EventListener;
import com.wacaw.stylebhai.event.InterfaceSupport;
import com.wacaw.stylebhai.event.ListenerInvocationHandler;
import com.wacaw.stylebhai.event.UIEvent;
import com.wacaw.stylebhai.util.BeanUtility;
import com.wacaw.stylebhai.widget.WidgetBuilder;
import com.wacaw.stylebhai.widget.WidgetWrapper;
import com.wacaw.stylebhai.widget.swt.event.AbstractEventListener;
import com.wacaw.stylebhai.widget.swt.event.SWTKeyEventHandler;
import com.wacaw.stylebhai.widget.swt.event.SWTMouseEventHandler;

/**
 * Utility class for doing widget creation leg-work.
 * 
 * @author saigopal
 *
 */
public class SWTWidgetUtils {

	/**
	 * static mapping of widget class to wrapper class
	 */
	private static Map<Class<?>, Class<?>> widgetToWraperMapping = new HashMap<>();
	/**
	 * static mapping of SWT events to styler events
	 */
	private static Map<UIEvent, AbstractEventListener> eventTypes = new HashMap<>();
	static {
		widgetToWraperMapping.put(Text.class, SWTText.class);
		widgetToWraperMapping.put(Label.class, SWTText.class);
		widgetToWraperMapping.put(Button.class, SWTButton.class);
		widgetToWraperMapping.put(Table.class, SWTTableModel.class);
		widgetToWraperMapping.put(DateTime.class, SWTDateTime.class);
		
		eventTypes.put(UIEvent.Click,  new SWTMouseEventHandler(SWT.Selection));
		eventTypes.put(UIEvent.MouseUp, new SWTMouseEventHandler(SWT.MouseUp));
		eventTypes.put(UIEvent.MouseDown, new SWTMouseEventHandler(SWT.MouseDown));
		eventTypes.put(UIEvent.DoubleClick, new SWTMouseEventHandler(SWT.MouseDoubleClick));
		eventTypes.put(UIEvent.KeyUp, new SWTKeyEventHandler(SWT.KeyUp));
		eventTypes.put(UIEvent.KeyDown, new SWTKeyEventHandler(SWT.KeyDown));
		eventTypes.put(UIEvent.Change, new SWTKeyEventHandler(SWT.Modify));
	}
	
	/**
	 * Creates a widget as per the input config.
	 * <ul>
	 * <li>Widget class - Simple name of a SWT widget (org.eclipse.swt.widgets) or Fully qualified class name of the widget</li>
	 * <li>styles - Use constants defined in {@link SWT}</li>
	 * <li>properties - Use the properties of the Widget class, as thier setters will be called</li>
	 * </ul>
	 * 
	 * @param config widget config
	 * @param parent parent widget
	 * @param mapToPopulate a map used to keep track of widgets created during the recursion.
	 * @throws Exception
	 * @see {@link #getSWTConstructor(Class, Object)}
	 */
	public static void createWidget(WidgetConfig config, Widget parent, Map<String, WidgetWrapper> mapToPopulate) throws Exception {
		String widgetClassName = config.getType();
		if (widgetClassName.indexOf('.') < 0) {
			widgetClassName = "org.eclipse.swt.widgets." + widgetClassName;
		}

		int styleValue = SWT.NONE;
		String style = config.getStyles();
		if (style != null) {
			String[] styleWords = style.split(",");
			for (String styleWord : styleWords) {
				int styleField = (Integer) SWT.class.getField(styleWord).get(null);
				styleValue |= styleField;
			}
		}

		Class<?> widgetClass = Class.forName(widgetClassName);
		Widget widgetInstance = (Widget) getSWTConstructor(widgetClass, parent)
				.newInstance(parent, styleValue);
		applyStyle(widgetInstance, config.getProperties());
		mapToPopulate.put(config.getId(), createWrapperWidget(widgetInstance));
		
		for (WidgetConfig child : config.getChildren()) {
			createWidget(child, widgetInstance, mapToPopulate);
		}
	}

	/**
	 * Constructor instance to be invoked for creating the widget.
	 * All SWT widgets have a constructor like Widget(Container, Style)
	 * 
	 * @param widgetClass widget class
	 * @param parent parent widget
	 * @return Constructor instance
	 * @throws NoSuchMethodException
	 */
	public static Constructor<?> getSWTConstructor(Class<?> widgetClass, Object parent)
			throws NoSuchMethodException {
		Class<?> parentClass = parent.getClass();
		while (parentClass != null) {
			try {
				return widgetClass.getConstructor(parentClass, Integer.TYPE);
			} catch (NoSuchMethodException me) {
				parentClass = parentClass.getSuperclass();
			}
		}
		throw new StylerException("No suitable Constructor found!");
	}

	/**
	 * Applies given styles to the widget.
	 * 
	 * This method is used by {@link #applyStyles()} to apply a set of styles to a widget.
	 * This method can be called explicitly if a widget is not declared as a field or any style needs to be changed at runtime.
	 * 
	 * @param widget widget on which styles need to be applied
	 * @param styleKey set of styles to be applied
	 * @throws Exception in case the any of the setter methods throw exception.
	 */
	public static void applyStyle(Widget widget, Map<String, String> styles) throws StylerException {
		for (Entry<String, String> entry : styles.entrySet()) {
			try {
				BeanUtility.setPropertyString(widget, entry.getKey(), entry.getValue());
			} catch (Exception e) {
				throw new StylerException("Error setting property {0}.{1} to {2}", e, 
						widget.getClass().getName(), entry.getKey(), entry.getValue());
			}
		}
	}

	/**
	 * Creates container along with its child controls.
	 * 
	 * @param parent parent container
	 * @param screenClass Class for which the container needs to be created.
	 * @return map of widget isntance against their names, the map contains element "this" representing the container.
	 * @throws Exception in case of excetion in parsing or widget creation
	 */
	public static Map<String, WidgetWrapper> createContainer(Composite parent, Class<?> screenClass) throws Exception {
		WidgetConfig config = WidgetBuilder.getConfig(screenClass);
		Map<String, WidgetWrapper> controlMapToUpdate = new HashMap<String, WidgetWrapper>();
		createWidget(config, parent, controlMapToUpdate);
		return controlMapToUpdate;
	}

	/**
	 * Creates Listeners for the widgets.
	 * 
	 * Creates listeners for the widgets by scanning methods annotated with {@link EventListener}.
	 * Inherited classes can override the method, if they want a different implementation.
	 * @param widgetMap map of widgets created
	 * @param screenObject object instance of {@link AbstractScreen}
	 */
	public static void createListeners(Map<String, WidgetWrapper> widgetMap, Object screenObject) {
		Method[] methods = screenObject.getClass().getMethods();
		for (Method method : methods) {
			EventListener el = method.getAnnotation(EventListener.class);
			if (el != null) {
				EventHandler handler = (EventHandler) Proxy.newProxyInstance(screenObject.getClass().getClassLoader(),
	                    new Class[] { EventHandler.class }, new ListenerInvocationHandler(screenObject, method));
				WidgetWrapper widget = widgetMap.get(el.widget());
				if (widget == null) {
					throw new StylerException("CREATE.LISTENER:" + el.widget() + " doesn't exist");
				}
				addListener(widget, el.eventType(), handler);
			}
		}
	}
	
	public static void addListener(WidgetWrapper widget, UIEvent event,
			final EventHandler handler) {
		AbstractEventListener listener = eventTypes.get(event);
		Widget nativeWidget = (Widget) widget.getNativeWidget();
		nativeWidget.addListener(listener.getEventTyp(), listener.clone(handler));
	}

	public static WidgetWrapper createWrapperWidget(Widget widget) {
		Class<?> wrapperClass = widgetToWraperMapping.get(widget.getClass());
		if (wrapperClass != null) {
			try {
				return (WidgetWrapper) wrapperClass.getConstructors()[0].newInstance(widget);
			} catch (Exception e) {
				throw new StylerException("Error creating wrapper", e);
			}
		} else {
			return new SWTWidget((Widget) widget);
		}
	}
	
	/**
	 * Creates the wrapper widgets which are injected to AbstractScreen instance variables.
	 * 
	 * @param widgetMap map of widgets
	 * @param widgetName name of the widget, for which the wrapper to be created.
	 * @return Wrapped widget instance
	 */
	public static WidgetWrapper createWrapperWidget(Map<String, Widget> widgetMap, String widgetName) {
		Widget widget = widgetMap.get(widgetName);
		return createWrapperWidget(widget);
	}

	/**
	 * Populates the screen instance with wrapper objects.
	 * 
	 * @param widgetMap map of widgets
	 * @param screenInstance screen instance
	 * @throws Exception
	 */
	public static void populateWrappersInInstance(Map<String, WidgetWrapper> widgetMap,
			Object screenInstance) throws Exception {
		Field[] fields = screenInstance.getClass().getDeclaredFields();
		for (Field field : fields) {
			WidgetWrapper widget = widgetMap.get(field.getName());
			if (widget != null) {
				field.setAccessible(true);
				field.set(screenInstance, widget);
			}
		}
	}

	/**
	 * Adds Interface support to an object.
	 * 
	 * @param screenObject
	 */
	public static void setupInterfaceHandlers(Object screenObject) {
		for (Class<?> intf : screenObject.getClass().getInterfaces()) {
			InterfaceSupport.addInterfaceSupport(screenObject, intf);
		}
	}

	/**
	 * Creates a window instance, taking care of widget creation, event listeners and interface support.
	 * 
	 * @param screenClass screen class
	 * @param parent parent container
	 * @param bf 
	 * @param params 
	 * @param params parameters, if any
	 * @return
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws Exception
	 */
	public static SWTWindow createScreen(Class<? extends AbstractScreen> screenClass,
			Composite parent, Object[] params, AutowireCapableBeanFactory bf) throws Exception {
		AbstractScreen screenObject = null;
		try {
			screenObject = (AbstractScreen) screenClass.newInstance();
		} catch (InstantiationException | IllegalAccessException e) {
			new StylerException(screenClass + " must have a no-args constructor.", e);
		}
		Map<String, WidgetWrapper> widgetMap = createContainer(parent, screenClass);
		createListeners(widgetMap, screenObject);
		populateWrappersInInstance(widgetMap, screenObject);
		InterfaceSupport.setupInterfaceHandlers(screenObject);
		
		SWTWindow window = new SWTWindow();
		window.setWidgetMap(widgetMap);
		window.setScreen(screenObject);
		screenObject.setWindowHandle(window);
		bf.autowireBean(screenObject);
		screenObject.postCreate();
		screenObject.initialize(params);
		parent.layout();
		return window;
	}

	public static ImageDescriptor getImage(String path) {
		return ImageDescriptor.createFromURL(SWTWidgetUtils.class.getResource("/images/" + path));
	}
}