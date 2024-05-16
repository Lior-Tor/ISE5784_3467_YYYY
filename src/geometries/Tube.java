package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;
import primitives.Util;

/**
 * Tube Class
 * Tube (infinite cylinder)
 */

public class Tube extends RadialGeometry {

    /**
     * axis (line with direction)
     */
    protected final Ray axis;

    /**
     * Tube constructor
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
                "axis=" + axis + ' ' + super.toString() +
                '}';
    }

    @Override
    public Vector getNormal(Point point) {
        return null;
    }

}
