package hr.fer.zemris.nenr.neuro.task.gestures;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;

public class Gesture {
    private double[] points;

    public Gesture(double[] points) {
        this.points = Arrays.copyOf(points, points.length);
    }

    public Gesture(List<Point> points, int partCount) {
        this.points = normalize(points, partCount);
    }

    private double[] normalize(List<Point> points, int partCount) {
        if (points.size() == 0) {
            return new double[]{};
        }
        double avgX = points.stream().map(p -> p.x).reduce(0., (acc, x) -> acc + x) / points.size();
        double avgY = points.stream().map(p -> p.x).reduce(0., (acc, x) -> acc + x) / points.size();

        List<Point> centered = points.stream().map(p -> new Point(p.x - avgX, p.y - avgY)).collect(Collectors.toList());

        double max = centered.stream().flatMapToDouble(p -> DoubleStream.of(p.x, p.y)).map(Math::abs).max().orElse(1.);

        List<Point> normalized = centered.stream().map(p -> new Point(p.x / max, p.y / max)).collect(Collectors.toList());

        double length = 0;
        Point last = normalized.get(0);
        for (int i = 1; i < normalized.size(); i++) {
            Point current = normalized.get(i);
            length += last.distance(current);
            last = current;
        }

        double partDist = length / partCount;
        double currentPartLength = 0;
        List<Point> newPoints = new ArrayList<>();

        last = normalized.get(0);
        for (int i = 1; i < normalized.size(); i++) {
            Point current = normalized.get(i);
            currentPartLength += last.distance(current);

            if (currentPartLength > partDist) {
                newPoints.add(current);
                currentPartLength = 0;
            }
            last = current;
        }
        while (newPoints.size() < partCount) {
            newPoints.add(normalized.get(normalized.size() - 1));
        }

        return newPoints.stream().flatMapToDouble(p -> DoubleStream.of(p.x, p.y)).toArray();
    }

    public double[] getPoints() {
        return points;
    }

    @Override
    public String toString() {
        String s = Arrays.toString(points).replaceAll(" ", "");
        return s.substring(1, s.length() - 1);
    }
}
