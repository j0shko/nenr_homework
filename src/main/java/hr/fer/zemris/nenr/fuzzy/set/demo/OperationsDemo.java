package hr.fer.zemris.nenr.fuzzy.set.demo;

import hr.fer.zemris.nenr.fuzzy.domain.AbstractDomain;
import hr.fer.zemris.nenr.fuzzy.domain.Domain;
import hr.fer.zemris.nenr.fuzzy.domain.DomainElement;
import hr.fer.zemris.nenr.fuzzy.set.FuzzySet;
import hr.fer.zemris.nenr.fuzzy.set.MutableFuzzySet;
import hr.fer.zemris.nenr.fuzzy.set.Operations;

public class OperationsDemo {

    public static void main(String[] args) {
        Domain d = AbstractDomain.intRange(0, 11);

        FuzzySet set1 = new MutableFuzzySet(d)
                .set(DomainElement.of(0), 1.0)
                .set(DomainElement.of(1), 0.8)
                .set(DomainElement.of(2), 0.6)
                .set(DomainElement.of(3), 0.4)
                .set(DomainElement.of(4), 0.2);
        set1.print("Set1 : ");

        FuzzySet notSet = set1.unaryOperation(Operations.zadehNot());
        notSet.print("notSet1:");

        FuzzySet union = set1.binaryOperation(notSet, Operations.zadehOr());
        union.print("Set1 union notSet1: ");

        FuzzySet hinters = set1.binaryOperation(notSet, Operations.hamacherTNorm(1.0));
        hinters.print("Set1 intersection with notSet1 using parameterised Hamacher T norm with parameter 1.0:");
    }
}
