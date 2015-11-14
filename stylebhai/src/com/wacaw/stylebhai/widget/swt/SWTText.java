package com.wacaw.stylebhai.widget.swt;

import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

import com.wacaw.stylebhai.core.StylerException;
import com.wacaw.stylebhai.widget.TextBox;

public class SWTText extends SWTWidget implements TextBox {

	public SWTText(Control control) {
		super(control);
	}

	@Override
	public void setText(String text) {
		text = (text == null) ? "" : text;
		if (getNativeWidget() instanceof Text) {
			Text textBox = (Text) getNativeWidget();
			textBox.setText(text);
		} else if (getNativeWidget() instanceof Label) {
			Label label = (Label) getNativeWidget();
			label.setText(text);
		} else {
			throw new StylerException("Unsupported operation");
		}
	}

	@Override
	public String getText() {
		if (getNativeWidget() instanceof Text) {
			Text textBox = (Text) getNativeWidget();
			return textBox.getText();
		} else if (getNativeWidget() instanceof Label) {
			Label label = (Label) getNativeWidget();
			return label.getText();
		} else {
			throw new StylerException("Unsupported operation");
		}
	}
	
	@Override
	public Object getValue() {
		return getText();
	}
	
	@Override
	public void setValue(Object value) {
		setText((String) value);
	}
	
	@Override
	public Class<?> getSupportedType() {
		return String.class;
	}
}
