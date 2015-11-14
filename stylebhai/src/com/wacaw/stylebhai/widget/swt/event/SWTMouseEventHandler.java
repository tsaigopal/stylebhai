package com.wacaw.stylebhai.widget.swt.event;

import org.eclipse.swt.widgets.Event;

import com.wacaw.stylebhai.event.StylerEvent;

public class SWTMouseEventHandler extends AbstractEventListener {
	
	public SWTMouseEventHandler(int eventType) {
		super(eventType);
	}

	@Override
	public void handleEvent(Event event) {
		StylerEvent stylerEvent = new StylerEvent();
		handler.handleEvent(stylerEvent);
	}
}
