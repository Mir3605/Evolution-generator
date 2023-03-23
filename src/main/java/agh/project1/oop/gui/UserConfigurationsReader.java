package agh.project1.oop.gui;

import agh.project1.oop.ParametersNames;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class UserConfigurationsReader {
    private final VBox configurationInterface;

    public UserConfigurationsReader(VBox configurationInterface) {
        this.configurationInterface = configurationInterface;
    }

    public void saveConfigurationToCSV(String filePath) {
        List<String[]> dataList = new ArrayList<>();
        dataList.add(new String[]{"OptionName, Value"});
        for (int i = 0; i < configurationInterface.getChildren().size() / 2 - 1; i++) {
            if (i < 12) {
                HBox hBox = (HBox) configurationInterface.getChildren().get(2 * i);
                Label label = (Label) hBox.getChildren().get(0);
                TextField field = (TextField) hBox.getChildren().get(1);
                String key = label.getText();
                String value = field.getText();
                dataList.add(new String[]{key, value});
            } else {
                CheckBox checkBox = (CheckBox) configurationInterface.getChildren().get(2 * i);
                String value = "false";
                if (checkBox.isSelected())
                    value = "true";
                String key = ParametersNames.TRUEIFRANDOMMUTATIONS.toString();
                switch (i) {
                    case 13 -> key = ParametersNames.TRUEIFLITTLECRAZYBEHAVIOUR.toString();
                    case 14 -> key = ParametersNames.TRUEIFGLOBEMAP.toString();
                    case 15 -> key = ParametersNames.TRUEIFTOXICBODIES.toString();
                }
                dataList.add(new String[]{key, value});
            }
        }
        File csvOutputFile = new File(filePath);
        try (PrintWriter pw = new PrintWriter(csvOutputFile)) {
            dataList.stream().map(UserConfigurationsReader::toCSVFormat).forEach(pw::println);
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    public static String toCSVFormat(String[] data) {
        return Stream.of(data).collect(Collectors.joining(","));
    }
}
