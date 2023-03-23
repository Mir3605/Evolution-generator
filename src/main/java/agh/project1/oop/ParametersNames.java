package agh.project1.oop;

public enum ParametersNames {
    PLANTENERGY,
    PLANTSDAILY,
    ENERGYTOBEFULL,
    ENERGYUSEDTOREPLICATE,
    MINMUTATIONS,
    MAXMUTATIONS,
    TRUEIFRANDOMMUTATIONS,
    GENOMELENGTH,
    MAPWIDTH,
    MAPHEIGHT,
    TRUEIFLITTLECRAZYBEHAVIOUR,
    INITIALANIMALENERGY,
    INITIALANIMALNUMBER,
    INITIALPLANTSNUMBER,
    TRUEIFGLOBEMAP,
    TRUEIFTOXICBODIES;

    public boolean isBoolean() {
        boolean toReturn = false;
        switch (this) {
            case TRUEIFRANDOMMUTATIONS, TRUEIFGLOBEMAP, TRUEIFTOXICBODIES, TRUEIFLITTLECRAZYBEHAVIOUR ->
                    toReturn = true;
        }
        return toReturn;
    }

    public int[] getDomainAndDefault() {
        int maxMapWidth = 25;
        int maxMapHeight = 25;
        int maxGenomeLength = 10;
        int[] domainAndDefault = new int[3];
        switch (this) {
            case PLANTENERGY -> {
                domainAndDefault[0] = 1;
                domainAndDefault[1] = 1000;
                domainAndDefault[2] = 20;
            }
            case PLANTSDAILY -> {
                domainAndDefault[0] = 0;
                domainAndDefault[1] = maxMapHeight * maxMapWidth;
                domainAndDefault[2] = 20;
            }
            case ENERGYTOBEFULL, INITIALANIMALENERGY -> {
                domainAndDefault[0] = 2;
                domainAndDefault[1] = 1000;
                domainAndDefault[2] = 20;
            }
            case ENERGYUSEDTOREPLICATE -> {
                domainAndDefault[0] = 1;
                domainAndDefault[1] = 1000;
                domainAndDefault[2] = 15;
            }
            case MINMUTATIONS, MAXMUTATIONS -> {
                domainAndDefault[0] = 0;
                domainAndDefault[1] = maxGenomeLength;
                domainAndDefault[2] = 0;
            }
            case TRUEIFRANDOMMUTATIONS, TRUEIFLITTLECRAZYBEHAVIOUR, TRUEIFTOXICBODIES -> {
                domainAndDefault[0] = 0;
                domainAndDefault[1] = 1;
                domainAndDefault[2] = 0;
            }
            case GENOMELENGTH -> {
                domainAndDefault[0] = 2;
                domainAndDefault[1] = maxGenomeLength;
                domainAndDefault[2] = 5;
            }
            case MAPHEIGHT -> {
                domainAndDefault[0] = 4;
                domainAndDefault[1] = maxMapHeight;
                domainAndDefault[2] = 20;
            }
            case MAPWIDTH -> {
                domainAndDefault[0] = 4;
                domainAndDefault[1] = maxMapWidth;
                domainAndDefault[2] = 20;
            }
            case INITIALANIMALNUMBER -> {
                domainAndDefault[0] = 1;
                domainAndDefault[1] = maxMapHeight * maxMapWidth;
                domainAndDefault[2] = 20;
            }
            case INITIALPLANTSNUMBER -> {
                domainAndDefault[0] = 0;
                domainAndDefault[1] = maxMapHeight * maxMapWidth;
                domainAndDefault[2] = 40;
            }
            case TRUEIFGLOBEMAP -> {
                domainAndDefault[0] = 0;
                domainAndDefault[1] = 1;
                domainAndDefault[2] = 1;
            }
        }
        return domainAndDefault;
    }
}
