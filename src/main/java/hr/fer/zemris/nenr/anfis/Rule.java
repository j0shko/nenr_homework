package hr.fer.zemris.nenr.anfis;

import java.util.Random;

public class Rule {
    private static final Random rand = new Random();

    public double a, b, c, d, p, q, r;

    public double dA, dB, dC, dD, dP, dQ, dR;

    public Rule() {
        a = randomDouble();
        b = randomDouble();
        c = randomDouble();
        d = randomDouble();
        p = randomDouble();
        q = randomDouble();
        r = randomDouble();

        dA = dB = dC = dD = dP = dQ = dR = 0;
    }

    private double randomDouble() {
        return rand.nextDouble() * 2 - 1;
    }

    public void update(double learningRate) {
        a += 0.1 * learningRate * dA;
        b += 0.1 * learningRate * dB;
        c += 0.1 * learningRate * dC;
        d += 0.1 * learningRate * dD;
        p += learningRate * dP;
        q += learningRate * dQ;
        r += learningRate * dR;
        dA = dB = dC = dD = dP = dQ = dR = 0;
    }
}
