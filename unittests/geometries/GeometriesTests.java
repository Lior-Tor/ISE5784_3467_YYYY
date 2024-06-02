package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GeometriesTests {

    @Test
    void findIntersections() {
        Sphere sphere = new Sphere(1, new Point(2, 2, 0));
        Triangle triangle = new Triangle(new Point(3, 2, 1), new Point(3, 4, 0), new Point(3, 2, -1));
        Plane plane = new Plane(new Point(4, 1, 1), new Point(4, 3, 0), new Point(4, 1, -1));
        Geometries geometries = new Geometries(sphere, triangle, plane);
        Geometries empty = new Geometries();

        // ============ Equivalence Partitions Tests ==============
        //TC01: Normal tests
        List<Point> result = geometries.findIntersections(new Ray(new Point(2, 2, 0), new Vector(5, -5, 0)));
        assertEquals(2, result.size(), "Wrong number of intersections");

        // =============== Boundary Values Tests ==================
        //TC11: Empty geometries
        assertNull(empty.findIntersections(new Ray(new Point(0, 2, 0), new Vector(5, 0, 0))), "wrong number of intersections when empty geometries");

        //TC12: No intersections
        assertNull(empty.findIntersections(new Ray(new Point(0, 2, 0), new Vector(-5, 0, 0))), "wrong number of intersections when No intersections");

        //TC13: Only one object intersected
        result = geometries.findIntersections(new Ray(new Point(2, 2, 2), new Vector(0, 0, -1)));
        assertEquals(2, result.size(), "Wrong number of intersections");

        //TC14: All objects intersected
        result = geometries.findIntersections(new Ray(new Point(0, 2.5, 0), new Vector(5, 0, 0)));
        assertEquals(4, result.size(), "Wrong number of intersections");
    }

}
