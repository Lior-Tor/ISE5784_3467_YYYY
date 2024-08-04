package lighting;

import primitives.Color;
import primitives.Double3;

/**
 * Represents an ambient light in a scene. Ambient light is light that is present everywhere in the scene.
 * It is used to simulate the light that is reflected off of surfaces and into the scene.
 */
public class AmbientLight extends Light {
    /**
     * A constant for no ambient light.
     */
    public static final AmbientLight NONE = new AmbientLight(Color.BLACK, 0.0);

    /**
     * Constructs an ambient light with a given intensity and scale factor.
     *
     * @param intensity the base intensity of the ambient light.
     * @param scale     the scale factor for the intensity.
     */
    public AmbientLight(Color intensity, Double3 scale) {
        super(intensity.scale(scale));
    }

    /**
     * Constructs an ambient light with a given intensity and scale factor.
     *
     * @param intensity the base intensity of the ambient light.
     * @param scale     the scale factor for the intensity.
     */
    public AmbientLight(Color intensity, double scale) {
        super(intensity.scale(scale));
    }

    /**
     * Gets the intensity of the ambient light.
     *
     * @return the intensity of the ambient light.
     */
    public Color getIntensity() {
        return super.getIntensity();
    }
}