package hr.fer.zemris.nenr.neuro;

import java.util.function.DoubleUnaryOperator;

public class Neuron {
    public static final DoubleUnaryOperator SIGMOID = x -> 1 / (1 + Math.exp(-1 * x));

    private double[] w;
    private DoubleUnaryOperator activationFunction;

    public Neuron(double[] w, DoubleUnaryOperator actvationFunction) {
        this.w = w;
        this.activationFunction = actvationFunction;
    }

    public Neuron(double[] w) {
        this(w, SIGMOID);
    }

    public Neuron(int size) {
        this(new double[size]);
    }

    public double calc(double[] input) {
        if (input.length != w.length) throw new IllegalArgumentException("Input not the same size as weights");
        double sum = 0;
        for (int i = 0; i < w.length; i++) {
            sum += w[i] * input[i];
        }
        return activationFunction.applyAsDouble(sum);
    }
}
