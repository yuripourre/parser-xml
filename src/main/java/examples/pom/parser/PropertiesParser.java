package examples.pom.parser;

import com.harium.parser.xml.NodeParserImpl;
import com.harium.parser.xml.XMLParser;
import examples.pom.model.Pom;
import examples.pom.model.Property;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

public class PropertiesParser extends NodeParserImpl<Pom> {

	private String key = "";
	private StringBuilder buffer;

	public PropertiesParser() {
		super("properties");
	}

	@Override
	public void startElement(String uri, String localName, String qName, Attributes attributes,
			XMLParser parser, Pom output)
			throws SAXException {
		if (!name.equals(qName)) {
			key = qName;
			buffer = new StringBuilder();
		}
	}

	@Override
	public void characters(char[] ch, int start, int length, XMLParser parser, Pom output)
			throws SAXException {
		if (buffer == null) {
			return;
		}
		String content = new String(ch, start, length);
		buffer.append(content);
	}

	@Override
	public void endElement(String uri, String localName, String qName, XMLParser parser, Pom output)
			throws SAXException {
		if (name.equals(qName)) {
			parser.popStack();
		} else {
			if (!key.isEmpty()) {
				output.addProperty(new Property(key, buffer.toString()));
			}
		}
	}

}