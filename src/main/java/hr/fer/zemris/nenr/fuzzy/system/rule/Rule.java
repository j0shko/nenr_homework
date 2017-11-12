package hr.fer.zemris.nenr.fuzzy.system.rule;

import hr.fer.zemris.nenr.fuzzy.domain.Domain;
import hr.fer.zemris.nenr.fuzzy.domain.DomainElement;
import hr.fer.zemris.nenr.fuzzy.set.FuzzySet;
import hr.fer.zemris.nenr.fuzzy.set.MutableFuzzySet;

import java.util.List;
import java.util.function.BinaryOperator;

public class Rule {
    private List<FuzzySet> antecedent;
    private FuzzySet consequent;

    private BinaryOperator<Double> tNorm;
    private BinaryOperator<Double> impl;

    public Rule(List<FuzzySet> antecedent, FuzzySet consequent, BinaryOperator<Double> tNorm, BinaryOperator<Double> impl) {
        this.antecedent = antecedent;
        this.consequent = consequent;
        this.tNorm = tNorm;
        this.impl = impl;
    }

    public FuzzySet conclude(int... vars) {
        Domain domain = consequent.getDomain();
        MutableFuzzySet conclusion = new MutableFuzzySet(domain);

        for (DomainElement d: domain) {
            double value = antecedent.get(0).getValueAt(DomainElement.of(vars[0]));

            for (int i = 1, end = antecedent.size(); i < end; i++) {
                value = tNorm.apply(value, antecedent.get(i).getValueAt(DomainElement.of(vars[i])));
            }

            conclusion.set(d, impl.apply(value, consequent.getValueAt(d)));
        }

        return conclusion;
    }

}
