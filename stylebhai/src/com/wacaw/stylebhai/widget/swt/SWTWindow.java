package com.wacaw.stylebhai.widget.swt;

import java.util.Map;

import com.wacaw.stylebhai.core.AbstractScreen;
import com.wacaw.stylebhai.widget.MDIWindow;
import com.wacaw.stylebhai.widget.WidgetWrapper;
import com.wacaw.stylebhai.widget.Window;

/**
 * SWT implementation of Window interface.
 * 
 * @author saigopal
 */
public class SWTWindow implements Window {
	private Map<String, WidgetWrapper> widgetMap;
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

	public Map<String, WidgetWrapper> getWidgetMap() {
		return widgetMap;
	}

	public void setWidgetMap(Map<String, WidgetWrapper> widgetMap) {
		this.widgetMap = widgetMap;
	}

	@Override
	public Object getNativeWidget() {
		return widgetMap.get("this").getNativeWidget();
	}
	
	@Override
	public WidgetWrapper getWidget(String widgetName) {
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
