package lighting;

import primitives.*;

import static primitives.Util.alignZero;

/**
 * A class representing a spotlight in a 3D scene.
 * A spotlight is a type of point light that emits light in a specific direction.
 */
public class SpotLight extends PointLight {
    private final Vector direction;
    private int narrowBeam = 1;

    /**
     * Constructs a spotlight with a given intensity, position, and direction.
     *
     * @param intensity the intensity of the spotlight.
     * @param position  the position of the spotlight in 3D space.
     * @param direction the direction in which the spotlight is pointing.
     */
    public SpotLight(Color intensity, Point position, Vector direction) {
        super(intensity, position);
        this.direction = direction.normalize(); // Normalize the direction vector
    }

    /**
     * Setter for the narrow beam of the spotlight.
     *
     * @param narrowBeam the angle of the narrow beam in degrees.
     * @return the SpotLight object.
     */
    public SpotLight setNarrowBeam(int narrowBeam) {
        this.narrowBeam = narrowBeam;
        return this;
    }

    @Override
    public SpotLight setkC(double kC) {
        super.setkC(kC);
        return this;
    }

    @Override
    public SpotLight setkL(double kL) {
        super.setkL(kL);
        return this;
    }

    @Override
    public SpotLight setkQ(double kQ) {
        super.setkQ(kQ);
        return this;
    }

    @Override
    public Color getIntensity(Point p) {
        double dl = alignZero(getL(p).dotProduct(this.direction));
        return dl <= 0 ? Color.BLACK : super.getIntensity(p).scale(dl);
    }

    @Override
    public Vector getL(Point p) {
        return super.getL(p);
    }
}