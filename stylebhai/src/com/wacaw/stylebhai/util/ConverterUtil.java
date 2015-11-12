package com.wacaw.stylebhai.util;
import java.lang.reflect.Constructor;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Layout;

import com.wacaw.stylebhai.core.StylerException;

/**
 * Utility class for converting String to a type, generally used for applying style to a widget. 
 * 
 * It uses a registry of converters, where more converters can be registered through {@link #registerConverter(Class, Converter)}.
 * It provides the default implementation for Integer, String, Boolean, {@link Point} & {@link Image}
 * @author saigopal
 * @see Converter
 */
public class ConverterUtil {
public static final String DATE_FORMAT = "MM/dd/yyyy";
private static HashMap<Class<?>, Converter> converters = new HashMap<Class<?>, Converter>();
	
	static {
		converters.put(String.class, new Converter() {
			@Override
			public Object convert(String str) {
				return str;
			}
			@Override
			public String convertBack(Object input) {
				return (String) input;
			}
		});
		Converter boolConverter = new Converter() {
			@Override
			public Object convert(String str) {
				return Boolean.valueOf(str);
			}
			@Override
			public String convertBack(Object input) {
				return String.valueOf((Boolean) input);
			}
		};
		converters.put(Boolean.TYPE, boolConverter);
		converters.put(Boolean.class, boolConverter);
		Converter integerConverter = new Converter() {
			@Override
			public Object convert(String str) {
				return Integer.parseInt(str);
			}
			@Override
			public String convertBack(Object input) {
				return String.valueOf(input);
			}
		};
		converters.put(Integer.TYPE, integerConverter);
		converters.put(Integer.class, integerConverter);

		Converter floatConverter = new Converter() {
			@Override
			public Object convert(String str) {
				return Float.parseFloat(str);
			}
			@Override
			public String convertBack(Object input) {
				return String.valueOf(input);
			}
		};
		converters.put(Float.TYPE, floatConverter);
		converters.put(Float.class, floatConverter);
		
		Converter doubleConverter = new Converter() {
			@Override
			public Object convert(String str) {
				return Double.parseDouble(str);
			}
			@Override
			public String convertBack(Object input) {
				return String.valueOf(input);
			}
		};
		converters.put(Double.TYPE, doubleConverter);
		converters.put(Double.class, doubleConverter);
		
		converters.put(Point.class, new Converter() {
			@Override
			public Object convert(String text) {
				String str = text;
				str = str.trim().substring(1, str.length() - 1); // remove square brackets
				int inx = str.indexOf(',');
				return new Point(Integer.parseInt(str.substring(0, inx).trim()), Integer.parseInt(str.substring(inx+1).trim()));
			}
			@Override
			public String convertBack(Object input) {
				throw new StylerException("not supported");
			}
		});
		converters.put(Image.class, new Converter() {
			@Override
			public Object convert(String str) {
				return new Image(Display.getDefault(), this.getClass().getResourceAsStream("/"+str));
			}
			@Override
			public String convertBack(Object input) {
				throw new StylerException("not supported");
			}
		});
		converters.put(Date.class, new Converter() {
			@Override
			public Object convert(String str) {
				try {
					return new SimpleDateFormat(DATE_FORMAT).parse(str);
				} catch (ParseException e) {
					throw new StylerException(e);
				}
			}
			@Override
			public String convertBack(Object input) {
				return new SimpleDateFormat(DATE_FORMAT).format((Date)input);
			}
		});
		
		converters.put(Layout.class, new Converter() {
			
			@Override
			public String convertBack(Object input) {
				throw new StylerException("not supported");
			}
			
			@Override
			public Object convert(String str) {
				String packageName = "org.eclipse.swt.layout.";
				return resolveConstructor(str, packageName);
			}
		});
		converters.put(Color.class, new Converter() {
			
			@Override
			public String convertBack(Object input) {
				throw new StylerException("not supported");
			}
			
			@Override
			public Object convert(String str) {
				return null;
			}
		});
		
		converters.put(Object.class, new Converter() {
			
			@Override
			public String convertBack(Object input) {
				throw new StylerException("not supported");
			}
			
			@Override
			public Object convert(String str) {
				if (str.indexOf('(') > 0) {
					String packageName = "org.eclipse.swt.layout.";
					return resolveConstructor(str, packageName);
				} else {
					return str;
				}
			}
		});
	}
	
	/**
	 * Converts the input string to the desired type.
	 * @param value input string 
	 * @param type desired type
	 * @return converted value into the desired type
	 */
	public static Object convert(String value, Class<?> type) {
		return findConverter(type).convert(value);
	}
	
	/**
	 * Converts the input string to the desired type.
	 * @param value input string 
	 * @param type desired type
	 * @return converted value into the desired type
	 */
	public static String convertBack(Object value, Class<?> type) {
		Converter converter = findConverter(type);
		return converter.convertBack(value);
	}

	private static Converter findConverter(Class<?> type) {
		Class<?> check = type;
		Converter c = converters.get(check);
		while (check.getSuperclass() != null && c == null) {
			check = type.getSuperclass();
			c = converters.get(check);
		}
		return c;
	}
	
	protected static Object resolveConstructor(String str, String packageName) {
		int bracket = str.indexOf('(');
		String layoutClassName = str;
		String[] constructorArgs = new String[0];
		if (bracket > 0) {
			layoutClassName = str.substring(0, bracket);
			String argText = str.substring(bracket+1, str.length()-1);
			if (argText.trim().length() > 0) {
				constructorArgs = argText.split(",");
			}
		}
		
		try {
			Class<?> layoutClass = Class.forName(packageName + layoutClassName);
			Constructor<?> constructor = null;
			for (Constructor<?> c : layoutClass.getConstructors()) {
				if (c.getParameterTypes().length == constructorArgs.length) {
					constructor = c;
					break;
				}
			}
			
			Object[] params = new Object[constructor.getParameterTypes().length];
			for (int i=0; i < constructor.getParameterTypes().length ; i++) {
				params[i] = ConverterUtil.convert(constructorArgs[i], constructor.getParameterTypes()[i]);
			}
			return constructor.newInstance(params);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * Register a type converter.
	 * @param type class type
	 * @param converter converter
	 */
	public void registerConverter(Class<?> type, Converter converter) {
		converters.put(type, converter);
	}
}
