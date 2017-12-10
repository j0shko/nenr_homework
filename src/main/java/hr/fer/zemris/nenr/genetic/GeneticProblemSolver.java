package hr.fer.zemris.nenr.genetic;

import java.util.ArrayList;
import java.util.List;

public class GeneticProblemSolver {
  private int populationSize;
  private EvolutionStrategy evolutionStrategy;
  private ProblemDefinition chromosomeProblemDefinition;

  private List<Chromosome> currentPopulation;

  public GeneticProblemSolver(int populationSize, EvolutionStrategy evolutionStrategy, ProblemDefinition chromosomeProblemDefinition) {
    if (populationSize <= 0) {
      throw new IllegalArgumentException("Population size must be positive integer");
    }

    if (chromosomeProblemDefinition == null) {
      throw new IllegalArgumentException("Problem must be defined");
    }

    this.evolutionStrategy = evolutionStrategy;
    this.populationSize = populationSize;
    this.chromosomeProblemDefinition = chromosomeProblemDefinition;
    currentPopulation = new ArrayList<>(populationSize);
  }

  public void evolve(int generationCount) {
    for (int i = 0; i < populationSize; i++) {
      currentPopulation.add(chromosomeProblemDefinition.generateRandomChromosome());
    }

    for (int i = 0; i < generationCount; i++) {
      currentPopulation = evolutionStrategy.evolve(currentPopulation);
    }
  }

  public List<Chromosome> getCurrentPopulation() {
    return currentPopulation;
  }

  public Chromosome getBestInCurrentPopulation() {
    return currentPopulation.stream().sorted().findFirst().orElse(null);
  }
}
