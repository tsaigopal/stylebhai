package com.wacaw.stylebhai.core;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.wacaw.stylebhai.event.EventListener;
import com.wacaw.stylebhai.event.InterfaceHandler;
import com.wacaw.stylebhai.widget.WidgetBuilder;
import com.wacaw.stylebhai.widget.swt.SWTWidgetUtils;

/**
 * Application runner for launching an application designed in styler.
 * 
 * @see WidgetBuilder
 * @see AbstractScreen
 * @see SWTWidgetUtils
 * @see EventListener
 * @see InterfaceHandler
 * @author saigopal
 */
public class StylerApp {
	/**
	 * Runs a styler application, by creating spring context, calling launcher and destroying context after done.
	 * 
	 * It Creates a AnnotationConfigApplicationContext with base packages as styler components base and the launcher package.
	 * So keep the launcher class (mostly its a inner class in a main class, so main class) in the root package of your 
	 * application.
	 * 
	 * @param launcher launcher instance
	 */
	public static void run(UILauncher launcher) {
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext("com.wacaw.stylebhai",
				launcher.getClass().getPackage().getName());
		try {
			WidgetBuilder builder = context.getBean(WidgetBuilder.class);
			launcher.startUI(builder);
		} finally {
			context.close();
		}
	}
}
