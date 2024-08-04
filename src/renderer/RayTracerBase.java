package renderer;

import primitives.Color;
import primitives.Ray;
import scene.Scene;

/**
 * Abstract class RayTracerBase is a base class for ray-tracing algorithms.
 */
public abstract class RayTracerBase {
    /**
     * The scene.
     */
    protected Scene scene;

    /**
     * Constructor for the RayTracerBase class.
     *
     * @param scene the scene.
     */
    public RayTracerBase(Scene scene) {
        this.scene = scene;
    }

    /**
     * This function traces a ray and returns the color of the point that the ray intersects with.
     *
     * @param ray the ray.
     * @return the color of the point that the ray intersects with.
     */
    public abstract Color traceRay(Ray ray);
}