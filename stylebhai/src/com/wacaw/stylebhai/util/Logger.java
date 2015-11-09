package com.wacaw.stylebhai.util;

import java.io.PrintStream;
import java.util.Date;

/**
 * Simple file logger for the application.
 * Methods {@link #init(PrintStream)} and {@link #close()} should be called at the start 
 * and end of the application to make sure the PrintWriter is initialized and closed respectively.
 * 
 * By default the logger writes to System.out. 
 * 
 * @author saigopal
 *
 */
public class Logger {
	private static PrintStream writer;
	static {
		writer = System.out;
	}
	
	public static void init(PrintStream writer) {
		Logger.writer = writer;
	}
	
	public static void log(String str, Exception...e) {
		writer.printf("%s %s\n", new Date(), str);
		if (e.length > 0)
			e[0].printStackTrace(writer);
	}
	
	public static void close() {
		writer.close();
	}
}
