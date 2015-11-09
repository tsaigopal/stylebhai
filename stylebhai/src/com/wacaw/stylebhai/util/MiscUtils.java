package com.wacaw.stylebhai.util;

import java.io.File;
import java.util.Calendar;
import java.util.Date;

import com.wacaw.stylebhai.core.StylerException;

/**
 * Utility class for various methods used in a UI application.
 * 
 * @author saigopal
 *
 */
public class MiscUtils {
	/**
	 * Return true if the input is null or contains blank spaces.
	 * 
	 * @param str input
	 * @return true if the input is null or contains blank spaces
	 */
	public static boolean isEmptyString(String str) {
		return str == null || str.trim().length() == 0;
	}

	/**
	 * Returns a Date object representing 00:00:00AM of the input date.
	 * 
	 * @param date input
	 * @return a Date object containing Time part as 00:00:00.000 AM.
	 */
	public static Date stripTime(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.clear();
		Calendar copy = Calendar.getInstance();
		copy.setTime(date);
		calendar.set(Calendar.DATE, calendar.get(Calendar.DATE));
		calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH));
		calendar.set(Calendar.YEAR, calendar.get(Calendar.YEAR));
		return calendar.getTime();
	}
	
	/**
	 * Returns absolute path of an executable by scanning system path. 
	 * Its used for getting path of an executables to launch using {@link Runtime#exec(String)}.
	 * 
	 * @param executable
	 * @return absolute path of a executable.
	 */
	public static String findExec(String executable) {
		String path = System.getenv("PATH");
		String[] directories = path.split(System.getProperty("path.separator"));
		for (String dir : directories) {
			String absolutePath = dir + "\\" + executable;
			if (new File(absolutePath).exists()) return absolutePath;
		}
		throw new StylerException("Executable :" + executable + " could not be found in the PATH. Please check you system settings");
	}	
}
