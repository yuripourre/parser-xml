package examples.pom.parser;

import com.harium.parser.xml.NodeParserImpl;
import com.harium.parser.xml.TextParser;
import com.harium.parser.xml.XMLParser;
import examples.pom.model.Dependency;
import examples.pom.model.Pom;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

public class DependencyParser extends NodeParserImpl<Pom> {

	public static final String ARTIFACT_ID = "artifactId";
	public static final String GROUP_ID = "groupId";
	public static final String VERSION = "version";

	private Dependency dependency;

	public DependencyParser() {
		super("dependency");
	}

	@Override
	public void startElement(String uri, String localName, String qName, Attributes attributes,
			XMLParser parser, Pom output)
			throws SAXException {
		if (name.equals(qName)) {
			dependency = new Dependency();
		} else if (GROUP_ID.equals(qName)) {
			TextParser textParser = new TextParser("groupId");
			textParser.setOnCloseListener(() -> dependency.setGroupId(textParser.getValue()));
			parser.add(textParser);
			textParser.startElement(uri, localName, qName, attributes, parser, output);
		} else if (ARTIFACT_ID.equals(qName)) {
			final TextParser textParser = new TextParser("artifactId");
			textParser.setOnCloseListener(() -> dependency.setArtifactId(textParser.getValue()));
			parser.add(textParser);
			textParser.startElement(uri, localName, qName, attributes, parser, output);
		} else if (VERSION.equals(qName)) {
			final TextParser textParser = new TextParser("version");
			textParser.setOnCloseListener(() -> dependency.setVersion(textParser.getValue()));
			parser.add(textParser);
			textParser.startElement(uri, localName, qName, attributes, parser, output);
		} else if (VERSION.equals(qName)) {
			final TextParser textParser = new TextParser("version");
			textParser.setOnCloseListener(() -> dependency.setVersion(textParser.getValue()));
			parser.add(textParser);
			textParser.startElement(uri, localName, qName, attributes, parser, output);
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
			output.addDependency(dependency);
			parser.popStack();
		}
	}

}
