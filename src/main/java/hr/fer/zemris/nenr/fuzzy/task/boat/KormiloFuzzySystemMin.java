package hr.fer.zemris.nenr.fuzzy.task.boat;

import hr.fer.zemris.nenr.fuzzy.system.Defuzzifier;
import hr.fer.zemris.nenr.fuzzy.system.FuzzySystem;

public class KormiloFuzzySystemMin implements FuzzySystem {

    private Defuzzifier defuzzifier;

    public KormiloFuzzySystemMin(Defuzzifier defuzzifier) {
        this.defuzzifier = defuzzifier;
    }

    public int conclude(int L, int D, int LK, int DK, int V, int s) {
        return 0;
    }

    @Override
    public int conclude(int... vars) {
        return this.conclude(vars[0], vars[1], vars[2], vars[3], vars[4], vars[5]);
    }
}
