import agh.project1.oop.*;
import agh.project1.oop.gui.SimulationVisualisation;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class GenomeTests {
    /* Old
    Configuration configuration;
    Animal bear;
    Animal otherBear;
    Genome genome1;
    Genome genome2;

    @BeforeEach
    public void doBefore()
    {
        configuration = new Configuration(10, 10, 10, 5,
                0,0, false, 100, 10, 10,
                false);
        SimulationEngine engine = new SimulationEngine(configuration, 10, 10,
                10, true, false, new SimulationVisualisation(false, ""));
        genome1 = new Genome(configuration);
        bear = new Animal(0, genome1, 80, 1, new Vector2d(1, 1),
                configuration, engine.map);
        genome2 = new Genome(configuration);
        otherBear = new Animal(0, genome2, 20, 1, new Vector2d(1, 1),
                configuration, engine.map);
    }

    @Test
    public void getActiveGeneTest()
    {
        for(int i=0; i < 101; i++)
        {
            Assertions.assertEquals(genome1.genes[(i+1)%100], genome1.getActiveGeneToMove());
        }
    }
    @Test
    public void generatingGenomeFromParentsTest()
    {
        Genome childGenome = new Genome(bear, otherBear, configuration);
        int childAndBearCorrelation = 0;
        int childAndOtherBearCorrelation = 0;
        int bearAndOtherBearCorrelation = 0;
        for(int i=0; i < 100; i++)
        {
            if(childGenome.genes[i] == bear.genome.genes[i])
                childAndBearCorrelation++;
            if(childGenome.genes[i] == otherBear.genome.genes[i])
                childAndOtherBearCorrelation++;
            if(bear.genome.genes[i] == otherBear.genome.genes[i])
                bearAndOtherBearCorrelation++;
        }
        Assertions.assertTrue(childAndBearCorrelation >= 80);
        Assertions.assertTrue(childAndOtherBearCorrelation >= 20);
        System.out.println("Bear and other bear genes correlation: " + bearAndOtherBearCorrelation);
    }*/
}
