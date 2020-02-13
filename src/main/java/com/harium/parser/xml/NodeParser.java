package com.harium.parser.xml;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

public interface NodeParser<T> {

	String getName();

	void startElement(String uri, String localName, String qName, Attributes attributes,
			XMLParser parser, T output) throws SAXException;

	void characters(char[] ch, int start, int length, XMLParser parser, T output)
			throws SAXException;

	void endElement(String uri, String localName, String qName, XMLParser parser, T output)
			throws SAXException;

}
