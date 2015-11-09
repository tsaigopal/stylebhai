package com.wacaw.stylebhai.event;

import com.wacaw.stylebhai.core.AbstractScreen;

/**
 * Implementing of this interface would add additional functionality to a {@link AbstractScreen}.
 * 
 * The methods {@link #addSupport(Object)} & {@link #removeSupport(Object)} would receive the
 * reference to the object on which the additional functionality to be added.
 * 
 * @author saigopal
 *
 * @param <T> type of interface supported for the additional functionality.
 * 
 * @see InterfaceSupport
 */
public interface InterfaceHandler<T> {
	/**
	 * Adds interface support to the input.
	 * 
	 * @param input input object instance of the class implementing the interface
	 * @throws Exception in case of any exception.
	 */
	void addSupport(T input) throws Exception;
	
	/**
	 * Removes interface support from the input.
	 * Removing the interface support may be necessary as some implementations may hold system resources, which
	 * needs to be released.
	 * 
	 * @param input object instance of the class implementing the interface.
	 * @throws Exception
	 */
	void removeSupport(T input) throws Exception;
}
