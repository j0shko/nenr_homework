package hr.fer.zemris.nenr.anfis.example;

import hr.fer.zemris.nenr.anfis.Anfis;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.function.DoubleBinaryOperator;

public class Lab6 {

    private static final int RULES_COUNT = 10;
    private static final double THRESHOLD = 0.02;
    private static final int MAX_ITER = 1_000_000;
    private static final boolean STOCHASTIC = false;

    public static final DoubleBinaryOperator f = (x, y) -> (Math.pow(x - 1, 2) + Math.pow(y + 2, 2) - 5*x*y + 3) * Math.pow(Math.cos(x / 5), 2);

    public static void main(String[] args) throws IOException {
        List<Example> examples = new ArrayList<>();

        Path p = Paths.get("./anfis/data.txt");
        List<String> lines = Files.readAllLines(p);
        for (String line : lines) {
            String[] parts = line.split("\t");
            examples.add(new Example(Double.parseDouble(parts[0]), Double.parseDouble(parts[1]), Double.parseDouble(parts[2])));
        }

        Anfis anfis = new Anfis(RULES_COUNT);
        anfis.learn(examples, 0.01, STOCHASTIC, THRESHOLD, MAX_ITER);
        System.out.println("MSE = " + anfis.mse(examples));
    }
}
