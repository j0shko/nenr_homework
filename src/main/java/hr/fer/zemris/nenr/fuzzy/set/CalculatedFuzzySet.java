package hr.fer.zemris.nenr.fuzzy.set;

import hr.fer.zemris.nenr.fuzzy.domain.Domain;
import hr.fer.zemris.nenr.fuzzy.domain.DomainElement;

import java.util.function.Function;

public class CalculatedFuzzySet implements FuzzySet {

    private Domain domain;
    private Function<Integer, Double> function;

    public CalculatedFuzzySet(Domain domain, Function<Integer, Double> function) {
        this.domain = domain;
        this.function = function;
    }

    @Override
    public Domain getDomain() {
        return domain;
    }

    @Override
    public double getValueAt(DomainElement element) {
        return function.apply(domain.indexOfElement(element));
    }
}
