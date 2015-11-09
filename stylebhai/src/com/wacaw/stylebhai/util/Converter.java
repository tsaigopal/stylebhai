package com.wacaw.stylebhai.util;
/**
 * Interface for implementing a type converter.
 * 
 * @author saigopal
 * @see ConverterUtil
 */
public interface Converter {
	/**
	 * Converts the input str to a specific type.
	 * @param str input string
	 * @return converted value.
	 */
	Object convert(String str);
	
	String convertBack(Object input);
}
