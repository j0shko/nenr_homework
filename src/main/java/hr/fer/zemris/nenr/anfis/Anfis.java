package hr.fer.zemris.nenr.anfis;

import hr.fer.zemris.nenr.anfis.example.Example;

import java.util.ArrayList;
import java.util.List;

public class Anfis {

    private static final double THRESHOLD = 0.2;

    private List<Rule> rules;

    public Anfis(int ruleCount) {
        rules = new ArrayList<>();
        for (int i = 0; i < ruleCount; i++) {
            rules.add(new Rule());
        }
    }

    public double calc(double x, double y) {
        return this.calcWithDetails(x, y).output;
    }

    private Result calcWithDetails(double x, double y) {
        List<Details> detailsList = new ArrayList<>(rules.size());
        double sumW = 0;

        for (Rule rule : rules) {
            double alfa = transfer(rule.a, rule.b, x);
            double beta = transfer(rule.c, rule.d, y);
            double w = tNorm(alfa, beta);
            double f = rule.p * x + rule.q * y + rule.r;
            sumW += w;
            detailsList.add(new Details(alfa, beta, w, f));
        }

        double resultSum = 0;
        for (Details d : detailsList) {
            resultSum += d.w * d.f;
        }

        return new Result(resultSum / sumW, detailsList, resultSum, sumW);
    }

    private static class Details {
        public double alfa;
        public double beta;
        public double w;
        public double f;

        public Details(double alfa, double beta, double w, double f) {
            this.alfa = alfa;
            this.beta = beta;
            this.w = w;
            this.f = f;
        }
    }

    private static class Result {
        public double output;
        public List<Details> details;
        public double resultSum;
        public double sumW;

        public Result(double output, List<Details> details, double resultSum, double sumW) {
            this.output = output;
            this.details = details;
            this.resultSum = resultSum;
            this.sumW = sumW;
        }
    }

    public double transfer(double a, double b, double x) {
        return 1. / (1 + Math.exp(b * (x - a)));
    }

    public double tNorm(double a, double b) {
        return a * b;
    }

    public void learn(List<Example> examples, double learningRate) {
        double mse;
        double minmse = 999999;
        long iter = 0;
        do {
            mse = 0;
            for (Example example : examples) {
                Result result = this.calcWithDetails(example.x, example.y);
                double error = example.z - result.output;
                mse += Math.pow(error, 2);

                for (int i = 0; i < rules.size(); i++) {
                    Details det = result.details.get(i);

                    double fsum = 0;
                    for (int j = 0; j < rules.size(); j++) {
                        Details other = result.details.get(j);
                        fsum += other.w * (det.f - other.f);
                    }

                    Rule rule = rules.get(i);

                    double a = rule.a, b = rule.b, c = rule.c, d = rule.d;
                    double prod1 = learningRate * error * det.w;
                    double prod2 = prod1 * (fsum / Math.pow(result.sumW, 2));

                    rule.a += 0.01 * prod2 * (1 - det.alfa) * b;
                    rule.b += 0.01 * prod2 * (1 - det.alfa) * (example.x - a);

                    rule.c += 0.01 * prod2 * (1 - det.beta) * d;
                    rule.d += 0.01 * prod2 * (1 - det.beta) * (example.y - c);

                    rule.p += prod1 * example.x / result.sumW;
                    rule.q += prod1 * example.y / result.sumW;
                    rule.r += prod1 / result.sumW;
                }
            }
            mse /= examples.size();
            if (mse < minmse) {
                minmse = mse;
                System.out.println(iter + ": " +minmse);
            }
            iter++;
//            System.out.println("MSE = " + mse);
        } while (mse > THRESHOLD);
    }
}
