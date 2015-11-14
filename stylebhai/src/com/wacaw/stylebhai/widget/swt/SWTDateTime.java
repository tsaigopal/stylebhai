package com.wacaw.stylebhai.widget.swt;

import java.util.Calendar;
import java.util.Date;

import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.DateTime;

import com.wacaw.stylebhai.widget.DateField;

public class SWTDateTime extends SWTWidget implements DateField {

	public SWTDateTime(Control control) {
		super(control);
	}

	@Override
	public void setDate(Date date) {
		Calendar calendar = Calendar.getInstance();
		if (date != null) {
			calendar.setTime(date);
		}
		getNativeWidget().setDate(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
	}

	@Override
	public Date getDate() {
		Calendar calendar = Calendar.getInstance();
		calendar.clear();
		calendar.set(Calendar.DATE, getNativeWidget().getDay());
		calendar.set(Calendar.MONTH, getNativeWidget().getMonth());
		calendar.set(Calendar.YEAR, getNativeWidget().getYear());
		return calendar.getTime();
	}
	
	@Override
	public DateTime getNativeWidget() {
		return (DateTime)super.getNativeWidget();
	}

	@Override
	public Object getValue() {
		return getDate();
	}
	
	@Override
	public void setValue(Object value) {
		setDate((Date) value);
	}
	
	@Override
	public Class<?> getSupportedType() {
		return Date.class;
	}
}
