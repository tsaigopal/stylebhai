package com.wacaw.stylebhai.widget.swt.event;

import java.lang.reflect.InvocationTargetException;

import org.eclipse.swt.widgets.Listener;

import com.wacaw.stylebhai.core.StylerException;
import com.wacaw.stylebhai.event.EventHandler;

public abstract class AbstractEventListener implements Listener {
	public AbstractEventListener(int eventType) {
		this.eventType = eventType;
	}
	
	protected EventHandler handler;

	private int eventType;

	public EventHandler getHandler() {
		return handler;
	}

	public void setHandler(EventHandler handler) {
		this.handler = handler;
	}

	public int getEventTyp() {
		return eventType;
	}

	public void setEventTyp(int eventTyp) {
		this.eventType = eventTyp;
	}
	
	public AbstractEventListener clone(EventHandler handler) {
		try {
			AbstractEventListener listener = (AbstractEventListener) this.getClass().getConstructors()[0].newInstance(this.eventType);
			listener.handler = handler;
			return listener;
		} catch (InstantiationException | IllegalAccessException
				| IllegalArgumentException | InvocationTargetException
				| SecurityException e) {
			throw new StylerException(e);
		}
	}
}
