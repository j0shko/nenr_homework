package hr.fer.zemris.nenr.anfis.example;

import hr.fer.zemris.nenr.anfis.Anfis;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.function.DoubleBinaryOperator;

public class Lab6 {

    private static final int RULES_COUNT = 10;
    private static final double THRESHOLD = 0.000002;
    private static final int MAX_ITER = 100_000;
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

//        saveRules(anfis);
//        saveErrors(anfis, examples);
//        mseTest(anfis, examples);
//        etaTest(examples);
    }

    private static void saveRules(Anfis anfis) throws IOException {
        Path rulesPath = Paths.get("./reports/rules.txt");
        Files.write(rulesPath, anfis.getRules().getBytes(Charset.forName("UTF-8")));
    }

    private static void saveErrors(Anfis anfis, List<Example> examples) throws IOException {
        StringBuilder errors = new StringBuilder();
        for (int i = 0; i < examples.size(); i++) {
            Example e = examples.get(i);
            double error = anfis.calc(e.x, e.y) - e.z;
            errors.append(error).append((i + 1) % 9 == 0 ? "\n" : " ");
        }

        Path errorsPath = Paths.get("./reports/errors.txt");
        Files.write(errorsPath, errors.toString().getBytes(Charset.forName("UTF-8")));
    }

    private static void mseTest(Anfis anfis, List<Example> examples) throws IOException {
        Path mses1Path = Paths.get("./reports/mses_" + (STOCHASTIC ? "st" : "gr") + ".txt");
        Files.write(mses1Path, anfis.getMses().getBytes(Charset.forName("UTF-8")));

        Anfis anfisOther = new Anfis(RULES_COUNT);
        anfisOther.learn(examples, 0.01, !STOCHASTIC, THRESHOLD, MAX_ITER);
        Path mses2Path = Paths.get("./reports/mses_" + (!STOCHASTIC ? "st" : "gr") + ".txt");
        Files.write(mses2Path, anfisOther.getMses().getBytes(Charset.forName("UTF-8")));
    }

    private static void etaTest(List<Example> examples) throws IOException {
        Anfis anfis1 = new Anfis(RULES_COUNT);
        anfis1.learn(examples, 0.01, false, THRESHOLD, MAX_ITER);
        Path etas1Path = Paths.get("./reports/etas_1.txt");
        Files.write(etas1Path, anfis1.getMses().getBytes(Charset.forName("UTF-8")));

        Anfis anfis2 = new Anfis(RULES_COUNT);
        anfis2.learn(examples, 0.001, false, THRESHOLD, MAX_ITER);
        Path etas2Path = Paths.get("./reports/etas_2.txt");
        Files.write(etas2Path, anfis2.getMses().getBytes(Charset.forName("UTF-8")));

        Anfis anfis3 = new Anfis(RULES_COUNT);
        anfis3.learn(examples, 0.0001, false, THRESHOLD, MAX_ITER);
        Path etas3Path = Paths.get("./reports/etas_3.txt");
        Files.write(etas3Path, anfis3.getMses().getBytes(Charset.forName("UTF-8")));
    }
}
