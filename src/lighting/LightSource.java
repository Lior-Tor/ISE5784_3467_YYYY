package lighting;

import primitives.Color;
import primitives.Point;
import primitives.Vector;

/**
 * Represents a light source in a scene.
 */
public interface LightSource {
    /**
     * Gets the intensity of the light at a specific point.
     *
     * @param p the point at which the intensity is evaluated.
     * @return the intensity of the light at the given point.
     */
    public Color getIntensity(Point p);

    /**
     * Gets the direction vector from the light source towards a specific point.
     *
     * @param p the point at which the direction vector is evaluated.
     * @return the direction vector from the light source towards the given point.
     */
    public Vector getL(Point p);

    /**
     * Gets the distance between the light source and a specific point.
     *
     * @param p the point at which the distance is evaluated.
     * @return the distance between the light source and the given point.
     */
    public double getDistance(Point p);
}