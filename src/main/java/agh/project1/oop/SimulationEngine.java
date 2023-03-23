package agh.project1.oop;

import agh.project1.oop.gui.SimulationVisualisation;
import javafx.application.Platform;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicBoolean;

public class SimulationEngine implements Runnable {
    private int day = 0;
    private final AtomicBoolean simulationShouldStop;
    private final AtomicBoolean simulationShouldPause;
    private int animalIDCounter = 0;
    public final Configuration simulationConfiguration;
    public final AbstractWorldMap map;
    public final SimulationStatistics statistics;
    public SimulationVisualisation visualisationObserver;
    public List<Animal> animals;

    public SimulationEngine(Configuration simulationConfiguration, int initialAnimalEnergy, int initialAnimalNumber,
                            int initialPlantsNumber, boolean trueIfGlobeMap, boolean trueIfToxicBodies,
                            SimulationVisualisation visualisationObserver) {
        this.simulationConfiguration = simulationConfiguration;
        simulationShouldStop = new AtomicBoolean(false);
        simulationShouldPause = new AtomicBoolean(false);
        this.visualisationObserver = visualisationObserver;
        statistics = new SimulationStatistics(this);
        if (trueIfGlobeMap) {
            map = new GlobeMap(simulationConfiguration, trueIfToxicBodies, statistics, this);
        } else {
            map = new PortalToHellMap(simulationConfiguration, trueIfToxicBodies, statistics, this);
        }
        animals = new LinkedList<>();
        map.generatePlants(initialPlantsNumber);
        initializeAnimals(initialAnimalEnergy, initialAnimalNumber);
        statistics.map = map;
    }

    public void killAllAnimals() //for debug and tests
    {
        while (animals.size() > 0) {
            map.remove(animals.get(0));
            animals.remove(animals.get(0));
        }
    }

    public void birthNewAnimal(Animal parentOne, Animal parentTwo) {
        if (parentTwo.getEnergy() < simulationConfiguration.energyToBeFull() ||
                parentOne.getEnergy() < simulationConfiguration.energyToBeFull())
            return;
        Genome childGenome = new Genome(parentOne, parentTwo, simulationConfiguration);
        Animal animal = new Animal(animalIDCounter, childGenome, simulationConfiguration.energyUsedToReplicate() * 2,
                day, parentOne.getPosition(), simulationConfiguration, map);
        animals.add(animal);
        statistics.animalBorn(animal);
        animalIDCounter++;
        parentOne.hasChild();
        parentTwo.hasChild();
    }

    private void initializeAnimals(int initialAnimalEnergy, int initialAnimalNumber) {
        for (int i = 0; i < initialAnimalNumber; i++) {
            Genome genome = new Genome(simulationConfiguration);
            int x = ThreadLocalRandom.current().nextInt(0, simulationConfiguration.mapWidth());
            int y = ThreadLocalRandom.current().nextInt(0, simulationConfiguration.mapHeight());
            Vector2d initialPosition = new Vector2d(x, y);
            Animal animal = new Animal(animalIDCounter, genome, initialAnimalEnergy, day, initialPosition,
                    simulationConfiguration, map);
            animals.add(animal);
            statistics.animalBorn(animal);
            animalIDCounter++;
        }
    }

    private void killIfNecessary(Animal animal) {
        if (animal.getEnergy() <= 0) {
            map.remove(animal);
            animals.remove(animal);
            statistics.animalDied(animal);
            animal.die(day);
        }
    }

    private void diePhase() {
        for (int i = 0; i < animals.size(); i++) {
            Animal animal = animals.get(i);
            killIfNecessary(animal);
        }
    }

    private void movePhase() {
        for (Animal animal : animals) {
            animal.move();
        }
    }

    private void eatPhase() {
        for (Animal animal : animals) {
            map.eatPlantAt(animal.getPosition());
        }
    }

    private void replicatePhase() {
        map.replicateOnEveryField();
    }

    private void growPlantsPhase() {
        map.generatePlants(simulationConfiguration.plantsDaily());
    }

    @Override
    public synchronized void run() {
        while (getAliveAnimalsNumber() > 0 && !simulationShouldStop.get()) {
            diePhase();
            movePhase();
            eatPhase();
            replicatePhase();
            growPlantsPhase();
            day++;
            try {
                Thread.sleep(750);
                Platform.runLater(() -> visualisationObserver.mapStateChanged());
            } catch (InterruptedException e) {
                System.out.println(e.getMessage());
            }
            while (simulationShouldPause.get()) {
                try {
                    wait();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    System.err.println("Thread Interrupted");
                }
            }
        }
    }

    public int getAliveAnimalsNumber() {
        return animals.size();
    }

    public int getDay() {
        return day;
    }

    public int getNextAnimalID() {
        return animalIDCounter;
    }

    public void stop() {
        if (!isRunning()) {
            play();
        }
        simulationShouldStop.set(true);
    }

    public void play() {
        if (simulationShouldPause.get()) {
            simulationShouldPause.set(false);
            notifyMe();
        }
    }

    public void pause() {
        if (!simulationShouldPause.get()) {
            simulationShouldPause.set(true);
        }
    }

    public boolean isRunning() {
        return !simulationShouldPause.get();
    }

    private synchronized void notifyMe() {
        notify();
    }
}
