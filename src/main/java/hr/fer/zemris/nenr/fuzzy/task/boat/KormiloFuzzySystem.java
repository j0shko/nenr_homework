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

public class KormiloFuzzySystem extends FuzzySystem {

    private static final BinaryOperator<Double> T_NORM = Operations.zadehAnd();
    private static final BinaryOperator<Double> S_NORM = Operations.zadehOr();
    private static final BinaryOperator<Double> IMPLICATION = Operations.zadehAnd();

    public KormiloFuzzySystem(Defuzzifier defuzzifier) {
        super(getRuleBase(), defuzzifier);
    }

    private static RuleBase getRuleBase() {
        RuleBase ruleBase = new RuleBase(S_NORM, T_NORM, IMPLICATION);

        Domain angleDomain = BoatUtils.angleDomain();

        Domain distanceDomain = BoatUtils.distanceDomain();
        FuzzySet distanceSet = BoatUtils.distanceSet();

        FuzzySet directionSet = BoatUtils.directionSet();
        FuzzySet velocitySet = BoatUtils.velocitySet();

        FuzzySet steerRight = new CalculatedFuzzySet(
                angleDomain,
                StandardFuzzySets.lFunction(
                        angleDomain.indexOfElement(DomainElement.of(-80)),
                        angleDomain.indexOfElement(DomainElement.of(-10))
                )
        );

        FuzzySet steerLeft = new CalculatedFuzzySet(
                angleDomain,
                StandardFuzzySets.gammaFunction(
                        angleDomain.indexOfElement(DomainElement.of(10)),
                        angleDomain.indexOfElement(DomainElement.of(80))
                )
        );

        FuzzySet closeToWall = new CalculatedFuzzySet(
                distanceDomain,
                StandardFuzzySets.lFunction(
                        distanceDomain.indexOfElement(DomainElement.of(100)),
                        distanceDomain.indexOfElement(DomainElement.of(200))
                )
        );

        ruleBase.addRule(List.of(distanceSet, distanceSet, distanceSet, closeToWall, velocitySet, directionSet), steerLeft);
        ruleBase.addRule(List.of(distanceSet, distanceSet, closeToWall, distanceSet, velocitySet, directionSet), steerRight);

        return ruleBase;
    }
}
