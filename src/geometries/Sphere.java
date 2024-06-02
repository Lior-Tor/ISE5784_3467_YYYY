package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Util;
import primitives.Vector;

import java.util.List;

/**
 * Sphere Class
 */
public class Sphere extends RadialGeometry {

    private final Point center;

    /**
     * Sphere constructor
     *
     * @param radius field of Sphere
     * @param center field of Sphere
     */
    public Sphere(double radius, Point center) {
        super(radius);
        this.center = center;
    }

    @Override
    public Vector getNormal(Point point) {
        Vector n = point.subtract(center);
        return n.normalize();
    }

    @Override
    public String toString() {
        return "Sphere{" +
                "center=" + center + ' ' + super.toString() +
                '}';
    }

    @Override
    public List<Point> findIntersections(Ray ray) {
        Point p0 = ray.getHead();
        Point o = this.center;
        Vector v = ray.getDirection();
        double r = this.radius;
        Vector u;

        try {
            u = o.subtract(p0);
        } catch (IllegalArgumentException ex) {
            // p0 is the center of the sphere
            Point intersection = o.add(v.normalize().scale(this.radius));
            return List.of(intersection);
        }

        double tm = Util.alignZero(v.dotProduct(u));
        double dSquared = Util.isZero(tm) ? Util.alignZero(u.lengthSquared()) : Util.alignZero(u.lengthSquared() - tm * tm);

        // No intersection: the ray direction is outside the sphere
        if (dSquared >= r * r)
            return null;

        double th = Math.sqrt(Util.alignZero(r * r - dSquared));
        double t1 = Util.alignZero(tm - th);
        double t2 = Util.alignZero(tm + th);

        if (t1 > 0 && t2 > 0) {
            Point P1 = ray.getPoint(t1);
            Point P2 = ray.getPoint(t2);
            return List.of(P1, P2);
        }

        if (t1 > 0) {
            Point P1 = ray.getPoint(t1);
            return List.of(P1);
        }

        if (t2 > 0) {
            Point P2 = ray.getPoint(t2);
            return List.of(P2);
        }

        return null;
    }

}
