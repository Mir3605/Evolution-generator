package agh.project1.oop.gui;

import agh.project1.oop.*;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.stage.Stage;


import java.io.*;


public class SimulationVisualisation {
    private SimulationEngine engine;
    private SimulationStatistics simulationStatistics;
    private AbstractWorldMap map;
    private GridPane grid;
    private VBox statisticsBox;
    private VBox animalStatisticsBox;
    private final boolean saveToCSVRequired;
    private final String[] statisticsLabelsNames;
    private String[] currentStatisticsValues;
    private final String[] animalStatisticsLabelsNames;
    private String[] currentAnimalStatisticsValues;
    private Animal followedAnimal;
    String statisticsPath;
    private final String CSVConfigurationPath;
    Button playPause;

    public SimulationVisualisation(boolean saveToCSVRequired, String CSVConfigurationPath) {
        this.saveToCSVRequired = saveToCSVRequired;
        this.CSVConfigurationPath = CSVConfigurationPath;
        statisticsLabelsNames = new String[]{"Day", "Number of alive animals", "Number of plants on the map",
                "Empty fields on the map", "Most popular genotype", "Number of most popular genome instances",
                "Average energy level", "Average lifetime for dead animals"};
        currentStatisticsValues = new String[statisticsLabelsNames.length];
        animalStatisticsLabelsNames = new String[]{"Animal's id", "Animal's position", "Animal's genome",
                "Active genome part", "Animal's energy", "Plants eaten", "Children number", "Days alive", "Day of death"};
        currentAnimalStatisticsValues = new String[animalStatisticsLabelsNames.length];
        if (saveToCSVRequired) {
            statisticsPath = "src/main/resources/statistics/simulation-statistics.csv";
            try {
                FileWriter csvOutputFile = new FileWriter(statisticsPath, false);
                PrintWriter pw = new PrintWriter(csvOutputFile);
                pw.println(UserConfigurationsReader.toCSVFormat(statisticsLabelsNames));
                pw.close();
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public void beginSimulation() {
        SimulationInitializer initializer = new SimulationInitializer(this);
        engine = initializer.getSimulationEngineForParameters(CSVConfigurationPath);
        map = engine.map;
        Thread simulationThread = new Thread(engine);
        simulationStatistics = engine.statistics;
        reloadCurrentStatisticsValues();

        Stage simulationStage = new Stage();
        grid = new GridPane();
        drawGrid(true);
        grid.setStyle("-fx-border-width: 2px; -fx-border-color: gray");
        playPause = new Button("play/pause");
        playPause.setOnAction(actionEvent -> {
            if (engine.isRunning()) {
                engine.pause();
            } else {
                engine.play();
            }
        });
        Button showAnimalsWithGenome = new Button("Show animals with the most popular genome");
        showAnimalsWithGenome.setOnAction(actionEvent -> {
            if (!engine.isRunning())
                drawGrid(false);
        });
        VBox buttons = new VBox(playPause, new Label(" "), showAnimalsWithGenome, new Label(" "));
        statisticsBox = new VBox();
        reloadStatisticsLabels();
        VBox rightPanel = new VBox(buttons, statisticsBox, new Label("Darker colors represent animals with more energy"));
        rightPanel.setStyle("-fx-border-width: 2px; -fx-border-color: gray; -fx-padding: 4px");

        animalStatisticsBox = new VBox();
        reloadAnimalStatisticsLabels();
        Label pickAnimalLabel = new Label("Insert position of animal you want to pick:");
        TextField xTextField = new TextField();
        xTextField.setStyle("-fx-max-width: 40px");
        TextField yTextField = new TextField();
        yTextField.setStyle("-fx-max-width: 40px");
        HBox boxToPickAnimals = new HBox(new Label("x"), xTextField, new Label("y"), yTextField);
        Button pickAnimalButton = new Button("Pick animal");
        pickAnimalButton.setOnAction(actionEvent -> {
            if (!engine.isRunning()) {
                try {
                    int x = Integer.parseInt(xTextField.getText());
                    int y = Integer.parseInt(yTextField.getText());
                    followedAnimal = map.animalAt(new Vector2d(x, y));
                } catch (NumberFormatException e) {
                    System.out.println(e.getMessage());
                }
                reloadAnimalStatisticsLabels();
            }
        });
        VBox followedAnimalData = new VBox(pickAnimalLabel, new Label(" "), boxToPickAnimals, new Label(" "),
                pickAnimalButton, new Label(" "), animalStatisticsBox);
        followedAnimalData.setStyle("-fx-border-width: 2px; -fx-border-color: gray; -fx-padding: 4px");

        HBox generalField = new HBox(followedAnimalData, new Label(" "), grid, new Label(" "), rightPanel);
        generalField.setAlignment(Pos.CENTER);
        Scene scene = new Scene(generalField, 20 * map.width + 560, Math.max(20 * map.height + 30, 450));
        simulationStage.setScene(scene);
        simulationStage.setOnCloseRequest(event -> engine.stop());
        simulationStage.show();
        simulationThread.start();
    }

    private void drawGrid(boolean regular) {
        grid.getChildren().clear();
        Label label = new Label("y/x");
        grid.add(label, 0, 0);
        GridPane.setHalignment(label, HPos.CENTER);

        for (int i = 0; i < map.width; i++) {
            Label label1 = new Label(String.valueOf(i));
            grid.add(label1, i + 1, 0);
            GridPane.setHalignment(label1, HPos.CENTER);
        }


        for (int i = 1; i <= map.height; i++) {
            Label label1 = new Label(String.valueOf(map.height - i));
            grid.add(label1, 0, i);
            GridPane.setHalignment(label1, HPos.CENTER);
        }
        MapElementBoxMaker boxMaker = new MapElementBoxMaker();
        for (int i = 1; i <= map.height; i++) {
            for (int j = 0; j < map.width; j++) {
                Vector2d position = new Vector2d(j, map.height - i);
                IMapElement element = map.objectAt(position);
                VBox box = boxMaker.createBox(element);
                if (!regular && element != null) {
                    if (element instanceof Animal) {
                        if (((Animal) element).getGenome().equals(currentStatisticsValues[4])) {
                            box = boxMaker.createHighlightedAnimalBox();
                        }
                    }
                }
                grid.add(box, j + 1, i);
            }
        }
    }

    private void reloadStatisticsLabels() {
        statisticsBox.getChildren().clear();
        for (int i = 0; i < statisticsLabelsNames.length; i++) {
            statisticsBox.getChildren().add(new Label(statisticsLabelsNames[i] + ": " + currentStatisticsValues[i]));
            statisticsBox.getChildren().add(new Label(""));
        }
    }

    public void mapStateChanged() {
        drawGrid(true);
        reloadCurrentStatisticsValues();
        reloadStatisticsLabels();
        reloadAnimalStatisticsLabels();
        if (saveToCSVRequired) {
            try {
                FileWriter fw = new FileWriter(statisticsPath, true);
                PrintWriter pw = new PrintWriter(fw);
                pw.println(UserConfigurationsReader.toCSVFormat(currentStatisticsValues));
                pw.close();
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private void reloadCurrentStatisticsValues() {
        currentStatisticsValues[0] = Integer.toString(simulationStatistics.getDay());
        currentStatisticsValues[1] = Integer.toString(simulationStatistics.getAliveAnimalsNumber());
        currentStatisticsValues[2] = Integer.toString(simulationStatistics.getPlantsNumber());
        currentStatisticsValues[3] = Integer.toString(simulationStatistics.getEmptyFieldsNumber());
        currentStatisticsValues[4] = simulationStatistics.getMostPopularGenome();
        currentStatisticsValues[5] = Integer.toString(simulationStatistics.getNumberOfInstancesOfGenome(currentStatisticsValues[4]));
        currentStatisticsValues[6] = Integer.toString(simulationStatistics.getAverageEnergyLevel());
        currentStatisticsValues[7] = Integer.toString(simulationStatistics.getAverageLifetime());
    }

    private void reloadCurrentAnimalStatisticsValues() {
        AnimalStatistics animalStatistics = followedAnimal.stats;
        currentAnimalStatisticsValues[0] = Integer.toString(followedAnimal.getId());
        currentAnimalStatisticsValues[1] = followedAnimal.getPosition().toString();
        currentAnimalStatisticsValues[2] = followedAnimal.getGenome();
        currentAnimalStatisticsValues[3] = Integer.toString(followedAnimal.genome.getActiveGene());
        currentAnimalStatisticsValues[4] = Integer.toString(followedAnimal.getEnergy());
        currentAnimalStatisticsValues[5] = Integer.toString(animalStatistics.getPlantsEaten());
        currentAnimalStatisticsValues[6] = Integer.toString(animalStatistics.getChildrenNumber());
        currentAnimalStatisticsValues[7] = Integer.toString(animalStatistics.getDaysAlive(simulationStatistics.getDay()));
        if (!followedAnimal.isDead()) {
            currentAnimalStatisticsValues[8] = "-";
        } else {
            currentAnimalStatisticsValues[8] = Integer.toString(animalStatistics.getDeathDay());
        }
    }

    private void reloadAnimalStatisticsLabels() {
        animalStatisticsBox.getChildren().clear();
        if (followedAnimal == null) {
            for (int i = 0; i < animalStatisticsLabelsNames.length; i++) {
                animalStatisticsBox.getChildren().add(new Label(animalStatisticsLabelsNames[i] + ": -"));
                animalStatisticsBox.getChildren().add(new Label(""));
            }
        } else {
            reloadCurrentAnimalStatisticsValues();
            for (int i = 0; i < animalStatisticsLabelsNames.length; i++) {
                animalStatisticsBox.getChildren().add(new Label(animalStatisticsLabelsNames[i] + ": " +
                        currentAnimalStatisticsValues[i]));
                animalStatisticsBox.getChildren().add(new Label(""));
            }
        }

    }
}
