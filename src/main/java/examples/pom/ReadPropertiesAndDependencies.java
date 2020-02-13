package examples.pom;

import examples.pom.model.Dependency;
import examples.pom.model.Pom;
import examples.pom.model.Property;
import examples.pom.parser.PomParser;
import java.io.File;

public class ReadPropertiesAndDependencies {

	public static void main(String[] args) throws Exception {
		File file = new File("pom.xml");

		Pom pom = new PomParser().parse(file);

		printHeader(pom);
		System.out.println("--------------------------------------");
		printProperties(pom);
		System.out.println("--------------------------------------");
		printDependencies(pom);
	}

	private static void printHeader(Pom pom) {
		System.out.println("Name: "+pom.getName());
		System.out.println("Group Id: "+pom.getGroupId());
		System.out.println("Artifact Id: "+pom.getArtifactId());
		System.out.println("Version: "+pom.getVersion());
	}

	private static void printDependencies(Pom pom) {
		for (Dependency dependency : pom.getDependencies()) {
			System.out.println(dependency.getGroupId() + ":" + dependency.getArtifactId() + ":" +
					dependency.getVersion());
		}
	}

	private static void printProperties(Pom pom) {
		for (Property property : pom.getProperties()) {
			System.out.println(property.getKey() + " = " + property.getValue());
		}
	}

}
