package agh.project1.oop;

import java.util.concurrent.ThreadLocalRandom;

public class PortalToHellMap extends AbstractWorldMap {

    public PortalToHellMap(Configuration simulationConfiguration, boolean trueIfToxicBodies,
                           SimulationStatistics simulationStatistics, SimulationEngine simulationEngine) {
        super(simulationConfiguration, trueIfToxicBodies, simulationStatistics, simulationEngine);
    }

    @Override
    public Vector2d moveToCornerCase(Animal animal, Vector2d position) {
        int x = ThreadLocalRandom.current().nextInt(0, this.width);
        int y = ThreadLocalRandom.current().nextInt(0, this.height);
        animal.reduceEnergy(this.configuration.energyUsedToReplicate());
        return new Vector2d(x, y);
    }
}
