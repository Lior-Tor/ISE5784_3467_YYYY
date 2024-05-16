package geometries;

/**
 * RadialGeometry abstract Class
 */
public abstract class RadialGeometry extends Geometry {

    /**
     * Radius
     */
    protected final double radius;

    /**
     * RadialGeometry constructor
     *
     * @param radius fields of RadialGeometry
     */
    public RadialGeometry(double radius) {
        this.radius = radius;
    }

    @Override
    public String toString() {
        return "RadialGeometry{" +
                "radius=" + radius +
                '}';
    }

}
