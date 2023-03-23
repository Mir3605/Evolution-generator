package agh.project1.oop;

import java.util.HashMap;

public class SimulationStatistics {
    private final SimulationEngine engine;
    private final Configuration simulationConfiguration;
    private int[][] deathsByField;
    private int sumOfDaysAlive = 0;
    public AbstractWorldMap map;
    public HashMap<String, Integer> genomesNumbers;

    public SimulationStatistics(SimulationEngine engine) {
        this.engine = engine;
        simulationConfiguration = engine.simulationConfiguration;
        deathsByField = new int[simulationConfiguration.mapWidth()][simulationConfiguration.mapHeight()];
        genomesNumbers = new HashMap<>();
    }

    public int getAliveAnimalsNumber() {
        return engine.getAliveAnimalsNumber();
    }

    public int getDeadAnimalsNumber() {
        return engine.getNextAnimalID() - getAliveAnimalsNumber();
    }

    public int getDay() {
        return engine.getDay();
    }

    public int getPlantsNumber() {
        return map.getPlantsNumber();
    }

    public int getEmptyFieldsNumber() {
        return map.getEmptyFieldsNumber();
    }

    public int getAverageLifetime() {
        if (getDeadAnimalsNumber() > 0)
            return sumOfDaysAlive / getDeadAnimalsNumber();
        return 0;
    }

    public int getAverageEnergyLevel() {
        int sumOfEnergy = 0;
        for (Animal animal : engine.animals)
            sumOfEnergy += animal.getEnergy();
        return sumOfEnergy / getAliveAnimalsNumber();
    }

    public void animalDied(Animal animal) {
        Vector2d position = animal.getPosition();
        deathsByField[position.x][position.y]++;
        sumOfDaysAlive += getDay() - animal.getBirthday();
        String genome = animal.getGenome();
        int i = genomesNumbers.get(genome);
        genomesNumbers.remove(genome);
        if (i > 1)
            genomesNumbers.put(genome, i - 1);
    }

    public void animalBorn(Animal animal) {
        String genome = animal.getGenome();
        if (!genomesNumbers.containsKey(genome)) {
            genomesNumbers.put(genome, 1);
        } else {
            int i = genomesNumbers.get(genome);
            genomesNumbers.remove(genome);
            genomesNumbers.put(genome, i + 1);
        }
    }

    public int getAnimalsDeathsNumberAt(Vector2d position) {
        return deathsByField[position.x][position.y];
    }

    public String getMostPopularGenome() {
        String genome = "";
        int numberOfGenome = 0;
        for (String key : genomesNumbers.keySet()) {
            if (numberOfGenome < genomesNumbers.get(key)) {
                genome = key;
                numberOfGenome = genomesNumbers.get(key);
            }
        }
        return genome;
    }

    public int getNumberOfInstancesOfGenome(String genome) {
        if (genomesNumbers.get(genome) == null)
            return 0;
        return genomesNumbers.get(genome);
    }
}
