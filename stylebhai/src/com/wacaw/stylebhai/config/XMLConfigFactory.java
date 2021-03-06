package com.wacaw.stylebhai.config;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

import com.wacaw.stylebhai.core.StylerException;

/**
 * Factory class for creating {@link WidgetConfig} from xml files.
 * It searches the xml file with the same class name and package.
 * 
 * The file extension is XML by default. If another implementation is required, then {@link #getFileExtension()} can be used
 * to change and iherit the behaviour of this class.
 * 
 * @author saigopal
 */
public class XMLConfigFactory implements WidgetConfigFactory {

	private DocumentBuilder docBuilder;

	public XMLConfigFactory() {
		try {
			docBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
		} catch (ParserConfigurationException e) {
			throw new StylerException("Error creating xml parser");
		}
		initDocBuilder(docBuilder);
	}
	
	protected void initDocBuilder(DocumentBuilder docBuilder) {
		
	}

	@Override
	public boolean canCreate(Class<?> clazz) {
		InputStream stream = getStream(clazz);
		return stream != null;
	}

	private InputStream getStream(Class<?> clazz) {
		String file = "/" + clazz.getName().replace('.', '/') + "." + getFileExtension();
		InputStream stream = this.getClass().getResourceAsStream(file);
		return stream;
	}
	
	protected String getFileExtension() {
		return "xml";
	}

	@Override
	public WidgetConfig getConfig(Class<?> className) {
		InputStream stream = getStream(className);
		Document doc = null;
		try {
			doc = docBuilder.parse(stream);
		} catch (Exception e) {
			throw new StylerException(e);
		}
		return createConfig(doc.getDocumentElement(), new HashMap<String, Integer>());
	}

	private WidgetConfig createConfig(Element documentElement, Map<String, Integer> widgetCounters) {
		WidgetConfig config = new WidgetConfig();
		config.setType(documentElement.getNodeName());
		Map<String, String> attrs = getAttributes(documentElement);
		String id = getOrGenerateId(attrs.remove("id"), widgetCounters, config.getType());
		config.setId(id);
		config.setStyles(attrs.remove("style"));
		config.setProperties(attrs);
		for (int i=0; i<documentElement.getChildNodes().getLength();i++) {
			Node n = documentElement.getChildNodes().item(i);
			if (n instanceof Element) {
				config.getChildren().add(createConfig((Element) n, widgetCounters));
			}
			if (n.getNodeType() == Node.TEXT_NODE && n.getTextContent().trim().length() > 0) {
				config.setData(n.getTextContent());
			}
		}
		return config;
	}
	
	private String getOrGenerateId(String id,
			Map<String, Integer> widgetCounters, String type) {
		if (id != null) {
			return id;
		}
		Integer counter = widgetCounters.get(type);
		if (counter == null) counter = 1;
		String result = type + "_" + counter;
		counter++;
		widgetCounters.put(type, counter);
		return result;
	}

	public Map<String, String> getAttributes(Element elm) {
		Map<String, String> map = new HashMap<String, String>();
		for (int i=0; i<attrs(elm).getLength(); i++) {
			String attr = attrs(elm).item(i).getNodeName();
			map.put(attr, elm.getAttribute(attr));
		}
		return map;
	}

	private NamedNodeMap attrs(Element elm) {
		return elm.getAttributes();
	}
}
