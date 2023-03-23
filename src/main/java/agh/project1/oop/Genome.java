package agh.project1.oop;

import java.util.HashSet;
import java.util.concurrent.ThreadLocalRandom;

public class Genome {
    public final Configuration configuration;
    public final int[] genes;
    private int currentGeneIndex = 0;

    public Genome(Configuration simulationConfiguration) {
        configuration = simulationConfiguration;
        genes = new int[configuration.genomeLength()];
        for (int i = 0; i < configuration.genomeLength(); i++) {
            genes[i] = ThreadLocalRandom.current().nextInt(0, 8);
        }
    }

    public Genome(Animal parentOne, Animal parentTwo, Configuration simulationConfiguration) {
        configuration = simulationConfiguration;
        Genome genomeOne = parentOne.genome;
        Genome genomeTwo = parentTwo.genome;
        int genomeLength = configuration.genomeLength();
        genes = new int[genomeLength];
        int firstParentGenes = (int) ((float) genomeLength * (float) parentOne.getEnergy() / ((float) parentOne.getEnergy()
                + (float) parentTwo.getEnergy()));
        int secondParentGenes = genomeLength - firstParentGenes;
        int firstGoesFromLeft = ThreadLocalRandom.current().nextInt(0, 2);
        int firstBeginsAt = 0;
        int secondBeginsAt = 0;
        if (firstGoesFromLeft == 1) {
            secondBeginsAt = firstParentGenes;
        } else {
            firstBeginsAt = secondParentGenes;
        }
        System.arraycopy(genomeOne.genes, firstBeginsAt, genes, firstBeginsAt, firstParentGenes);
        System.arraycopy(genomeTwo.genes, secondBeginsAt, genes, secondBeginsAt, secondParentGenes);

        if (configuration.maxMutations() > 0) {
            int numberOfMutations = Math.min(ThreadLocalRandom.current().nextInt(configuration.minMutations(),
                    configuration.maxMutations() + 1), genomeLength);
            HashSet<Integer> mutationsAvailable = new HashSet<>();
            for (int i = 0; i < genomeLength; i++) {
                mutationsAvailable.add(i);
            }
            for (int i = 0; i < numberOfMutations; i++) {
                int chosenNumber = ThreadLocalRandom.current().nextInt(0, mutationsAvailable.size());
                int geneIndex = 0;
                int j = 0;
                for (int iter : mutationsAvailable) {
                    if (j == chosenNumber) {
                        geneIndex = iter;
                        break;
                    }
                    j++;
                }
                mutateThisGene(geneIndex);
                mutationsAvailable.remove(geneIndex);
            }
        }
    }

    public int getActiveGeneToMove() {
        if (configuration.trueIfLittleCrazyBehaviour()) {
            int rand = ThreadLocalRandom.current().nextInt(0, 5);
            if (rand == 0)
                this.currentGeneIndex = (this.currentGeneIndex + 2) % configuration.genomeLength();
            else
                this.currentGeneIndex = (this.currentGeneIndex + 1) % configuration.genomeLength();
        } else
            this.currentGeneIndex = (this.currentGeneIndex + 1) % configuration.genomeLength();
        return genes[this.currentGeneIndex];
    }

    public int getActiveGene() {
        return genes[this.currentGeneIndex];
    }

    private void mutateThisGene(int geneIndex) {
        if (configuration.trueIfRandomMutations()) {
            genes[geneIndex] = ThreadLocalRandom.current().nextInt(0, 8);
        } else {
            int actualGene = genes[geneIndex];
            int rand = ThreadLocalRandom.current().nextInt(0, 2);
            if (rand == 0)
                rand--;
            actualGene += rand;
            if (actualGene < 0)
                actualGene += 8;
            genes[geneIndex] = actualGene % 8;
        }
    }

    @Override
    public String toString() {
        StringBuilder stringRepresentation = new StringBuilder();
        for (int i : genes)
            stringRepresentation.append(i);
        return stringRepresentation.toString();
    }
}
