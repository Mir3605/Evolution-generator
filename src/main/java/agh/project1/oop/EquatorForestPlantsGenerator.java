package agh.project1.oop;

import java.util.TreeSet;

public class EquatorForestPlantsGenerator extends AbstractPlantsGenerator {
    public EquatorForestPlantsGenerator(AbstractWorldMap map) {
        super(map);
        emptyFields = new TreeSet<>((v1, v2) -> {
            int distanceFromEquatorCompared = Math.abs(map.height / 2 - v1.y) - Math.abs(map.height / 2 - v2.y);
            if (distanceFromEquatorCompared == 0) {
                int distanceFromMeridian0 = Math.abs(map.width / 2 - v1.x) - Math.abs(map.width / 2 - v2.x);
                if (distanceFromMeridian0 == 0) {
                    if (v1.y == v2.y)
                        return v1.x - v2.x;
                    return v1.y - v2.y;
                }
                return distanceFromMeridian0;
            }
            return distanceFromEquatorCompared;
        });
        for (int i = 0; i < map.width; i++) {
            for (int j = 0; j < map.height; j++) {
                emptyFields.add(new Vector2d(i, j));
            }
        }
    }


    @Override
    void refresh() {
    }
}
