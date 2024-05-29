package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import static org.junit.jupiter.api.Assertions.*;

// Tube (infinite cylinder)
class TubeTest {

    /**
     * Test method for {@link geometries.Tube#Tube(double radius, Ray ray)}.
     */
    @Test
    public void testConstructor() {
        // ============ Equivalence Partitions Tests ==============
        // TC01 : Try to create a Tube
        try {
            new Tube(2, new Ray(new Point(1, 2, 3), new Vector(1, 2, 1)));
        } catch (IllegalArgumentException e) {
            fail("Failed constructing a correct Sphere");
        }
    }

    /**
     * Test method for {@link geometries.Tube#getNormal(Point)}.
     */
    @Test
    void getNormal() {
        // ============ Equivalence Partitions Tests ==============
        // TC01 : All the points are the same
        final Tube tube = new Tube(2, new Ray(new Point(0, 0, 0), new Vector(0, 0, 1)));
        Point o = new Point(0, 0, 1);
        Point pEP = new Point(0, 2, 1);
        Vector expected1 = new Vector(0, 2, 0).normalize();
        assertEquals(expected1, tube.getNormal(pEP), "EP : Wrong result of normal in tube");

        // =============== Boundary Values Tests ==================
        // TC11: when (P-P0) is orthogonal to the vector
        Point pBVA = new Point(0, 2, 0);
        Vector expected2 = new Vector(0, 2, 0).normalize();
        assertEquals(expected2, tube.getNormal(pBVA), "BVA : Wrong result of normal in tube");
    }

}

