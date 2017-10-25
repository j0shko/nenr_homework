package hr.fer.zemris.nenr.fuzzy.relation.demo;

import hr.fer.zemris.nenr.fuzzy.domain.AbstractDomain;
import hr.fer.zemris.nenr.fuzzy.domain.Domain;
import hr.fer.zemris.nenr.fuzzy.domain.DomainElement;
import hr.fer.zemris.nenr.fuzzy.relation.Relations;
import hr.fer.zemris.nenr.fuzzy.set.FuzzySet;
import hr.fer.zemris.nenr.fuzzy.set.MutableFuzzySet;

public class RelationsDemo {

    public static void main(String[] args) {
        Domain u = AbstractDomain.intRange(1, 6);

        Domain u2 = AbstractDomain.combine(u, u);

        FuzzySet r1 = new MutableFuzzySet(u2)
                .set(DomainElement.of(1, 1), 1)
                .set(DomainElement.of(2, 2), 1)
                .set(DomainElement.of(3, 3), 1)
                .set(DomainElement.of(4, 4), 1)
                .set(DomainElement.of(5, 5), 1)
                .set(DomainElement.of(3, 1), 0.5)
                .set(DomainElement.of(1, 3), 0.5);

        FuzzySet r2 = new MutableFuzzySet(u2)
                .set(DomainElement.of(1, 1), 1)
                .set(DomainElement.of(2, 2), 1)
                .set(DomainElement.of(3, 3), 1)
                .set(DomainElement.of(4, 4), 1)
                .set(DomainElement.of(5, 5), 1)
                .set(DomainElement.of(3, 1), 0.5)
                .set(DomainElement.of(1, 3), 0.1);

        FuzzySet r3 = new MutableFuzzySet(u2)
                .set(DomainElement.of(1, 1), 1)
                .set(DomainElement.of(2, 2), 1)
                .set(DomainElement.of(3, 3), 0.3)
                .set(DomainElement.of(4, 4), 1)
                .set(DomainElement.of(5, 5), 1)
                .set(DomainElement.of(1, 2), 0.6)
                .set(DomainElement.of(2, 1), 0.6)
                .set(DomainElement.of(2, 3), 0.7)
                .set(DomainElement.of(3, 2), 0.7)
                .set(DomainElement.of(3, 1), 0.5)
                .set(DomainElement.of(1, 3), 0.5);

        FuzzySet r4 = new MutableFuzzySet(u2)
                .set(DomainElement.of(1, 1), 1)
                .set(DomainElement.of(2, 2), 1)
                .set(DomainElement.of(3, 3), 1)
                .set(DomainElement.of(4, 4), 1)
                .set(DomainElement.of(5, 5), 1)
                .set(DomainElement.of(1, 2), 0.4)
                .set(DomainElement.of(2, 1), 0.4)
                .set(DomainElement.of(2, 3), 0.5)
                .set(DomainElement.of(3, 2), 0.5)
                .set(DomainElement.of(1, 3), 0.4)
                .set(DomainElement.of(3, 1), 0.4);

        boolean test1 = Relations.isUtimesURelation(r1);
        System.out.println("r1 je definiran nad UxU? " + test1);

        boolean test2 = Relations.isSymmetric(r1);
        System.out.println("r1 je simetrična? " + test2);

        boolean test3 = Relations.isSymmetric(r2);
        System.out.println("r2 je simetrična? " + test3);

        boolean test4 = Relations.isReflexive(r1);
        System.out.println("r1 je refleksivna? " + test4);

        boolean test5 = Relations.isReflexive(r3);
        System.out.println("r3 je refleksivna? " + test5);

        boolean test6 = Relations.isMaxMinTransitive(r3);
        System.out.println("r3 je max-min tranzitivna? " + test6);

        boolean test7 = Relations.isMaxMinTransitive(r4);
        System.out.println("r4 je max-min tranzitivna? " + test7);
    }
}
