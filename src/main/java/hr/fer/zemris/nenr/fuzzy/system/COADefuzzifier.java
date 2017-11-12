package hr.fer.zemris.nenr.fuzzy.system;

import hr.fer.zemris.nenr.fuzzy.domain.DomainElement;
import hr.fer.zemris.nenr.fuzzy.set.FuzzySet;

public class COADefuzzifier implements Defuzzifier {


    @Override
    public double defuzzify(FuzzySet fuzzySet) {
        double valueElementSum = 0;
        double valueSum = 0;

        for (DomainElement d : fuzzySet.getDomain()) {
            double value = fuzzySet.getValueAt(d);
            valueElementSum += value * d.getComponentValue(0);
            valueSum += value;
        }

        return valueElementSum / valueSum;
    }
}
