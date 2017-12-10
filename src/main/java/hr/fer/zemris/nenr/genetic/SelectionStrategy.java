package hr.fer.zemris.nenr.genetic;

import java.util.List;

public interface SelectionStrategy {
  List<Chromosome> select(List<Chromosome> chromosomes, float crossoverRatio);
}
