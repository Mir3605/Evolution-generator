import agh.project1.oop.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


public class EquatorForestPlantsGeneratorTest {
    /* Old
    Configuration configuration;
    AbstractWorldMap map;
    EquatorForestPlantsGenerator generator;
    @BeforeEach
    public void doBefore()
    {
        configuration = new Configuration(10, 10, 10, 5,
                0,0, false, 10, 10, 10,
                false);
        SimulationEngine engine = new SimulationEngine(configuration, 10, 10,
                10, true, false);
        this.map = engine.map;
        generator = (EquatorForestPlantsGenerator) map.plantsGenerator;
    }

    @Test
    public void generatePlantsTest()
    {
        int sizeBefore = map.height*map.width - 10;
        Assertions.assertEquals(sizeBefore, generator.emptyFields.size());
        generator.generatePlants(10);
        Assertions.assertEquals(sizeBefore - 10, generator.emptyFields.size());
        int plantsOnMap = 0;
        for(int i = 0; i < map.width; i++)
        {
            for(int j = 0; j < map.height; j++)
            {
                if(map.plantAt(new Vector2d(i, j)) != null)
                    plantsOnMap++;
            }
        }
        Assertions.assertEquals(20,plantsOnMap);
    }

    @Test
    public void plantRemovedTest()
    {
        Assertions.assertThrows(IllegalArgumentException.class, () -> generator.plantRemoved(new Vector2d(0, 0)));
        generator.generatePlants(map.height* map.width);
        generator.plantRemoved(new Vector2d(0, 0));
        Assertions.assertTrue(generator.emptyFields.contains(new Vector2d(0, 0)));
        Assertions.assertEquals(1, generator.emptyFields.size());
    }*/
}
