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

    @Override
    protected List<GeoPoint> findGeoIntersectionsHelper(Ray ray, double maxDistance) {
        List<GeoPoint> intersections = null; // The list of intersections to return.

        for (Intersectable item : this.intersections) { // Iterate over the geometries in the list.
            List<GeoPoint> current = item.findGeoIntersections(ray, maxDistance); // Find the intersections of the current geometry.
            if (current != null) { // If there are intersections.
                if (intersections == null) // If this is the first geometry with intersections.
                    intersections = new LinkedList<>(current); // Initialize the list of intersections.
                else
                    intersections.addAll(current); // Add the intersections to the list.
            }
        }

        return intersections; // Return the list of intersections.
    }
}