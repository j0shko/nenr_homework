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

    default void print(String headerMessage, boolean smart) {
        if (headerMessage != null) {
            System.out.println(headerMessage);
        }

        if (!smart) {
            for (DomainElement element : getDomain()) {
                System.out.println("d(" + element + ") = " + getValueAt(element));
            }
        } else {
            DomainElement lastZero = null;
            DomainElement last = null;
            for (DomainElement element : getDomain()) {
                double value = getValueAt(element);
                if (value == 0.) {
                    if (lastZero == null) {
                        lastZero = element;
                    }
                } else {
                    if (last != null && last.equals(lastZero)) {
                        System.out.println("d(" + last + ") = 0" );
                    }
                    if (lastZero != null) {
                        System.out.println("d(" + lastZero + ") - d(" + last + ") = 0");
                        lastZero = null;
                    }
                    System.out.println("d(" + element + ") = " + value);
                }
                last = element;
            }
            if (getValueAt(last) == 0.0) {
                if (lastZero != null) {
                    System.out.println("d(" + lastZero + ") - d(" + last + ") = 0");
                } else {
                    System.out.println("d(" + last + ") = 0");
                }
            }
        }
        System.out.println();
    }

    default void print(String headerMessage) {
        print(headerMessage, false);
    }
}
