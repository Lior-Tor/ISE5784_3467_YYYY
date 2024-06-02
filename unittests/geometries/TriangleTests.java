package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TriangleTests {

    /**
     * Test method for {@link geometries.Triangle#getNormal(Point)}.
     */
    @Test
    public void testGetNormal() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: All the point are the same
        Point[] pts =
                {new Point(0, 0, 1), new Point(2, 0, 0), new Point(0, 4, 0)};
        final Triangle tr = new Triangle(pts[0], pts[1], pts[2]);
        assertEquals(new Vector(4, 2, 8).normalize(), tr.getNormal(new Point(0, 0, 0)), "The normal of the triangle is not normalized");
    }

    @Test
    void testFindIntersections() {
        Point[] pts =
                {new Point(0, 0, 2), new Point(2, 0, 0), new Point(0, 2, 0)};
        final Triangle triangle = new Triangle(pts[0], pts[1], pts[2]);
        Point intersect;
        List<Point> result;

        // ============ Equivalence Partitions Tests ==============
        // TC01 : Inside polygon/triangle
        intersect = new Point(0.4182041820418204, 0.9175891758917589, 0.6642066420664207);
        result = triangle.findIntersections(new Ray(new Point(0, -2, 0), new Vector(0.85, 5.93, 1.35)));
        assertEquals(1, result.size(), "Wrong number of points");
        assertEquals(List.of(intersect), result, "there is one intersection");

        // TC02 : outside against edge
        assertNull(triangle.findIntersections(new Ray(new Point(3.92, -5.01, -2.36), new Vector(-3.92, 11.01, 2.36))), "there is no intersection");

        // TC03 : outside against vertex
        assertNull(triangle.findIntersections(new Ray(new Point(-5.28, 3.07, -0.84), new Vector(10.79, -1.4, 0.84))), "there is no intersection");

        // =============== Boundary Values Tests ==================
        // The ray begins "before" the plane
        // TC11 : On edge
        assertNull(triangle.findIntersections(new Ray(new Point(6.7, -1.6, 0), new Vector(-7.59, 3.23, 0))), "there is no intersection");

        // TC12 : In Vertex
        assertNull(triangle.findIntersections(new Ray(new Point(-0.48, 3.67, 0), new Vector(1.48, -5.16, 0))), "there is no intersection");

        // TC13 : On edge's continuation
        assertNull(triangle.findIntersections(new Ray(new Point(6, 0, 0), new Vector(-8.51, -2.84, 0))), "there is no intersection");
    }

}
