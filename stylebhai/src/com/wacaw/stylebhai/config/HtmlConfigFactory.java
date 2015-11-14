package com.wacaw.stylebhai.config;

import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;

import org.xml.sax.EntityResolver;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public class HtmlConfigFactory extends XMLConfigFactory {
	@Override
	protected void initDocBuilder(DocumentBuilder docBuilder) {
		docBuilder.setEntityResolver(new EntityResolver() {
		    @Override
		    public InputSource resolveEntity(String publicId, String systemId)
		            throws SAXException, IOException {
		        if (systemId.contains("styler-html.dtd")) {
		            return new InputSource(this.getClass().getResourceAsStream("styler-html.dtd"));
		        } else {
		            return null;
		        }
		    }
		});
	}
	
	@Override
	protected String getFileExtension() {
		return "html";
	}
	
	@Override
	public WidgetConfig getConfig(Class<?> className) {
		WidgetConfig htmlConfig = super.getConfig(className);
		return getXMLConfig(htmlConfig);
	}

	private WidgetConfig getXMLConfig(WidgetConfig htmlConfig) {
		WidgetConfig xmlConfig = new WidgetConfig();
		xmlConfig.setId(htmlConfig.getId());
		
		if (htmlConfig.getType().equals("div")) {
			buildDivConfig(htmlConfig, xmlConfig);
		} else if (htmlConfig.getType().equals("table")) {
			buildTableConfig(htmlConfig, xmlConfig);
		} else if (htmlConfig.getType().equals("input")) {
			buildInputConfig(htmlConfig, xmlConfig);
		} else if (htmlConfig.getType().equals("grid")) {
			buildGridConfig(htmlConfig, xmlConfig);
		}
		return xmlConfig;
	}

	private void buildGridConfig(WidgetConfig htmlConfig, WidgetConfig xmlConfig) {
		xmlConfig.setType("Table");
		xmlConfig.setStyles("FULL_SELECTION");
		xmlConfig.getProperties().put("headerVisible", "true");
		xmlConfig.getProperties().put("linesVisible", "true");
		
		for(WidgetConfig column : htmlConfig.getChildrenByType("column")) {
			column.setType("TableColumn");
		}
		xmlConfig.setChildren(htmlConfig.getChildren());
	}

	private void buildInputConfig(WidgetConfig htmlConfig,
			WidgetConfig xmlConfig) {
		String type = htmlConfig.getProperties().get("type");
		if (type.equals("text")) {
			xmlConfig.setType("Text");
			xmlConfig.setStyles("LEFT,BORDER");
		} else if (type.equals("date")) {
			xmlConfig.setType("DateTime");
			xmlConfig.setStyles("DATE,DROP_DOWN");
		} else if (type.equals("check")) {
			xmlConfig.setType("Button");
			xmlConfig.setStyles("CHECK");
		} else if (type.equals("radio")) {
			xmlConfig.setType("Button");
			xmlConfig.setStyles("RADIO");
		} else if (type.equals("button")) {
			xmlConfig.setType("Button");
			xmlConfig.setStyles("PUSH");
			xmlConfig.getProperties().put("text", htmlConfig.getProperties().get("value"));
		}
	}

	private void buildDivConfig(WidgetConfig htmlConfig, WidgetConfig xmlConfig) {
		xmlConfig.setType("Composite");
		xmlConfig.getProperties().put("layout", "FillLayout()");
		for (WidgetConfig child : htmlConfig.getChildren()) {
			xmlConfig.getChildren().add(getXMLConfig(child));
		}
	}
	
	public void buildTableConfig(WidgetConfig htmlConfig, WidgetConfig xmlConfig) {
		xmlConfig.setType("Composite");
		int columns = 1;
		for (WidgetConfig tr : htmlConfig.getChildrenByType("tr")) {
			columns = Math.max(columns, tr.getChildrenByType("td").size());
		}
		
		xmlConfig.getProperties().put("layout", "GridLayout(" + columns + ",false)");
		
		for (WidgetConfig tr : htmlConfig.getChildrenByType("tr")) {
			int thisRowColumns = 0;
			for (WidgetConfig td : tr.getChildrenByType("td")) {
				thisRowColumns++;
				WidgetConfig child;
				if (td.getData() != null || td.getChildren().size() == 0) {
					child = new WidgetConfig();
					child.setType("Label");
					child.setId(td.getId());
					if(td.getData() != null) {
						child.getProperties().put("text", td.getData());
					}
				} else {
					if (td.getChildren().size() == 1) {
						child = getXMLConfig(td.getChildren().get(0));
					} else {
						child = new WidgetConfig();
						child.setType("Composite");
						for (WidgetConfig element : td.getChildren()) {
							child.getChildren().add(getXMLConfig(element));
						}
					}
				}
				if (child != null) {
					xmlConfig.getChildren().add(child);
				}
				if (td.getProperties().get("colspan") != null) {
					int colSpan = Integer.parseInt(td.getProperties().get("colspan"));
					thisRowColumns += (colSpan-1);
					child.getProperties().put("layoutData", "GridData(4,4,true,true," + colSpan + ",1)");
				}
			}
			for (;thisRowColumns < columns; thisRowColumns++) {
				WidgetConfig label = new WidgetConfig();
				label.setType("Label");
				label.getProperties().put("text", "");
				xmlConfig.getChildren().add(label);
			}
		}
	}
}
