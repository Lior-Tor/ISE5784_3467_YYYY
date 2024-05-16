package geometries;

import primitives.Point;
import primitives.Vector;

/**
 * Sphere Class
 */
public class Sphere extends RadialGeometry {

    private final Point center;

    /**
     * Sphere constructor
     *
     * @param radius field of Sphere
     * @param center field of Sphere
     */
    public Sphere(double radius, Point center) {
        super(radius);
        this.center = center;
    }

    @Override
    public Vector getNormal(Point point) {
        return null;
    }

    @Override
    public String toString() {
        return "Sphere{" +
                "center=" + center + ' ' + super.toString() +
                '}';
    }

}
