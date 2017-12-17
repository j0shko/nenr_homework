package hr.fer.zemris.nenr.neuro;

public interface ActivationFunction {

    double calc(double x);

    // expected input is result of calc
    double calcDerivation(double x);
}
