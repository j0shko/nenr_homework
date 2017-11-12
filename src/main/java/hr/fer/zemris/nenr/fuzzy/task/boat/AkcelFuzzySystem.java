package hr.fer.zemris.nenr.fuzzy.task.boat;

import hr.fer.zemris.nenr.fuzzy.domain.Domain;
import hr.fer.zemris.nenr.fuzzy.domain.DomainElement;
import hr.fer.zemris.nenr.fuzzy.set.CalculatedFuzzySet;
import hr.fer.zemris.nenr.fuzzy.set.FuzzySet;
import hr.fer.zemris.nenr.fuzzy.set.Operations;
import hr.fer.zemris.nenr.fuzzy.set.StandardFuzzySets;
import hr.fer.zemris.nenr.fuzzy.system.Defuzzifier;
import hr.fer.zemris.nenr.fuzzy.system.FuzzySystem;
import hr.fer.zemris.nenr.fuzzy.system.rule.RuleBase;

import java.util.List;
import java.util.function.BinaryOperator;

public class AkcelFuzzySystem extends FuzzySystem {

    private static final BinaryOperator<Double> T_NORM = Operations.zadehAnd();
    private static final BinaryOperator<Double> S_NORM = Operations.zadehOr();
    private static final BinaryOperator<Double> IMPLICATION = Operations.zadehAnd();

    public AkcelFuzzySystem(Defuzzifier defuzzifier) {
        super(getRuleBase(), defuzzifier);
    }

    private static RuleBase getRuleBase() {
        RuleBase ruleBase = new RuleBase(S_NORM, T_NORM, IMPLICATION);

        FuzzySet distanceSet = BoatUtils.distanceSet();
        FuzzySet directionSet = BoatUtils.directionSet();

        Domain velocityDomain = BoatUtils.velocityDomain();

        FuzzySet slow = new CalculatedFuzzySet(
                velocityDomain,
                StandardFuzzySets.lFunction(
                        velocityDomain.indexOfElement(DomainElement.of(10)),
                        velocityDomain.indexOfElement(DomainElement.of(30))
                )
        );

        FuzzySet fast = new CalculatedFuzzySet(
                velocityDomain,
                StandardFuzzySets.gammaFunction(
                        velocityDomain.indexOfElement(DomainElement.of(20)),
                        velocityDomain.indexOfElement(DomainElement.of(40))
                )
        );

        FuzzySet slowDown = new CalculatedFuzzySet(
                velocityDomain,
                StandardFuzzySets.lFunction(
                        velocityDomain.indexOfElement(DomainElement.of(10)),
                        velocityDomain.indexOfElement(DomainElement.of(30))
                )
        );

        FuzzySet speedUp = new CalculatedFuzzySet(
                velocityDomain,
                StandardFuzzySets.lambdaFunction(
                    velocityDomain.indexOfElement(DomainElement.of(10)),
                    velocityDomain.indexOfElement(DomainElement.of(20)),
                    velocityDomain.indexOfElement(DomainElement.of(30))
                )
        );

        ruleBase.addRule(List.of(distanceSet, distanceSet, distanceSet, distanceSet, fast, directionSet), slowDown);
        ruleBase.addRule(List.of(distanceSet, distanceSet, distanceSet, distanceSet, slow, directionSet), speedUp);

        return ruleBase;
    }
}
