package geometries;

import primitives.Util;
import primitives.Vector;
import primitives.Point;
import primitives.Ray;

import java.util.List;

import static primitives.Util.alignZero;

/**
 * Class Cylinder is the class representing a cylinder in the 3D space.
 */
public class Cylinder extends Tube {
    private final double height;

    /**
     * Constructor for the Cylinder class receiving a radius, an axis, and a height.
     *
     * @param radius the radius of the cylinder.
     * @param axis   the axis of the cylinder.
     * @param height the height of the cylinder.
     * @throws IllegalArgumentException if the height is less than or equal to 0.
     */
    public Cylinder(double radius, Ray axis, double height) {
        super(radius, axis);
        if (alignZero(height) <= 0) {
            throw new IllegalArgumentException("Height must be greater than 0");
        }
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
        Point head = this.axis.getHead();
        Vector direction = this.axis.getDirection();

        if (head.equals(point)) // If the point is the head of the axis
            return direction.normalize();

        Vector sub; // The vector from the head of the axis to the point
        double t; // The scalar of the projection of the vector sub on the direction vector

        try {
            sub = point.subtract(head);
            t = Util.alignZero(direction.dotProduct(sub));
        } catch (IllegalArgumentException ex) {
            return null;
        }

        if (Util.isZero(t)) // If the point is on the base of the cylinder
            return direction.normalize();

        if (Util.isZero(t - this.height)) // If the point is on the top of the cylinder
            return direction;

        Point o; // The point on the axis
        Vector normal; // The normal vector to the cylinder
        try {
            o = head.add(direction.scale(t));
            normal = point.subtract(o).normalize();
        } catch (IllegalArgumentException ex) {
            return null;
        }

        return normal;
    }
}