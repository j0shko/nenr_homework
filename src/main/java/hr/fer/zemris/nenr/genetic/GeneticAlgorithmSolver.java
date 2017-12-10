package hr.fer.zemris.nenr.genetic;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class GeneticAlgorithmSolver {
  private static Random ran = new Random();
  private int populationSize;
  private SelectionStrategy selectionStrategy;
  private float mutationRate;
  private float crossoverRate;
  private int elitismCount;

  private List<Chromosome> currentPopulation;

  public GeneticAlgorithmSolver(int populationSize, float mutationRate, float crossoverRatio, float elitismRate,
                                SelectionStrategy selectionStrategy, ProblemDefinition problemDefinition) {
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

    if (problemDefinition == null) {
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
    currentPopulation = new ArrayList<>(populationSize);

    for (int i = 0; i < populationSize; i++) {
      currentPopulation.add(problemDefinition.generateRandomChromosome());
    }
  }

  public void evolve(int generationCount) {
    for (int i = 0; i < generationCount; i++) {
      evolveOneGeneration();
    }
  }

  public void evolveOneGeneration() {
    List<Chromosome> parents = new ArrayList<>(selectionStrategy.select(currentPopulation, crossoverRate));

    List<Chromosome> newPopulation = new ArrayList<>(populationSize);

    newPopulation.addAll(bestN(currentPopulation, elitismCount));
    while(newPopulation.size() < populationSize) {
      Chromosome parent1 = parents.get(ran.nextInt(parents.size()));
      Chromosome parent2 = parents.get(ran.nextInt(parents.size()));
      newPopulation.add(parent1.crossover(parent2));
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
    return new ArrayList<>(chromosomeList.subList(0, count));
  }
}
