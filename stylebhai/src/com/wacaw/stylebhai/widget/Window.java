package com.wacaw.stylebhai.widget;

/**
 * Class representing a window in a MDI application.
 * 
 * @author saigopal
 *
 */
public interface Window extends WidgetWrapper {
	/**
	 * Creates a dialog instance for this window.
	 * 
	 * @param dialogClass
	 * @param params
	 * @return
	 */
	Object createDialog(Class<?> dialogClass, Object... params);

	void showMessage(String title, String message, Exception e);

	boolean close();

	Object getWidget(String widgetName);

	MDIWindow getParent();
}
