package com.wacaw.stylebhai.event;

import java.util.HashMap;
import java.util.Map;

import com.wacaw.stylebhai.core.StylerException;
import com.wacaw.stylebhai.util.Logger;

/**
 * Utility class which is used for adding additional functionality to a class by implementing some interface.
 * This class holds a map of Interface to {@link InterfaceHandler} mapping.
 * The methods {@link #addInterfaceSupport(Object, Class)} & {@link #removeInterfaceSupport(Object, Class)}
 * delegate the calls to appropriate {@link InterfaceHandler}s.
 * More such support can be added through {@link #registerInterfaceSupport(Class, InterfaceHandler)}
 * 
 * @author saigopal
 *
 */
public class InterfaceSupport {
	private static Map<Class<?>, InterfaceHandler<?>> registry = new HashMap<Class<?>, InterfaceHandler<?>>();

	static {
//		registry.put(WindowsSessionListener.class,
//				new WindowSessionIntfHandler());
	}

	/**
	 * Adds interface support to an object instance which implements the interface.
	 * 
	 * @param input object instance of the class implementing the interface
	 * @param intf interface class
	 * @return true if support is successfully added
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static boolean addInterfaceSupport(Object input, Class<?> intf) {
		boolean success = false;
		InterfaceHandler handler = registry.get(intf);
		
		try {
			if (handler != null) {
				handler.addSupport(input);
				success = true;
			}
		} catch (Exception e) {
			throw new StylerException(e);
		}
		
		return success;
	}

	/**
	 * Removes interface support from the object instance of the class which implements the interface.
	 * Removing the support may be important if the additional support holds reference to some system resources.
	 * 
	 * @param input input object to which the support was added.
	 * @param intf interface class
	 * @return if the support was successfully removed
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static boolean removeInterfaceSupport(Object input, Class intf) {
		boolean success = false;
		InterfaceHandler handler = registry.get(intf);
		
		try {
			if (handler != null) {
				handler.removeSupport(input);
				success = true;
			}
		} catch (Exception e) {
			throw new StylerException(e);
		}
		
		return success;
	}
	
	/**
	 * Register support for an interface.
	 * 
	 * @param intf interface class
	 * @param handler handler instance for adding the interface support
	 */
	public static <T> void registerInterfaceSupport(Class<T> intf, InterfaceHandler<T> handler) {
		registry.put(intf, handler);
	}

	/**
	 * Adds Interface support to an object.
	 * 
	 * @param screenObject
	 */
	public static void setupInterfaceHandlers(Object screenObject) {
		for (Class<?> intf : screenObject.getClass().getInterfaces()) {
			addInterfaceSupport(screenObject, intf);
		}
	}
}
