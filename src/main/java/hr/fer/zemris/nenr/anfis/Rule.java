package hr.fer.zemris.nenr.anfis;

import java.util.Random;

public class Rule {
    private static final Random rand = new Random();

    public double a;
    public double b;
    public double c;
    public double d;
    public double p;
    public double q;
    public double r;

    public Rule() {
        a = randomDouble();
        b = randomDouble();
        c = randomDouble();
        d = randomDouble();
        p = randomDouble();
        q = randomDouble();
        r = randomDouble();
    }

    private double randomDouble() {
        return rand.nextDouble() * 2 - 1;
    }
}
