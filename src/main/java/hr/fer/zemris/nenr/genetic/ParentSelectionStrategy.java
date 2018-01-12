package hr.fer.zemris.nenr.genetic;

import java.util.List;

public interface ParentSelectionStrategy {
    Chromosome select(List<Chromosome> chromosomes);
}
