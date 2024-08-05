package primitives;

/**
 * Class Material is the class representing a material in the 3D space.
 */
public class Material {
    /**
     * The diffuse reflection coefficient.
     */
    public Double3 kD = Double3.ZERO;

    /**
     * The specular reflection coefficient.
     */
    public Double3 kS = Double3.ZERO;

    /**
     * The transparency coefficient.
     */
    public Double3 kT = Double3.ZERO;

    /**
     * The reflection coefficient.
     */
    public Double3 kR = Double3.ZERO;

    /**
     * The shininess of the material.
     */
    public int nShininess = 0;

    /**
     * Setter to kD with Double3 parameter.
     *
     * @param kD the diffuse reflection coefficient.
     * @return this.
     */
    public Material setkD(Double3 kD) {
        this.kD = kD;
        return this;
    }

    /**
     * Setter to kD with double parameter.
     *
     * @param kD the diffuse reflection coefficient.
     * @return this.
     */
    public Material setkD(double kD) {
        this.kD = new Double3(kD);
        return this;
    }

    /**
     * Setter to kS with Double3 parameter.
     *
     * @param kS the specular reflection coefficient.
     * @return this.
     */
    public Material setkS(Double3 kS) {
        this.kS = kS;
        return this;
    }

    /**
     * Setter to kS with double parameter.
     *
     * @param kS the specular reflection coefficient.
     * @return this.
     */
    public Material setkS(double kS) {
        this.kS = new Double3(kS);
        return this;
    }

    /**
     * Setter to kT with Double3 parameter.
     *
     * @param kT the transparency coefficient.
     * @return this.
     */
    public Material setkT(Double3 kT) {
        this.kT = kT;
        return this;
    }

    /**
     * Setter to kT with double parameter.
     *
     * @param kT the transparency coefficient.
     * @return this.
     */
    public Material setkT(double kT) {
        this.kT = new Double3(kT);
        return this;
    }

    /**
     * Setter to kR with Double3 parameter.
     *
     * @param kR the reflection coefficient.
     * @return this.
     */
    public Material setkR(Double3 kR) {
        this.kR = kR;
        return this;
    }

    /**
     * Setter to kR with double parameter.
     *
     * @param kR the reflection coefficient.
     * @return this.
     */
    public Material setkR(double kR) {
        this.kR = new Double3(kR);
        return this;
    }

    /**
     * Setter to nShininess with int parameter.
     *
     * @param nShininess the shininess of the material.
     * @return this.
     */
    public Material setnShininess(int nShininess) {
        this.nShininess = nShininess;
        return this;
    }
}