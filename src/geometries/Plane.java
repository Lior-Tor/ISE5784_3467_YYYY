package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Util;
import primitives.Vector;

import static primitives.Util.alignZero;
import static primitives.Util.isZero;

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
    protected List<GeoPoint> findGeoIntersectionsHelper(Ray ray, double maxDistance) {
        Point p0 = ray.getHead();
        Vector v = ray.getDirection();
        Vector sub;

        try { // Calculate the vector from the head of the ray to the point of the plane.
            sub = q.subtract(p0);
        } catch (IllegalArgumentException ignore) {
            return null;
        }

        double nv = normal.dotProduct(v);
        if (isZero(nv))  // The ray is parallel to the plane.
            return null;

        double t = alignZero(normal.dotProduct(sub) / nv);
        return t > 0 && alignZero(t - maxDistance) <= 0 ? List.of(new GeoPoint(this, ray.getPoint(t))) : null;
    }
}