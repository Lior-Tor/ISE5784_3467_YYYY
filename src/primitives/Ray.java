package primitives;

import java.util.Objects;

/**
 * Ray - represents a "Semi-straight - all the points on the straight line that are on one
 * side of the given point on the straight line called the beginning of the ray
 */
public class Ray {

    private Point head;
    private Vector direction;

    /**
     * constructor for Ray
     *
     * @param h   head
     * @param vec vector
     */
    public Ray(Point h, Vector vec) {
        this.direction = vec.normalize();
        this.head = h;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Ray ray)) return false;
        return head.equals(ray.head) && direction.equals(ray.direction);
    }

    @Override
    public int hashCode() {
        return Objects.hash(head, direction);
    }

    @Override
    public String toString() {
        return "Ray{" +
                "p0=" + head +
                ", dir=" + direction +
                '}';
    }

}
