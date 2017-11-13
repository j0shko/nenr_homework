package hr.fer.zemris.nenr.fuzzy.task.boat;

import hr.fer.zemris.nenr.fuzzy.domain.Domain;
import hr.fer.zemris.nenr.fuzzy.domain.DomainElement;
import hr.fer.zemris.nenr.fuzzy.set.*;
import hr.fer.zemris.nenr.fuzzy.system.Defuzzifier;
import hr.fer.zemris.nenr.fuzzy.system.FuzzySystem;
import hr.fer.zemris.nenr.fuzzy.system.rule.RuleBase;

import java.util.List;
import java.util.function.BinaryOperator;

public class KormiloFuzzySystem extends FuzzySystem {

    private static final BinaryOperator<Double> S_NORM = Operations.zadehOr();
    private static final BinaryOperator<Double> T_NORM = Operations.zadehAnd();
    private static final BinaryOperator<Double> IMPLICATION = Operations.zadehAnd();

    public KormiloFuzzySystem(Defuzzifier defuzzifier) {
        super(getRuleBase(), defuzzifier);
    }

    public static RuleBase getRuleBase() {
        RuleBase ruleBase = new RuleBase(S_NORM, T_NORM, IMPLICATION);

        Domain angleDomain = BoatUtils.angleDomain();

        Domain distanceDomain = BoatUtils.distanceDomain();
        FuzzySet distanceSet = BoatUtils.distanceSet();

        Domain directionDomain = BoatUtils.directionDomain();
        FuzzySet directionSet = BoatUtils.directionSet();
        FuzzySet velocitySet = BoatUtils.velocitySet();

        FuzzySet steerRight = new CalculatedFuzzySet(
                angleDomain,
                StandardFuzzySets.lFunction(
                        angleDomain.indexOfElement(DomainElement.of(-40)),
                        angleDomain.indexOfElement(DomainElement.of(-10))
                )
        );

        FuzzySet steerRightHard = new CalculatedFuzzySet(
                angleDomain,
                StandardFuzzySets.lFunction(
                        angleDomain.indexOfElement(DomainElement.of(-75)),
                        angleDomain.indexOfElement(DomainElement.of(-30))
                )
        );

        FuzzySet steerLeft = new CalculatedFuzzySet(
                angleDomain,
                StandardFuzzySets.gammaFunction(
                        angleDomain.indexOfElement(DomainElement.of(10)),
                        angleDomain.indexOfElement(DomainElement.of(40))
                )
        );

        FuzzySet steerLeftHard = new CalculatedFuzzySet(
                angleDomain,
                StandardFuzzySets.gammaFunction(
                        angleDomain.indexOfElement(DomainElement.of(30)),
                        angleDomain.indexOfElement(DomainElement.of(75))
                )
        );

        FuzzySet closeToWallDiagonal = new CalculatedFuzzySet(
                distanceDomain,
                StandardFuzzySets.lFunction(
                        distanceDomain.indexOfElement(DomainElement.of(60)),
                        distanceDomain.indexOfElement(DomainElement.of(180))
                )
        );

        FuzzySet closeToWall = new CalculatedFuzzySet(
                distanceDomain,
                StandardFuzzySets.lFunction(
                        distanceDomain.indexOfElement(DomainElement.of(45)),
                        distanceDomain.indexOfElement(DomainElement.of(85))
                )
        );

        FuzzySet wrongWay = new MutableFuzzySet(directionDomain).set(DomainElement.of(0), 1);

        ruleBase.addRule(List.of(distanceSet, distanceSet, distanceSet, closeToWallDiagonal, velocitySet, directionSet), steerLeft);
        ruleBase.addRule(List.of(distanceSet, closeToWall, distanceSet, distanceSet, velocitySet, directionSet), steerLeftHard);

        ruleBase.addRule(List.of(distanceSet, distanceSet, closeToWallDiagonal, distanceSet, velocitySet, directionSet), steerRight);
        ruleBase.addRule(List.of(closeToWall, distanceSet, distanceSet, distanceSet, velocitySet, directionSet), steerRightHard);

        ruleBase.addRule(List.of(distanceSet, distanceSet, distanceSet, distanceSet, velocitySet, wrongWay), steerLeftHard);

        return ruleBase;
    }
}
