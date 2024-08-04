package renderer;

import primitives.*;

import java.util.MissingResourceException;

import static primitives.Util.alignZero;
import static primitives.Util.isZero;

/**
 * Class Camera represents a camera in the 3D space.
 */
public class Camera implements Cloneable {
    private Point p0;
    private Vector vRight, vUp, vTo;
    private double height = 0.0, width = 0.0, distance = 0.0;
    ImageWriter imageWriter;
    RayTracerBase rayTracer;

    /**
     * Constructs a Camera object with default values.
     */
    private Camera() {
        p0 = Point.ZERO;
        vRight = new Vector(1, 0, 0);
        vUp = new Vector(0, 1, 0);
        vTo = new Vector(0, 0, -1);
    }

    @Override
    public Camera clone() {
        try {
            return (Camera) super.clone();
        } catch (CloneNotSupportedException e) {
            return null; // This should not happen since Camera implements Cloneable
        }
    }

    /**
     * Gets a Builder instance for constructing a Camera object.
     *
     * @return a new Builder instance.
     */
    public static Builder getBuilder() {
        return new Builder();
    }

    /**
     * Constructs a ray for the given pixel coordinates (i, j).
     *
     * @param nX total number of pixels in width
     * @param nY total number of pixels in height
     * @param j  column index of the pixel (from left to right)
     * @param i  row index of the pixel (from top to bottom)
     * @return a Ray object representing the ray passing through the pixel (i, j)
     */
    public Ray constructRay(int nX, int nY, int j, int i) {
        Point pIJ = p0.add(vTo.scale(distance)); // Calculate the intersection point of the ray with the view plane

        double rX = width / nX; // Calculate the width of a pixel
        double rY = height / nY; // Calculate the height of a pixel
        double xJ = (j - (nX - 1) / 2.0) * rX; // Calculate the x coordinate of the pixel's center
        double yI = (i - (nY - 1) / 2.0) * rY; // Calculate the y coordinate of the pixel's center
        if (!isZero(xJ)) { // If the x coordinate is not zero
            pIJ = pIJ.add(vRight.scale(xJ));
        }
        if (!isZero(yI)) { // If the y coordinate is not zero
            pIJ = pIJ.add(vUp.scale(-yI));
        }

        return new Ray(p0, pIJ.subtract(p0)); // Return the ray from the camera
    }

    /**
     * Gets the location of the camera.
     *
     * @return the location of the camera.
     */
    public Point getLocation() {
        return p0;
    }

    /**
     * Gets the right direction vector of the camera.
     *
     * @return the right direction vector of the camera.
     */
    public Vector getvRight() {
        return vRight;
    }

    /**
     * Gets the up direction vector of the camera.
     *
     * @return the up direction vector of the camera.
     */
    public Vector getvUp() {
        return vUp;
    }

    /**
     * Gets the direction vector towards which the camera is facing.
     *
     * @return the direction vector towards which the camera is facing.
     */
    public Vector getvTo() {
        return vTo;
    }

    /**
     * Gets the height of the view plane.
     *
     * @return the height of the view plane.
     */
    public double getVpHeight() {
        return height;
    }

    /**
     * Gets the width of the view plane.
     *
     * @return the width of the view plane.
     */
    public double getVpWidth() {
        return width;
    }

    /**
     * Gets the distance from the camera to the view plane.
     *
     * @return the distance from the camera to the view plane.
     */
    public double getVpDistance() {
        return distance;
    }

    /**
     * Prints a grid on the image with the specified interval and color.
     *
     * @param interval the interval for grid lines.
     * @param color    the color of the grid lines.
     * @return this Camera instance.
     * @throws MissingResourceException if the ImageWriter is missing.
     */
    public Camera printGrid(int interval, Color color) {
        if (imageWriter == null) { // If the ImageWriter is missing
            throw new MissingResourceException("ImageWriter", "ImageWriter", "ImageWriter is missing");
        }

        int nX = imageWriter.getNx(); // Get the number of pixels in width
        int nY = imageWriter.getNy(); // Get the number of pixels in height
        for (int i = 0; i < nY; i++) { // Iterate over the rows
            for (int j = 0; j < nX; j++) { // Iterate over the columns
                if (i % interval == 0 || j % interval == 0) { // If the pixel is on a grid line
                    imageWriter.writePixel(j, i, color); // Write the color to the pixel
                }
            }
        }

        return this; // Return this Camera instance
    }

    /**
     * Writes the rendered image to the output using the ImageWriter.
     *
     * @throws MissingResourceException if the ImageWriter is missing.
     */
    public void writeToImage() {
        if (imageWriter == null) { // If the ImageWriter is missing
            throw new MissingResourceException("ImageWriter", "ImageWriter", "ImageWriter is missing");
        }
        imageWriter.writeToImage(); // Write the image to the output
    }

