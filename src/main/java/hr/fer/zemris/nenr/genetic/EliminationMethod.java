package hr.fer.zemris.nenr.genetic;

import java.util.List;

public interface EliminationMethod {
    EliminationEvolutionStrategy.Elimination eliminate(List<Chromosome> generation);
}
