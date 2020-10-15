package examples.pom.parser;

import com.harium.parser.xml.XMLParser;
import examples.pom.model.Pom;
import java.io.File;
import java.io.IOException;
import javax.xml.parsers.ParserConfigurationException;
import org.xml.sax.SAXException;

public class PomParser {

	public Pom parse(File file) throws IOException, SAXException, ParserConfigurationException {
		Pom pom = new Pom();
		XMLParser handler = new XMLParser(pom);
		handler.register(new ExclusionsParser());
		handler.register(new DependencyParser());
		handler.register(new PropertiesParser());
		handler.register(new ProjectParser());
		handler.parse(file);

		return pom;
	}

}
