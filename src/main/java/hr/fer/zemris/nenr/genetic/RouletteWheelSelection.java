package hr.fer.zemris.nenr.genetic;

import java.util.*;

public class RouletteWheelSelection implements ParentSelectionStrategy {
  @Override
  public Chromosome select(List<Chromosome> chromosomes) {
    float fitnessSum = (float) chromosomes.stream().mapToDouble(Chromosome::getFitness).sum();

    Random ran = new Random();
    float random = ran.nextFloat() * fitnessSum;

    for (int i = chromosomes.size() - 1; i >= 0; i--) {
        Chromosome c = chromosomes.get(i);
        random -= c.getFitness();
        if (random <= 0) return c;
    }
    return chromosomes.get(chromosomes.size() - 1);
  }
}
