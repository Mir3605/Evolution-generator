import agh.project1.oop.*;
import agh.project1.oop.gui.SimulationVisualisation;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class AnimalTests {
    /* Old
    Animal bear;
    Configuration configuration;
    AbstractWorldMap map;

    @BeforeEach
    public void doBefore()
    {
        configuration = new Configuration(10, 10, 10, 5,
                0,0, false, 10, 10, 10,
                false);
        SimulationEngine engine = new SimulationEngine(configuration, 10, 10,
                10, true, false);
        this.map = engine.map;
        this.bear = new Animal(0, new Genome(configuration), 15, 0, new Vector2d(0, 1),
                configuration, map);
    }

    @Test
    public void compareTest()
    {
        Animal squirrel = new Animal(1, new Genome(configuration), 14, 0, new Vector2d(3, 3),
                configuration, map);
        Assertions.assertTrue(squirrel.compare(bear) < 0);
        Animal tortoise = new Animal(1, new Genome(configuration), 15, 1, new Vector2d(3, 3),
                configuration, map);
        Assertions.assertTrue(tortoise.compare(bear) < 0);
        Animal monkey = new Animal(0, new Genome(configuration), 10, 0, new Vector2d(0, 1),
                configuration, map);
        bear.hasChild();
        Assertions.assertTrue(bear.compare(monkey) > 0);
    }

    @Test
    public void moveTest()
    {
        Animal squirrel = new Animal(1, new Genome(configuration), 14, 0, new Vector2d(3, 3),
                configuration, map);

    }*/
}
