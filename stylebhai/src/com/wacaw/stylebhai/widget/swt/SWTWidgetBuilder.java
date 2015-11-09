package com.wacaw.stylebhai.widget.swt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.stereotype.Component;

import com.wacaw.stylebhai.core.AbstractScreen;
import com.wacaw.stylebhai.widget.MDIWindow;
import com.wacaw.stylebhai.widget.WidgetBuilder;

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
}
