package hr.fer.zemris.nenr.genetic.example;

import hr.fer.zemris.nenr.genetic.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Lab4 {
    private static final boolean ELIMINATION = true;
    private static final float MUTATION_RATE = 0.1f;
    private static final float CROSSOVER_RATE = 0.3f;
    private static final int POPULATION_SIZE = 20;
    private static final int ELITISM_RATE = 1;

    public static void main(String[] args) throws IOException {
        Path examplesPath = Paths.get("./geneticExamples/examples1.txt");
        List<List<Float>> examples = new ArrayList<>();
        List<String> lines = Files.readAllLines(examplesPath);
        for (String line: lines) {
            examples.add(Arrays.stream(line.split("\\t")).map(Float::parseFloat).collect(Collectors.toList()));
        }

        ProblemDefinition problem = new ParameterProblemDefinition(-4f, 4f, 5, function(), examples);
        GeneticProblemSolver ga = new GeneticProblemSolver(POPULATION_SIZE, getEvolutionStrategy(), problem);
        ga.evolve(10_000);

        ParameterProblemDefinition.ParameterChromosome result = (ParameterProblemDefinition.ParameterChromosome) ga.getBestInCurrentPopulation();
        System.out.println("Resulting betas: " + result);
        System.out.println("Error: " + result.getMeanSquareError());

        List<Float> example = List.of(2.0844105010240943f, -3.492457339859441f, 0.1156763907610976f);
        System.out.println("Example: " + function().apply(example,result.getBetas()) + " ~ " + example.get(2));
    }

    public static ParametrizedFunction function() {
        return ((parameters, betas) -> {
            float x = parameters.get(0);
            float y = parameters.get(1);

            return (float) (Math.sin(betas[0] + betas[1] * x) + betas[2] * Math.cos(x * (betas[3] + y)) * (1 / (1 + Math.pow(Math.E, Math.pow(x - betas[4], 2)))));
        });
    }

    public static EvolutionStrategy getEvolutionStrategy() {
        if (ELIMINATION) {
            return new EliminationEvolutionStrategy(MUTATION_RATE, new TriTournamentElimination());
        } else {
            return new GenerationEvolutionStrategy(new BasicSelectionMethod(), MUTATION_RATE, CROSSOVER_RATE, ELITISM_RATE);
        }
    }
}
