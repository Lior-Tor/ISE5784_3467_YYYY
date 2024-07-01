package renderer;

import geometries.Intersectable;
import geometries.Plane;
import geometries.Sphere;
import geometries.Triangle;
import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Vector;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CameraIntegrationTests {

    final int VIEW_PLANE = 3;
    static final Point ZERO_POINT = new Point(0, 0, 0);

    /**
     * This function returns the number of intersections between the camera's rays and the geometry
     * @param camera the camera
     * @param geometry Sphere, Triangle, or Plane
     * @return the number of intersections
     */
    int countIntersections(Camera camera, Intersectable geometry) {
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

    @Test
    void CameraSphereIntersections() {
        // TC01: Sphere radius = 1
        Camera camera = Camera.getBuilder()
                .setLocation(ZERO_POINT)
                .setDirection(new Vector(0, 0, -1), new Vector(0, -1, 0))
                .setVpDistance(1)
                .setVpSize(VIEW_PLANE, VIEW_PLANE)
                .setRayTracer(new SimpleRayTracer(new Scene("Test")))
                .setImageWriter(new ImageWriter("Test", VIEW_PLANE, VIEW_PLANE))
                .build();
        assertEquals(2, countIntersections(camera, new Sphere(1, new Point(0, 0, -3))), "wrong number of intersections");

        // TC02: Sphere radius = 2.5
        camera = Camera.getBuilder()
                .setLocation(new Point(0, 0, 0.5))
                .setDirection(new Vector(0, 0, -1), new Vector(0, -1, 0))
                .setVpDistance(1)
                .setVpSize(VIEW_PLANE, VIEW_PLANE)
                .setRayTracer(new SimpleRayTracer(new Scene("Test")))
                .setImageWriter(new ImageWriter("Test", VIEW_PLANE, VIEW_PLANE))
                .build();
        assertEquals(18, countIntersections(camera, new Sphere(2.5, new Point(0, 0, -2.5))), "wrong number of intersections");

        // TC03: Sphere radius = 2
        assertEquals(10, countIntersections(camera, new Sphere(2, new Point(0, 0, -2))), "wrong number of intersections");

        // TC04: Sphere radius = 4
        camera = Camera.getBuilder()
                .setLocation(ZERO_POINT)
                .setDirection(new Vector(0, 0, -1), new Vector(0, -1, 0))
                .setVpDistance(1)
                .setVpSize(VIEW_PLANE, VIEW_PLANE)
                .setRayTracer(new SimpleRayTracer(new Scene("Test")))
                .setImageWriter(new ImageWriter("Test", VIEW_PLANE, VIEW_PLANE))
                .build();
        assertEquals(9, countIntersections(camera, new Sphere(4, new Point(0, 0, -2))), "wrong number of intersections");

        // TC05: Sphere radius = 0.5
        assertEquals(0, countIntersections(camera, new Sphere(0.5, new Point(0, 0, 1))), "wrong number of intersections");
    }

    @Test
    void CameraPlaneIntersections() {
        Camera camera = Camera.getBuilder()
                .setLocation(ZERO_POINT)
                .setDirection(new Vector(0, 0, -1), new Vector(0, -1, 0))
                .setVpDistance(1)
                .setVpSize(VIEW_PLANE, VIEW_PLANE)
                .setRayTracer(new SimpleRayTracer(new Scene("Test")))
                .setImageWriter(new ImageWriter("Test", VIEW_PLANE, VIEW_PLANE))
                .build();

        // TC01: Plane parallel to view plane
        assertEquals(9, countIntersections(camera, new Plane(new Point(0, 0, -5), camera.getvTo())), "wrong number of intersections");

        // TC02: Plane with angle
        assertEquals(9, countIntersections(camera, new Plane(new Point(1, 0, -2), new Vector(0, -1, 2))), "wrong number of intersections");

        // TC03: Plane with another angle
        assertEquals(6, countIntersections(camera, new Plane(new Point(0, 0, -5), new Vector(0, -1, 1))), "wrong number of intersections");
    }

    @Test
    public void CameraTriangleIntersections() {
        Camera camera = Camera.getBuilder()
                .setLocation(ZERO_POINT)
                .setDirection(new Vector(0, 0, -1), new Vector(0, -1, 0))
                .setVpDistance(1)
                .setVpSize(VIEW_PLANE, VIEW_PLANE)
                .setRayTracer(new SimpleRayTracer(new Scene("Test")))
                .setImageWriter(new ImageWriter("Test", VIEW_PLANE, VIEW_PLANE))
                .build();

        // TC01: Small triangle
        assertEquals(1, countIntersections(camera, new Triangle(new Point(0, 1, -2), new Point(1, -1, -2), new Point(-1, -1, -2))), "wrong number of intersections");

        // TC02: Large triangle
        assertEquals(2, countIntersections(camera, new Triangle(new Point(1, -1, -2), new Point(-1, -1, -2), new Point(0, 20, -2))), "wrong number of intersections");
    }
}
