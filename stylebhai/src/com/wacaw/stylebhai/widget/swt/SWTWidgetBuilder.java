package com.wacaw.stylebhai.widget.swt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.stereotype.Component;

import com.wacaw.stylebhai.core.AbstractScreen;
import com.wacaw.stylebhai.event.EventHandler;
import com.wacaw.stylebhai.event.UIEvent;
import com.wacaw.stylebhai.widget.MDIWindow;
import com.wacaw.stylebhai.widget.WidgetBuilder;
import com.wacaw.stylebhai.widget.WidgetWrapper;
import com.wacaw.stylebhai.widget.Window;

/**
 * Class for creating shells with externalized styles and an elegant event
 * handling.
 * 
 * @author saigopal
 *
 */
@Component
public class SWTWidgetBuilder extends WidgetBuilder {
	
	@Autowired
	private AutowireCapableBeanFactory factory;
	
	@Override
	public MDIWindow createMDIWindow(Class<? extends AbstractScreen> screenClass) {
		return new SWTMDIWindow(screenClass, factory);
	}

	@Override
	public Object createDialog(final Class<? extends AbstractScreen> screenClass) {
		return new SWTDialog(screenClass, factory).getReturnValue();
	}

	@Override
	public Window createWindow(final Class<? extends AbstractScreen> screenClass) {
		return new SWTShellWindow(screenClass, factory);
	}

	@Override
	public void addListener(WidgetWrapper widget, UIEvent event,
			EventHandler handler) {
		SWTWidgetUtils.addListener(widget, event, handler);
	}
}
