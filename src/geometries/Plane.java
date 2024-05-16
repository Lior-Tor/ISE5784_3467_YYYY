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
     * Constructor
     * Calculate the normal according to what was learned about the normal to a triangle
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
     * Put in normal filed the normalized vector received as parameter
     *
     * @param point
     * @param normal - getting a vector, not necessary a normal vector
     */
    public Plane(Point point, Vector normal) {
        this.q = point;
        this.normal = normal.normalize();
    }

    @Override
    public String toString() {
        return "Plane{" +
                "q=" + q +
                ", normal=" + normal +
                '}';
    }

    @Override
    public Vector getNormal(Point point) {
        return normal;
    }

    /**
     * Getter for normal field
     *
     * @return normal
     */
    public Vector getNormal() {
        return this.normal;
    }

}
