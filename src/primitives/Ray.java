package primitives;

import geometries.Intersectable.GeoPoint;

import java.util.List;
import java.util.Objects;

/**
 * Class Ray is the class representing a ray in the 3D space.
 * Ray - represents a "Semi-straight - all the points on the straight line that are on one
 * side of the given point on the straight line called the beginning of the ray.
 */
public class Ray {
    private Point head;
    private Vector direction;

    /**
     * Ray head shift size for shading rays
     */
    private static final double DELTA = 0.1;

    /**
     * Constructor for Ray class receiving a point and a vector.
     * The point is the head of the ray and the vector is the direction of the ray.
     *
     * @param p   the head of the ray.
     * @param vec the direction of the ray.
     */
    public Ray(Point p, Vector vec) {
        this.direction = vec.normalize();
        this.head = p;
    }

    /**
     * Constructor for Ray class that creates a ray from a point, a direction and a normal.
     *
     * @param p0     the head of the ray.
     * @param dir    the direction of the ray.
     * @param normal the normal vector to the point.
     */
    public Ray(Point p0, Vector dir, Vector normal) {
        double nd = normal.dotProduct(dir);
        Vector epsVector = normal.scale(nd >= 0 ? DELTA : -DELTA);
        head = p0.add(epsVector);
        direction = dir.normalize();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Ray ray)) return false;
        return head.equals(ray.head) && direction.equals(ray.direction);
    }

    @Override
    public int hashCode() {
        return Objects.hash(head, direction);
    }

    @Override
    public String toString() {
        return "Ray{" +
                "head=" + head +
                ", direction=" + direction +
                '}';
    }

    /**
     * Getter for the head of the ray.
     *
     * @return the head of the ray.
     */
    public Point getHead() {
        return head;
    }

    /**
     * Getter for the direction of the ray.
     *
     * @return the direction of the ray.
     */
    public Vector getDirection() {
        return direction;
    }

    /**
     * Calculate the point on the ray by the parameter t.
     *
     * @param t the scalar of the direction vector.
     * @return the point on the ray.
     */
    public Point getPoint(double t) {
        if (Util.isZero(t)) { // If t is zero
            return this.head;
        }
        Vector vec = this.direction.scale(t);
        return this.head.add(vec);
    }

    /**
     * Find the closest point to the head of the ray from a list of points.
     *
     * @param points the list of points.
     * @return the closest point to the head of the ray.
     */
    public Point findClosestPoint(List<Point> points) {
        return points == null || points.isEmpty() ? null
                : findClosestGeoPoint(points.stream().map(p -> new GeoPoint(null, p)).toList()).point;
    }

    /**
     * Find the closest GeoPoint to the head of the ray from a list of GeoPoints.
     *
     * @param pointList the list of GeoPoints.
     * @return the closest GeoPoint to the head of the ray.
     */
    public GeoPoint findClosestGeoPoint(List<GeoPoint> pointList) {
        if (pointList == null) // If the list is null
            return null;
        double temp; // The temporary distance squared
        GeoPoint closestPoint = null; // The closest point
        double min = Double.POSITIVE_INFINITY; // The minimum distance

        for (GeoPoint geo : pointList) { // Iterate over the list
            temp = geo.point.distanceSquared(head); // Calculate the distance squared
            if (temp < min) { // If the distance squared is less than the minimum distance
                min = temp; // Update the minimum distance
                closestPoint = geo; // Update the closest point
            }
        }

        return closestPoint; // Return the closest point
    }
}