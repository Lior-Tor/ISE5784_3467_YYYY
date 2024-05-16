package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Util;
import primitives.Vector;

/**
 * Tube Class
 * Tube (infinite cylinder)
 */

public class Tube extends RadialGeometry {

    /**
     * axisRay (line with direction)
     */
    protected final Ray axis;

    /**
     * Tube's Constructor
     *
     * @param radius double's type
     * @param ray    Ray's type
     */
    public Tube(double radius, Ray ray) {
        super(radius);
        this.axis = ray;
    }

    @Override
    public String toString() {
        return "Tube{" +
                "axisRay=" + axis + ' ' + super.toString() +
                '}';
    }

    @Override
    public Vector getNormal(Point point) {
        return null;
    }

}
