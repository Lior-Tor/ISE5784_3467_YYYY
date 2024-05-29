package primitives;

import java.util.Objects;

/**
 * Point - represents a point in the dimension
 */
public class Point {

    final Double3 xyz;

    public static final Point ZERO = new Point(0, 0, 0);

    /**
     * Constructor for Point with 3 double parameters
     *
     * @param x x value of the point
     * @param y y value of the point
     * @param z z value of the point
     */
    public Point(double x, double y, double z) {
        this.xyz = new Double3(x, y, z);
    }

    /**
     * Constructor for point by immutable parameter of Double3 Type
     *
     * @param xyz
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
     * Adding Vector to the current point and returns a new updated point
     *
     * @param vec vector to add to point
     * @return new updated point
     */
    public Point add(Vector vec) {
        Double3 temp = this.xyz.add(vec.xyz);
        return new Point(temp.d1, temp.d2, temp.d3);
    }

    /**
     * Vector subtraction
     *
     * @param point - receives a second point in the parameter to subtract with
     * @return a vector from the second point to the point on which the subtraction is performed the action
     */
    public Vector subtract(Point point) {
        Double3 temp = this.xyz.subtract(point.xyz);
        return new Vector(temp.d1, temp.d2, temp.d3);
    }

    /**
     * Calculates the distance between the current point and the point received by parameter
     *
     * @param point - point to calculate distance from
     * @return the distance between the current point and the point received by parameter
     */
    public double distance(Point point) {
        return Math.sqrt(distanceSquared(point));
    }

    /**
     * distanceSquared calculation
     *
     * @param point - point to calculate distance from
     * @return the pow of the distance between the current point and the point received by parameter
     */
    public double distanceSquared(Point point) {
        Double3 temp = point.xyz;
        Double3 temp2 = this.xyz;
        /**
         * x*x + y*y + z*z
         */
        return ((temp.d1 - temp2.d1) * (temp.d1 - temp2.d1) + (temp.d2 - temp2.d2) * (temp.d2 - temp2.d2) + (temp.d3 - temp2.d3) * (temp.d3 - temp2.d3));
    }

}
