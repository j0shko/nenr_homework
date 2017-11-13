package hr.fer.zemris.nenr.fuzzy.task.boat;

import hr.fer.zemris.nenr.fuzzy.set.FuzzySet;
import hr.fer.zemris.nenr.fuzzy.system.COADefuzzifier;
import hr.fer.zemris.nenr.fuzzy.system.Defuzzifier;
import hr.fer.zemris.nenr.fuzzy.system.rule.Rule;
import hr.fer.zemris.nenr.fuzzy.system.rule.RuleBase;

import java.util.Scanner;

public class BoatTestRule {

    private static RuleBase rulebase = AkcelFuzzySystem.getRuleBase();
    private static Defuzzifier defuzzifier = new COADefuzzifier();

    public static void main(String[] args) {
        System.out.println("Here are the acceleration rules:");
        System.out.println(rulebase.getDesc());

        System.out.print("Pick a rule: ");
        Scanner in = new Scanner(System.in);
        int i = in.nextInt();

        Rule rule = rulebase.getRule(i - 1 );

        System.out.print("Enter values (L, D, LK, DK, V, S): ");
        int L = in.nextInt();
        int D = in.nextInt();
        int LK = in.nextInt();
        int DK = in.nextInt();
        int V = in.nextInt();
        int S = in.nextInt();

        FuzzySet fuzzySet = rule.conclude(L, D, LK, DK, V, S);
        fuzzySet.print("Result set:", true);
        System.out.println("Deffuzified result : " + defuzzifier.defuzzify(fuzzySet));
    }
}
