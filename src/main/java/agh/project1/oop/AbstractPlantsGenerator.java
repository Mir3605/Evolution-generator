package agh.project1.oop;


import java.util.TreeSet;
import java.util.concurrent.ThreadLocalRandom;

public abstract class AbstractPlantsGenerator implements IPlantsObserver {
    public final AbstractWorldMap map;
    public TreeSet<Vector2d> emptyFields;

    public AbstractPlantsGenerator(AbstractWorldMap map) {
        this.map = map;
    }

    public void generatePlants(int quantity) {
        this.refresh();
        if (quantity >= emptyFields.size()) {
            while (emptyFields.size() > 0) {
                Vector2d position = emptyFields.first();
                generatePlantAt(position);
            }
            return;
        }
        for (int i = 0; i < quantity; i++) {
            int rand = ThreadLocalRandom.current().nextInt(0, 5);
            int field_no;
            if (rand == 0) {
                field_no = ThreadLocalRandom.current().nextInt(emptyFields.size() / 5, emptyFields.size());
            } else
                field_no = ThreadLocalRandom.current().nextInt(0, Math.max(emptyFields.size() / 5, 1));
            Vector2d positionToGenerate = emptyFields.first();
            for (Vector2d position : emptyFields) {
                if (field_no == 0) {
                    positionToGenerate = position;
                    break;
                } else
                    field_no--;
            }
            generatePlantAt(positionToGenerate);
        }
    }

    private void generatePlantAt(Vector2d position) {
        map.place(new Plant(position), position);
        emptyFields.remove(position);
    }

    abstract void refresh();

    @Override
    public void plantRemoved(Vector2d position) throws IllegalArgumentException {
        if (emptyFields.contains(position))
            throw new IllegalArgumentException("Field " + position + " should already be empty");
        emptyFields.add(position);
    }

    public int getEmptyFieldsNumber() {
        return emptyFields.size();
    }
}
