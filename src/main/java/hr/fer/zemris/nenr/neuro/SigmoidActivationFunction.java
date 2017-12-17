package hr.fer.zemris.nenr.neuro;

public class SigmoidActivationFunction implements ActivationFunction {

    @Override
    public double calc(double x) {
        return  1 / (1 + Math.exp(-1 * x));
    }

    @Override
    public double calcDerivation(double value) {
        return value * (1 - value);
    }
}
