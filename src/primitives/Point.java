package primitives;

import java.util.Objects;

/**
 * Class Point is the class representing a point in the 3D space.
 */
public class Point {
    /**
     * The point in the 3D space.
     */
    final Double3 xyz;

    /**
     * The point (0,0,0).
     */
    public static final Point ZERO = new Point(0, 0, 0);

    /**
     * Constructor for point by 3 double parameters.
     *
     * @param x - x coordinate.
     * @param y - y coordinate.
     * @param z - z coordinate.
     */
    public Point(double x, double y, double z) {
        this.xyz = new Double3(x, y, z);
    }

    /**
     * Constructor for point by a Double3 parameter.
     *
     * @param xyz - the point in the 3D space.
     */
    public Point(final Double3 xyz) {
        this.xyz = xyz;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Point point)) return false;
        return xyz.equals(point.xyz);
    }

    @Override
    public int hashCode() {
        return Objects.hash(xyz);
    }

    @Override
    public String toString() {
        return "Point: " + xyz;
    }

    /**
     * Adding Vector to the current point and returns a new updated point.
     *
     * @param vec - receives a vector in the parameter to add with.
     * @return a point that is the result of the addition of the vector to the point.
     */
    public Point add(Vector vec) {
        Double3 temp = this.xyz.add(vec.xyz);
        return new Point(temp.d1, temp.d2, temp.d3);
    }

    /**
     * Vector subtraction.
     *
     * @param point - point to subtract from.
     * @return the vector that is the result of the subtraction of the point from the current point.
     */
    public Vector subtract(Point point) {
        Double3 temp = this.xyz.subtract(point.xyz);
        return new Vector(temp.d1, temp.d2, temp.d3);
    }

    /**
     * Distance calculation.
     *
     * @param point - point to calculate distance from.
     * @return the distance between the current point and the point received by parameter.
     */
    public double distance(Point point) {
        return Math.sqrt(distanceSquared(point));
    }

    /**
     * distanceSquared calculation.
     *
     * @param point - point to calculate distance from.
     * @return the squared distance between the current point and the point received by parameter.
     */
    public double distanceSquared(Point point) {
        Double3 temp = point.xyz;
        Double3 temp2 = this.xyz;
        return ((temp.d1 - temp2.d1) * (temp.d1 - temp2.d1) + (temp.d2 - temp2.d2) * (temp.d2 - temp2.d2) + (temp.d3 - temp2.d3) * (temp.d3 - temp2.d3));
    }

    /**
     * Getter for the point.
     *
     * @return the point.
     */
    public Double3 getXYZ() {
        return xyz;
    }
}