package hr.fer.zemris.nenr.genetic;

import java.util.*;

public class TriTournamentElimination implements EliminationMethod {

    private static Random ran = new Random();

    @Override
    public EliminationEvolutionStrategy.Elimination eliminate(List<Chromosome> generation) {
        Set<Chromosome> attendeesSet = new TreeSet<>();
        while (attendeesSet.size() < 3) {
            attendeesSet.add(generation.get(ran.nextInt(generation.size())));
        }
        List<Chromosome> attendeesList = new ArrayList<>(attendeesSet);
        Chromosome firstParent = attendeesList.get(0);
        Chromosome secondParent = attendeesList.get(1);

        Chromosome child = firstParent.crossover(secondParent);

        return new EliminationEvolutionStrategy.Elimination(List.of(child), List.of(attendeesList.get(2)));
    }
}
