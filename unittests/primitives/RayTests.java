package primitives;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RayTests {

    @Test
    void testGetPoint() {
        Ray ray = new Ray(new Point(1, 2, 3), new Vector(1, 0, 0));

        // ============ Equivalence Partitions Tests ==============
        // TC01: Positive distance
        assertEquals(new Point(3, 2, 3), ray.getPoint(2), "getPoint() wrong result for positive distance");

        // TC02: Negative distance
        assertEquals(new Point(0, 2, 3), ray.getPoint(-1), "getPoint() wrong result for negative distance");

        // =============== Boundary Values Tests ==================
        // TC11: Zero distance
        assertEquals(new Point(1, 2, 3), ray.getPoint(0), "getPoint() wrong result for zero distance");
    }
}
