package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static primitives.Util.alignZero;

/**
 * Class Triangle is the class representing a triangle in the 3D space.
 */
public class Triangle extends Polygon {
    /**
     * Constructor for Triangle class receiving three points.
     *
     * @param p1 the first point.
     * @param p2 the second point.
     * @param p3 the third point.
     */
    public Triangle(Point p1, Point p2, Point p3) {
        super(p1, p2, p3);
    }

    @Override
    protected List<GeoPoint> findGeoIntersectionHelper(Ray ray) {
        Vector v = ray.getDirection(); // The direction of the ray
        Point p0 = ray.getHead(); // The start point of the ray

        List<GeoPoint> intersections = this.plane.findGeoIntersectionHelper(ray); // The intersections with the plane

        if (intersections == null) // If there are no intersections with the plane
            return null;

        Vector v1, v2, v3;
        Vector n1, n2, n3;

        Point p1 = this.vertices.get(0); // The first point of the triangle
        Point p2 = this.vertices.get(1); // The second point of the triangle
        Point p3 = this.vertices.get(2); // The third point of the triangle
        try { // Calculate the vectors of the triangle
            v1 = p1.subtract(p0);
            v2 = p2.subtract(p0);
            v3 = p3.subtract(p0);
        } catch (IllegalArgumentException ex) {
            return null;
        }

        try { // Calculate the normals of the triangle
            n1 = v1.crossProduct(v2).normalize();
            n2 = v2.crossProduct(v3).normalize();
            n3 = v3.crossProduct(v1).normalize();
        } catch (IllegalArgumentException ex) {
            return null;
        }

        double vn1, vn2, vn3; // The dot product of the direction of the ray and the normals of the triangle
        vn1 = alignZero(v.dotProduct(n1));
        vn2 = alignZero(v.dotProduct(n2));
        vn3 = alignZero(v.dotProduct(n3));

        if ((vn1 < 0 && vn2 < 0 && vn3 < 0) || (vn1 > 0 && vn2 > 0 && vn3 > 0)) { // The ray is outside the triangle
            intersections.getFirst().geometry = this; // Update the geometry of the intersection
            return intersections; // Return the intersections
        }

        return null; // The ray does not intersect the triangle
    }
}