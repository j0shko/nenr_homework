package hr.fer.zemris.nenr.anfis.example;

public class Example {
    public final double x;
    public final double y;
    public final double z;

    public Example(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    @Override
    public String toString() {
        return String.format("%f, %f -> %f", x, y, z);
    }
}
