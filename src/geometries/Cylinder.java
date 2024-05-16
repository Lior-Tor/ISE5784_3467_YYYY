package geometries;

import primitives.Util;
import primitives.Vector;
import primitives.Point;
import primitives.Ray;

/**
 * Cylinder class
 */
public class Cylinder extends Tube {

    private final double height;

    /**
     * Cylinder constructor
     *
     * @param radius
     * @param ray
     * @param height
     */
    public Cylinder(double radius, Ray ray, double height) {
        super(radius, ray);
        this.height = height;
    }

    @Override
    public String toString() {
        return "Cylinder{" +
                "height=" + height +
                ", axis=" + axis +
                ", radius=" + radius +
                '}';
    }

    @Override
    public Vector getNormal(Point point) {
        return null;
    }

}
