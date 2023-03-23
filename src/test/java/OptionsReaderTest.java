import agh.project1.oop.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.util.HashMap;

public class OptionsReaderTest {
    @Test
    public void loadDataFromCSVTest() {
        OptionsReader optionsReader = new OptionsReader();
        try {
            optionsReader.loadDataFromCSV("src/test/resources/TestData.csv");
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        }
        HashMap<ParametersNames, Parameter> optionsCorrect = new HashMap<>();
        HashMap<ParametersNames, Parameter> optionsToCheck = optionsReader.getOptions();
        optionsCorrect.put(ParametersNames.PLANTENERGY, new Parameter(ParametersNames.PLANTENERGY, 10));
        optionsCorrect.put(ParametersNames.PLANTSDAILY, new Parameter(ParametersNames.PLANTSDAILY, 10));
        optionsCorrect.put(ParametersNames.TRUEIFTOXICBODIES, new Parameter(ParametersNames.TRUEIFTOXICBODIES, -1));
        for (ParametersNames key : optionsCorrect.keySet()) {
            Assertions.assertEquals(optionsCorrect.get(key).getValue(), optionsToCheck.get(key).getValue());
        }
    }
}