    /**
     * Casts a ray through the pixel at the given coordinates (i, j) and writes the result to the image.
     *
     * @param nX total number of pixels in width.
     * @param nY total number of pixels in height.
     * @param j  column index of the pixel (from left to right).
     * @param i  row index of the pixel (from top to bottom).
     * @throws MissingResourceException if the RayTracer is missing.
     */
    public void castRay(int nX, int nY, int j, int i) {
        if (rayTracer == null) { // If the RayTracer is missing
            throw new MissingResourceException("RayTracer", "RayTracer", "RayTracer is missing");
        }

        Ray ray = constructRay(nX, nY, j, i); // Construct a ray for the pixel
        Color color = rayTracer.traceRay(ray); // Trace the ray and get the color
        imageWriter.writePixel(j, i, color); // Write the color to the pixel
    }

    /**
     * Renders the entire image by casting rays through each pixel and writing the results to the image.
     *
     * @return this Camera instance.
     * @throws MissingResourceException if the ImageWriter or RayTracer is missing.
     */
    public Camera renderImage() {
        if (imageWriter == null) { // If the ImageWriter is missing
            throw new MissingResourceException("ImageWriter", "ImageWriter", "ImageWriter is missing");
        }
        if (rayTracer == null) { // If the RayTracer is missing
            throw new MissingResourceException("RayTracer", "RayTracer", "RayTracer is missing");
        }

        int nX = imageWriter.getNx(); // Get the number of pixels in width
        int nY = imageWriter.getNy(); // Get the number of pixels in height
        for (int i = 0; i < nY; i++) { // Iterate over the rows
            for (int j = 0; j < nX; j++) { // Iterate over the columns
                castRay(nX, nY, j, i); // Cast a ray through the pixel and write the result to the image
            }
        }

        return this; // Return this Camera instance
    }

    /**
     * Builder class for constructing a Camera object.
     */
    public static class Builder {
        private final Camera camera = new Camera();

        /**
         * Sets the location of the camera.
         *
         * @param location the location point.
         * @return the Builder instance.
         */
        public Builder setLocation(Point location) {
            if (location == null) {
                throw new IllegalArgumentException("Location cannot be null");
            }
            camera.p0 = location;
            return this;
        }

        /**
         * Sets the direction vectors of the camera.
         *
         * @param directionTo the direction vector towards which the camera is facing.
         * @param directionUp the up direction vector.
         * @return the Builder instance.
         */
        public Builder setDirection(Vector directionTo, Vector directionUp) {
            if (directionTo == null || directionUp == null) {
                throw new IllegalArgumentException("Direction vectors cannot be null");
            }
            if (!isZero(directionTo.dotProduct(directionUp))) {
                throw new IllegalArgumentException("Direction vectors must be orthogonal");
            }
            camera.vTo = directionTo.normalize();
            camera.vUp = directionUp.normalize();
            camera.vRight = camera.vTo.crossProduct(camera.vUp).normalize();
            return this;
        }

        /**
         * Sets the size of the view plane (screen plane).
         *
         * @param width  the width of the view plane.
         * @param height the height of the view plane.
         * @return the Builder instance.
         */
        public Builder setVpSize(double width, double height) {
            if (alignZero(width) <= 0 || alignZero(height) <= 0) {
                throw new IllegalArgumentException("Width and height must be positive");
            }
            camera.width = width;
            camera.height = height;
            return this;
        }

        /**
         * Sets the distance from the camera to the view plane (screen plane).
         *
         * @param distance the distance from the camera to the view plane.
         * @return the Builder instance.
         */
        public Builder setVpDistance(double distance) {
            if (alignZero(distance) <= 0) {
                throw new IllegalArgumentException("Distance must be positive");
            }
            camera.distance = distance;
            return this;
        }

        /**
         * Builds and returns the Camera object.
         *
         * @return the constructed Camera object.
         */
        public Camera build() {
            if (camera.p0 == null) {
                throw new MissingResourceException("Location", "Point", "Location point is missing");
            }
            if (camera.vTo == null || camera.vUp == null || camera.vRight == null) {
                throw new MissingResourceException("Direction vectors", "Vector", "Direction vectors are missing");
            }
            if (alignZero(camera.width) <= 0 || alignZero(camera.height) <= 0 || alignZero(camera.distance) <= 0) {
                throw new IllegalArgumentException("Width, height, and distance must be positive");
            }
            if (!isZero(camera.vRight.dotProduct(camera.vTo))) {
                throw new IllegalArgumentException("Direction vectors must be orthogonal");
            }
            camera.vRight = camera.vTo.crossProduct(camera.vUp).normalize();
            return (Camera) camera.clone();
        }

        /**
         * Sets the ImageWriter for the camera.
         *
         * @param imageWriter the ImageWriter to set.
         * @return the Builder instance.
         */
        public Builder setImageWriter(ImageWriter imageWriter) {
            camera.imageWriter = imageWriter;
            return this;
        }

        /**
         * Sets the RayTracer for the camera.
         *
         * @param rayTracer the RayTracer to set.
         * @return the Builder instance.
         */
        public Builder setRayTracer(RayTracerBase rayTracer) {
            camera.rayTracer = rayTracer;
            return this;
        }
    }
}