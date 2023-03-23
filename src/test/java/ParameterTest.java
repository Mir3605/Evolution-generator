import agh.project1.oop.Parameter;
import agh.project1.oop.ParametersNames;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ParameterTest {
    @Test
    public void getValueTest() {
        int value = 7;
        Parameter parameter = new Parameter(ParametersNames.MAPWIDTH, value);
        Assertions.assertEquals(value, parameter.getValue());
        Parameter parameter1 = new Parameter(ParametersNames.MAPWIDTH, 20000);
        Assertions.assertEquals(20, parameter1.getValue());
    }
}
