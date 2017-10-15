package hr.fer.zemris.nenr.fuzzy.set;

import hr.fer.zemris.nenr.fuzzy.domain.Domain;
import hr.fer.zemris.nenr.fuzzy.domain.DomainElement;

import java.util.function.BinaryOperator;
import java.util.function.UnaryOperator;

public interface FuzzySet {

    Domain getDomain();

    double getValueAt(DomainElement element);

    default FuzzySet unaryOperation(UnaryOperator<Double> operator) {
        FuzzySet original = this;
        return new FuzzySet() {

            @Override
            public Domain getDomain() {
                return original.getDomain();
            }

            @Override
            public double getValueAt(DomainElement element) {
                return operator.apply(original.getValueAt(element));
            }
        };
    }

    default FuzzySet binaryOperation(FuzzySet other, BinaryOperator<Double> operator) {
        FuzzySet first = this;
        return new FuzzySet() {
            @Override
            public Domain getDomain() {
                return first.getDomain();
            }

            @Override
            public double getValueAt(DomainElement element) {
                return operator.apply(first.getValueAt(element), other.getValueAt(element));
            }
        };
    }

    default void print(String headerMessage) {
        if (headerMessage != null) {
            System.out.println(headerMessage);
        }

        for (DomainElement element : getDomain()) {
            System.out.println("d(" + element + ") = " + getValueAt(element));
        }
        System.out.println();
    }
}
