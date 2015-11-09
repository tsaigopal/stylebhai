package com.wacaw.stylebhai.widget;

/**
 * Widget class for Dialog.
 * Dialog class can return value through {@link #setReturnValue(Object)}.
 * 
 * @author saigopal
 */
public interface Dialog extends Window {
	Object getReturnValue();
}
