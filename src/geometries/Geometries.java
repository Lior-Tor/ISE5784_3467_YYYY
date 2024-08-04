package geometries;

import primitives.Point;
import primitives.Ray;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * Class Geometries is the class representing a collection of geometries in the 3D space.
 */
public class Geometries extends Intersectable {
    private final List<Intersectable> intersections;

    /**
     * Default constructor.
     */
    public Geometries() {
        this.intersections = new LinkedList<>();
    }

    /**
     * Constructor with a list of geometries.
     *
     * @param geometries the geometries to add.
     */
    public Geometries(Intersectable... geometries) {
        this.intersections = List.of(geometries);
    }

    /**
     * Add geometries to the list.
     *
     * @param geometries the geometries to add.
     */
    public void add(Intersectable... geometries) {
        Collections.addAll(intersections, geometries);
    }

//    @Override
//    public List<Point> findIntersections(Ray ray) {
//        List<Point> points = null; // The list of points that intersect the ray
//
//        /* For each geometry in the list, find the intersections with the ray */
//        for (Intersectable geo : intersections) { // For each geometry in the list
//            List<Point> hasIntersections = geo.findIntersections(ray); // Find the intersections
//            if (hasIntersections != null) { // If there are intersections
//                if (points == null) { // If the list of points is not initialized
//                    points = new ArrayList<>(); // Initialize the list of points
//                }
//                points.addAll(hasIntersections); // Add the intersections to the list
//            }
//        }
//
//        return points; // Return the list of points
//    }

    @Override
    protected List<GeoPoint> findGeoIntersectionHelper(Ray ray) {
        List<GeoPoint> geoIntersections = null; // The list of GeoPoints that intersect the ray

        /* For each geometry in the list, find the intersections with the ray */
        for (Intersectable item : intersections) { // For each geometry in the list
            List<GeoPoint> current = item.findGeoIntersections(ray); // Find the intersections
            if (current != null) { // If there are intersections
                if (geoIntersections == null) // If the list of GeoPoints is not initialized
                    geoIntersections = new LinkedList<>(current); // Initialize the list of GeoPoints
                else
                    geoIntersections.addAll(current); // Add the intersections to the list
            }
        }

        return geoIntersections; // Return the list of GeoPoints
    }
}