package hr.fer.zemris.nenr.neurogenetic;

public interface Neuron {
    double calc(double[] input);
    double[] getParams();
    int getParamsSize();
    void setParams(double[] params);
}
