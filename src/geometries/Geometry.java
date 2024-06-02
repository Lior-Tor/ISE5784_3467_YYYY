package geometries;

import primitives.Point;
import primitives.Vector;

/**
 * Geometry Interface
 */
public interface Geometry extends Intersectable {

    /**
     * Calculate the normal Vector from the parameter Point
     *
     * @param point used to calculate the normal Vector
     * @return the normal Vector
     */
    public abstract Vector getNormal(Point point);

}
