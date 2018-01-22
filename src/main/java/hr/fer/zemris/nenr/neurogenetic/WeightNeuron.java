package hr.fer.zemris.nenr.neurogenetic;

import hr.fer.zemris.nenr.neuro.ActivationFunction;
import hr.fer.zemris.nenr.neuro.SigmoidActivationFunction;

import java.util.Random;

public class WeightNeuron implements Neuron {
    private static final Random rand = new Random();

    private double[] w;
    private ActivationFunction activationFunction;

    public WeightNeuron(double[] w, ActivationFunction activationFunction) {
        this.w = w;
        this.activationFunction = activationFunction;
    }

    public WeightNeuron(double[] w) {
        this(w, new SigmoidActivationFunction());
    }

    public WeightNeuron(int size) {
        double[] init = new double[size + 1];
        for (int i = 0; i < size + 1; i++) {
            init[i] = rand.nextDouble() * 4 - 2;
        }
        this.w = init;
        this.activationFunction = new SigmoidActivationFunction();
    }

    public double calc(double[] input) {
        if (input.length + 1 != w.length) throw new IllegalArgumentException("Input not the same size as weights");
        double sum = 0;
        sum += w[0];
        for (int i = 0; i < w.length - 1; i++) {
            sum += w[i + 1] * input[i];
        }
        return activationFunction.calc(sum);
    }

    @Override
    public double[] getParams() {
        return w;
    }

    @Override
    public int getParamsSize() {
        return w.length;
    }

    public void setParams(double[] params) {
        this.w = params;
    }

    public double[] getW() {
        return w;
    }
}
