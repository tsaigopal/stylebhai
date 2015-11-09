package com.wacaw.stylebhai.config;

/**
 * Interface for creating WidgetConfigs.
 * 
 * @author saigopal
 *
 */
public interface WidgetConfigFactory {
	/**
	 * Should return true if it can create WidgetConfig for a given class 
	 * @param className abstract screen class name
	 * @return true if it can create WidgetConfig for a given class
	 */
	boolean canCreate(Class<?> className);
	/**
	 * Returns a screen config object for the given class
	 * @param className class name
	 * @return screen/widget config
	 */
	WidgetConfig getConfig(Class<?> className);
}
