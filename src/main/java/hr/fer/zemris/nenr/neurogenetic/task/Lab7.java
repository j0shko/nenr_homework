package hr.fer.zemris.nenr.neurogenetic.task;

import hr.fer.zemris.nenr.genetic.*;
import hr.fer.zemris.nenr.neuro.Example;
import hr.fer.zemris.nenr.neurogenetic.NeuralNetwork;
import hr.fer.zemris.nenr.neurogenetic.NeuroProblemDefinition;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Lab7 {

    private static final float MUTATION_TYPE_PROBABILITY = 0.65f;
    private static final double SIGMA_1 = 0.4;
    private static final double SIGMA_2 = 0.8;

    private static final float MUTATION_RATE = 0.01f;
    private static final int POPULATION_SIZE = 30;
    private static final int ELITISM_RATE = 3;

    public static void main(String[] args) throws IOException {

        Path dataset = Paths.get("./datasets/dataset-7.txt");

        List<Example> examples = new ArrayList<>();
        for (String line : Files.readAllLines(dataset)) {
            String[] parts = line.split("\t");
            List<Double> d = Arrays.stream(parts).map(Double::parseDouble).collect(Collectors.toList());
            examples.add(new Example(
                    new double[] {d.get(0), d.get(1)},
                    new double[] {d.get(2), d.get(3), d.get(4)})
            );
        }

        NeuralNetwork neuralNetwork = new NeuralNetwork(2, 8, 3);
        ProblemDefinition problemDefinition = new NeuroProblemDefinition(examples, neuralNetwork, MUTATION_TYPE_PROBABILITY, SIGMA_1, SIGMA_2);
        EvolutionStrategy evolutionStrategy = new GenerationEvolutionStrategy(new RouletteWheelSelection(), MUTATION_RATE, ELITISM_RATE);
        GeneticProblemSolver ga = new GeneticProblemSolver(POPULATION_SIZE, evolutionStrategy, problemDefinition);

        ga.evolve(40_000);

        NeuroProblemDefinition.NeuroChromosome best = (NeuroProblemDefinition.NeuroChromosome) ga.getBestInCurrentPopulation();
        System.out.println("Error: " + best.mse());

        double[] output = neuralNetwork.calc(best.getParams(), examples.get(0).input);
        System.out.println("Example : " + Arrays.toString(output) + " ~ " + Arrays.toString(examples.get(0).output));
    }
}
