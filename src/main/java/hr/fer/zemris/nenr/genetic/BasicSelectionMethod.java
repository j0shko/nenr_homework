package hr.fer.zemris.nenr.genetic;

import java.util.List;

public class BasicSelectionMethod implements SelectionStrategy {
  @Override
  public List<Chromosome> select(List<Chromosome> chromosomes, float crossoverRatio) {
    return chromosomes.subList(0, Math.round(chromosomes.size() * crossoverRatio));
  }
}
