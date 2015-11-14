package com.wacaw.stylebhai.widget.swt;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;

import com.wacaw.stylebhai.core.StylerException;
import com.wacaw.stylebhai.util.BeanUtility;
import com.wacaw.stylebhai.widget.RowStyler;
import com.wacaw.stylebhai.widget.TableModel;

/**
 * Provides list based model support for SWT Table, with edit functionality.
 * 
 * This class provides a Table editor for a list of object of type <T>. It
 * providing binding of model to view.
 * 
 * This class internally uses {@link BeanUtility} for converting bean attributes
 * to strings and vice-versa. Any changes to view (through editor) is
 * automatically captured in model. Any changes to model outside the knowledge
 * this class should be explicitly updated here by calling
 * {@link #update(Object)}. Changes to model though
 * {@link #setChangeListener(PropertyChangeListener)} will be updated to view
 * and doesn't need explicit call to {@link #update(Object)}.
 * 
 * @author sai
 *
 * @param <T>
 *            type of objects in the model
 */
public class SWTTableModel<T> implements TableModel<TableItem, T> {
	Table table;
	boolean[] editableColumns = null;
	List<T> objectsInView;
	RowStyler<TableItem, T> rowStyler;

	AbstractTableEditorListener tableEditorListener;
	private PropertyChangeListener changeListner;

	/**
	 * Constructor for TableModel
	 * 
	 * @param table
	 *            table widget
	 * @param properties
	 *            propertsetries of the Type <T> to be shown in the table
	 *            columns
	 */
	public SWTTableModel(Table table) {
		this.table = table;
	}

	@Override
	public void setRowStyler(RowStyler<TableItem, T> rowStyler) {
		this.rowStyler = rowStyler;
	}

	@Override
	public void setEditable(boolean[] editableCols) {
		this.editableColumns = editableCols;
		boolean editable = editableColumns != null;
		if (editable) {
			table.removeListener(SWT.MouseDoubleClick, tableEditorListener);
			tableEditorListener = new AbstractTableEditorListener(table) {
				@Override
				public String getText(Control control) {
					if (control instanceof Text) {
						Text text = (Text) control;
						return text.getText();
					}
					return "";
				}

				@Override
				public Control createControl(Table table, int column,
						String text) {
					if (editableColumns.length == 0 || editableColumns[column]) {
						Text textControl = new Text(table, SWT.NONE);
						textControl.setText(text);
						textControl.selectAll();
						return textControl;
					} else {
						return null;
					}
				}

				@SuppressWarnings("unchecked")
				@Override
				public void tableItemChanged(TableItem ti, int column,
						String value) {
					try {
						String propertyName = (String) table.getColumn(column)
								.getData();
						Object oldValue = BeanUtility.getProperty(ti.getData(),
								propertyName);
						BeanUtility.setPropertyString(ti.getData(),
								propertyName, value);
						Object newValue = BeanUtility.getProperty(ti.getData(),
								propertyName);
						if (changeListner != null) {
							changeListner
									.propertyChange(new PropertyChangeEvent(ti
											.getData(), propertyName, oldValue,
											newValue));
							updateRow(ti, (T) ti.getData());
						}
					} catch (Exception e) {
						throw new StylerException(e);
					}
				}
			};
			table.addListener(SWT.MouseDoubleClick, tableEditorListener);
		} else {
			if (tableEditorListener != null) {
				table.removeListener(SWT.MouseDoubleClick, tableEditorListener);
			}
		}
	}

	@Override
	public void setChangeListener(PropertyChangeListener listener) {
		this.changeListner = listener;
	}

	@Override
	public void setObjects(List<T> objects) {
		this.objectsInView = objects;
		refresh();
	}

	public void refresh() {
		int currentSize = table.getItemCount();
		int newSize = objectsInView.size();
		int reuse = Math.min(currentSize, newSize);
		for (int i = 0; i < reuse; i++) {
			updateRow(table.getItem(i), objectsInView.get(i));
		}
		// remove excess records
		if (reuse >= 0 && currentSize >= 0 && reuse < currentSize) {
			table.remove(reuse, currentSize - 1);
		}

		// add remaining items
		for (int i = reuse; i < newSize; i++) {
			TableItem ti = new TableItem(table, SWT.NONE);
			updateRow(ti, objectsInView.get(i));
		}
	}

	private void updateRow(TableItem item, T object) {
		item.setData(object);
		for (int i = 0; i < table.getColumnCount(); i++) {
			String value = BeanUtility.getPropertyAsString(object,
					(String) table.getColumn(i).getData());
			item.setText(i, value);
		}
		if (rowStyler != null) {
			rowStyler.setStyle(item, object);
		}
	}

	@Override
	public void addObject(T object) {
		TableItem ti = new TableItem(table, SWT.NONE);
		updateRow(ti, object);
		objectsInView.add(object);
	}

	@Override
	public T removeItem(T object) {
		int index = objectsInView.indexOf(object);
		return removeItem(index);
	}

	@SuppressWarnings("unchecked")
	private T removeItem(int index) {
		T result = (T) table.getItem(index).getData();
		table.remove(index);
		objectsInView.remove(index);
		return result;
	}

	@Override
	public List<T> removeSelectedItems() {
		int[] indices = table.getSelectionIndices();
		Arrays.sort(indices);
		List<T> removed = new ArrayList<T>();
		for (int i = indices.length - 1; i >= 0; i--) {
			T removedItem = removeItem(indices[i]);
			removed.add(removedItem);
		}
		return removed;
	}

	@Override
	public List<T> getObjects() {
		return objectsInView;
	}

	@Override
	@SuppressWarnings("unchecked")
	public T getSelection() {
		TableItem[] selections = table.getSelection();
		T result = null;
		if (selections.length > 0) {
			result = (T) selections[0].getData();
		}
		return result;
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<T> getSelections() {
		TableItem[] selections = table.getSelection();
		List<T> newList = new ArrayList<T>();
		for (TableItem item : selections) {
			newList.add((T) item.getData());
		}
		return newList;
	}

	@Override
	public void update(T object) {
		int index = objectsInView.indexOf(object);
		updateRow(table.getItem(index), object);
	}

	@Override
	public void setVisible(boolean visible) {
		table.setVisible(visible);
	}

	@Override
	public boolean isVisible() {
		return table.isVisible();
	}

	@Override
	public Object getNativeWidget() {
		return table;
	}

	@Override
	public void setSelection(T... objects) {
		int[] indices = new int[objects.length];
		for (int i = 0; i < indices.length; i++) {
			indices[i] = this.objectsInView.indexOf(objects[i]);
		}
		table.setSelection(indices);
	}

	@Override
	public Object getValue() {
		return objectsInView;
	}

	@Override
	public void setValue(Object value) {
		this.objectsInView = (List<T>) value;
	}

	@Override
	public Class<?> getSupportedType() {
		return List.class;
	}
}
