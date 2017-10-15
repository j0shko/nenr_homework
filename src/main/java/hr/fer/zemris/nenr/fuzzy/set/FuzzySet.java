package hr.fer.zemris.nenr.fuzzy.set;

import hr.fer.zemris.nenr.fuzzy.domain.Domain;
import hr.fer.zemris.nenr.fuzzy.domain.DomainElement;

public interface FuzzySet {

    Domain getDomain();

    double getValueAt(DomainElement element);

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
