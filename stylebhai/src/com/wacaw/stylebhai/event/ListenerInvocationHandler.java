package com.wacaw.stylebhai.event;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * Invocation Handler for handling request from Proxy Event Listeners. Each
 * event listener should have separate instance of this class, which holds the
 * reference to the instance and method which needs to be invoked.
 * 
 * @author saigopal
 *
 */
public class ListenerInvocationHandler implements InvocationHandler {
	Object listenerObject;
	Method listenerMethod;

	/**
	 * Constructor with instance and method as input.
	 * 
	 * @param object
	 *            instance on which the method to be called
	 * @param method
	 *            method to be called
	 */
	public ListenerInvocationHandler(Object object, Method method) {
		this.listenerObject = object;
		this.listenerMethod = method;
	}

	@Override
	public Object invoke(Object proxy, Method method, Object[] args)
			throws Throwable {
		if (listenerMethod.getParameterTypes().length == 0) {
			return listenerMethod.invoke(listenerObject);
		} else {
			return listenerMethod.invoke(listenerObject, args);
		}
	}
}
