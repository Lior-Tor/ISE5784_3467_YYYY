package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Vector;

import static org.junit.jupiter.api.Assertions.*;

class SphereTests {

    /**
     * Test method for {@link geometries.Sphere#Sphere(double radius, Point center)}.
     */
    @Test
    public void testConstructor() {
        // ============ Equivalence Partitions Tests ==============
        // TC01 : Try to create a Sphere
        try {
            new Sphere(2, new Point(1, 1, 1));
        } catch (IllegalArgumentException e) {
            fail("Failed constructing a correct Sphere");
        }
    }

    /**
     * Test method for {@link geometries.Sphere#getNormal(Point)}.
     */
    @Test
    void getNormal() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: All the points are the same
        final Sphere sphere = new Sphere(2, new Point(1, 1, 1));
        assertEquals(new Vector(2, 0, 0).normalize(), sphere.getNormal(new Point(3, 1, 1)), "The normal of the sphere is not normalized");
    }

}

