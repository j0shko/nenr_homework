package hr.fer.zemris.nenr.fuzzy.relation;

import hr.fer.zemris.nenr.fuzzy.domain.AbstractDomain;
import hr.fer.zemris.nenr.fuzzy.domain.Domain;
import hr.fer.zemris.nenr.fuzzy.domain.DomainElement;
import hr.fer.zemris.nenr.fuzzy.set.FuzzySet;
import hr.fer.zemris.nenr.fuzzy.set.MutableFuzzySet;

import java.util.ArrayList;
import java.util.List;

public class Relations {

    public static boolean isSymmetric(FuzzySet fuzzySet) {
        if (!isUtimesURelation(fuzzySet)) return false;

        Domain domain = fuzzySet.getDomain();

        for (DomainElement element : domain) {
            DomainElement reverse = DomainElement.of(element.getComponentValue(1), element.getComponentValue(0));
            if (fuzzySet.getValueAt(element) - fuzzySet.getValueAt(reverse) > 0.001) {
                return false;
            }
        }

        return true;
    }

    public static boolean isReflexive(FuzzySet fuzzySet) {
        if (!isUtimesURelation(fuzzySet)) return false;

        for (DomainElement element : fuzzySet.getDomain()) {
            if (element.getComponentValue(0) == element.getComponentValue(1) && fuzzySet.getValueAt(element) != 1) {
                return false;
            }
        }
        return true;
    }

    public static boolean isMaxMinTransitive(FuzzySet fuzzySet) {
        if (!isUtimesURelation(fuzzySet)) return false;

        Domain u = fuzzySet.getDomain().getComponent(0);
        for (DomainElement element : fuzzySet.getDomain()) {
            List<Double> mins = new ArrayList<>();
            for (DomainElement uElement : u) {
                DomainElement firstMiddle = DomainElement.of(element.getComponentValue(0), uElement.getComponentValue(0));
                DomainElement secondMiddle = DomainElement.of(uElement.getComponentValue(0), element.getComponentValue(1));
                mins.add(Math.min(fuzzySet.getValueAt(firstMiddle), fuzzySet.getValueAt(secondMiddle)));
            }
            if (fuzzySet.getValueAt(element) < mins.stream().max(Double::compareTo).orElse(0.))
                return false;
        }

        return true;
    }

    public static FuzzySet compositionOfBinaryRelations(FuzzySet first, FuzzySet second) {
        Domain u = first.getDomain().getComponent(0);
        Domain v = first.getDomain().getComponent(1);
        Domain w = second.getDomain().getComponent(1);

        Domain uw = AbstractDomain.combine(u, w);

        MutableFuzzySet fuzzySet = new MutableFuzzySet(uw);

        for (DomainElement element : fuzzySet.getDomain()) {
            List<Double> mins = new ArrayList<>();
            for (DomainElement vElement : v) {
                DomainElement firstMiddle = DomainElement.of(element.getComponentValue(0), vElement.getComponentValue(0));
                DomainElement secondMiddle = DomainElement.of(vElement.getComponentValue(0), element.getComponentValue(1));
                mins.add(Math.min(first.getValueAt(firstMiddle), second.getValueAt(secondMiddle)));
            }

            fuzzySet.set(element, mins.stream().max(Double::compareTo).orElse(0.));
        }
        return fuzzySet;
    }

    public static boolean isFuzzyEquivalence(FuzzySet fuzzySet) {
        return false;
    }

    public static boolean isUtimesURelation(FuzzySet fuzzySet) {
        Domain domain = fuzzySet.getDomain();
        if (domain.getNumberOfComponents() != 2) return false;

        Domain component1 = domain.getComponent(0);
        Domain component2 = domain.getComponent(1);
        return component1.equals(component2);
    }
}
