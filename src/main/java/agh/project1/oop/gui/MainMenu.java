package agh.project1.oop.gui;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.control.Button;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class MainMenu extends Application {
    private Stage primaryStage;

    @Override
    public void start(Stage primaryStage) throws Exception {
        this.primaryStage = primaryStage;
        moveToMainMenu();
        primaryStage.show();
    }

    private void moveToPreparedConfigurations() {
        DefaultConfigurationsReader configurationsReader = new DefaultConfigurationsReader();
        List<String> configurationNames = configurationsReader.getConfigurationNames();
        List<String> configurationPaths = configurationsReader.getConfigurationPaths();
        List<Button> buttons = new ArrayList<>();
        VBox configurationsAndMenu = new VBox();
        CheckBox saveToCSVCheckBox = new CheckBox("Check if you want to save simulation statistics into .csv file");
        for (int i = 0; i < configurationPaths.size(); i++) {
            buttons.add(new Button(configurationNames.get(i)));
            String path = configurationPaths.get(i);
            buttons.get(i).setOnAction(actionEvent -> {
                SimulationVisualisation visualisation = new SimulationVisualisation(saveToCSVCheckBox.isSelected(),
                        path);
                moveToMainMenu();
                visualisation.beginSimulation();
            });
        }
        configurationsAndMenu.getChildren().add(saveToCSVCheckBox);
        configurationsAndMenu.getChildren().add(new Label(""));
        for (Button button : buttons) {
            configurationsAndMenu.getChildren().add(button);
            configurationsAndMenu.getChildren().add(new Label(" "));
        }
        Button mainMenu = new Button("main menu");
        mainMenu.setOnAction(actionEvent -> moveToMainMenu());
        configurationsAndMenu.getChildren().add(mainMenu);
        configurationsAndMenu.setAlignment(Pos.CENTER);
        Scene scene = new Scene(configurationsAndMenu, 400, 300);
        primaryStage.setScene(scene);
    }

    private void moveToUserConfiguration() {
        List<HBox> textFieldsData = new LinkedList<>();
        Label label = new Label("Plant energy ");
        textFieldsData.add(new HBox(label, new TextField()));
        label = new Label("Plants daily ");
        textFieldsData.add(new HBox(label, new TextField()));
        label = new Label("Energy to be full ");
        textFieldsData.add(new HBox(label, new TextField()));
        label = new Label("Energy used to replicate ");
        textFieldsData.add(new HBox(label, new TextField()));
        label = new Label("Min mutations ");
        textFieldsData.add(new HBox(label, new TextField()));
        label = new Label("Max mutations ");
        textFieldsData.add(new HBox(label, new TextField()));
        label = new Label("Genome length ");
        textFieldsData.add(new HBox(label, new TextField()));
        label = new Label("Map width ");
        textFieldsData.add(new HBox(label, new TextField()));
        label = new Label("Map height ");
        textFieldsData.add(new HBox(label, new TextField()));
        label = new Label("Initial animal energy ");
        textFieldsData.add(new HBox(label, new TextField()));
        label = new Label("Initial animal number ");
        textFieldsData.add(new HBox(label, new TextField()));
        label = new Label("Initial plants number ");
        textFieldsData.add(new HBox(label, new TextField()));
        List<CheckBox> checkBoxes = new LinkedList<>();
        CheckBox checkBox = new CheckBox("Random mutations");
        checkBoxes.add(checkBox);
        checkBox = new CheckBox("Little crazy behaviour");
        checkBoxes.add(checkBox);
        checkBox = new CheckBox("Globe map (uncheck for portal to hell)");
        checkBoxes.add(checkBox);
        checkBox = new CheckBox("Toxic bodies plants generator\n(uncheck for equator forest generator)");
        checkBoxes.add(checkBox);
        CheckBox saveToCSVCheckBox = new CheckBox("Check if you want to save simulation statistics into .csv file");
        checkBoxes.add(saveToCSVCheckBox);
        VBox configurationInterface = new VBox();
        for (HBox field : textFieldsData) {
            configurationInterface.getChildren().add(field);
            field.getChildren().get(1).setStyle("-fx-max-width: 150px");
            field.setStyle("-fx-max-width: 300px");
            field.setAlignment(Pos.CENTER);
            configurationInterface.getChildren().add(new Label(""));
        }
        for (CheckBox box : checkBoxes) {
            configurationInterface.getChildren().add(box);
            configurationInterface.getChildren().add(new Label(""));
        }
        Button startSimulation = new Button("Start simulation");
        startSimulation.setOnAction(actionEvent -> {
            UserConfigurationsReader reader = new UserConfigurationsReader(configurationInterface);
            String temporaryConfigPath = "src/main/resources/configurations/last-user-configuration.csv";
            reader.saveConfigurationToCSV(temporaryConfigPath);
            SimulationVisualisation visualisation = new SimulationVisualisation(saveToCSVCheckBox.isSelected(),
                    temporaryConfigPath);
            moveToMainMenu();
            visualisation.beginSimulation();
        });
        Button mainMenu = new Button("Main menu");
        mainMenu.setOnAction(actionEvent -> moveToMainMenu());
        HBox steeringButtons = new HBox(startSimulation, new Label("  "), mainMenu);
        steeringButtons.setAlignment(Pos.CENTER);
        configurationInterface.getChildren().add(steeringButtons);
        configurationInterface.setAlignment(Pos.CENTER);
        Scene scene = new Scene(configurationInterface, 350, 800);
        primaryStage.setScene(scene);
    }

    private void moveToMainMenu() {
        Button configurations = new Button("Prepared configurations");
        configurations.setOnAction(actionEvent -> moveToPreparedConfigurations());
        Button userConfiguration = new Button("User configuration");
        userConfiguration.setOnAction(actionEvent -> moveToUserConfiguration());
        VBox menuButtons = new VBox(configurations, new Label(" "), userConfiguration);
        menuButtons.setAlignment(Pos.CENTER);
        Scene scene = new Scene(menuButtons, 200, 200);
        primaryStage.setScene(scene);
    }
}
