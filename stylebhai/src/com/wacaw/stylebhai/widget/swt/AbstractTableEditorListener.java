package com.wacaw.stylebhai.widget.swt;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.TableEditor;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableItem;


/**
 * Utility for making a table editable. Implementations can control the editor controls to be created for each cell.
 * 
 * @author saigopal
 */
public abstract class AbstractTableEditorListener implements Listener {
	private Table table;
	private TableEditor tableEditor;
	public AbstractTableEditorListener(Table table) {
		this.table = table;
		this.tableEditor = new TableEditor(table);
		tableEditor.horizontalAlignment = SWT.LEFT;
		tableEditor.grabHorizontal = true;
		tableEditor.minimumWidth = 50;
	}
	
	@Override
	public void handleEvent(Event event) {
		Rectangle clientArea = table.getClientArea ();
		Point pt = new Point (event.x, event.y);
		int index = table.getTopIndex ();
		while (index < table.getItemCount ()) {
			boolean visible = false;
			final TableItem item = table.getItem (index);
			for (int i=0; i<table.getColumnCount (); i++) {
				Rectangle rect = item.getBounds (i);
				if (rect.contains (pt)) {
					final int column = i;
					final Control editorControl = createControl(table, i, item.getText (i));
					if (editorControl != null) {
						Listener textListener = new Listener () {
							public void handleEvent (final Event e) {
								String text = getText(editorControl);
								if (e.type == SWT.FocusOut) {
									item.setText (column, text);
									editorControl.dispose ();
									tableItemChanged(item, column, text);
								} else if (e.type == SWT.Traverse) {
									if (e.detail == SWT.TRAVERSE_RETURN) {
										item.setText (column, text);
										tableItemChanged(item, column, text);
									} else if (e.detail == SWT.TRAVERSE_ESCAPE) {
										editorControl.dispose ();
										e.doit = false;
									}
								}
							}
						};
						editorControl.addListener (SWT.FocusOut, textListener);
						editorControl.addListener (SWT.Traverse, textListener);
						tableEditor.setEditor (editorControl, item, i);
						editorControl.setFocus ();
					}
					return;
				}
				if (!visible && rect.intersects (clientArea)) {
					visible = true;
				}
			}
			if (!visible) return;
			index++;
		}
	}
	
	public abstract Control createControl(Table table, int column, String text);
	
	public abstract String getText(Control control);
	
	public abstract void tableItemChanged(TableItem ti, int column, String value);
}
