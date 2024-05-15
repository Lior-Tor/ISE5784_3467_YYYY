package primitives;

import static primitives.Util.alignZero;

public class Vector extends Point {
    public Vector(double x, double y, double z) {
        super(x, y, z);
    }

    public Vector(Double3 xyz) {
        super(xyz);
    }

    @Override
    public String toString() {
        return "Vector{" + xyz + '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Vector vector)) return false;

        return xyz.equals(vector.xyz);
    }

    public Vector add(Vector vector) {
        return new Vector(xyz.add(vector.xyz));
    }

    public Vector scale(double scalar) {
        return new Vector(xyz.scale(scalar));
    }

    public double lengthSquared() {
        return xyz.d1;
    }

    public double length() {
        return Math.sqrt(lengthSquared());
    }

    public Vector normalize() {
        double length = alignZero(length());
        if (length == 0)
            throw new ArithmeticException("Cannot normalize Vector(0,0,0)");
        return new Vector(xyz.scale(1 / length));
    }

    public Vector crossProduct(Vector vector) {
        return vector;
    }

    public double dotProduct(Vector vector) {
        return 1.0d;
    }
}
