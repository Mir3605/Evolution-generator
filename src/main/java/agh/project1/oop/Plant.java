package agh.project1.oop;

public class Plant implements IMapElement {
    public final Vector2d position;

    public Plant(Vector2d position) {
        this.position = position;
    }

    @Override
    public synchronized Vector2d getPosition() {
        return this.position;
    }

    @Override
    public synchronized String getImgPath() {
        return "src/main/resources/PlantField.png";
    }
}
