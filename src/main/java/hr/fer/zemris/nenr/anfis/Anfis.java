package hr.fer.zemris.nenr.anfis;

import hr.fer.zemris.nenr.anfis.example.Example;

import java.util.ArrayList;
import java.util.List;

public class Anfis {

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

    public void learn(List<Example> examples, double learningRate, boolean stochastic, double threshold, int maxIter) {
        double mse;
        int iter = 0;
        do {
            if (stochastic) {
                Example example = examples.get(iter % examples.size());
                calcError(example);
            } else {
                for (Example example : examples) {
                    calcError(example);
                }
            }
            updateRules(learningRate);
            mse = mse(examples);
            iter++;
            System.out.println(iter + ": " + mse);
        } while (mse > threshold && iter < maxIter);
    }

    private void calcError(Example example) {
        Result result = this.calcWithDetails(example.x, example.y);
        double error = example.z - result.output;

        for (int i = 0; i < rules.size(); i++) {
            Details det = result.details.get(i);

            double fsum = 0;
            for (int j = 0; j < rules.size(); j++) {
                Details other = result.details.get(j);
                fsum += other.w * (det.f - other.f);
            }

            Rule rule = rules.get(i);

            double prod1 = error * det.w;
            double prod2 = prod1 * (fsum / Math.pow(result.sumW, 2));

            rule.dA += 0.01 * prod2 * (1 - det.alfa) * rule.b;
            rule.dB += 0.01 * prod2 * (1 - det.alfa) * (rule.a - example.x);

            rule.dC += 0.01 * prod2 * (1 - det.beta) * rule.d;
            rule.dD += 0.01 * prod2 * (1 - det.beta) * (rule.c - example.y);

            rule.dP += prod1 * example.x / result.sumW;
            rule.dQ += prod1 * example.y / result.sumW;
            rule.dR += prod1 / result.sumW;
        }
    }

    private void updateRules(double learningRate) {
        for (Rule rule : rules) {
            rule.update(learningRate);
        }
    }

    public double mse(List<Example> examples) {
        double mse = 0;
        for (Example example : examples) {
            double z = this.calc(example.x, example.y);
            mse += Math.pow(z - example.z, 2);
        }
        mse /= 2 * examples.size();
        return mse;
    }
}
