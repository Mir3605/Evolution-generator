import agh.project1.oop.MapDirection;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class MapDirectionTests {
    MapDirection direction;

    @BeforeEach
    public void doBefore() {
        direction = MapDirection.NORTH;
    }

    @Test
    public void rotateTest() {
        MapDirection direction1 = direction.rotate(0);
        Assertions.assertEquals(direction1, MapDirection.NORTH);
        direction1 = direction1.rotate(3);
        Assertions.assertEquals(direction1, MapDirection.SOUTHEAST);
        direction1 = direction.rotate(9);
        Assertions.assertEquals(direction1, MapDirection.NORTHEAST);
        direction1 = direction.rotate(-1);
        Assertions.assertEquals(direction1, MapDirection.NORTHWEST);
    }

    @Test
    public void oppositeTest() {
        Assertions.assertEquals(direction.opposite(), MapDirection.SOUTH);
    }
}
