package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Util;
import primitives.Vector;

import java.util.List;

/**
 * Class Tube is the class representing a tube in the 3D space.
 * Infinite cylinder with a radius and an axis.
 */
public class Tube extends RadialGeometry {
    /**
     * The axis of the tube (line with a direction).
     */
    protected Ray axis;

    /**
     * Constructor for the Tube class receiving a radius and an axis.
     *
     * @param radius the radius of the tube.
     * @param ray    the axis of the tube.
     */
    public Tube(double radius, Ray ray) {
        super(radius);
        this.axis = ray;
    }

    @Override
    public String toString() {
        return "Tube{" +
                "axis=" + axis + ' ' + super.toString() +
                '}';
    }

    @Override
    public Vector getNormal(Point point) {
        double t; // The scalar of the projection of the vector sub on the direction vector
        Point p0 = this.axis.getHead(); // The head of the axis
        Vector sub; // The vector from the head of the axis to the point
        Vector dir = this.axis.getDirection(); // The direction of the axis
        try { // If the point is the head of the axis
            sub = point.subtract(p0);
            t = Util.alignZero(dir.dotProduct(sub));
        } catch (IllegalArgumentException ex) {
            return null;
        }

        if (Util.isZero(t)) // If the point is on the axis
            return sub.normalize();

        Point o = p0.add(dir.scale(t)); // The point on the axis closest to the point
        Vector normal; // The normal to the tube
        try { // Calculate the normal
            normal = point.subtract(o).normalize();
        } catch (IllegalArgumentException ex) {
            return null;
        }

        return normal; // Return the normal
    }

    @Override
    protected List<GeoPoint> findGeoIntersectionsHelper(Ray ray, double maxDistance) {
        return null;
    }
}