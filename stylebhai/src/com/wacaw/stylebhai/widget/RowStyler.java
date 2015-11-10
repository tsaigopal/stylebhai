package com.wacaw.stylebhai.widget;


/**
 * Interface for styling a table row based on its data.
 * 
 * TODO move to swt package
 * @author saigopal
 *
 * @param <C> control representing a table row.
 * @param <T> type handled by the table
 */
public interface RowStyler<C, T> {
	void setStyle(C item, T object);
}
