package hr.fer.zemris.nenr.genetic;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class GeneticProblemSolver {
  private static Random ran = new Random();
  private int populationSize;
  private SelectionStrategy selectionStrategy;
  private float mutationRate;
  private float crossoverRate;
  private int elitismCount;
  private ChromosomeProblemDefinition chromosomeProblemDefinition;

  private List<Chromosome> currentPopulation;

  public GeneticProblemSolver(int populationSize, float mutationRate, float crossoverRatio, float elitismRate,
                              SelectionStrategy selectionStrategy, ChromosomeProblemDefinition chromosomeProblemDefinition) {
    if (mutationRate < 0 || mutationRate > 1) {
      throw new IllegalArgumentException("Mutation rate must be between 0 and 1");
    }
    if (crossoverRatio < 0 || crossoverRatio > 1) {
      throw new IllegalArgumentException("Crossover ratio must be between 0 and 1");
    }
    if (populationSize <= 0) {
      throw new IllegalArgumentException("Population size must be positive integer");
    }
    if (elitismRate <= 0) {
      throw new IllegalArgumentException("Elitism rate must be between 0 and 1");
    }

    if (chromosomeProblemDefinition == null) {
      throw new IllegalArgumentException("Problem must be defined");
    }

    if (selectionStrategy == null) {
      this.selectionStrategy = new BasicSelectionMethod();
    } else {
      this.selectionStrategy = selectionStrategy;
    }

    this.populationSize = populationSize;
    this.mutationRate = mutationRate;
    this.crossoverRate = crossoverRatio;
    this.elitismCount = Math.round(elitismRate * populationSize);
    this.chromosomeProblemDefinition = chromosomeProblemDefinition;
    currentPopulation = new ArrayList<>(populationSize);
  }

  public void evolve(int generationCount) {
    for (int i = 0; i < populationSize; i++) {
      currentPopulation.add(chromosomeProblemDefinition.generateRandomChromosome());
    }

    for (int i = 0; i < generationCount; i++) {
      evolveOneGeneration();
    }
  }

  public void evolveOneGeneration() {
    List<Chromosome> parents = selectionStrategy.select(currentPopulation, crossoverRate);

    List<Chromosome> newPopulation = new ArrayList<>(populationSize);

    newPopulation.addAll(bestN(currentPopulation, elitismCount));
    while(newPopulation.size() < populationSize) {
      newPopulation.add(parents.get(ran.nextInt(parents.size())).crossover(parents.get(ran.nextInt(parents.size()))));
    }

    for (Chromosome chromosome : newPopulation) {
      chromosome.mutate(mutationRate);
    }

    currentPopulation = newPopulation;
  }

  public List<Chromosome> getCurrentPopulation() {
    return currentPopulation;
  }

  public Chromosome getBestInCurrentPopulation() {
    return currentPopulation.stream().sorted().findFirst().get();
  }

  private static List<Chromosome> bestN(List<Chromosome> chromosomeList, int count) {
    Collections.sort(chromosomeList);
    return chromosomeList.subList(0, count);
  }
}
