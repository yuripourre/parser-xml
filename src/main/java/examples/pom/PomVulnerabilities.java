package examples.pom;

import examples.pom.model.Dependency;
import examples.pom.model.Pom;
import examples.pom.parser.PomParser;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class PomVulnerabilities {

    public static void main(String[] args) throws Exception {
        File file = new File("pom.xml");

        Pom pom = new PomParser().parse(file);

        for (Dependency dependency : pom.getDependencies()) {
            String url = getUrl(dependency);
            String version = getVersion(pom, dependency);

            Map<String, Report> reports = parseReports(url);
            Report report = reports.get(version);
            System.out.println(dependency.getGroupId() + ":" + dependency.getArtifactId() + " - " + report);
        }
    }

    private static Map<String, Report> parseReports(String url) throws IOException {
        Document doc = Jsoup.connect(url).get();
        Elements elements = doc.body().select(".package-versions-table__container table tbody tr");

        Map<String, Report> reports = new HashMap<>();
        for (Element element : elements) {
            Report report = new Report();

            report.version = element.child(0).text();
            report.date = element.child(1).text();
            countVulnerabilities(element.child(2), report);

            reports.put(report.version, report);
        }

        return reports;
    }

    private static void countVulnerabilities(Element child, Report report) {
        Elements elements = child.select("li");
        for(Element element: elements) {
            String type = element.child(1).text();
            String countText = element.child(0).text();
            if ("C".equals(type)) {
                report.critical = Integer.parseInt(countText);
            } else if ("H".equals(type)) {
                report.high = Integer.parseInt(countText);
            } else if ("M".equals(type)) {
                report.medium = Integer.parseInt(countText);
            } else if ("L".equals(type)) {
                report.low = Integer.parseInt(countText);
            }
        }
    }

    private static String getVersion(Pom pom, Dependency dependency) {
        String version = dependency.getVersion();
        if (version != null && version.startsWith("${")) {
            String key = dependency.getVersion().substring(2, dependency.getVersion().length() - 1);
            version = pom.getProperty(key);
        }
        return version;
    }

    private static String getUrl(Dependency dependency) {
        return "https://security.snyk.io/package/maven/" + dependency.getGroupId() + ":" + dependency.getArtifactId();
    }

    static class Report {
        String version;
        String date;
        int critical;
        int high;
        int medium;
        int low;

        @Override
        public String toString() {
            return "{" +
                    "version: '" + version + '\'' +
                    ", date: '" + date + '\'' +
                    ", critical: " + critical +
                    ", high: " + high +
                    ", medium: " + medium +
                    ", low: " + low +
                    '}';
        }
    }
}