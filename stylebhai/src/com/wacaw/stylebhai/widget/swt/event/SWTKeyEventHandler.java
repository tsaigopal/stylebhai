package com.wacaw.stylebhai.widget.swt.event;

import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Text;

public class SWTKeyEventHandler extends AbstractEventListener {
	
	public SWTKeyEventHandler(int eventType) {
		super(eventType);
	}

	@Override
	public void handleEvent(Event event) {
		com.wacaw.stylebhai.event.StylerEvent stylerEvent = new com.wacaw.stylebhai.event.StylerEvent();
		stylerEvent.setData(((Text)event.widget).getText());
		handler.handleEvent(stylerEvent);
	}
}
