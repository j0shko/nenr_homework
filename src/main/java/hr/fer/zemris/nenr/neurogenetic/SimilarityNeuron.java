package hr.fer.zemris.nenr.neurogenetic;

import java.util.Arrays;
import java.util.Random;

public class SimilarityNeuron implements Neuron {
    private static final Random rand = new Random();

    private double[] w;
    private double[] s;

    public SimilarityNeuron(double[] w, double[] s) {
        this.w = w;
        this.s = s;
    }

    public SimilarityNeuron(int size) {
        double[] w = new double[size];
        double[] s = new double[size];
        for (int i = 0; i < size; i++) {
            w[i] = rand.nextDouble() * 4 - 2;
            double num;
            do {
                num = rand.nextDouble();
            } while (num == 0);
            s[i] = num;
        }
        this.w = w;
        this.s = s;
    }

    @Override
    public double calc(double[] input) {
        double sum = 0;
        for (int i = 0; i < input.length; i++) {
            sum += Math.abs(input[i] - w[i]) / Math.abs(s[i]);
        }
        return 1 / (1 + sum);
    }

    @Override
    public double[] getParams() {
        double[] params = new double[2 * w.length];
        for (int i = 0; i < w.length; i++) {
            params[i] = w[i];
            params[i + w.length] = s[i];
        }
        return params;
    }

    @Override
    public int getParamsSize() {
        return w.length + s.length;
    }

    @Override
    public void setParams(double[] params) {
        if (params.length != w.length * 2) throw new IllegalArgumentException("Params array is wrong size");
        this.w = Arrays.copyOfRange(params, 0, params.length / 2);
        this.s = Arrays.copyOfRange(params, params.length / 2, params.length);
    }

    public double[] getS() {
        return s;
    }

    public double[] getW() {
        return w;
    }
}
