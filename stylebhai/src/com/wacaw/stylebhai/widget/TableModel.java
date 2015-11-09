package com.wacaw.stylebhai.widget;

import java.beans.PropertyChangeListener;
import java.util.List;

/**
 * Wrapper around a table widget, representing the underlying model, supporting 2-way binding of model to view. 
 * This class enables screen classes to work with value objects instead of manipulating table columns,rows.
 * <ul>
 * <li>Table can be populated using {@link #setObjects(List)}</li>
 * <li>Provides methods to query and update the row(s) using value objects.</li> 
 * <li>This class provides feature of styling a row/column through the {@link #setRowStyler(RowStyler)} interface.</li>
 * <li>Provides feature to make columns editable using {@link #setEditable(boolean[])}. 
 * By default none of columns are editable.</li>
 * </ul>
 * 
 * @author saigopal
 *
 * @param <T> value object class
 */
public interface TableModel<T> extends WidgetWrapper {

	/**
	 * Sets the RowStyler.
	 * 
	 * RowStyler is called when updating view for each row.
	 * 
	 * @param rowStyler
	 */
	public abstract void setRowStyler(RowStyler<T> rowStyler);

	/**
	 * Sets the editable columns.
	 * 
	 * If passed null, makes the table readonly.
	 * If passed as a blank array (length 0), makes all columns editable.
	 * Else makes columns editable based on array contents. 
	 * @param editableCols array of boolean values indicating whether to make a column editable.
	 */
	public abstract void setEditable(boolean[] editableCols);

	/**
	 * Sets the Change listener.
	 * @param listner
	 */
	public abstract void setChangeListener(PropertyChangeListener listener);

	/**
	 * Sets the table content
	 * @param objects
	 */
	public abstract void setObjects(List<T> objects);

	/**
	 * Adds a row to the table for the given object.
	 * @param object input object
	 */
	public abstract void addObject(T object);

	/**
	 * Removes the item from the underlying model and view
	 * @param object object to be removed
	 * @return if the object was present, null otherwise
	 */
	public abstract T removeItem(T object);

	/**
	 * Removes the selected items from the model and view.
	 * @return List of items removed.
	 */
	public abstract List<T> removeSelectedItems();

	/**
	 * Returns the current state of underlying model.
	 * @return List of objects shown in the table.
	 */
	public abstract List<T> getObjects();

	/**
	 * Returns the first selected item.
	 * @return first selected item
	 */
	public abstract T getSelection();

	/**
	 * Returns the selected items
	 * @return selected itesm
	 */
	public abstract List<T> getSelections();
	
	/**
	 * Sets the selected items in the view.
	 * @param objects objects to be selected.
	 */
	void setSelection(T... objects);

	/**
	 * Updates the view for the given object instance.
	 * @param object object to be updated
	 */
	public abstract void update(T object);
}