package geometries;

import primitives.Point;
import primitives.Vector;

/**
 * RadialGeometry abstract Class
 */
public abstract class RadialGeometry implements Geometry {

    /**
     * Radius
     */
    protected double radius;

    /**
     * RadialGeometry constructor
     *
     * @param radius fields of RadialGeometry
     */
    public RadialGeometry(double radius) {
        this.radius = radius;
    }

    @Override
    public String toString() {
        return "RadialGeometry{" +
                "radius=" + radius +
                '}';
    }

}

