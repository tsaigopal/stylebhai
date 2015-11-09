package com.wacaw.stylebhai.widget;

import java.util.Date;

/**
 * Widget Class for a Date/Time field.
 * 
 * @author saigopal
 */
public interface DateField extends WidgetWrapper {
	public void setDate(Date date);
	public Date getDate();
}
