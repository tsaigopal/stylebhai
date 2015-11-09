package com.wacaw.stylebhai.core;

import com.wacaw.stylebhai.widget.WidgetBuilder;

/**
 * Interface to be used with @link {@link StylerApp} for creating a windows and/or dialogs in a styler application.
 * @author saigopal
 */
public interface UILauncher {
	void startUI(WidgetBuilder builder);
}