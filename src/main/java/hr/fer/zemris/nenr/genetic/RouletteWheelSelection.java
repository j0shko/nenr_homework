package hr.fer.zemris.nenr.genetic;

import java.util.*;
import java.util.stream.Collectors;

public class RouletteWheelSelection implements SelectionStrategy {
  @Override
  public List<Chromosome> select(List<Chromosome> chromosomes, float crossoverRatio) {
    double fitnessSum = chromosomes.stream().mapToDouble(Chromosome::getFitness).sum();
    chromosomes = chromosomes.stream().sorted().collect(Collectors.toList());

    // create roulette wheel list with chromosomes
    List<ProbabilityEntry<Chromosome>> rouletteWheelList = new ArrayList<>();
    double previousProbability = 0f;
    for (Chromosome c : chromosomes) {
      double probability = previousProbability + (c.getFitness() + 1) / fitnessSum;
      rouletteWheelList.add(new ProbabilityEntry<>(c, probability));
      previousProbability = probability;
    }

    // pick n different chromosomes
    int parentCount = Math.round(crossoverRatio * chromosomes.size());
    Set<Chromosome> chosenOnes = new HashSet<>();
    Random ran = new Random();
    while (chosenOnes.size() < parentCount) {
      float random = ran.nextFloat();
      for (ProbabilityEntry<Chromosome> pc : rouletteWheelList) {
        if (random <= pc.probabilityMargin) {
          chosenOnes.add(pc.entry);
          break;
        }
      }
    }

    return new ArrayList<>(chosenOnes);
  }

  private class ProbabilityEntry<T> {
    T entry;
    double probabilityMargin;

    public ProbabilityEntry(T entry, double probabilityMargin) {
      this.entry = entry;
      this.probabilityMargin = probabilityMargin;
    }
  }
}
