package geometries;

import org.junit.jupiter.api.Test;
import primitives.Double3;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for geometries.Plane class
 */
class PlaneTests {
    /**
     * Test method for {@link geometries.Plane#Plane(Point p1, Point p2, Point p3)}.
     */
    @Test
    public void testConstructor() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: All the points are the same
        // try to crate a plane
        try {
            new Plane(new Point(0, 0, 1), new Point(2, 0, 0), new Point(0, 4, 0));
        } catch (IllegalArgumentException e) {
            fail("Failed constructing a correct Plane");
        }

        // =============== Boundary Values Tests ==================
        // TC11: two points are the same
        assertThrows(IllegalArgumentException.class,
                () -> new Plane(new Point(1, 2, 3), new Point(1, 2, 3), new Point(1, 2, 6)),
                "Error in ctr when two points are the same");

        // TC12: all the points are on the same line
        assertThrows(IllegalArgumentException.class,
                () -> new Plane(new Point(1, 2, 3), new Point(2, 4, 6), new Point(-1, -2, -3)),
                "Error in ctr when all the points are on the same line");
    }

    /**
     * Test method for {@link Plane#getNormal()}.
     */
    @Test
    void getNormal() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: All the points are the same
        final Plane plane = new Plane(new Point(0, 0, 1), new Point(2, 0, 0), new Point(0, 4, 0));
        assertEquals(new Vector(4, 2, 8).normalize(), plane.getNormal(), "The normal of the plane is not normalized");
    }

    /**
     * Test method for {@link Plane#findIntersections(Ray)}.
     */
    @Test
    void testFindIntersections() {
        Plane plane = new Plane(new Point(0, 1, 0), new Point(1, 1, 0), new Point(0, 1, 1));
        Point intersect1;
        List<Point> result;

        // ============ Equivalence Partitions Tests ==============
        // The Ray must be neither orthogonal nor parallel to the plane
        // TC01: Ray intersects the plane
        intersect1 = new Point(-0.33333333333333337, 1, 0);
        result = plane.findIntersections(new Ray(new Point(0, 0.5, 0), new Vector(-1, 1.5, 0)));
        assertEquals(1, result.size(), "Wrong number of points");
        assertEquals(List.of(intersect1), result, "There is an intersection!");

        // TC02: Ray does not intersect the plane
        assertNull(plane.findIntersections(new Ray(new Point(0, 0.5, 0), new Vector(-1, -1.5, 0))), "Ray line out of plane");

        // =============== Boundary Values Tests ==================
        // Group: Ray is parallel to the plane
        // TC11: The ray included in the plane
        assertNull(plane.findIntersections(new Ray(new Point(0, 1, 0), new Vector(-1, 0, 0))), "Ray is not parallel to the plane");

        // TC12: The ray not included in the plane
        assertNull(plane.findIntersections(new Ray(new Point(0, 0, 0), new Vector(-1, 0, 0))), "Ray is not parallel to the plane");

        // Group: Ray is orthogonal to the plane
        // TC13: Before plane
        intersect1 = new Point(0.36333333333333334, 1, 0);
        result = plane.findIntersections(new Ray(new Point(0.36, 0.51, 0), new Vector(0.01, 1.47, 0)));
        assertEquals(1, result.size(), "Wrong number of points");
        assertEquals(List.of(intersect1), result, "There is an intersection!");

        // TC14: In plane
        assertNull(plane.findIntersections(new Ray(new Point(0.5, 1, 0), new Vector(0, 1, 0))), " There are no intersections");

        // TC15: After plane
        assertNull(plane.findIntersections(new Ray(new Point(-0.5, 2, 0), new Vector(0, 0.5, 0))), " There are no intersections");

        // TC16: Ray is neither orthogonal nor parallel to and begins at the plane
        //      (p0 is in the plane, but not the ray)
        assertNull(plane.findIntersections(new Ray(new Point(0.5, 1, 0), new Vector(-1.5, 1, 0))), " There are no intersections");

        // TC17: Ray is neither orthogonal nor parallel to the plane and begins in
        //      the same point which appears as reference point in the plane (Q)
        assertNull(plane.findIntersections(new Ray(new Point(0, 1, 0), new Vector(-0.5, 1, 0))), " There are no intersections");
    }
}