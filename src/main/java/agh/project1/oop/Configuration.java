package agh.project1.oop;


public record Configuration(int plantEnergy, int plantsDaily, int energyToBeFull, int energyUsedToReplicate,
                            int minMutations, int maxMutations, boolean trueIfRandomMutations, int genomeLength,
                            int mapWidth, int mapHeight, boolean trueIfLittleCrazyBehaviour) {
}
