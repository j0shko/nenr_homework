package hr.fer.zemris.nenr.neuro;

import java.util.Random;

public class TrainableNeuron {
    public static final Random rand = new Random();

    private double[] w;
    private ActivationFunction activationFunction;

    private double delta;
    private double output;

    private double[] dw;

    public TrainableNeuron(double[] w, ActivationFunction activationFunction) {
        this.w = w;
        this.activationFunction = activationFunction;

        this.dw = new double[w.length];
    }

    public TrainableNeuron(double[] w) {
        this(w, new SigmoidActivationFunction());
    }

    public TrainableNeuron(int size) {
        double[] init = new double[size];
        for (int i = 0; i < size; i++) {
            init[i] = rand.nextDouble() * 4 - 2;
        }
        this.w = init;
        this.activationFunction = new SigmoidActivationFunction();
        this.dw = new double[size];
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

    public void updateWeight(double learnRate) {
        for (int i = 0; i < w.length; i++) {
            w[i] += learnRate * dw[i];
        }
        dw = new double[w.length];
    }

    public void updateDeltaW(double[] input) {
        for (int i = 0; i < w.length; i++) {
            dw[i] += delta * input[i];
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
