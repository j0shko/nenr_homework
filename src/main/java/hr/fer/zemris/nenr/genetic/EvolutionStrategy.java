package hr.fer.zemris.nenr.genetic;

import java.util.List;

public interface EvolutionStrategy {
  // should return modified list if difference is not big
  List<Chromosome> evolve(List<Chromosome> chromosomes);
}