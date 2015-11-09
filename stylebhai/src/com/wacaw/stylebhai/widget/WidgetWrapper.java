package com.wacaw.stylebhai.widget;

/**
 * Class representing a widget in the application. 
 * The subclasses of this are injected as screen instance variables.
 * Implementation of these classes are wrapper around native widgets, 
 * which can be accessed using {@link #getNativeWidget()}.
 * 
 * @author saigopal
 */
public interface WidgetWrapper {
	/**
	 * Sets the visibility of the widget,
	 * @param visible 
	 */
	void setVisible(boolean visible);
	boolean isVisible();
	
	Object getNativeWidget();
}
