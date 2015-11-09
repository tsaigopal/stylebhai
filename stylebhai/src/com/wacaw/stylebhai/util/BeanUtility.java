package com.wacaw.stylebhai.util;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.NestedNullException;
import org.apache.commons.beanutils.PropertyUtils;

import com.wacaw.stylebhai.core.StylerException;


/**
 * Utility class for working with java beans.
 * 
 * @author saigopal
 */
public class BeanUtility {
	/**
	 * Returns the property value.
	 * @param input input object
	 * @param propertyName property name
	 * @return value of the property
	 */
	public static Object getProperty(Object input, String propertyName) {
		try {
			if (input instanceof Map<?, ?>) {
				Map<?, ?> map = (Map<?, ?>) input;
				return map.get(propertyName);
			} if (input.getClass().isArray()) {
				Object[] arr = (Object[]) input;
				return arr[Integer.parseInt(propertyName)];
			}
			return PropertyUtils.getProperty(input, propertyName);
//			PropertyDescriptor pd = new PropertyDescriptor(propertyName, input.getClass());
//			return pd.getReadMethod().invoke(input);
		} catch (NestedNullException ne) {
			return null;
		}
		catch (Exception e) {
			throw new StylerException(e);
		}
	}
	
	public static String getPropertyAsString(Object input, String propertyName) {
		Object value = getProperty(input, propertyName);
		if (value == null) return "";
		return ConverterUtil.convertBack(value, value.getClass());
	}
	
	/**
	 * Sets the property value of a bean.
	 * @param input input bean
	 * @param propertyName property name
	 * @param value value of the property to be set
	 * @throws Exception any exception thrown while setting the property
	 */
	public static void setProperty(Object input, String propertyName, Object value) {
//		PropertyDescriptor pd = new PropertyDescriptor(propertyName, input.getClass());
//		pd.getWriteMethod().invoke(input, value);
		try {
			PropertyUtils.setProperty(input, propertyName, value);
		} catch (IllegalAccessException | InvocationTargetException
				| NoSuchMethodException e) {
			throw new StylerException(e);
		}
	}
	
	/**
	 * Sets the property value of a bean after converting the input string using {@link ConverterUtil}
	 * 
	 * @param input input bean
	 * @param propertyName property name
	 * @param value string value of the property
	 * @throws Exception any exception thrown while setting the property
	 */
	public static void setPropertyString(Object input, String propertyName, String value) {
		PropertyDescriptor pd;
		try {
			pd = new PropertyDescriptor(propertyName, input.getClass());
		} catch (IntrospectionException e) {
			throw new StylerException(e);
		}
		setProperty(input, propertyName, ConverterUtil.convert(value, pd.getPropertyType()));
		
	}
	
	/**
	 * Gets the declared field either in the input class or all super classes.
	 * 
	 * @param c input class
	 * @param field field name
	 * @return Field object
	 */
	public static Field getField(Class<?> c, String field) {
		Field f = null;
		Class<?> current = c;
		while (f == null && current != null) {
			try {
				f = current.getDeclaredField(field);
				f.setAccessible(true);
			} catch (Exception ignore) {
			}
			current = current.getSuperclass();
		}
		return f;
	}
	
	/**
	 * Returns all fields defined in the input class and its all super classes.
	 * 
	 * @param c input class
	 * @return List of fields in the whole class hierarcy.
	 */
	public static List<Field> getFields(Class<?> c) {
		List<Field> fields = new ArrayList<Field>();
		Class<?> current = c;
		while (current != null) {
			try {
				fields.addAll(Arrays.asList(current.getDeclaredFields()));
				current = current.getSuperclass();
			} catch (Exception ignore) {
			}
		}
		return fields;
	}
}
