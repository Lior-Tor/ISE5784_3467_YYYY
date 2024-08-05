package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Util;
import primitives.Vector;

import java.util.List;

import static primitives.Util.alignZero;

/**
 * Class Sphere is the class representing a sphere in the 3D space.
 */
public class Sphere extends RadialGeometry {
    private final Point center;

    /**
     * Constructor for Sphere class receiving a radius and a center point.
     *
     * @param radius of Sphere.
     * @param center of Sphere.
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
    protected List<GeoPoint> findGeoIntersectionsHelper(Ray ray,double maxDistance) {
        Vector u; // Vector from the center of the sphere to the head of the ray
        try { // When p0 and the center are the same point
            u = this.center.subtract(ray.getHead());
        } catch (IllegalArgumentException ex) {
            return  List.of(new GeoPoint(this, ray.getPoint(this.radius)));
        }

        double tm = u.dotProduct(ray.getDirection());
        double d2 = u.lengthSquared() - tm * tm;
        double th2 = (radius*radius) - d2;

        if (alignZero(th2) <= 0) // if the ray doesn't intersect the sphere
            return null;

        double th = Math.sqrt(th2);
        double t2 = alignZero(tm + th); // double t2 = alignZero(tm + th2);

        if (t2 <= 0) // if the ray starts after the sphere
            return null;

        double t1 = alignZero(tm -th);
        if(alignZero(t1-maxDistance)>0 || alignZero(t2-maxDistance)>0) // if the ray is too far
            return null;

        return t1 <= 0 ? List.of(new GeoPoint(this, ray.getPoint(t2))) : List.of(new GeoPoint(this, ray.getPoint(t1)),new GeoPoint(this, ray.getPoint(t2)));
    }
}