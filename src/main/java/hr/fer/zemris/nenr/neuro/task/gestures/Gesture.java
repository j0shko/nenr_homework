package hr.fer.zemris.nenr.neuro.task.gestures;

import java.util.Arrays;
import java.util.List;
import java.util.stream.DoubleStream;

public class Gesture {
    private double[] points;

    public Gesture(double[] points) {
        this.points = Arrays.copyOf(points, points.length);
    }

    public Gesture(List<Point> points) {
        this.points = points.stream().flatMapToDouble(p -> DoubleStream.of(p.x, p.y)).toArray();
    }
}
