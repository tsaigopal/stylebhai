package com.wacaw.stylebhai.widget.swt;

import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Control;

public class SWTButton extends SWTWidget implements com.wacaw.stylebhai.widget.Button {

	public SWTButton(Control control) {
		super(control);
	}

	@Override
	public void setText(String text) {
		getNativeWidget().setText(text);
	}

	@Override
	public String getText() {
		return getNativeWidget().getText();
	}
	
	@Override
	public Button getNativeWidget() {
		return (Button)super.getNativeWidget();
	}

	@Override
	public boolean getSelection() {
		return getNativeWidget().getSelection();
	}
	
	public void setSelection(boolean selected) {
		getNativeWidget().setSelection(selected);
	}
}
