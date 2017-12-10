package hr.fer.zemris.nenr.genetic;

import java.util.List;

public class EliminationEvolutionStrategy implements EvolutionStrategy {

    static class Elimination {
        private final List<Chromosome> toAdd;
        private final List<Chromosome> toDelete;

        Elimination(List<Chromosome> toAdd, List<Chromosome> toDelete) {
            this.toAdd = toAdd;
            this.toDelete = toDelete;
        }
    }

    private float mutationRate;
    private EliminationMethod eliminationMethod;

    public EliminationEvolutionStrategy(float mutationRate, EliminationMethod eliminationMethod) {
        this.mutationRate = mutationRate;
        this.eliminationMethod = eliminationMethod;
    }

    @Override
    public List<Chromosome> evolve(List<Chromosome> chromosomes) {
        Elimination elimination = eliminationMethod.eliminate(chromosomes);
        chromosomes.removeAll(elimination.toDelete);
        elimination.toAdd.forEach(c -> c.mutate(mutationRate));
        chromosomes.addAll(elimination.toAdd);
        return chromosomes;
    }
}