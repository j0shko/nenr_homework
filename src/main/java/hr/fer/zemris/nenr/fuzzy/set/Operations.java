package hr.fer.zemris.nenr.fuzzy.set;

import java.util.function.BinaryOperator;
import java.util.function.UnaryOperator;

public class Operations {

    private Operations() {
    }

    public static UnaryOperator<Double> zadehNot() {
        return x -> 1 - x;
    }

    public static BinaryOperator<Double> zadehAnd() {
        return Math::min;
    }

    public static BinaryOperator<Double> zadehOr() {
        return Math::max;
    }

    public static BinaryOperator<Double> algebaricProduct() {
        return (x, y) -> x * y;
    }

    public static BinaryOperator<Double> hamacherTNorm(double v) {
        return (x, y) -> (x * y) / v + (1 - v) * (x + y - x * y);
    }

    public static BinaryOperator<Double> hamacherSNorm(double v) {
        return (x, y) -> (x + y - (2 - v) * x * y) / (1 - (1 - v) * x * y);
    }
}
