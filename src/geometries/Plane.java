package geometries;

import primitives.Point;
import primitives.Vector;

/**
 * Plane class
 */
public class Plane extends Geometry {

    private final Point q;

    private final Vector normal;

    /**
     * constructor
     * calculate the normal according to what was learned about the normal to a triangle
     *
     * @param p1
     * @param p2
     * @param p3
     */
    public Plane(Point p1, Point p2, Point p3) {
        this.q = p1;
        this.normal = null;
    }

    /**
     * put in normal filed the normalized vector received as parameter
     *
     * @param point
     * @param normal - getting a vector, not necessary a normal vector
     */
    public Plane(Point point, Vector normal) {
        this.q = point;
        this.normal = normal.normalize();
    }

    @Override
    public Vector getNormal(Point point) {
        return normal;
    }

    @Override
    public String toString() {
        return "Plane{" +
                "q0=" + q +
                ", normal=" + normal +
                '}';
    }

    /**
     * getter for normal field
     *
     * @return normal
     */
    public Vector getNormal() {
        return this.normal;
    }

}