package examples.pom.parser;

import com.harium.parser.xml.NodeParserImpl;
import com.harium.parser.xml.TextParser;
import com.harium.parser.xml.XMLParser;
import examples.pom.model.Dependency;
import examples.pom.model.Pom;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

public class ProjectParser extends NodeParserImpl<Pom> {

	public static final String NAME = "name";
	public static final String ARTIFACT_ID = "artifactId";
	public static final String GROUP_ID = "groupId";
	public static final String VERSION = "version";

	public static final String PARENT = "parent";

	private boolean parsingParent = false;

	public ProjectParser() {
		super("project");
	}

	@Override
	public void startElement(String uri, String localName, String qName, Attributes attributes,
			XMLParser parser, Pom output)
			throws SAXException {
		if (GROUP_ID.equals(qName)) {
			TextParser textParser = new TextParser("groupId");
			textParser.setOnCloseListener(() -> {
				if (parsingParent) {
					output.getParent().setGroupId(textParser.getValue());
					return;
				}
				if (output.getGroupId().isEmpty()) {
					output.setGroupId(textParser.getValue());
				}
			});
			parser.add(textParser);
			textParser.startElement(uri, localName, qName, attributes, parser, output);
		} else if (ARTIFACT_ID.equals(qName)) {
			final TextParser textParser = new TextParser("artifactId");
			textParser.setOnCloseListener(() -> {
				if (parsingParent) {
					output.getParent().setArtifactId(textParser.getValue());
					return;
				}
				if (output.getArtifactId().isEmpty()) {
					output.setArtifactId(textParser.getValue());
				}
			});
			parser.add(textParser);
			textParser.startElement(uri, localName, qName, attributes, parser, output);
		} else if (VERSION.equals(qName)) {
			final TextParser textParser = new TextParser("version");
			textParser.setOnCloseListener(() -> {
				if (parsingParent) {
					output.getParent().setVersion(textParser.getValue());
					return;
				}
				if (output.getVersion().isEmpty()) {
					output.setVersion(textParser.getValue());
				}
			});
			parser.add(textParser);
			textParser.startElement(uri, localName, qName, attributes, parser, output);
		} else if (NAME.equals(qName)) {
			final TextParser textParser = new TextParser("name");
			textParser.setOnCloseListener(() -> {
				if (output.getName().isEmpty()) {
					output.setName(textParser.getValue());
				}
			});
			parser.add(textParser);
			textParser.startElement(uri, localName, qName, attributes, parser, output);
		} else if (PARENT.equals(qName)) {
			parsingParent = true;
			output.setParent(new Dependency());
		}
	}

	@Override
	public void endElement(String uri, String localName, String qName, XMLParser parser, Pom output) {
		if (name.equals(qName)) {
			parser.popStack();
		}
		if (PARENT.equals(qName)) {
			parsingParent = false;
		}
	}

}
