package hr.fer.zemris.nenr.fuzzy.set.demo;

import hr.fer.zemris.nenr.fuzzy.domain.AbstractDomain;
import hr.fer.zemris.nenr.fuzzy.domain.Domain;
import hr.fer.zemris.nenr.fuzzy.domain.DomainElement;
import hr.fer.zemris.nenr.fuzzy.set.CalculatedFuzzySet;
import hr.fer.zemris.nenr.fuzzy.set.FuzzySet;
import hr.fer.zemris.nenr.fuzzy.set.MutableFuzzySet;
import hr.fer.zemris.nenr.fuzzy.set.StandardFuzzySets;

public class FuzzySetDemo {

    public static void main(String[] args) {
        Domain d = AbstractDomain.intRange(0, 11);

        FuzzySet set1 = new MutableFuzzySet(d)
                .set(DomainElement.of(0), 1.0)
                .set(DomainElement.of(1), 0.8)
                .set(DomainElement.of(2), 0.6)
                .set(DomainElement.of(3), 0.4)
                .set(DomainElement.of(4), 0.2);
        set1.print("Set1 : ");

        Domain d2 = AbstractDomain.intRange(-5, 6);
        FuzzySet set2 = new CalculatedFuzzySet(
                d2,
                StandardFuzzySets.lambdaFunction(-4, 0, 4)
        );
        set2.print("Set2 : ");

    }
}
