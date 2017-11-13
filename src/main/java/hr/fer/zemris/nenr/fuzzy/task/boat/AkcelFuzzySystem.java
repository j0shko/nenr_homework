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

    private static final BinaryOperator<Double> S_NORM = Operations.zadehOr();
    private static final BinaryOperator<Double> T_NORM = Operations.zadehAnd();
    private static final BinaryOperator<Double> IMPLICATION = Operations.zadehAnd();

    public AkcelFuzzySystem(Defuzzifier defuzzifier) {
        super(getRuleBase(), defuzzifier);
    }

    public static RuleBase getRuleBase() {
        RuleBase ruleBase = new RuleBase(S_NORM, T_NORM, IMPLICATION);

        Domain distanceDomain = BoatUtils.distanceDomain();
        FuzzySet distanceSet = BoatUtils.distanceSet();
        FuzzySet directionSet = BoatUtils.directionSet();

        Domain velocityDomain = BoatUtils.velocityDomain();
        FuzzySet velocitySet = BoatUtils.velocitySet();

        Domain accelerationDomain = BoatUtils.accelerationDomain();

        FuzzySet slow = new CalculatedFuzzySet(
                velocityDomain,
                StandardFuzzySets.lFunction(
                        velocityDomain.indexOfElement(DomainElement.of(70)),
                        velocityDomain.indexOfElement(DomainElement.of(100))
                )
        );

        FuzzySet fast = new CalculatedFuzzySet(
                velocityDomain,
                StandardFuzzySets.gammaFunction(
                        velocityDomain.indexOfElement(DomainElement.of(60)),
                        velocityDomain.indexOfElement(DomainElement.of(90))
                )
        );

        FuzzySet slowDown = new CalculatedFuzzySet(
                accelerationDomain,
                StandardFuzzySets.lambdaFunction(
                        accelerationDomain.indexOfElement(DomainElement.of(-15)),
                        accelerationDomain.indexOfElement(DomainElement.of(-10)),
                        accelerationDomain.indexOfElement(DomainElement.of(-5))
                )
        );

        FuzzySet speedUpHard = new CalculatedFuzzySet(
                accelerationDomain,
                StandardFuzzySets.lambdaFunction(
                        accelerationDomain.indexOfElement(DomainElement.of(15)),
                        accelerationDomain.indexOfElement(DomainElement.of(20)),
                        accelerationDomain.indexOfElement(DomainElement.of(35))
                )
        );

        FuzzySet speedUp = new CalculatedFuzzySet(
                accelerationDomain,
                StandardFuzzySets.lambdaFunction(
                        accelerationDomain.indexOfElement(DomainElement.of(5)),
                        accelerationDomain.indexOfElement(DomainElement.of(10)),
                        accelerationDomain.indexOfElement(DomainElement.of(15))
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
                        distanceDomain.indexOfElement(DomainElement.of(30)),
                        distanceDomain.indexOfElement(DomainElement.of(50))
                )
        );

        ruleBase.addRule(List.of(distanceSet, distanceSet, distanceSet, distanceSet, fast, directionSet), slowDown);
        ruleBase.addRule(List.of(distanceSet, distanceSet, distanceSet, distanceSet, slow, directionSet), speedUp);

        ruleBase.addRule(List.of(distanceSet, distanceSet, closeToWallDiagonal, distanceSet, velocitySet, directionSet), slowDown);
        ruleBase.addRule(List.of(distanceSet, distanceSet, distanceSet, closeToWallDiagonal, velocitySet, directionSet), slowDown);

        String desc =
                "1. IF fast THEN slowDown\n" +
                "2. IF slow THEN speedUp\n" +
                "3. IF closeToWallDiagonalLeft THEN slowDown\n" +
                "4. IF closeToWallDiagonalRight THEN slowDown\n";
        ruleBase.setDesc(desc);

        return ruleBase;
    }
}
