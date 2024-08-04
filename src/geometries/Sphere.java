package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Util;
import primitives.Vector;

import java.util.List;

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
    protected List<GeoPoint> findGeoIntersectionHelper(Ray ray) {
        Point p0 = ray.getHead(); // The start point of the ray
        Point o = this.center; // The center of the sphere
        Vector v = ray.getDirection(); // The direction of the ray
        double r = this.radius; // The radius of the sphere
        Vector u; // The vector from the start point of the ray to the center of the sphere

        try {
            u = o.subtract(p0);
        } catch (IllegalArgumentException ex) {
            /* p0 is the center of the sphere. */
            Point intersection = o.add(v.normalize().scale(this.radius));
            return List.of(new GeoPoint(this, intersection));
        }

        double tm = Util.alignZero(v.dotProduct(u)); // The projection of u on v
        double squaredDistance = Util.isZero(tm) ? Util.alignZero(u.lengthSquared()) : Util.alignZero(u.lengthSquared() - tm * tm); // The squared distance between the start point of the ray and the center of the sphere

        /* The ray does not intersect the sphere (the squared distance is greater than the squared radius). The ray direction is outside the sphere. */
        if (squaredDistance >= r * r)
            return null;

        double th = Math.sqrt(Util.alignZero(r * r - squaredDistance)); // The height of the triangle formed by the ray, the center of the sphere, and the intersection point
        double t1 = Util.alignZero(tm - th); // The distance from the start point of the ray to the first intersection point
        double t2 = Util.alignZero(tm + th); // The distance from the start point of the ray to the second intersection point

        if (t1 > 0 && t2 > 0) { // The ray intersects the sphere in two points
            Point P1 = ray.getPoint(t1); // The first intersection point
            Point P2 = ray.getPoint(t2); // The second intersection point
            return List.of(new GeoPoint(this, P1), new GeoPoint(this, P2)); // Return the list of the intersection points
        }

        if (t1 > 0) { // The ray intersects the sphere in one point (first intersection point)
            Point P1 = ray.getPoint(t1); // The intersection point
            return List.of(new GeoPoint(this, P1)); // Return the list of the intersection points
        }

        if (t2 > 0) { // The ray intersects the sphere in one point (second intersection point)
            Point P2 = ray.getPoint(t2); // The intersection point
            return List.of(new GeoPoint(this, P2)); // Return the list of the intersection points
        }

        return null;
    }
}