package geometries;

import org.junit.jupiter.api.Test;
import primitives.Double3;
import primitives.Point;
import primitives.Vector;

import static org.junit.jupiter.api.Assertions.*;

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
                () -> new Plane(new Point(1, 2, 3), new Point(1, 2, 3), new Point(1, 2, 6)), //
                "Error in ctr when two points are the same");

        // TC12: all the points are on the same line
        assertThrows(IllegalArgumentException.class,
                () -> new Plane(new Point(1, 2, 3), new Point(2, 4, 6), new Point(-1, -2, -3)), //
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

}

