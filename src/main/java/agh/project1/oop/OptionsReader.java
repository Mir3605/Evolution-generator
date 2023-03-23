package agh.project1.oop;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Scanner;

public class OptionsReader {
    HashMap<ParametersNames, Parameter> options = new HashMap<>();

    public void loadDataFromCSV(String filePath) throws FileNotFoundException {
        Scanner scanner = new Scanner(new File(filePath));
        scanner.useDelimiter("[;\n,]");
        while (scanner.hasNext()) {
            String option = scanner.next();
            if (!scanner.hasNext())
                break;
            String value = scanner.next();
            String formattedOption = option.replaceAll(" ", "").
                    replaceAll("\r", "").toUpperCase();
            ParametersNames optionName = null;
            try {
                optionName = ParametersNames.valueOf(formattedOption);
            } catch (IllegalArgumentException e) {
                System.out.println("Failed to read " + option + " option, option name not recognised");
            }
            if (optionName != null) {
                try {
                    String formattedValue = value.replaceAll(" ", "").
                            replaceAll("\r", "").toLowerCase();
                    Parameter parameter;
                    if (formattedValue.equals("false"))
                        parameter = new Parameter(optionName, 0);
                    else if (formattedValue.equals("true"))
                        parameter = new Parameter(optionName, 1);
                    else {
                        parameter = new Parameter(optionName, Integer.parseInt(formattedValue));
                    }
                    options.put(optionName, parameter);
                } catch (NumberFormatException e) {
                    System.out.println("Failed to read " + formattedOption + " option, because value " + value +
                            " is not int or boolean");
                }
            }
        }
        scanner.close();
        for (ParametersNames parameterName : ParametersNames.values()) {
            if (!options.containsKey(parameterName))
                options.put(parameterName, new Parameter(parameterName, -1));
        }
        if (options.get(ParametersNames.MINMUTATIONS).getValue() > options.get(ParametersNames.MAXMUTATIONS).getValue()) {
            options.remove(ParametersNames.MINMUTATIONS);
            options.put(ParametersNames.MINMUTATIONS, new Parameter(ParametersNames.MINMUTATIONS,
                    options.get(ParametersNames.MAXMUTATIONS).getValue()));
        }
        if (options.get(ParametersNames.ENERGYUSEDTOREPLICATE).getValue() > options.get(ParametersNames.ENERGYTOBEFULL).getValue()) {
            options.remove(ParametersNames.ENERGYUSEDTOREPLICATE);
            options.put(ParametersNames.ENERGYUSEDTOREPLICATE, new Parameter(ParametersNames.ENERGYUSEDTOREPLICATE,
                    options.get(ParametersNames.ENERGYTOBEFULL).getValue() - 1));
        }
    }

    public HashMap<ParametersNames, Parameter> getOptions() {
        return options;
    }
}
