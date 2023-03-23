import agh.project1.oop.*;
import agh.project1.oop.gui.SimulationVisualisation;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class GlobeMapTests {
    /* Old
    GlobeMap map;
    Configuration configuration;
    @BeforeEach
    public void doBefore()
    {
        configuration = new Configuration(10, 10, 10, 5,
                0,0, false, 10, 10, 10,
                false);
        SimulationEngine engine = new SimulationEngine(configuration, 10, 10,
                10, true, false);
        this.map = (GlobeMap) engine.map;
        engine.killAllAnimals();
    }

    @Test
    public void placeRemoveAndObjectAtTest()
    {
        Animal bear = new Animal(0, new Genome(configuration), 10, 1, new Vector2d(1, 1),
                configuration, map);
        Assertions.assertSame(map.animalAt(new Vector2d(1, 1)), bear);
        Assertions.assertNull(map.animalAt(new Vector2d(0, 0)));
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
        Animal squirrel = new Animal(0, new Genome(configuration), 10, 1, new Vector2d(10, 2),
                configuration, map);
        });
        map.remove(bear);
        Assertions.assertNull(map.animalAt(new Vector2d(1, 1)));
        Plant mathew;
        if(map.plantAt(new Vector2d(2, 2)) != null)
        {
            mathew = map.plantAt(new Vector2d(2, 2));
            map.remove(mathew);
        }
        mathew = new Plant(new Vector2d(2, 2));
        map.place(mathew, new Vector2d(2, 2));
        Assertions.assertSame(mathew, map.plantAt(new Vector2d(2, 2)));
        Plant bart = new Plant(new Vector2d(2, 2));
        Assertions.assertThrows(IllegalArgumentException.class, () -> map.place(bart, new Vector2d(2, 2)));

        Animal tortoise = new Animal(0, new Genome(configuration), 10, 1, new Vector2d(3, 3),
                configuration, map);
        Plant george = new Plant(new Vector2d(3, 3));
        map.place(george, new Vector2d(3, 3));
        Assertions.assertSame(tortoise, map.objectAt(new Vector2d(3, 3)));
        map.remove(tortoise);
        Assertions.assertSame(george, map.objectAt(new Vector2d(3, 3)));
    }

    @Test
    public void canMoveRegularlyToTest()
    {
        for(int i=0; i<configuration.mapWidth(); i++)
        {
            for(int j=0; j<configuration.mapHeight(); j++)
                Assertions.assertTrue(map.canMoveRegularlyTo(new Vector2d(i, j)));
        }
        for(int i=0; i < map.width; i++)
        {
            Assertions.assertFalse(map.canMoveRegularlyTo(new Vector2d(i, -i-1)));
        }
        Assertions.assertFalse(map.canMoveRegularlyTo(new Vector2d(map.width, 0)));
        Assertions.assertFalse(map.canMoveRegularlyTo(new Vector2d(0, map.height)));
    }
    @Test
    public void moveToCornerCaseTest()
    {
        Animal bear = new Animal(0, new Genome(configuration), 10, 1, new Vector2d(0, map.height-1),
                configuration, map);
        Assertions.assertEquals(map.moveToCornerCase(bear, new Vector2d(0, map.height)), bear.getPosition());
        Assertions.assertEquals(map.moveToCornerCase(bear, new Vector2d(-1, map.height-1)),
                new Vector2d(map.width-1, map.height-1));
    }*/
}
