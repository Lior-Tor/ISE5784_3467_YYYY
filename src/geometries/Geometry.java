package geometries;

import primitives.*;

/**
 * Abstract class Geometry is a base class for geometries in the 3D space.
 */
public abstract class Geometry extends Intersectable {
    /***
     * The color of the emission.
     */
    protected Color emission = Color.BLACK;
    private Material material = new Material();

    /***
     * Abstract function to get the normal to the geometry at a given point.
     * @param point the point to get the normal at.
     * @return the normal to the geometry at the given point.
     */
    public abstract Vector getNormal(Point point);

    /***
     * getter to emission.
     * @return the color of the emission.
     */
    public Color getEmission() {
        return emission;
    }

    /***
     * setter to emission.
     * @param emission the color of the emission.
     * @return the geometry.
     */
    public Geometry setEmission(Color emission) {
        this.emission = emission;
        return this;
    }

    /***
     * getter to material.
     * @return the material of the geometry.
     */
    public Material getMaterial() {
        return material;
    }

    /***
     * setter to material.
     * @param material the material of the geometry.
     * @return the geometry.
     */
    public Geometry setMaterial(Material material) {
        this.material = material;
        return this;
    }
}