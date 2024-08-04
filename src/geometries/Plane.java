package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Util;
import primitives.Vector;

import java.util.List;

/**
 * Class Plane is the class representing a plane in the 3D space.
 */
public class Plane extends Geometry {
    private final Point q;
    private final Vector normal;

    /**
     * Constructor for the Plane class receiving three points.
     * The plane is defined by the three points.
     * The normal is calculated by the cross product of the vectors from p1 to p2 and from p2 to p3.
     *
     * @param p1 the first point.
     * @param p2 the second point.
     * @param p3 the third point.
     * @throws IllegalArgumentException if the points are on the same line.
     */
    public Plane(Point p1, Point p2, Point p3) {
        this.q = p1;
        Vector v1;
        Vector v2;
        Vector tmp;

        try {
            v1 = p2.subtract(p1);
            v2 = p3.subtract(p2);
            tmp = v1.crossProduct(v2);
        } catch (IllegalArgumentException ex) {
            throw ex;
        }

        this.normal = tmp.normalize();
    }

    /**
     * Constructor for the Plane class receiving a point and a normal.
     * The plane is defined by the point and the normal.
     *
     * @param point  the point.
     * @param normal getting a vector, not necessary a normal vector.
     */
    public Plane(Point point, Vector normal) {
        this.q = point;
        this.normal = normal.normalize();
    }

    @Override
    public String toString() {
        return "Plane{" +
                "q=" + q +
                ", normal=" + normal +
                '}';
    }

    /**
     * Getter for normal field.
     *
     * @return the normal.
     */
    public Vector getNormal() {
        return this.normal;
    }

    @Override
    public Vector getNormal(Point point) {
        return normal;
    }

    @Override
    protected List<GeoPoint> findGeoIntersectionHelper(Ray ray) {
        Point p0 = ray.getHead(); // The head of the ray
        Vector v = ray.getDirection(); // The direction of the ray
        Vector n = this.normal; // The normal to the plane
        Point q = this.q; // A point on the plane
        Vector sub; // The vector from the head of the ray to the point on the plane
        double nv; // The dot product of the normal and the direction
        double nqp0; // The dot product of the normal and the vector from the head of the ray to the point on the plane

        try {
            sub = q.subtract(p0);
        } catch (IllegalArgumentException ex) {
            return null;
        }

        nv = n.dotProduct(v);
        if (Util.isZero(nv)) { // If the ray is parallel to the plane
            return null;
        }

        nqp0 = n.dotProduct(sub);
        if (Util.isZero(nqp0)) { // If the ray is on the plane
            return null;
        }

        double t = Util.alignZero(nqp0 / nv);
        if (t <= 0) { // If the point is behind the head of the ray
            return null;
        }

        Point result = ray.getPoint(t); // The point on the plane
        return List.of(new GeoPoint(this, result)); // Return the point on the plane
    }
}