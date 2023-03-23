package agh.project1.oop;

import java.util.TreeSet;

public class ToxicBodiesPlantsGenerator extends AbstractPlantsGenerator {
    private final SimulationStatistics statistics;

    public ToxicBodiesPlantsGenerator(AbstractWorldMap map, SimulationStatistics statistics) {
        super(map);
        this.statistics = statistics;
        emptyFields = new TreeSet<>(this::compareFields);
        for (int i = 0; i < map.width; i++) {
            for (int j = 0; j < map.height; j++) {
                emptyFields.add(new Vector2d(i, j));
            }
        }
    }

    @Override
    public void refresh() {
        TreeSet<Vector2d> newEmptyFields = new TreeSet<>(this::compareFields);
        while (emptyFields.size() > 0) {
            Vector2d field = emptyFields.first();
            emptyFields.remove(field);
            newEmptyFields.add(field);
        }
        emptyFields = newEmptyFields;
    }

    private int compareFields(Vector2d v1, Vector2d v2) {
        if (v1.equals(v2))
            return 0;
        int deathDifference = statistics.getAnimalsDeathsNumberAt(v2) - statistics.getAnimalsDeathsNumberAt(v1);
        if (deathDifference == 0) {
            int distanceFromEquatorCompared = Math.abs(map.height / 2 - v1.y) - Math.abs(map.height / 2 - v2.y);
            if (distanceFromEquatorCompared == 0)
                return Math.abs(map.width / 2 - v1.x) - Math.abs(map.width / 2 - v2.x);
            return distanceFromEquatorCompared;
        }
        return deathDifference;
    }
}
