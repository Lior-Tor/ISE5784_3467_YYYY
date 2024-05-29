package geometries;

import primitives.Point;
import primitives.Vector;
import org.junit.jupiter.api.Test;

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

}
