package hr.fer.zemris.nenr.genetic;

public interface Chromosome extends Comparable<Chromosome> {
  Chromosome crossover(Chromosome other);

  void mutate(float mutationRate);

  double getFitness();

  @Override
  default int compareTo(Chromosome o) {
    return -1* Double.compare(this.getFitness(), o.getFitness());
  }
}
