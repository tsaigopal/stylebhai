package com.wacaw.stylebhai.widget.swt;

import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Widget;

import com.wacaw.stylebhai.widget.WidgetWrapper;

public class SWTWidget implements WidgetWrapper {
	
	private Widget control;

	public SWTWidget(Widget control) {
		this.control = control;
	}
	
	@Override
	public void setVisible(boolean visible) {
		if (control instanceof Control) {
			((Control)control).setVisible(visible);
		}
	}

	@Override
	public boolean isVisible() {
		if (control instanceof Control) {
			return ((Control)control).isVisible();
		}
		return false;
	}

	@Override
	public Object getNativeWidget() {
		return control;
	}

	@Override
	public Object getValue() {
		return null;
	}

	@Override
	public void setValue(Object value) {
	
	}
	
	@Override
	public Class<?> getSupportedType() {
		return Object.class;
	}
}
