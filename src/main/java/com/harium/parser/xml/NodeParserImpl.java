package com.harium.parser.xml;

import org.xml.sax.SAXException;

public abstract class NodeParserImpl<T> implements NodeParser<T> {

	protected String name;

	public NodeParserImpl(String name) {
		this.name = name;
	}

	@Override
	public void characters(char[] ch, int start, int length, XMLParser parser, T output)
			throws SAXException {
	}

	public String getName() {
		return name;
	}
}
