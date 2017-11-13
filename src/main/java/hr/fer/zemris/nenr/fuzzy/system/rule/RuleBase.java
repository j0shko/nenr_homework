package hr.fer.zemris.nenr.fuzzy.system.rule;

import hr.fer.zemris.nenr.fuzzy.set.FuzzySet;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BinaryOperator;

public class RuleBase {

    private List<Rule> rules;
    private BinaryOperator<Double> sNorm;
    private BinaryOperator<Double> tNorm;
    private BinaryOperator<Double> implication;

    private String desc;


    public RuleBase(BinaryOperator<Double> sNorm, BinaryOperator<Double> tNorm, BinaryOperator<Double> implication) {
        this.rules = new ArrayList<>();
        this.sNorm = sNorm;
        this.tNorm = tNorm;
        this.implication = implication;
    }

    public RuleBase addRule(List<FuzzySet> antecedent, FuzzySet consequent) {
        this.rules.add(new Rule(antecedent, consequent, tNorm, implication));
        return this;
    }

    public Rule getRule(int index) {
        return this.rules.get(index);
    }

    public FuzzySet conclude(int... vars) {
        FuzzySet conclusion = rules.get(0).conclude(vars);

        for (int i = 1, end = rules.size(); i < end; i++) {
            conclusion = conclusion.binaryOperation(rules.get(i).conclude(vars), sNorm);
        }
        return conclusion;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getDesc() {
        return desc;
    }
}
