package com.wacaw.stylebhai.widget.swt;

import org.eclipse.swt.widgets.Control;

import com.wacaw.stylebhai.widget.WidgetWrapper;

public class SWTWidget implements WidgetWrapper {
	
	private Control control;

	public SWTWidget(Control control) {
		this.control = control;
	}
	
	@Override
	public void setVisible(boolean visible) {
		control.setVisible(visible);
	}

	@Override
	public boolean isVisible() {
		return control.isVisible();
	}

	@Override
	public Object getNativeWidget() {
		return control;
	}
}
