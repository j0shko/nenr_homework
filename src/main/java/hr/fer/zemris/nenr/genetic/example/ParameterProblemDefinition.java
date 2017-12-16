package hr.fer.zemris.nenr.genetic.example;

import hr.fer.zemris.nenr.genetic.Chromosome;
import hr.fer.zemris.nenr.genetic.ProblemDefinition;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class ParameterProblemDefinition implements ProblemDefinition {
    private static final Random ran = new Random();

    private float parameterMin;
    private float parameterMax;
    private int parameterCount;
    private ParametrizedFunction function;
    private List<List<Float>> testExamples;

    public class ParameterChromosome implements Chromosome {

        private float[] betas;

        public ParameterChromosome(float[] betas) {
            this.betas = betas;
        }

        @Override
        public Chromosome crossover(Chromosome other) {
            float[] childBetas = new float[betas.length];
            for (int i = 0; i < betas.length; i++) {
                childBetas[i] = (betas[i] + ((ParameterChromosome) other).betas[i]) / 2;
            }
            return new ParameterChromosome(childBetas);
        }

        @Override
        public void mutate(float mutationRate) {
            if (ran.nextFloat() <= mutationRate) {
                int i = ran.nextInt(betas.length);
                betas[i] = clamp((float) (betas[i] + (ran.nextBoolean() ? 1 : -1) * 0.2 * (parameterMax - parameterMin)), parameterMin, parameterMax);
            }
        }

        private float clamp(float start, float end, float value) {
            if (value < start) return start;
            if (value > end) return end;
            return value;
        }

        @Override
        public double getFitness() {
            float error = getMeanSquareError();
            if (error == 0) return 0;
            return 1 / error;
        }

        public float[] getBetas() {
            return betas;
        }

        public float getMeanSquareError() {
            float error = 0;
            for (List<Float> example : testExamples) {
                List<Float> parameters = example.subList(0, example.size() - 1);
                float result = example.get(example.size() - 1);
                error += Math.pow(result - function.apply(parameters, betas), 2);
            }
            error /= testExamples.size();
            return error;
        }

        @Override
        public String toString() {
            return Arrays.toString(betas);
        }
    }

    public ParameterProblemDefinition(float parameterMin, float parameterMax, int parameterCount, ParametrizedFunction function, List<List<Float>> testExamples) {
        this.parameterMin = parameterMin;
        this.parameterMax = parameterMax;
        this.parameterCount = parameterCount;
        this.function = function;
        this.testExamples = testExamples;
    }

    @Override
    public Chromosome generateRandomChromosome() {
        float[] randBetas = new float[parameterCount];
        for (int i = 0; i < parameterCount; i++) {
            randBetas[i] = ran.nextFloat() * (parameterMax - parameterMin) + parameterMin;
        }
        return new ParameterChromosome(randBetas);
    }
}
