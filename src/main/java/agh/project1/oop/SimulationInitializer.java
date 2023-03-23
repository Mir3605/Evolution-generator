package agh.project1.oop;

import agh.project1.oop.gui.SimulationVisualisation;

import java.io.FileNotFoundException;
import java.util.HashMap;

public class SimulationInitializer {
    public final SimulationVisualisation visualisation;

    public SimulationInitializer(SimulationVisualisation visualisation) {
        this.visualisation = visualisation;
    }

    public SimulationEngine getSimulationEngineForParameters(String CSVFilePath) {
        OptionsReader optionsReader = new OptionsReader();
        try {
            optionsReader.loadDataFromCSV(CSVFilePath);
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        }

        HashMap<ParametersNames, Parameter> options = optionsReader.getOptions();
        Configuration configuration = new Configuration(options.get(ParametersNames.PLANTENERGY).getValue(),
                options.get(ParametersNames.PLANTSDAILY).getValue(), options.get(ParametersNames.ENERGYTOBEFULL).getValue(),
                options.get(ParametersNames.ENERGYUSEDTOREPLICATE).getValue(), options.get(ParametersNames.MINMUTATIONS).getValue(),
                options.get(ParametersNames.MAXMUTATIONS).getValue(), options.get(ParametersNames.TRUEIFRANDOMMUTATIONS).getValue() != 0,
                options.get(ParametersNames.GENOMELENGTH).getValue(), options.get(ParametersNames.MAPWIDTH).getValue(),
                options.get(ParametersNames.MAPHEIGHT).getValue(), options.get(ParametersNames.TRUEIFLITTLECRAZYBEHAVIOUR).getValue() != 0);
        SimulationEngine engine = new SimulationEngine(configuration, options.get(ParametersNames.INITIALANIMALENERGY).getValue(),
                options.get(ParametersNames.INITIALANIMALNUMBER).getValue(), options.get(ParametersNames.INITIALPLANTSNUMBER).getValue(),
                options.get(ParametersNames.TRUEIFGLOBEMAP).getValue() != 0,
                false, visualisation);
        return engine;
    }
}
