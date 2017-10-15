package hr.fer.zemris.nenr.fuzzy.set;

import hr.fer.zemris.nenr.fuzzy.domain.Domain;
import hr.fer.zemris.nenr.fuzzy.domain.DomainElement;

import java.util.HashMap;
import java.util.Map;

public class MutableFuzzySet implements FuzzySet {
    private Map<DomainElement, Double> valueMap;
    private Domain domain;

    public MutableFuzzySet(Domain domain) {
        this.domain = domain;
        this.valueMap = new HashMap<>();
    }

    public Domain getDomain() {
        return domain;
    }

    public double getValueAt(DomainElement element) {
        return valueMap.getOrDefault(element, 0.0);
    }

    public MutableFuzzySet set(DomainElement element, double value) {
        valueMap.put(element, value);
        return this;
    }
}
