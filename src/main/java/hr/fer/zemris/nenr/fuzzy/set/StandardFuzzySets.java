package hr.fer.zemris.nenr.fuzzy.set;

import java.util.function.Function;

public class StandardFuzzySets {
    private StandardFuzzySets() {
    }

    public static Function<Integer, Double> gammaFunction(int riseStart, int riseEnd) {
        return x -> {
            if (x < riseStart) return 0.;
            if (x > riseEnd) return 1.;
            return (double) (x - riseStart) / (riseEnd - riseStart);
        };
    }

    public static Function<Integer, Double> lFunction(int fallStart, int fallEnd) {
        return x -> {
            if (x < fallStart) return 1.;
            if (x > fallEnd) return 0.;
            return (double) (fallStart - x) / (fallEnd - fallStart);
        };
    }

    public static Function<Integer, Double> lambdaFunction(int start, int point, int end) {
        return x -> {
            if (x < start || x > end) return 0.;
            if (x == point) return 1.;
            if (x < point) {
                return (double) (x - start) / (point - start);
            } else {
                return (double) (end - x) / (end - point);
            }
        };
    }
}
