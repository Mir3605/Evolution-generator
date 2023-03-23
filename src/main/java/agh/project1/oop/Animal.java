package agh.project1.oop;

import java.util.ArrayList;

public class Animal implements IMapElement {
    private Vector2d position;
    private MapDirection orientation;
    public final Configuration configuration;
    public final Genome genome;
    ArrayList<IPositionObserver> observers = new ArrayList<>();
    public final AnimalStatistics stats;
    public final AbstractWorldMap map;
    private Boolean isDead = false;

    public Animal(int id, Genome genome, int initialEnergy, int today, Vector2d initialPosition,
                  Configuration simulationConfiguration, AbstractWorldMap map) {
        this.configuration = simulationConfiguration;
        this.genome = genome;
        this.orientation = MapDirection.NORTH;
        this.position = initialPosition;
        this.map = map;
        addObserver(map);
        this.stats = new AnimalStatistics(this, id, today, initialEnergy);
        map.place(this, position);
    }

    @Override
    public Vector2d getPosition() {
        return position;
    }

    @Override
    public String getImgPath() {
        int e = getEnergy();
        if (e <= 10)
            return "src/main/resources/Animal5.png";
        if (e <= 20)
            return "src/main/resources/Animal4.png";
        if (e <= 30)
            return "src/main/resources/Animal3.png";
        if (e <= 50)
            return "src/main/resources/Animal2.png";
        if (e <= 75)
            return "src/main/resources/Animal1.png";
        return "src/main/resources/Animal0.png";
    }

    public void addObserver(IPositionObserver observer) {
        observers.add(observer);
    }

    public void move() {
        Vector2d moveTo = this.position.add(this.orientation.rotate(genome.getActiveGeneToMove()).toUnitVector());
        if (!map.canMoveRegularlyTo(moveTo)) {
            moveTo = map.moveToCornerCase(this, moveTo);
            if (moveTo.equals(this.position)) {
                return;
            }
        }
        this.notifyObservers(moveTo);
        this.position = moveTo;
        reduceEnergy(1);
    }

    private void notifyObservers(Vector2d position) {
        for (IPositionObserver observer : observers)
            observer.positionChanged(this, position);
    }

    public void rotateToOpposite() {
        this.orientation = this.orientation.opposite();
    }

    public void reduceEnergy(int value) {
        stats.reduceEnergy(value);
    }

    public void hasChild() {
        stats.increaseChildrenCounter();
        reduceEnergy(configuration.energyUsedToReplicate());
    }

    public int compare(Animal other) {
        return stats.compare(other.stats);
    }

    public int getEnergy() {
        return stats.getEnergy();
    }

    public void atePlant() {
        stats.atePlant();
    }

    public int getId() {
        return stats.id;
    }

    public int getBirthday() {
        return stats.birthday;
    }

    public String getGenome() {
        return genome.toString();
    }

    public void die(int day) {
        isDead = true;
        stats.setDeathDay(day);
        while (observers.size() > 0) {
            observers.remove(0);
        }
    }

    public boolean isDead() {
        return isDead;
    }
}
