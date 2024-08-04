package geometries;

import primitives.Point;
import primitives.Vector;

/**
 * Abstract class RadialGeometry is a base class for geometries.
 */
public abstract class RadialGeometry extends Geometry {
    /**
     * The radius of the geometry.
     */
    protected double radius;

    /**
     * Constructor for the RadialGeometry class receiving a radius.
     *
     * @param radius the radius of the geometry.
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