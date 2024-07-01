package renderer;

import geometries.Geometries;

public class Scene {

    private final String sceneName;
    private Geometries geometries;

    public Scene(String sceneName) {
        this.sceneName = sceneName;
        this.geometries = new Geometries();
    }

}
