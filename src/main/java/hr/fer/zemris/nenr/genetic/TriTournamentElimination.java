package hr.fer.zemris.nenr.genetic;


import java.util.*;

public class TriTournamentElimination implements EliminationMethod {

    private ParentSelectionStrategy selectionStrategy;

    public TriTournamentElimination(ParentSelectionStrategy selectionStrategy) {
        this.selectionStrategy = selectionStrategy;
    }

    @Override
    public EliminationEvolutionStrategy.Elimination eliminate(List<Chromosome> generation) {
        List<Chromosome> attendees = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            attendees.add(selectionStrategy.select(generation));
        }
        Collections.sort(attendees);
        Chromosome firstParent = attendees.get(0);
        Chromosome secondParent = attendees.get(1);

        Chromosome child = firstParent.crossover(secondParent);

        return new EliminationEvolutionStrategy.Elimination(List.of(child), List.of(attendees.get(2)));
    }
}
