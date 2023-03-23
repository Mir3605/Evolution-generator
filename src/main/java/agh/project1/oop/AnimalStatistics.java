package agh.project1.oop;

public class AnimalStatistics {
    public final Animal owner;
    public final int id;
    public final int birthday;
    private int deathDay = -1;
    private int childrenNumber = 0;
    private int plantsEaten = 0;
    private int energy;
    public final Configuration configuration;

    public AnimalStatistics(Animal owner, int id, int birthday, int energy) {
        this.owner = owner;
        this.id = id;
        this.birthday = birthday;
        this.energy = energy;
        this.configuration = owner.configuration;
    }

    public int getEnergy() {
        return energy;
    }

    public void reduceEnergy(int value) {
        this.energy = this.energy - value;
    }

    public int getChildrenNumber() {
        return childrenNumber;
    }

    public int getPlantsEaten() {
        return plantsEaten;
    }

    public int getDaysAlive(int currentDay) {
        if (owner.isDead())
            return deathDay - birthday;
        return currentDay - birthday;
    }

    public int compare(AnimalStatistics other) {
        if (this.energy != other.energy)
            return this.energy - other.energy;
        if (this.birthday != other.birthday)
            return other.birthday - this.birthday;
        if (this.childrenNumber != other.childrenNumber)
            return this.childrenNumber - other.childrenNumber;
        return other.id - this.id;
    }

    public void increaseChildrenCounter() {
        childrenNumber++;
    }

    public void increaseEnergy(int value) {
        this.energy += value;
    }

    public void atePlant() {
        plantsEaten++;
        increaseEnergy(configuration.plantEnergy());
    }

    public int getDeathDay() {
        return deathDay;
    }

    public void setDeathDay(int day) {
        if (deathDay == -1)
            deathDay = day;
    }
}
