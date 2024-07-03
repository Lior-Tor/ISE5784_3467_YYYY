package primitives;

import java.util.List;
import java.util.Objects;

/**
 * Ray - represents a "Semi-straight - all the points on the straight line that are on one
 * side of the given point on the straight line called the beginning of the ray
 */
public class Ray {

    private final Point head;

    private final Vector direction;

    /**
     * Constructor for Ray
     *
     * @param p
     * @param vec
     */
    public Ray(Point p, Vector vec) {
        this.direction = vec.normalize();
        this.head = p;
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

    public Point getHead() {
        return head;
    }

    public Vector getDirection() {
        return direction;
    }

    /**
     * Calculate the point on the ray by the parameter t
     *
     * @param t
     * @return the point on the ray
     */
    public Point getPoint(double t) {
        if (Util.isZero(t)) {
            return this.head;
        }
        Vector vec = this.direction.scale(t);
        return this.head.add(vec);
    }

    /**
     * Finds the closest point to the head of the ray from a list of points.
     *
     * @param pointList
     * @return the closest point from the list
     */
    public Point findClosestPoint(List<Point> pointList) {
        double temp;
        Point min = null;
        double minDistance = Double.POSITIVE_INFINITY;
        for (Point point : pointList) {
            temp = this.head.distance(point);
            if (temp < minDistance) {
                min = point;
                minDistance = temp;
            }
        }
        return min;
    }

}

