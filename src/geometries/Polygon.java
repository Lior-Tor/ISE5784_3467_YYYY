package geometries;

import java.util.List;

import static primitives.Util.alignZero;
import static primitives.Util.isZero;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

/**
 * Polygon class represents two-dimensional polygon in 3D Cartesian coordinate system
 *
 * @author Dan Zilberstein
 */
public class Polygon extends Geometry {
    /**
     * List of polygon's vertices
     */
    protected final List<Point> vertices;

    /**
     * Associated plane in which the polygon lays
     */
    protected final Plane plane;

    /**
     * The size of the polygon - the amount of the vertices in the polygon
     */
    private final int size;

    /**
     * Polygon constructor based on vertices list. The list must be ordered by edge
     * path. The polygon must be convex.
     *
     * @param vertices list of vertices according to their order by
     *                 edge path
     * @throws IllegalArgumentException in any case of illegal combination of
     *                                  vertices:
     *                                  <ul>
     *                                  <li>Less than 3 vertices</li>
     *                                  <li>Consequent vertices are in the same
     *                                  point
     *                                  <li>The vertices are not in the same
     *                                  plane</li>
     *                                  <li>The order of vertices is not according
     *                                  to edge path</li>
     *                                  <li>Three consequent vertices lay in the
     *                                  same line (180&#176; angle between two
     *                                  consequent edges)
     *                                  <li>The polygon is concave (not convex)</li>
     *                                  </ul>
     */
    public Polygon(Point... vertices) {
        if (vertices.length < 3)
            throw new IllegalArgumentException("A polygon can't have less than 3 vertices");
        this.vertices = List.of(vertices);
        size = vertices.length;

        // Generate the plane according to the first three vertices and associate the
        // polygon with this plane.
        // The plane holds the invariant normal (orthogonal unit) vector to the polygon
        plane = new Plane(vertices[0], vertices[1], vertices[2]);
        if (size == 3) return; // no need for more tests for a Triangle

        Vector n = plane.getNormal();
        // Subtracting any subsequent points will throw an IllegalArgumentException
        // because of Zero Vector if they are in the same point
        Vector edge1 = vertices[vertices.length - 1].subtract(vertices[vertices.length - 2]);
        Vector edge2 = vertices[0].subtract(vertices[vertices.length - 1]);

        // Cross Product of any subsequent edges will throw an IllegalArgumentException
        // because of Zero Vector if they connect three vertices that lay in the same
        // line.
        // Generate the direction of the polygon according to the angle between last and
        // first edge being less than 180 deg. It is hold by the sign of its dot product
        // with the normal. If all the rest consequent edges will generate the same sign
        // - the polygon is convex ("kamur" in Hebrew).
        boolean positive = edge1.crossProduct(edge2).dotProduct(n) > 0;
        for (var i = 1; i < vertices.length; ++i) {
            // Test that the point is in the same plane as calculated originally
            if (!isZero(vertices[i].subtract(vertices[0]).dotProduct(n)))
                throw new IllegalArgumentException("All vertices of a polygon must lay in the same plane");
            // Test the consequent edges have
            edge1 = edge2;
            edge2 = vertices[i].subtract(vertices[i - 1]);
            if (positive != (edge1.crossProduct(edge2).dotProduct(n) > 0))
                throw new IllegalArgumentException("All vertices must be ordered and the polygon must be convex");
        }
    }

    @Override
    public Vector getNormal(Point point) {
        return plane.getNormal();
    }

    @Override
    protected List<GeoPoint> findGeoIntersectionsHelper(Ray ray, double maxDistance) {
        List<GeoPoint> intersections = plane.findGeoIntersections(ray, maxDistance);

        if (intersections == null) // if the ray doesn't intersect the plane
            return null;

        Point p0 = ray.getHead(); // p0 is the ray's head
        Vector v0 = ray.getDirection(); // v0 is the ray's direction
        Point p1 = vertices.get(1); // p1 is the second vertex
        Point p2 = vertices.get(0); // p2 is the first vertex
        Vector v1 = p1.subtract(p0); // v1 is the vector from p0 to p1
        Vector v2 = p2.subtract(p0); // v2 is the vector from p0 to p2

        double sign = alignZero(v0.dotProduct(v1.crossProduct(v2))); // sign is the dot product of v0 and the cross product of v1 and v2
        if (isZero(sign))  // if the sign is zero
            return null; // the ray doesn't intersect the polygon

        boolean positive = (sign > 0); // positive is true if the sign is positive

        for (int i = vertices.size() - 1; i > 0; --i) { // for each vertex in the polygon
            v1 = v2;
            v2 = vertices.get(i).subtract(p0);

            sign = alignZero(v0.dotProduct(v1.crossProduct(v2)));
            if (isZero(sign)) // if the sign is zero
                return null;
            if (positive != (sign > 0)) // if the sign is not positive
                return null;
        }

        for (GeoPoint gp : intersections) // for each intersection point
            gp.geometry = this;

        return intersections; // return the list of intersection points
    }
}