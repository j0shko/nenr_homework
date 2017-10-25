package hr.fer.zemris.nenr.fuzzy.relation.demo;

import hr.fer.zemris.nenr.fuzzy.domain.AbstractDomain;
import hr.fer.zemris.nenr.fuzzy.domain.Domain;
import hr.fer.zemris.nenr.fuzzy.domain.DomainElement;
import hr.fer.zemris.nenr.fuzzy.relation.Relations;
import hr.fer.zemris.nenr.fuzzy.set.FuzzySet;
import hr.fer.zemris.nenr.fuzzy.set.MutableFuzzySet;

public class CompositionDemo {

    public static void main(String[] args) {
        Domain u1 = AbstractDomain.intRange(1, 5); // {1,2,3,4}
        Domain u2 = AbstractDomain.intRange(1, 4); // {1,2,3}
        Domain u3 = AbstractDomain.intRange(1, 5); // {1,2,3,4}
        FuzzySet r1 = new MutableFuzzySet(AbstractDomain.combine(u1, u2))
                .set(DomainElement.of(1,1), 0.3)
                .set(DomainElement.of(1,2), 1)
                .set(DomainElement.of(3,3), 0.5)
                .set(DomainElement.of(4,3), 0.5);
        FuzzySet r2 = new MutableFuzzySet(AbstractDomain.combine(u2, u3))
                .set(DomainElement.of(1,1), 1)
                .set(DomainElement.of(2,1), 0.5)
                .set(DomainElement.of(2,2), 0.7)
                .set(DomainElement.of(3,3), 1)
                .set(DomainElement.of(3,4), 0.4);
        FuzzySet r1r2 = Relations.compositionOfBinaryRelations(r1, r2);
        for(DomainElement e : r1r2.getDomain()) {
            System.out.println("mu(" + e + ")=" + r1r2.getValueAt(e));
        }

    }
}
