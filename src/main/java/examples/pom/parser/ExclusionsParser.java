package examples.pom.parser;

import com.harium.parser.xml.NodeParserImpl;
import com.harium.parser.xml.TextParser;
import com.harium.parser.xml.XMLParser;
import examples.pom.model.Dependency;
import examples.pom.model.Pom;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

public class ExclusionsParser extends NodeParserImpl<Pom> {

	public ExclusionsParser() {
		super("exclusions");
	}

	@Override
	public void startElement(String uri, String localName, String qName, Attributes attributes,
			XMLParser parser, Pom output)
			throws SAXException {
		if (name.equals(qName)) {
			return;
		}
	}

	@Override
	public void characters(char[] ch, int start, int length, XMLParser parser, Pom output)
			throws SAXException {

	}

	@Override
	public void endElement(String uri, String localName, String qName, XMLParser parser, Pom output)
			throws SAXException {
		if (name.equals(qName)) {
			parser.popStack();
		}
	}

}
