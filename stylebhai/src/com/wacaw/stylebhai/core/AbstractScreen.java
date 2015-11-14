package com.wacaw.stylebhai.core;

import com.wacaw.stylebhai.config.WidgetConfig;
import com.wacaw.stylebhai.event.EventListener;
import com.wacaw.stylebhai.widget.MDIWindow;
import com.wacaw.stylebhai.widget.WidgetWrapper;
import com.wacaw.stylebhai.widget.Window;

/**
 * Abstract class for implementing a screen. The same class can be extended for implementing a Main Window, Child Window and Dialog.
 * Each such class has a screen definition associated with it, represented by a config file ({@link WidgetConfig}).
 * 
 * The implementation class
 * <ul>
 * <li>must have a no-arg constructor. It should pass the screen title and imageRef to super constructor.</li>
 * <li>must have a screen definition, containing the widgets and their properties/styles.</li>
 * <li>can have widget wrappers ({@link WidgetWrapper} as attributes, which will be injected by framework. 
 * attribute name should be same as id in the WidgetConfig</li>
 * <li>can have event listener methods annotated with {@link EventListener}</li>
 * <li>override {@link #initialize(Object...)} to initialize the widgets</li>
 * <li>can have autowired spring beans, though these instances are not spring beans.
 * <li>can access Window instance using {@link #getWindowHandle()}</li>
 * <li>can access native UI elements using {@link #getWindowHandle().getWidget()}</li>
 * </ul>
 * 
 * @see WidgetConfig
 * @author saigopal
 */
public abstract class AbstractScreen {
	/**
	 * window handle to the wrapped window object represented by teh config of this class.
	 */
	Window windowHandle;
	/**
	 * Used by dialogs to return a value
	 */
	Object returnValue;
	
	/**
	 * Title of the screen to be shown in the wrapped container
	 */
	private String title;
	
	/**
	 * path to the icon of the screen to be shown in the wrapped container
	 */
	private String icon;
	
	/**
	 * Screen class with title and icon path.
	 * @param title title of the screen
	 * @param icon icon path.
	 */
	public AbstractScreen(String title, String icon) {
		this.title = title;
		this.icon = icon;
	}
	
	/**
	 * @return handle to the wrapper window object
	 */
	public Window getWindowHandle() {
		return windowHandle;
	}
	
	/**
	 * @param handle sets window handle
	 */
	public void setWindowHandle(Window handle) {
		this.windowHandle = handle;
	}

	/**
	 * @return return value if its for a dialog
	 */
	public Object getReturnValue() {
		return returnValue;
	}

	/**
	 * @param returnValue sets the return value for a dialog
	 */
	public void setReturnValue(Object returnValue) {
		this.returnValue = returnValue;
	}

	/**
	 * Called on press of a button (in a dialog), returns true if handled.
	 * 
	 * Must override if its for a Dialog screen to handle ok/cancel buttons.
	 * 
	 * @param action "ok" or "cancel" as per the button pressed.
	 * @return true id handled by this method, false otherwize
	 */
	public boolean executeAction(String action) {
		return false;
	}

	/**
	 * Initialize the screen for the given arugments.
	 * 
	 * @param objects
	 * @throws Exception
	 * @see {@link MDIWindow#openChild(Class, Object...)}
	 */
	public abstract void initialize(Object...objects) throws Exception;

	public String getTitle() {
		return title;
	}

	public String getIcon() {
		return icon;
	}
	
	public void postCreate() {
		
	}
}
