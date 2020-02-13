package com.harium.parser.xml;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

public class TextParser implements NodeParser<Object> {

	private String name;
	private String value;
	private StringBuilder buffer;

	private OnCloseListener listener;

	public TextParser(String name) {
		this.name = name;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public void startElement(String uri, String localName, String qName, Attributes attributes,
			XMLParser model, Object output) throws SAXException {
		buffer = new StringBuilder();
	}

	@Override
	public void characters(char[] ch, int start, int length, XMLParser model, Object output)
			throws SAXException {
		String content = new String(ch, start, length);
		buffer.append(content);
	}

	@Override
	public void endElement(String uri, String localName, String qName, XMLParser model, Object output)
			throws SAXException {
		if (qName.equals(name)) {
			value = buffer.toString();
			listener.onClose();
			model.popStack();
		}
	}

	public String getValue() {
		return value;
	}

	public TextParser setOnCloseListener(OnCloseListener listener) {
		this.listener = listener;
		return this;
	}
}
