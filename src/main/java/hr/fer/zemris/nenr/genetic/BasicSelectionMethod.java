package hr.fer.zemris.nenr.genetic;

import java.util.List;
import java.util.Random;

public class BasicSelectionMethod implements ParentSelectionStrategy {

    @Override
    public Chromosome select(List<Chromosome> chromosomes) {
        Random rand = new Random();
        return chromosomes.get(rand.nextInt(chromosomes.size()));
    }
}
