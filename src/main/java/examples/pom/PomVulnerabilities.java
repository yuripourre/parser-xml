package examples.pom;

import examples.pom.model.Dependency;
import examples.pom.model.Pom;
import examples.pom.parser.PomParser;
import java.io.File;

public class PomVulnerabilities {

	public static void main(String[] args) throws Exception {
		File file = new File("pom.xml");

		Pom pom = new PomParser().parse(file);

		for (Dependency dependency : pom.getDependencies()) {
			System.out.println(
					"https://snyk.io/vuln/maven:" + dependency.getGroupId() + "%3A" + dependency
							.getArtifactId());

			String version = dependency.getVersion();
			if (version!=null && version.startsWith("${")) {
				String key = dependency.getVersion().substring(2, dependency.getVersion().length() - 1);
				version = pom.getProperty(key);
			}
			System.out.println(version);
		}
	}
}