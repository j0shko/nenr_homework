package hr.fer.zemris.nenr.fuzzy.set;

import hr.fer.zemris.nenr.fuzzy.domain.Domain;
import hr.fer.zemris.nenr.fuzzy.domain.DomainElement;

public class IdentityFuzzySet implements FuzzySet {

    private Domain domain;

    public IdentityFuzzySet(Domain domain) {
        this.domain = domain;
    }

    @Override
    public Domain getDomain() {
        return domain;
    }

    @Override
    public double getValueAt(DomainElement element) {
        return 1;
    }
}
