package com.wacaw.stylebhai.widget.swt;

import java.util.Map;

import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Widget;

import com.wacaw.stylebhai.core.AbstractScreen;
import com.wacaw.stylebhai.widget.MDIWindow;
import com.wacaw.stylebhai.widget.Window;

/**
 * SWT implementation of Window interface.
 * 
 * @author saigopal
 */
public class SWTWindow implements Window {
	private Map<String, Widget> widgetMap;
	private MDIWindow parent;
	private AbstractScreen screen;
	
	public SWTWindow() {
	}
	
	public MDIWindow getParent() {
		return parent;
	}
	
	public AbstractScreen getScreen() {
		return screen;
	}

	public void setScreen(AbstractScreen screen) {
		this.screen = screen;
	}

	public void setParent(MDIWindow parent) {
		this.parent = parent;
	}

	public Map<String, Widget> getWidgetMap() {
		return widgetMap;
	}

	public void setWidgetMap(Map<String, Widget> widgetMap) {
		this.widgetMap = widgetMap;
	}

	@Override
	public void setVisible(boolean visible) {
		((Control) getNativeWidget()).setVisible(visible);
	}

	@Override
	public boolean isVisible() {
		return ((Control) getNativeWidget()).getVisible();
	}

	@Override
	public Object getNativeWidget() {
		return widgetMap.get("this");
	}
	
	@Override
	public Object getWidget(String widgetName) {
		return widgetMap.get(widgetName);
	}

	@Override
	public Object createDialog(Class<?> dialogClass, Object ... params) {
		return parent.createDialog(dialogClass, params);
	}

	@Override
	public void showMessage(String title, String message, Exception e) {
		parent.showMessage(title, message, e);
	}

	@Override
	public boolean close() {
		parent.closeChild(this);
		return true;
	}
}
