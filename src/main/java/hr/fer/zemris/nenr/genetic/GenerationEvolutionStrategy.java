package hr.fer.zemris.nenr.genetic;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class GenerationEvolutionStrategy implements EvolutionStrategy {
    private static Random ran = new Random();

    private ParentSelectionStrategy selectionStrategy;
    private float mutationRate;
    private float crossoverRate;
    private int elitismCount;

    public GenerationEvolutionStrategy(ParentSelectionStrategy selectionStrategy, float mutationRate, float crossoverRate, int elitismCount) {
        this.selectionStrategy = selectionStrategy;
        this.mutationRate = mutationRate;
        this.crossoverRate = crossoverRate;
        this.elitismCount = elitismCount;
    }

    @Override
    public List<Chromosome> evolve(List<Chromosome> chromosomes) {
        List<Chromosome> parents = new ArrayList<>(selectionStrategy.select(chromosomes, crossoverRate));
        int populationSize = chromosomes.size();
        List<Chromosome> newPopulation = new ArrayList<>(populationSize);

        newPopulation.addAll(bestN(chromosomes, elitismCount));
        while (newPopulation.size() < populationSize) {
            newPopulation.add(parents.get(ran.nextInt(parents.size())).crossover(parents.get(ran.nextInt(parents.size()))));
        }

        for (Chromosome chromosome : newPopulation) {
            chromosome.mutate(mutationRate);
        }

        return newPopulation;
    }

    private static List<Chromosome> bestN(List<Chromosome> chromosomeList, int count) {
        Collections.sort(chromosomeList);
        return chromosomeList.subList(0, count);
    }
}