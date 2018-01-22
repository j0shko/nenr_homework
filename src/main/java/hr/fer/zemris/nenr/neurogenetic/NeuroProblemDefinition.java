package hr.fer.zemris.nenr.neurogenetic;

import hr.fer.zemris.nenr.genetic.Chromosome;
import hr.fer.zemris.nenr.genetic.ProblemDefinition;
import hr.fer.zemris.nenr.neuro.Example;

import java.util.List;
import java.util.Random;

public class NeuroProblemDefinition implements ProblemDefinition {
    private static final Random ran = new Random();

    public class NeuroChromosome implements Chromosome {
        private double[] params;
        private double fitness;

        public NeuroChromosome(double[] params) {
            this.params = params;
            this.fitness = calcFitness();
        }

        public double[] getParams() {
            return params;
        }

        @Override
        public Chromosome crossover(Chromosome other) {
            double r = ran.nextDouble();
            double[] newParams = new double[params.length];
            double[] otherParams = ((NeuroChromosome) other).params;
            if (r < 0.33) {
                discreteRecombination(newParams, otherParams);
            } else if (r < 0.66) {
                wholeArithmeticRecombination(newParams, otherParams);
            } else {
                simpleArithmeticRecombination(newParams, otherParams);
            }
            return new NeuroChromosome(newParams);
        }

        private void discreteRecombination(double[] newParams, double[] other) {
            for (int i = 0; i < newParams.length; i++) {
                newParams[i] = ran.nextBoolean() ? params[i] : other[i];
            }
        }

        private void wholeArithmeticRecombination(double[] newParams, double[] other) {
            for (int i = 0; i < newParams.length; i++) {
                newParams[i] = (params[i] + other[i]) / 2;
            }
        }

        private void simpleArithmeticRecombination(double[] newParams, double[] other) {
            int point = ran.nextInt(newParams.length);
            for (int i = 0; i < newParams.length; i++) {
                newParams[i] = i >= point ? params[i] : (params[i] + other[i]) / 2;
            }
        }

        @Override
        public void mutate(float mutationRate) {
            for (int i = 0; i < params.length; i++) {
                if (ran.nextFloat() <= mutationRate) {
                    if (ran.nextFloat() <= mutationType) {
                        params[i] += ran.nextGaussian() * sigma1;
                    } else {
                        params[i] = ran.nextGaussian() * sigma2;
                    }
                }
            }
            this.fitness = calcFitness();
        }

        @Override
        public double getFitness() {
            return fitness;
        }

        private double calcFitness() {
            double mse = mse();
            if (mse == 0) return 999999;
            return 1 / mse;
        }

        public double mse() {
            double sum = 0;
            for (Example e : examples) {
                double[] calculated = network.calc(params, e.input);
                for (int i = 0; i < e.output.length; i++) {
                    sum += Math.pow(e.output[i] - calculated[i], 2);
                }
            }
            return sum / examples.size();
        }
    }

    private List<Example> examples;
    private NeuralNetwork network;
    private float mutationType;
    private double sigma1;
    private double sigma2;


    public NeuroProblemDefinition(List<Example> examples, NeuralNetwork network, float mutationType, double sigma1, double sigma2) {
        this.examples = examples;
        this.network = network;
        this.mutationType = mutationType;
        this.sigma1 = sigma1;
        this.sigma2 = sigma2;
    }

    @Override
    public Chromosome generateRandomChromosome() {
        double[] params = new double[network.paramCount()];
        for (int i = 0; i < params.length; i++) {
            params[i] = ran.nextGaussian();
        }
        return new NeuroChromosome(params);
    }
}
