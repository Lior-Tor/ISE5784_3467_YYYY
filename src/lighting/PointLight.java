package lighting;

import primitives.*;

/**
 * Represents a point light source in a scene. A point light source emits light in all directions from a single point.
 */
public class PointLight extends Light implements LightSource {
    /**
     * The position of the point light source.
     */
    protected Point position;

    private double kC = 1;
    private double kL = 0;
    private double kQ = 0;

    /**
     * Constructs a point light with a given intensity and position.
     *
     * @param intensity the intensity of the point light.
     * @param position  the position of the point light in 3D space.
     */
    public PointLight(Color intensity, Point position) {
        super(intensity);
        this.position = position;
    }

    @Override
    public Color getIntensity(Point p) {
        double tempDistance = position.distance(p); // distance between the light source and the point
        double denominator = kC + kL * tempDistance + kQ * (tempDistance * tempDistance);
        return intensity.scale(1 / denominator);
    }

    @Override
    public Vector getL(Point p) {
        return p.subtract(position).normalize();
    }

    /**
     * Setter for the constant attenuation factor (kC).
     *
     * @param kC the constant attenuation factor.
     * @return the updated PointLight object.
     */
    public PointLight setkC(double kC) {
        this.kC = kC;
        return this;
    }

    /**
     * Setter for the linear attenuation factor (kL).
     *
     * @param kL the linear attenuation factor.
     * @return the updated PointLight object.
     */
    public PointLight setkL(double kL) {
        this.kL = kL;
        return this;
    }

    /**
     * Setter for the quadratic attenuation factor (kQ).
     *
     * @param kQ the quadratic attenuation factor.
     * @return the updated PointLight object.
     */
    public PointLight setkQ(double kQ) {
        this.kQ = kQ;
        return this;
    }

    /**
     * Setter for the position of the point light.
     *
     * @param position the new position of the point light.
     * @return the updated PointLight object.
     */
    public PointLight setPosition(Point position) {
        this.position = position;
        return this;
    }
}