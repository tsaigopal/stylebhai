package com.wacaw.stylebhai.event;

/**
 * Interface to be implemented by classes which want to listen to Windows XP/7 session state change.
 * This is an example of what kind of interface support can be added dynamically to an screen.
 * 
 * @author saigopal
 * @deprecated
 */
public interface WindowsSessionListener {
	/**
	 * User has locked the session.
	 */
	void sessionLocked();
	/**
	 * User has unlocked the session.
	 */
	void sessionUnLocked();
	/**
	 * Retuns the HWND value on which the session event listerner would be installed.
	 * @return
	 */
	int getHandleToRegister();
	/**
	 * Session is about to end.
	 */
	void sessionEnding();
}
