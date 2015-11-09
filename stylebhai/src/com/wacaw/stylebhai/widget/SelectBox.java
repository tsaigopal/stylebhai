package com.wacaw.stylebhai.widget;

import java.util.List;

/**
 * Widget class representing a Select Box, Combo box, List box
 * @author saigopal
 */
public interface SelectBox extends WidgetWrapper {
	/**
	 * Sets the list of object to be shown in the select/combo/list.
	 * @param objects
	 */
	public void setObjects(List<Object> objects);
	
	/**
	 * Sets the selected item(s).
	 * @param objs sets the selection
	 */
	public void setSelection(Object... objs);
	
	/**
	 * Returns the current selected item(s)
	 * @return current selected item(s)
	 */
	public Object[] getSelection();
}
