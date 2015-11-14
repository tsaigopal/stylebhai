package com.wacaw.stylebhai.core;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.ParameterizedType;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.PropertyUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.wacaw.stylebhai.event.EventHandler;
import com.wacaw.stylebhai.event.StylerEvent;
import com.wacaw.stylebhai.event.UIEvent;
import com.wacaw.stylebhai.util.ConverterUtil;
import com.wacaw.stylebhai.widget.WidgetBuilder;
import com.wacaw.stylebhai.widget.WidgetWrapper;

/**
 * Screen class supporting 2 way binding of model to view. Following things should be taken care to make it work properly.
 * 
 * <ul>
 * <li>The id of widgets should be the model property name, to which it should be bound automatically.
 * <li>Call {@link #setModel(Object)} to set the contents of the widgets.</li>
 * <li>Call {@link #getModel(Object)} to get the model object.</li>
 * <li>For changes to model through other events, call {@link #setProperty(String, Object)}.</li>
 * </ul>
 * 
 * @author saigopal
 *
 * @param <T>
 */
public abstract class AbstractBindableScreen<T> extends AbstractScreen {
	@Autowired
	private WidgetBuilder widgetBuilder;
	
	private T model;
	
	public AbstractBindableScreen(String title, String icon) {
		super(title, icon);
	}
	
	@Override
	public void postCreate() {
		super.postCreate();
		initModelListeners();
	}
	
	public T getModel() {
		return model;
	}
	
	public void setModel(T model) {
		this.model = model;
		populateModel();
	}
	
	public void setProperty(String name, Object value) {
		this.getWindowHandle().getWidget(name);
		try {
			PropertyUtils.setProperty(model, name, value);
		} catch (IllegalAccessException | InvocationTargetException
				| NoSuchMethodException e) {
			throw new StylerException(e);
		}
	}
	
	public Object getProperty(String name) {
		return this.getWindowHandle().getWidget(name).getValue();
	}
	
	private void populateModel() {
		try {
			Map<String, Object> values = PropertyUtils.describe(model);
			for (Entry<String, Object> entry : values.entrySet()) {
				WidgetWrapper wrapper = this.getWindowHandle().getWidget(entry.getKey());
				if (wrapper != null) {
					Object value = entry.getValue();
					if (wrapper.getSupportedType() == String.class) {
						value = ConverterUtil.convertBack(value);
					}
					wrapper.setValue(value);
				}
			}
		} catch (IllegalAccessException | InvocationTargetException
				| NoSuchMethodException e) {
			throw new StylerException("Error while autopopulating model to screen elements", e);
		}
	}
	
	@SuppressWarnings({ "unchecked" })
	private void initModelListeners() {
		Class<?> persistentClass = (Class<T>) ((ParameterizedType) getClass()
                .getGenericSuperclass()).getActualTypeArguments()[0];
		BeanInfo beanInfo;
		try {
			beanInfo = Introspector.getBeanInfo(persistentClass);
		} catch (IntrospectionException e1) {
			throw new StylerException("Error introspecing " + persistentClass, e1);
		}
		PropertyDescriptor[] properties = beanInfo.getPropertyDescriptors();
		for (PropertyDescriptor prop : properties) {
			final String attr = prop.getName();
			WidgetWrapper wrapper = this.getWindowHandle().getWidget(attr);
			if (wrapper != null) {
				widgetBuilder.addListener(wrapper, UIEvent.Change, new EventHandler() {
					@Override
					public void handleEvent(StylerEvent event) {
						try {
							String data = (String) event.getData();
							BeanUtils.setProperty(model, attr, data);
						} catch (IllegalAccessException
								| InvocationTargetException e) {
							throw new StylerException(e);
						}
					}
				});
			}
		}
	}
}