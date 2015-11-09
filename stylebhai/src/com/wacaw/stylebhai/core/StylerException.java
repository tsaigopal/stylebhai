package com.wacaw.stylebhai.core;

/**
 * Exception class raised by the styler framework
 * @author saigopal
 */
public class StylerException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4778370436239678754L;

	public StylerException(String message) {
		super(message);
	}

	public StylerException(Throwable cause) {
		super(cause);
	}

	public StylerException(String message, Throwable cause) {
		super(message, cause);
	}

	public StylerException(String message, Throwable cause, Object...msgParams) {
		super(message, cause);
	}
}
