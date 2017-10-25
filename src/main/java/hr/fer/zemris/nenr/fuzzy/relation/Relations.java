package hr.fer.zemris.nenr.fuzzy.relation;

import hr.fer.zemris.nenr.fuzzy.domain.Domain;
import hr.fer.zemris.nenr.fuzzy.domain.DomainElement;
import hr.fer.zemris.nenr.fuzzy.set.FuzzySet;

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
        return false;
    }

    public static boolean isMaxMinTransitive(FuzzySet fuzzySet) {
        return false;
    }

    public static FuzzySet compositionOfBinaryRelations(FuzzySet first, FuzzySet second) {
        return null;
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
