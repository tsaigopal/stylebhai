package com.wacaw.stylebhai.config;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.wacaw.stylebhai.event.EventListener;

/**
 * Represents a free style configuration of a widget, to be used for creating Widgets in a screen.
 * 
 * @author saigopal
 * @see WidgetConfigFactory
 */
public class WidgetConfig {
	/**
	 * Id of the widget, to be used in widget map and event listeners.
	 * @see EventListener
	 */
	private String id;
	/**
	 * Type of the widget. Usually class name.
	 */
	private String type;
	/**
	 * Styles to be applied to the widget
	 */
	private String styles;
	/**
	 * Other initializing properties, which are usually call as setters.
	 */
	private Map<String, String> properties = new HashMap<String, String>();
	
	/**
	 * Represents additional data provided to the config.
	 * 
	 */
	private String data;
	/**
	 * Configuration for the child widget, creating a tree of widgets (just like in any GUI framework)
	 */
	private List<WidgetConfig> children = new ArrayList<WidgetConfig>();

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getStyles() {
		return styles;
	}

	public void setStyles(String styles) {
		this.styles = styles;
	}

	public Map<String, String> getProperties() {
		return properties;
	}

	public void setProperties(Map<String, String> properties) {
		this.properties = properties;
	}

	public List<WidgetConfig> getChildren() {
		return children;
	}

	public void setChildren(List<WidgetConfig> children) {
		this.children = children;
	}
	
	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	/**
	 * Returns the first child of the type, returns null if not found.
	 * To be used when only one child of that type is expected.
	 * 
	 * @param type {@link WidgetConfig}.type
	 * @return configuration for the child with type as input, null if not found.
	 */
	public WidgetConfig getChild(String type) {
		for (WidgetConfig config: children) {
			if (type.equals(config.type)) {
				return config;
			}
		}
		return null;
	}
	
	/**
	 * Returns all child widget configs of a given type, empty list if not found
	 * @param type {@link WidgetConfig}.type
	 * @return list of children having type as input, empty list if not found
	 */
	public List<WidgetConfig> getChildrenByType(String type) {
		List<WidgetConfig> result = new ArrayList<WidgetConfig>();
		for (WidgetConfig config: children) {
			if (type.equals(config.type)) {
				result.add(config);
			}
		}
		return result;
	}

	@Override
	public String toString() {
		return "WidgetConfig [id=" + id + ", type=" + type + ", styles="
				+ styles + ", properties=" + properties + ", data=" + data
				+ ", children=" + children + "]";
	}
}
