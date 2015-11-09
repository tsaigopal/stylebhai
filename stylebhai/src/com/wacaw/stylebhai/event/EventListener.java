package com.wacaw.stylebhai.event;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.wacaw.stylebhai.core.AbstractScreen;
import com.wacaw.stylebhai.widget.swt.SWTWidgetUtils;

/**
 * Indicates the method is an UI EventListener. 
 * Applies to methods of implementation of {@link AbstractScreen}
 * 
 * @author saigopal
 * @see SWTWidgetUtils#createListeners(java.util.Map, Object)
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface EventListener {
	/**
	 * Widget name on which the event is required.
	 * 
	 * @return
	 */
	String widget();

	/**
	 * Event type.
	 * 
	 * @return event type
	 */
	UIEvent eventType();

	/**
	 * Native/Custom event not specified in the {@link UIEvent}
	 * @return custom value representing a event type
	 */
	String custom() default "";
}
