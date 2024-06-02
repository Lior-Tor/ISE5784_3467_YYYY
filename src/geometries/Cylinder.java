package geometries;

import primitives.Util;
import primitives.Vector;
import primitives.Point;
import primitives.Ray;

import java.util.List;

/**
 * Cylinder Class
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
        Point p0 = this.axis.getHead();
        Vector dir = this.axis.getDirection();

        if (p0.equals(point))
            return dir.normalize();

        Vector sub;
        double t;

        try {
            sub = point.subtract(p0);
            t = Util.alignZero(dir.dotProduct(sub));
        } catch (IllegalArgumentException ex) {
            return null;
        }

        if (Util.isZero(t))
            return dir.normalize();

        if (Util.isZero(t - this.height))
            return dir;

        Point o;
        Vector normal;
        try {
            o = p0.add(dir.scale(t));
            normal = point.subtract(o).normalize();
        } catch (IllegalArgumentException ex) {
            return null;
        }

        return normal;
    }

    @Override
    public List<Point> findIntersections(Ray ray) {
        return null;
    }

}
