package hr.fer.zemris.nenr.neuro;

import java.util.Random;

public class Neuron {
    public static final Random rand = new Random();

    private double[] w;
    private ActivationFunction activationFunction;

    private double delta;
    private double output;

    public Neuron(double[] w, ActivationFunction activationFunction) {
        this.w = w;
        this.activationFunction = activationFunction;
    }

    public Neuron(double[] w) {
        this(w, new SigmoidActivationFunction());
    }

    public Neuron(int size) {
        double[] init = new double[size];
        for (int i = 0; i < size; i++) {
            init[i] = rand.nextDouble() * 4 - 2;
        }
        this.w = init;
        this.activationFunction = new SigmoidActivationFunction();
    }

    public double[] getW() {
        return w;
    }

    public void setW(double[] w) {
        this.w = w;
    }

    public double getDelta() {
        return delta;
    }

    public void setDelta(double delta) {
        this.delta = delta;
    }

    public double getOutput() {
        return output;
    }

    public void updateWeight(double[] input, double learnRate) {
        for (int i = 0; i < w.length; i++) {
            w[i] += learnRate * delta * input[i];
        }
    }

    public double calc(double[] input) {
        if (input.length != w.length) throw new IllegalArgumentException("Input not the same size as weights");
        double sum = 0;
        for (int i = 0; i < w.length; i++) {
            sum += w[i] * input[i];
        }
        this.output = activationFunction.calc(sum);
        return this.output;
    }
}
