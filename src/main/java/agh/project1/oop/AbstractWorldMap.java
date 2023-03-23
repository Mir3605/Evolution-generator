package agh.project1.oop;

import java.util.HashMap;
import java.util.TreeSet;

public abstract class AbstractWorldMap implements IPositionObserver {
    public HashMap<Vector2d, TreeSet<Animal>> animals = new HashMap<>();
    public HashMap<Vector2d, Plant> plants = new HashMap<>();
    public final int width;
    public final int height;
    public final Configuration configuration;
    public final AbstractPlantsGenerator plantsGenerator;
    public final SimulationEngine simulationEngine;
    private final Vector2d lowerLeft;
    private final Vector2d upperRight;

    public AbstractWorldMap(Configuration simulationConfiguration, boolean trueIfToxicBodies,
                            SimulationStatistics simulationStatistics, SimulationEngine simulationEngine) {
        this.configuration = simulationConfiguration;
        this.simulationEngine = simulationEngine;
        this.width = configuration.mapWidth();
        this.height = configuration.mapHeight();
        this.lowerLeft = new Vector2d(0, 0);
        this.upperRight = new Vector2d(width, height);
        if (trueIfToxicBodies)
            this.plantsGenerator = new ToxicBodiesPlantsGenerator(this, simulationStatistics);
        else
            this.plantsGenerator = new EquatorForestPlantsGenerator(this);
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                animals.put(new Vector2d(i, j), new TreeSet<>(Animal::compare));
            }
        }
    }

    public Plant plantAt(Vector2d position) {
        return plants.get(position);
    }

    public Animal animalAt(Vector2d position) {
        if (animals.get(position).size() > 0) {
            Animal animal = animals.get(position).last();
            if (animal.getPosition().equals(position) && !animal.isDead())
                return animal;
            else {
                remove(animal);
                return null;
            }
        }
        return null;
    }

    public IMapElement objectAt(Vector2d position) {
        if (animalAt(position) == null)
            return plantAt(position);
        return animalAt(position);
    }

    public void remove(IMapElement element) {
        if (element instanceof Animal) {
            Animal animal = (Animal) element;
            animals.get(animal.getPosition()).remove(animal);
        } else if (element instanceof Plant) {
            Plant plant = (Plant) element;
            plants.remove(plant.getPosition());
            plantsGenerator.plantRemoved(plant.getPosition());
        }
    }

    public boolean canMoveRegularlyTo(Vector2d position) {
        return position.isInRectangle(lowerLeft, upperRight);
    }

    public abstract Vector2d moveToCornerCase(Animal animal, Vector2d position);

    public void place(IMapElement element, Vector2d position) throws IllegalArgumentException {
        if (!position.isInRectangle(lowerLeft, upperRight)) {
            throw new IllegalArgumentException(position + " is not a legal position on this map. Pick one between " +
                    "(0, 0) and (" + width + ", " + height + ")");
        }
        if (element instanceof Animal) {
            Animal animal = (Animal) element;
            animals.get(position).add(animal);
        } else if (element instanceof Plant) {
            Plant plantToInsert = (Plant) element;
            Plant plantAtPosition = plants.get(position);
            if (plantAtPosition != null)
                throw new IllegalArgumentException("Plant on the " + position + " field already exists");
            else
                plants.put(position, plantToInsert);
        }
    }

    @Override
    public void positionChanged(Animal animal, Vector2d newPosition) {
        remove(animal);
        place(animal, newPosition);
    }

    public void generatePlants(int quantity) {
        plantsGenerator.generatePlants(quantity);
    }

    public void eatPlantAt(Vector2d position) {
        Animal animal = animalAt(position);
        if (animal == null)
            return;
        Plant plant = plantAt(position);
        if (plant == null)
            return;
        remove(animal);
        animal.atePlant();
        place(animal, position);
        remove(plant);
    }

    public void replicateOnEveryField() {
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                TreeSet<Animal> set = animals.get(new Vector2d(i, j));
                if (set.size() > 1) {
                    Animal parentOne = set.last();
                    set.remove(parentOne);
                    Animal parentTwo = set.last();
                    set.remove(parentTwo);
                    simulationEngine.birthNewAnimal(parentOne, parentTwo);
                    set.add(parentOne);
                    set.add(parentTwo);
                }
            }
        }
    }

    public int getPlantsNumber() {
        return width * height - plantsGenerator.getEmptyFieldsNumber();
    }

    public int getEmptyFieldsNumber() {
        int emptyFieldsNumber = 0;
        for (Vector2d key : animals.keySet()) {
            if (objectAt(key) == null)
                emptyFieldsNumber++;
        }
        return emptyFieldsNumber;
    }
}
