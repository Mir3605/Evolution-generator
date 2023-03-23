package agh.project1.oop;

public class GlobeMap extends AbstractWorldMap {
    public GlobeMap(Configuration simulationConfiguration, boolean trueIfToxicBodies,
                    SimulationStatistics simulationStatistics, SimulationEngine simulationEngine) {
        super(simulationConfiguration, trueIfToxicBodies, simulationStatistics, simulationEngine);
    }

    @Override
    public Vector2d moveToCornerCase(Animal animal, Vector2d position) {
        if (position.y < 0 || position.y >= height) {
            animal.rotateToOpposite();
            return animal.getPosition();
        }
        while (position.x < 0)
            position = new Vector2d(position.x + this.width, position.y);
        return new Vector2d(position.x % width, position.y);
    }
}
