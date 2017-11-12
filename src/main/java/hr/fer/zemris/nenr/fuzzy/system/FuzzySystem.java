package hr.fer.zemris.nenr.fuzzy.system;

import hr.fer.zemris.nenr.fuzzy.system.rule.RuleBase;

public abstract class FuzzySystem {

    private RuleBase ruleBase;
    private Defuzzifier defuzzifier;

    public FuzzySystem(RuleBase ruleBase, Defuzzifier defuzzifier) {
        this.ruleBase = ruleBase;
        this.defuzzifier = defuzzifier;
    }

    public int conclude(int... vars) {
        return defuzzifier.defuzzify(ruleBase.conclude(vars));
    }
}
