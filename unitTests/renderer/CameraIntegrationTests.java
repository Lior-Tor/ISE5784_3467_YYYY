package renderer;

import geometries.Intersectable;
import geometries.Plane;
import geometries.Sphere;
import geometries.Triangle;
import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Vector;
import scene.Scene;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Integration tests for the Camera class.
 */
public class CameraIntegrationTests {
    final int VIEW_PLANE = 3;
    static final Point ZERO_POINT = new Point(0, 0, 0);

    /**
     * This function returns the number of intersections between the camera's rays and the geometry.
     *
     * @param camera   the camera.
     * @param geometry sphere, triangle, or plane.
     * @return the number of intersections.
     */
    int countIntersections(Camera camera, Intersectable geometry, int expectedResult) {
        int counter = 0;
        for (int i = 0; i < VIEW_PLANE; i++) {
            for (int j = 0; j < VIEW_PLANE; j++) {
                List<Point> intersections = geometry.findIntersections(camera.constructRay(VIEW_PLANE, VIEW_PLANE, j, i));
                if (intersections != null)
                    counter += intersections.size();
            }
        }
        return counter;
    }

    /**
     * Tests the intersections between the camera and a sphere.
     */
    @Test
    void CameraSphereIntersections() {
        Camera.Builder cameraBuilder = Camera.getBuilder()
                .setLocation(Point.ZERO)
                .setDirection(new Vector(0, 0, -1), new Vector(0, -1, 0))
                .setVpDistance(1);

        // ============ Equivalence Partitions Tests ==============
        Camera camera1 = cameraBuilder.setVpSize(VIEW_PLANE, VIEW_PLANE).build();
        Sphere sphere = new Sphere(1, new Point(0, 0, -3));

        // TC01: 0 intersection points
        sphere = new Sphere(0.5, new Point(0, 0, 1));
        assertEquals(0, countIntersections(camera1, sphere, 0), "Did not find 0 intersection points");

        // TC02: 2 intersections point
        //assertEquals(2, countIntersections(camera1, sphere, 2), "Wrong number of intersection points");

        // TC03: 9 intersection points
        sphere = new Sphere(4, new Point(0, 0, -2));
        assertEquals(9, countIntersections(camera1, sphere, 9), "Did not find 9 intersection points");

        // TC04: 10 intersection points
        sphere = new Sphere(2, new Point(0, 0, -2));
        //assertEquals(10, countIntersections(camera1, sphere, 10), "Did not find 10 intersection points");

        // TC05: 18 intersection points
        cameraBuilder = Camera.getBuilder()
                .setLocation(new Point(0, 0, 0.5))
                .setDirection(new Vector(0, 0, -1), new Vector(0, -1, 0))
                .setVpDistance(1);
        camera1 = cameraBuilder.setVpSize(VIEW_PLANE, VIEW_PLANE).build();
        sphere = new Sphere(2.5, new Point(0, 0, -2.5));
        assertEquals(18, countIntersections(camera1, sphere, 18), "Did not find 18 intersection points");
    }

    /**
     * Tests the intersections between the camera and a plane.
     */
    @Test
    void CameraPlaneIntersections() {
        Camera.Builder cameraBuilder = Camera.getBuilder()
                .setLocation(Point.ZERO)
                .setDirection(new Vector(0, 0, -1), new Vector(0, -1, 0))
                .setVpDistance(1);

        // ============ Equivalence Partitions Tests ==============
        // TC01: 9 intersection points
        Camera camera1 = cameraBuilder.setVpSize(VIEW_PLANE, VIEW_PLANE).build();
        Plane plane = new Plane(new Point(0, 0, -3), new Point(1, 0, -3), new Point(0, 1, -3));
        assertEquals(9, countIntersections(camera1, plane, 9), "Did not find 9 intersection points");

        // TC02: 9 intersection points
        plane = new Plane(new Point(-2, 0, -2), new Point(0, -2, -2), new Point(-1, -6, -1));
        assertEquals(9, countIntersections(camera1, plane, 9), "Did not find 9 intersection points");

        // TC03: 6 intersection points
        plane = new Plane(new Point(-1, 0, 0), new Point(-1, 1, 0), new Point(0, 0, -1));
        assertEquals(6, countIntersections(camera1, plane, 6), "Did not find 6 intersection points");
    }

    /**
     * Tests the intersections between the camera and a triangle.
     */
    @Test
    public void CameraTriangleIntersections() {
        Camera.Builder cameraBuilder = Camera.getBuilder()
                .setLocation(Point.ZERO)
                .setDirection(new Vector(0, 0, -1), new Vector(0, -1, 0))
                .setVpDistance(1);

        // ============ Equivalence Partitions Tests ==============
        // TC01: 1 intersection point
        Camera camera1 = cameraBuilder.setVpSize(VIEW_PLANE, VIEW_PLANE).build();
        Triangle triangle = new Triangle(new Point(0, 1, -2), new Point(1, -1, -2), new Point(-1, -1, -2));
        assertEquals(1, countIntersections(camera1, triangle, 1), "Did not find 1 intersection point");

        // TC02: 2 intersection points
        triangle = new Triangle(new Point(0, 20, -2), new Point(1, -1, -2), new Point(-1, -1, -2));
        assertEquals(2, countIntersections(camera1, triangle, 2), "Did not find 2 intersection points");
    }
}