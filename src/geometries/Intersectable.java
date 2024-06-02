package geometries;

import primitives.Point;
import primitives.Ray;

import java.util.List;

/**
 * Intersectable Interface
 */
public interface Intersectable {

    /**
     * Find Intersections
     *
     * @param ray
     * @return
     */
    List<Point> findIntersections(Ray ray);

}
