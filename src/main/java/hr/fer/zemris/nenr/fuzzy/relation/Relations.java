package hr.fer.zemris.nenr.fuzzy.relation;

import hr.fer.zemris.nenr.fuzzy.domain.Domain;
import hr.fer.zemris.nenr.fuzzy.set.FuzzySet;

public class Relations {

    public static boolean isSymmetric(FuzzySet fuzzySet) {
        return false;
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
        if (component1.getCardinality() != component2.getCardinality()) return false;

        for (int i = 0, end = component1.getCardinality(); i < end; i++) {
            if (!component1.elementForIndex(i).equals(component2.elementForIndex(i))) {
                return false;
            }
        }

        return true;
    }
}
