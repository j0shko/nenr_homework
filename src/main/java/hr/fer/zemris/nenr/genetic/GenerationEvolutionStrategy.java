package hr.fer.zemris.nenr.genetic;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class GenerationEvolutionStrategy implements EvolutionStrategy {
    private static Random ran = new Random();

    private ParentSelectionStrategy selectionStrategy;
    private float mutationRate;
    private int elitismCount;

    public GenerationEvolutionStrategy(ParentSelectionStrategy selectionStrategy, float mutationRate, int elitismCount) {
        this.selectionStrategy = selectionStrategy;
        this.mutationRate = mutationRate;
        this.elitismCount = elitismCount;
    }

    @Override
    public List<Chromosome> evolve(List<Chromosome> chromosomes) {
        int populationSize = chromosomes.size();
        List<Chromosome> newPopulation = new ArrayList<>(populationSize);

        Collections.sort(chromosomes);
        newPopulation.addAll(bestN(chromosomes, elitismCount));
        while (newPopulation.size() < populationSize) {
            Chromosome parent1 = selectionStrategy.select(chromosomes);
            Chromosome parent2 = selectionStrategy.select(chromosomes);
            Chromosome child = parent1.crossover(parent2);
            child.mutate(mutationRate);
            newPopulation.add(child);
        }

        return newPopulation;
    }

    private static List<Chromosome> bestN(List<Chromosome> chromosomeList, int count) {
        return chromosomeList.subList(0, count);
    }
}