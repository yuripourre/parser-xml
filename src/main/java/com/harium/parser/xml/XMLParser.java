package com.harium.parser.xml;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class XMLParser<T> extends DefaultHandler {

	private NodeParser currentParser;

	private Stack<NodeParser> stack = new Stack<>();
	private Map<String, NodeParser> parsers = new HashMap<>();

	private SAXParser parser;
	private T output;

	public XMLParser(T output) throws ParserConfigurationException, SAXException {
		this.output = output;
		SAXParserFactory factory = SAXParserFactory.newInstance();
		parser = factory.newSAXParser();
	}

	public void add(NodeParser parser) {
		currentParser = parser;
		stack.add(parser);
	}

	public NodeParser popStack() {
		stack.pop();
		if (!stack.empty()) {
			currentParser = stack.lastElement();
		} else {
			currentParser = null;
		}
		return currentParser;
	}

	@Override
	public void startElement(String uri, String localName, String qName,
			Attributes attributes) throws SAXException {
		if (parsers.containsKey(qName)) {
			currentParser = parsers.get(qName);
			add(currentParser);
		}
		if (currentParser != null) {
			currentParser.startElement(uri, localName, qName, attributes, this, output);
		}
	}

	@Override
	public void characters(char[] ch, int start, int length)
			throws SAXException {
		if (currentParser != null) {
			currentParser.characters(ch, start, length, this, output);
		}
	}

	@Override
	public void endElement(String uri, String localName, String qName)
			throws SAXException {
		if (currentParser != null) {
			// TODO Remove from stack?
			currentParser.endElement(uri, localName, qName, this, output);
		}
	}

	public void register(NodeParser nodeParser) {
		parsers.put(nodeParser.getName(), nodeParser);
	}

	public void parse(File file) throws IOException, SAXException {
		parser.parse(file, this);
	}
}
