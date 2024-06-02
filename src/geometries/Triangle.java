package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

public class Triangle extends Polygon {

    /**
     * Triangle constructor
     *
     * @param p1
     * @param p2
     * @param p3
     */
    public Triangle(Point p1, Point p2, Point p3) {
        super(p1, p2, p3);
    }

    @Override
    public List<Point> findIntersections(Ray ray) {
        List<Point> list = this.plane.findIntersections(ray);
        if (list == null)
            return null;

        Vector v1, v2, v3;
        Vector n1, n2, n3;
        Vector v = ray.getDirection();
        Point p1 = this.vertices.get(0);
        Point p2 = this.vertices.get(1);
        Point p3 = this.vertices.get(2);

        try {
            v1 = p1.subtract(ray.getHead());
            v2 = p2.subtract(ray.getHead());
            v3 = p3.subtract(ray.getHead());
        } catch (IllegalArgumentException ex) {
            return null;
        }

        try {
            n1 = v1.crossProduct(v2).normalize();
            n2 = v2.crossProduct(v3).normalize();
            n3 = v3.crossProduct(v1).normalize();
        } catch (IllegalArgumentException ex) {
            return null;
        }

        double vn1, vn2, vn3;
        vn1 = v.dotProduct(n1);
        vn2 = v.dotProduct(n2);
        vn3 = v.dotProduct(n3);

        if ((vn1 < 0 && vn2 < 0 && vn3 < 0) || (vn1 > 0 && vn2 > 0 && vn3 > 0)) {
            return list;
        }
        return null;
    }

}
