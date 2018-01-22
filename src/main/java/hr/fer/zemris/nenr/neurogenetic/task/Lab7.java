package hr.fer.zemris.nenr.neurogenetic.task;

import hr.fer.zemris.nenr.genetic.*;
import hr.fer.zemris.nenr.neuro.Example;
import hr.fer.zemris.nenr.neurogenetic.NeuralNetwork;
import hr.fer.zemris.nenr.neurogenetic.NeuroProblemDefinition;
import hr.fer.zemris.nenr.neurogenetic.SimilarityNeuron;
import hr.fer.zemris.nenr.neurogenetic.WeightNeuron;

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

        NeuralNetwork neuralNetwork = new NeuralNetwork(2, 6, 4, 3);
        ProblemDefinition problemDefinition = new NeuroProblemDefinition(examples, neuralNetwork, MUTATION_TYPE_PROBABILITY, SIGMA_1, SIGMA_2);
        EvolutionStrategy evolutionStrategy = new GenerationEvolutionStrategy(new RouletteWheelSelection(), MUTATION_RATE, ELITISM_RATE);
        GeneticProblemSolver ga = new GeneticProblemSolver(POPULATION_SIZE, evolutionStrategy, problemDefinition);

        ga.evolve(20_000);

        NeuroProblemDefinition.NeuroChromosome best = (NeuroProblemDefinition.NeuroChromosome) ga.getBestInCurrentPopulation();
        System.out.println("Error: " + best.mse());
        neuralNetwork.setParams(best.getParams());


        System.out.println("Layer 1 Ws: ");
        neuralNetwork.getLayers().get(0).getNeurons().forEach(n -> System.out.println(Arrays.toString(((SimilarityNeuron) n).getW())));

        System.out.println("Layer 1 Ss: ");
        neuralNetwork.getLayers().get(0).getNeurons().forEach(n -> System.out.println(Arrays.toString(((SimilarityNeuron) n).getS())));

        System.out.println("Layer 2 ws:");
        neuralNetwork.getLayers().get(1).getNeurons().forEach(n -> System.out.println(Arrays.toString(((WeightNeuron) n).getW())));

        int correct = 0;
        for (Example example: examples) {
            double[] output = neuralNetwork.calc(best.getParams(), example.input);
            for (int i = 0; i < output.length; i++) {
                output[i] = Math.round(output[i]);
            }
            if (Arrays.equals(output, example.output)) {
                correct++;
            }
        }
        System.out.println("Number of correctly classified examples: " + correct);
    }
}
