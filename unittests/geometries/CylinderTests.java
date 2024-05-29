package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import static org.junit.jupiter.api.Assertions.*;

class CylinderTests {

    /**
     * Test method for {@link geometries.Cylinder#Cylinder(double radius, Ray ray, double height)}.
     */
    @Test
    public void testConstructor() {
        // ============ Equivalence Partitions Tests ==============
        // TC01 : Try to create a Tube
        try {
            new Cylinder(2, new Ray(new Point(1, 2, 3), new Vector(1, 2, 1)), 6);
        } catch (IllegalArgumentException e) {
            fail("Failed constructing a correct Sphere");
        }
    }

    /**
     * Test method for {@link geometries.Cylinder#getNormal(Point)}.
     */
    @Test
    void getNormal() {
        // ============ Equivalence Partitions Tests ==============
        // TC01 : on the round surface
        final Cylinder cylinder = new Cylinder(2, new Ray(new Point(0, 0, 0), new Vector(0, 0, 1)), 2);

        Point o = new Point(0, 0, 1);
        Point pEP = new Point(0, 2, 1);
        Vector expected1 = new Vector(0, 2, 0).normalize();
        assertEquals(expected1, cylinder.getNormal(pEP), "EP TC01 : Wrong result of normal in Cylinder");

        // TC02 : On the lower base
        pEP = new Point(1, 1, 0);
        assertEquals(new Vector(0, 0, 1).normalize(), cylinder.getNormal(pEP), "EP TC02: Wrong result of normal in cylinder");

        // TC03 : On the upper base
        pEP = new Point(0, 1, 2);
        expected1 = new Vector(0, 0, 1);
        assertEquals(expected1.normalize(), cylinder.getNormal(pEP), "EP TC03 : Wrong result of normal in cylinder");

        // =============== Boundary Values Tests ==================
        // TC11 : on the lower base edge
        Point pBVA = new Point(0, 2, 0);
        expected1 = new Vector(0, 0, 2);
        assertEquals(expected1.normalize(), cylinder.getNormal(pBVA), "EP TC11 : Wrong result of normal in cylinder");

        // TC12 : on the upper base edge
        pBVA = new Point(0, 2, 2);
        expected1 = new Vector(0, 0, 2);
        assertEquals(expected1.normalize(), cylinder.getNormal(pBVA), "EP TC12 : Wrong result of normal in cylinder");

        // TC13 : on the lower base center
        pBVA = new Point(0, 0, 0);
        expected1 = new Vector(0, 0, 1);
        assertEquals(expected1.normalize(), cylinder.getNormal(pBVA), "EP TC13: Wrong result of normal in cylinder");

        // TC14 : on the upper base center
        pBVA = new Point(0, 0, 2);
        expected1 = new Vector(0, 0, 2);
        assertEquals(expected1.normalize(), cylinder.getNormal(pBVA), "EP TC14 : Wrong result of normal in cylinder");
    }

}

