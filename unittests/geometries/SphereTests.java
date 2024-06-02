package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

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

    /**
     * Test method for {@link Sphere#findIntersections(Ray)}.
     */
    @Test
    public void testFindIntersections() {
        Sphere sphere = new Sphere(1d, new Point(1, 0, 0));
        Point intersect1;
        Point intersect2;
        List<Point> result;

        // ============ Equivalence Partitions Tests ==============
        // TC01: Ray's line is outside the sphere (0 points)
        assertNull(sphere.findIntersections(new Ray(new Point(-1, 0, 0), new Vector(1, 1, 0))),
                "Ray's line out of sphere");

        // TC02: Ray starts before and crosses the sphere (2 points)
        intersect1 = new Point(0.0651530771650466, 0.355051025721682, 0);
        intersect2 = new Point(1.53484692283495, 0.844948974278318, 0);
        result = sphere.findIntersections(new Ray(new Point(-1, 0, 0),
                new Vector(3, 1, 0)));
        assertEquals(2, result.size(), "Wrong number of points");
        if (result.get(0).getXYZ().getD1() > result.get(1).getXYZ().getD1())
            result = List.of(result.get(1), result.get(0));
        assertEquals(List.of(intersect1, intersect2), result, "Ray crosses sphere");

        // TC03: Ray starts inside the sphere (1 point)
        intersect1 = new Point(1.2299090833947006, 0.9732121111929344, 0);
        result = sphere.findIntersections(new Ray(new Point(0.5, 0, 0),
                new Vector(1.5, 2, 0)));
        assertEquals(1, result.size(), "Wrong number of points");
        assertEquals(List.of(intersect1), result, "Ray crosses sphere");

        // TC04: Ray starts after the sphere (0 points)
        assertNull(sphere.findIntersections(new Ray(new Point(0.5, -1, 0), new Vector(-0.5, -2, 0))),
                "Ray's line out of sphere");

        // =============== Boundary Values Tests ==================

        // **** Group: Ray's line crosses the sphere (but not the center)
        // TC11: Ray starts at sphere and goes inside (1 point)
        intersect1 = new Point(0.20785934140068296, -0.6103385757051905, 0);
        result = sphere.findIntersections(new Ray(new Point(1, 1, 0),
                new Vector(-1.52, -3.09, 0)));
        assertEquals(1, result.size(), "Wrong number of points");
        assertEquals(List.of(intersect1), result, "Ray crosses sphere");
        // TC12: Ray starts at sphere and goes outside (0 points)
        assertNull(sphere.findIntersections(new Ray(new Point(1, 1, 0), new Vector(-1, 1.5, 0))),
                "Ray's line out of sphere");

        // **** Group: Ray's line goes through the center
        // TC13: Ray starts before the sphere (2 points)
        intersect1 = new Point(1, -1, 0);
        intersect2 = new Point(1, 1, 0);
        result = sphere.findIntersections(new Ray(new Point(1, 1.5, 0),
                new Vector(0, -3, 0)));
        assertEquals(2, result.size(), "Wrong number of points");
        if (result.get(0).getXYZ().getD1() > result.get(1).getXYZ().getD1())
            result = List.of(result.get(1), result.get(0));
        else if (result.get(0).getXYZ().getD1() == result.get(1).getXYZ().getD1()) {
            if (result.get(0).getXYZ().getD2() > result.get(1).getXYZ().getD2())
                result = List.of(result.get(1), result.get(0));
            else if (result.get(0).getXYZ().getD2() == result.get(1).getXYZ().getD2()) {
                if (result.get(0).getXYZ().getD3() > result.get(1).getXYZ().getD3())
                    result = List.of(result.get(1), result.get(0));
            }
        }
        assertEquals(List.of(intersect1, intersect2), result, "Ray crosses sphere");
        // TC14: Ray starts at sphere and goes inside (1 point)
        intersect1 = new Point(1, -1, 0);
        result = sphere.findIntersections(new Ray(new Point(1, 1, 0),
                new Vector(0, -2, 0)));
        assertEquals(1, result.size(), "Wrong number of points");
        assertEquals(List.of(intersect1), result, "Ray crosses sphere");
        // TC15: Ray starts inside (1 point)
        intersect1 = new Point(1, 1, 0);
        result = sphere.findIntersections(new Ray(new Point(1, 0.5, 0),
                new Vector(0, 2, 0)));
        assertEquals(1, result.size(), "Wrong number of points");
        assertEquals(List.of(intersect1), result, "Ray crosses sphere");
        // TC16: Ray starts at the center (1 point)
        intersect1 = new Point(0.6286093236458963, 0.9284766908852594, 0.0);
        result = sphere.findIntersections(new Ray(new Point(1, 0, 0),
                new Vector(-1, 2.5, 0)));
        assertEquals(1, result.size(), "Wrong number of points");
        assertEquals(List.of(intersect1), result, "Ray crosses sphere");
        // TC17: Ray starts at sphere and goes outside (0 points)
        assertNull(sphere.findIntersections(new Ray(new Point(1, 1, 0), new Vector(0, 2, 0))),
                "Ray's line out of sphere");
        // TC18: Ray starts after sphere (0 points)
        assertNull(sphere.findIntersections(new Ray(new Point(1, 2, 0), new Vector(0, 2, 0))),
                "Ray's line out of sphere");
        // **** Group: Ray's line is tangent to the sphere (all tests 0 points)
        // TC19: Ray starts before the tangent point
        assertNull(sphere.findIntersections(new Ray(new Point(0, -0.5, 0), new Vector(0, 1, 0))),
                "Ray's line out of sphere");
        // TC20: Ray starts at the tangent point
        assertNull(sphere.findIntersections(new Ray(new Point(0, 0, 0), new Vector(0, 1, 0))),
                "Ray's line out of sphere");
        // TC21: Ray starts after the tangent point
        assertNull(sphere.findIntersections(new Ray(new Point(0, 0.5, 0), new Vector(0, 1, 0))),
                "Ray's line out of sphere");
        // **** Group: Special cases
        // TC19: Ray's line is outside, ray is orthogonal to ray start to sphere's center line
        assertNull(sphere.findIntersections(new Ray(new Point(-0.5, 0, 0), new Vector(0, 1, 0))),
                "Ray's line out of sphere");
    }

}
