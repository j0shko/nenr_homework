package hr.fer.zemris.nenr.fuzzy.system;

import hr.fer.zemris.nenr.fuzzy.domain.Domain;
import hr.fer.zemris.nenr.fuzzy.domain.DomainElement;
import hr.fer.zemris.nenr.fuzzy.set.FuzzySet;
import hr.fer.zemris.nenr.fuzzy.set.MutableFuzzySet;

public class SingletonFuzzifier implements Fuzzifier {

    private Domain domain;

    public SingletonFuzzifier(Domain domain) {
        this.domain = domain;
    }

    @Override
    public FuzzySet fuzzify(int value) {
        MutableFuzzySet set = new MutableFuzzySet(domain);
        set.set(DomainElement.of(value), 1.0);
        return set;
    }
}
