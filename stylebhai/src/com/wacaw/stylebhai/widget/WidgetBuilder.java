package com.wacaw.stylebhai.widget;

import java.util.ArrayList;
import java.util.List;

import com.wacaw.stylebhai.config.PropertiesConfigFactory;
import com.wacaw.stylebhai.config.WidgetConfig;
import com.wacaw.stylebhai.config.WidgetConfigFactory;
import com.wacaw.stylebhai.config.XMLConfigFactory;
import com.wacaw.stylebhai.core.AbstractScreen;
import com.wacaw.stylebhai.core.StylerException;
import com.wacaw.stylebhai.widget.swt.SWTWidgetBuilder;

/**
 * Abstract Builder for creating UI Widgets, providing starting point of a UI application.
 * 
 * @author saigopal
 *
 */
public abstract class WidgetBuilder {
	public static List<WidgetConfigFactory> configBuilders = new ArrayList<>();
	static {
		configBuilders.add(new XMLConfigFactory());
		configBuilders.add(new PropertiesConfigFactory());
	}
	
	/**
	 * Returns the WidgetBuilder instance for SWT.
	 * 
	 * @param screenClass
	 * @return
	 */
	public abstract MDIWindow createMDIWindow(Class<? extends AbstractScreen> screenClass);

	public abstract Object createDialog(Class<? extends AbstractScreen> screenClass);
	
//	public abstract Object openDialog(Class<? extends AbstractScreen> screenClass);

	public static WidgetConfig getConfig(Class<?> config) {
		for (WidgetConfigFactory builder : WidgetBuilder.configBuilders) {
			if (builder.canCreate(config)) {
				return builder.getConfig(config);
			}
		}
		throw new StylerException("No builder or no config found for class: " + config.getName());
	}

	public static WidgetBuilder getSWTWidgetBuilder() {
		return new SWTWidgetBuilder();
	}
}