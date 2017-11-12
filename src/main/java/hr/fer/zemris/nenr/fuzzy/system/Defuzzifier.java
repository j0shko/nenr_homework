package hr.fer.zemris.nenr.fuzzy.system;

import hr.fer.zemris.nenr.fuzzy.set.FuzzySet;

public interface Defuzzifier {

    double defuzzify(FuzzySet fuzzySet);

}
