package hr.fer.zemris.nenr.fuzzy.task.boat;

import hr.fer.zemris.nenr.fuzzy.domain.AbstractDomain;
import hr.fer.zemris.nenr.fuzzy.domain.Domain;
import hr.fer.zemris.nenr.fuzzy.set.FuzzySet;
import hr.fer.zemris.nenr.fuzzy.set.IdentityFuzzySet;

public interface BoatUtils {

    static Domain angleDomain() {
        return AbstractDomain.intRange(-90, 91);
    }

    static Domain distanceDomain() {
        return AbstractDomain.intRange(0, 1301);
    }

    static Domain velocityDomain() {
        return AbstractDomain.intRange(0, 101);
    }

    static Domain directionDomain() {
        return AbstractDomain.intRange(0, 2);
    }

    static FuzzySet distanceSet() {
        return new IdentityFuzzySet(distanceDomain());
    }

    static FuzzySet angleSet() {
        return new IdentityFuzzySet(angleDomain());
    }

    static FuzzySet velocitySet() {
        return new IdentityFuzzySet(velocityDomain());
    }

    static FuzzySet directionSet() {
        return new IdentityFuzzySet(directionDomain());
    }
}
