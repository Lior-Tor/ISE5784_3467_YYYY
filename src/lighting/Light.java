package lighting;

import primitives.Color;

/**
 * Represents a light source in a scene.
 */
public class Light {
    /**
     * The intensity of the light.
     */
    protected Color intensity;

    /**
     * Constructs a light with the specified intensity.
     *
     * @param intensity The intensity of the light.
     */
    protected Light(Color intensity) {
        this.intensity = intensity;
    }

    /**
     * Returns the intensity of the light.
     *
     * @return The intensity of the light.
     */
    public Color getIntensity() {
        return intensity;
    }
}