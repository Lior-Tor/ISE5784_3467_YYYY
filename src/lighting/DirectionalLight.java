package lighting;

import primitives.Color;
import primitives.Point;
import primitives.Vector;

/**
 * Represents a directional light source in a scene which illuminates all objects from a specified direction.
 */
public class DirectionalLight extends Light implements LightSource {
    private final Vector direction;

    /**
     * Constructs a directional light with a given direction and intensity.
     *
     * @param direction the direction from which the light is coming.
     * @param intensity the intensity of the light.
     */
    public DirectionalLight(Vector direction, Color intensity) {
        super(intensity);
        this.direction = direction.normalize(); // Normalize the direction vector
    }

    @Override
    public Color getIntensity(Point p) {
        return this.intensity;
    }

    @Override
    public Vector getL(Point p) {
        return direction.normalize(); // The direction vector is normalized
    }

    @Override
    public double getDistance(Point point) {
        return Double.POSITIVE_INFINITY;
    }
}