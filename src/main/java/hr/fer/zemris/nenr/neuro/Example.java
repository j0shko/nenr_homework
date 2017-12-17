package hr.fer.zemris.nenr.neuro;

import java.util.Arrays;

public class Example {
    public final double[] input;
    public final double[] output;

    public Example(double[] input, double[] output) {
        this.input = input;
        this.output = output;
    }

    @Override
    public String toString() {
        return Arrays.toString(input) + "\t" + Arrays.toString(output);
    }
}
