package agh.project1.oop;

public class Parameter {
    private final int value;

    public Parameter(ParametersNames name, int value) {
        int[] domainAndDefault = name.getDomainAndDefault();
        if (value >= domainAndDefault[0] && value <= domainAndDefault[1])
            this.value = value;
        else
            this.value = domainAndDefault[2];
    }

    public int getValue() {
        return value;
    }
}
