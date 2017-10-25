package hr.fer.zemris.nenr.fuzzy.relation.demo;

import hr.fer.zemris.nenr.fuzzy.domain.AbstractDomain;
import hr.fer.zemris.nenr.fuzzy.domain.Domain;
import hr.fer.zemris.nenr.fuzzy.domain.DomainElement;
import hr.fer.zemris.nenr.fuzzy.relation.Relations;
import hr.fer.zemris.nenr.fuzzy.set.FuzzySet;
import hr.fer.zemris.nenr.fuzzy.set.MutableFuzzySet;

public class EquivalenceDemo {

    public static void main(String[] args) {
        Domain u = AbstractDomain.intRange(1, 5); // {1,2,3,4}
        FuzzySet r = new MutableFuzzySet(AbstractDomain.combine(u, u))
                .set(DomainElement.of(1,1), 1)
                .set(DomainElement.of(2,2), 1)
                .set(DomainElement.of(3,3), 1)
                .set(DomainElement.of(4,4), 1)
                .set(DomainElement.of(1,2), 0.3)
                .set(DomainElement.of(2,1), 0.3)
                .set(DomainElement.of(2,3), 0.5)
                .set(DomainElement.of(3,2), 0.5)
                .set(DomainElement.of(3,4), 0.2)
                .set(DomainElement.of(4,3), 0.2);
        FuzzySet r2 = r;
        System.out.println("Početna relacija je neizrazita relacija ekvivalencije? " + Relations.isFuzzyEquivalence(r2));
        System.out.println();
        for (int i = 1; i <= 3; i++) {
            r2 = Relations.compositionOfBinaryRelations(r2, r);
            System.out.println("Broj odrađenih kompozicija: " + i + ". Relacija je:");
            for (DomainElement e : r2.getDomain()) {
                System.out.println("mu(" + e + ")=" + r2.getValueAt(e));
            }
            System.out.println("Ova relacija je neizrazita relacija ekvivalencije? " + Relations.isFuzzyEquivalence(r2));
            System.out.println();
        }

    }
}
