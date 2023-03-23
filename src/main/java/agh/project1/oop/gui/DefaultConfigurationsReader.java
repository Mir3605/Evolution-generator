package agh.project1.oop.gui;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class DefaultConfigurationsReader {
    List<String> configurationNames;
    List<String> configurationPaths;

    public DefaultConfigurationsReader() {
        configurationPaths = new ArrayList<>();
        configurationNames = new ArrayList<>();
        String configurationsDirectory = "src/main/resources/configurations";
        File resources = new File(configurationsDirectory);
        String[] pathNames = resources.list();
        for (String fileName : pathNames) {
            if (fileName.endsWith(".csv")) {
                configurationNames.add(fileName.replace(".csv", "").replaceAll("-", " "));
                String path = configurationsDirectory + "/" + fileName;
                configurationPaths.add(path);
                if (configurationNames.size() > 9)
                    break;
            }
        }
    }

    public List<String> getConfigurationNames() {
        return configurationNames;
    }

    public List<String> getConfigurationPaths() {
        return configurationPaths;
    }
}
