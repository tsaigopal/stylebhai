package com.wacaw.stylebhai.widget;
import org.eclipse.swt.widgets.TableItem;


/**
 * Interface for styling a table row based on its data.
 * 
 * TODO move to swt package
 * @author saigopal
 *
 * @param <T> type handled by the table
 */
public interface RowStyler<T> {
	void setStyle(TableItem item, T object);
}
