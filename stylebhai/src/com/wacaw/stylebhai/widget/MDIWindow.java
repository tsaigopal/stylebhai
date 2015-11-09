package com.wacaw.stylebhai.widget;

import com.wacaw.stylebhai.core.AbstractScreen;


/**
 * Widget class representing a Main Window of an stand-alone UI application.
 * @see WidgetBuilder#createMDIWindow(Class)
 * @author saigopal
 *
 */
public interface MDIWindow extends Window {
	/**
	 * Opens a new child window inside the main window.
	 * @param screenClass screen class for the child window.
	 * @param arguments arguments to the screen class instance.
	 * @return Window object representing the child window.
	 * @throws Exception in case of any exception creating/initializing the window class.
	 */
	Window openChild(Class<? extends AbstractScreen> screenClass, Object... arguments) throws Exception ;

	/**
	 * Closes the child window.
	 * 
	 * @param window child window instance.
	 */
	void closeChild(Window window);
}