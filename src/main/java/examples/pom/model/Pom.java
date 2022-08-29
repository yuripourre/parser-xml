package examples.pom.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Pom {

	private String name = "";
	private String groupId = "";
	private String artifactId = "";
	private String version = "";

	private Dependency parent;
	private List<Dependency> dependencies = new ArrayList<>();
	private Map<String, Property> properties = new HashMap<>();

	public void addDependency(Dependency dependency) {
		dependencies.add(dependency);
	}

	public List<Dependency> getDependencies() {
		return dependencies;
	}

	public void setDependencies(List<Dependency> dependencies) {
		this.dependencies = dependencies;
	}

	public Map<String, Property> getProperties() {
		return properties;
	}

	public void setProperties(List<Property> properties) {
		for (Property property : properties) {
			this.properties.put(property.getKey(), property);
		}
	}

	public void addProperty(Property property) {
		properties.put(property.getKey(), property);
	}

	public String getProperty(String property) {
		Property prop = properties.get(property);
		if (prop == null) {
			return "";
		} else {
			return prop.getValue();
		}
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	public String getArtifactId() {
		return artifactId;
	}

	public void setArtifactId(String artifactId) {
		this.artifactId = artifactId;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public Dependency getParent() {
		return parent;
	}

	public void setParent(Dependency parent) {
		this.parent = parent;
	}
}
