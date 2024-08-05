package scene;

import geometries.Geometries;
import lighting.AmbientLight;
import lighting.LightSource;
import primitives.Color;

import java.util.LinkedList;
import java.util.List;

/**
 * Class Scene is the class representing a scene in the 3D space.
 * Scene - represents a collection of geometries, lights, and background color.
 */
public class Scene {
    /**
     * The name of the scene.
     */
    public final String name;

    /**
     * The background color of the scene.
     */
    public Color background = Color.BLACK;

    /**
     * The ambient light of the scene.
     */
    public AmbientLight ambientLight = AmbientLight.NONE;

    /**
     * The geometries of the scene.
     */
    public Geometries geometries = new Geometries();

    /**
     * The lights of the scene.
     */
    public List<LightSource> lights = new LinkedList<>();

    /**
     * Constructor for the Scene class receiving a name.
     *
     * @param name the name of the scene.
     */
    public Scene(String name) {
        this.name = name;
    }

    /**
     * Set Background color.
     *
     * @param background the background color.
     * @return this.
     */
    public Scene setBackground(Color background) {
        this.background = background;
        return this;
    }

    /**
     * Set ambient light.
     *
     * @param ambientLight the ambient light.
     * @return this.
     */
    public Scene setAmbientLight(AmbientLight ambientLight) {
        this.ambientLight = ambientLight;
        return this;
    }

    /**
     * Set geometries list.
     *
     * @param geometries the geometries.
     * @return this.
     */
    public Scene setGeometries(Geometries geometries) {
        this.geometries = geometries;
        return this;
    }

    /**
     * Set light list.
     *
     * @param lights the lights.
     * @return this.
     */
    public Scene setLights(List<LightSource> lights) {
        this.lights = lights;
        return this;
    }

    /**
     * Gets the background of the scene.
     *
     * @return the background color.
     */
    public Color getBackground() {
        return background;
    }

    /**
     * Gets the ambient light of the scene.
     *
     * @return the ambient light.
     */
    public AmbientLight getAmbientLight() {
        return ambientLight;
    }

    /**
     * Gets the geometries of the scene.
     *
     * @return the geometries.
     */
    public Geometries getGeometries() {
        return geometries;
    }

    /**
     * Gets the light sources of the scene.
     *
     * @return the light sources.
     */
    public List<LightSource> getLights() {
        return lights;
    }
}