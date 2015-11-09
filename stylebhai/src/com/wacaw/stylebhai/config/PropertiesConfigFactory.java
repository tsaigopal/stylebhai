package com.wacaw.stylebhai.config;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;

import com.wacaw.stylebhai.core.StylerException;
import com.wacaw.stylebhai.util.MiscUtils;

/**
 * Factory implementation for creating WidgetConfig from properties file.
 * 
 * @author saigopal
 */
public class PropertiesConfigFactory implements WidgetConfigFactory {

	@Override
	public boolean canCreate(Class<?> clazz) {
		InputStream stream = getStream(clazz);
		return stream != null;
	}

	private InputStream getStream(Class<?> clazz) {
		String file = "/" + clazz.getName().replace('.', '/') + ".properties";
		InputStream stream = this.getClass().getResourceAsStream(file);
		return stream;
	}

	@Override
	public WidgetConfig getConfig(Class<?> className) {
		InputStream stream = getStream(className);
		Scanner scanner = new Scanner(stream);
		WidgetConfig root = new WidgetConfig();
		Map<String, WidgetConfig> parentMap = new HashMap<String, WidgetConfig>();
		try {
			while (scanner.hasNextLine()) {
				String str = scanner.nextLine();
				if (str.trim().startsWith("#")) { // comments
					continue;
				}
				int equals = str.indexOf("=");
				String id = str.substring(0, equals);
				WidgetConfig current = null;
				if (id.equals("this")) {
					current = root;
				} else {
					current = new WidgetConfig();
				}
				current.setId(id);
				parentMap.put(id, current);
				try {
					Map<String, String> propertyMap = parseStyles(str
							.substring(equals + 1));
					for (Entry<String, String> entry : propertyMap.entrySet()) {
						if (entry.getKey().equals("type")) {
							current.setType(entry.getValue());
						} else if (entry.getKey().equals("style")) {
							current.setStyles(entry.getValue()); //todo parse
						} else {
							current.getProperties().put(entry.getKey(), entry.getValue());
						}
					}
					if (!"this".equals(id)) {
						String parentKey = propertyMap.get("parent");
						WidgetConfig parent = root;
						if (parentKey != null) {
							parent = parentMap.get(parentKey);
						}
						parent.getChildren().add(current);
					}
				} catch (RuntimeException e) {
					throw new StylerException("Error parsing styles for :"
							+ id, e);
				}
			}
		} finally {
			scanner.close();
		}
		return root;
	}
	
	protected Map<String, String> parseStyles(String value) {
		Map<String, String> styles = new HashMap<String, String>();
		if (!MiscUtils.isEmptyString(value)) {
			for (String style : value.split(";")) {
				String[] kvPair = style.split(":");
				styles.put(kvPair[0].trim(), kvPair[1].trim());
			}
		}
		return styles;
	}
}
