package hr.fer.zemris.nenr.genetic.example;

import java.util.List;

public interface ParametrizedFunction {
    float apply(List<Float> parameters, float[] hyperParameters);
}
