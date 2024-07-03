package renderer;

import primitives.Util;
import primitives.Color;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.MissingResourceException;

import static primitives.Util.alignZero;
import static primitives.Util.isZero;

/**
 * Camera class represents a camera in the 3D space
 */
public class Camera implements Cloneable {

    private Point location;
    private Vector vTo, vUp, vRight;
    private double vpDistance, vpWidth, vpHeight;
    private RayTracerBase rayTracer;
    private ImageWriter imageWriter;

    /**
     * Private default constructor
     */
    private Camera() {
        location = Point.ZERO;
        vTo = null;
        vUp = null;
        vRight = null;
        vpDistance = 0;
        vpWidth = 0;
        vpHeight = 0;
        rayTracer = null;
        imageWriter = null;
    }

    /**
     * Returns a new Builder instance
     *
     * @return new Builder instance
     */
    public static Builder getBuilder() {
        return new Builder();
    }

    /**
     * Construct a ray through a specific pixel
     *
     * @param nX number of columns
     * @param nY number of rows
     * @param j  column index of the pixel
     * @param i  row index of the pixel
     * @return Ray through the pixel
     */
    public Ray constructRay(int nX, int nY, int j, int i) {
        Point pC = location.add(vTo.scale(vpDistance)); // Center point of the view plane
        double rY = vpHeight / nY; // Height of a pixel
        double rX = vpWidth / nX; // Width of a pixel

        double yI = alignZero(-(i - (nY - 1) / 2d) * rY);
        double xJ = alignZero((j - (nX - 1) / 2d) * rX);

        Point pIJ = pC;
        if (!isZero(xJ))
            pIJ = pIJ.add(vRight.scale(xJ));
        if (!isZero(yI))
            pIJ = pIJ.add(vUp.scale(yI));


        Vector vIJ = pIJ.subtract(location);

        return new Ray(location, vIJ);
    }

    public void setVpSize(int viewPlane, int viewPlane1) {

    }

    /**
     * Camera Builder class using Builder Design Pattern
     */
    public static class Builder {
        private final Camera camera;

        /**
         * Default constructor initializes Camera
         */
        public Builder() {
            camera = new Camera();
        }

        /**
         * Sets the camera location
         *
         * @param location the camera location
         * @return this Builder instance
         * @throws IllegalArgumentException if location is null
         */
        public Builder setLocation(Point location) {
            if (location == null)
                throw new IllegalArgumentException("Location cannot be null");
            camera.location = location;
            return this;
        }

        /**
         * Sets the camera direction
         *
         * @param vTo forward direction vector
         * @param vUp up direction vector
         * @return this Builder instance
         * @throws IllegalArgumentException if vectors are not perpendicular or null
         */
        public Builder setDirection(Vector vTo, Vector vUp) {
            if (vTo == null || vUp == null)
                throw new IllegalArgumentException("Direction vectors cannot be null");
            if (!vTo.isOrthogonal(vUp))
                throw new IllegalArgumentException("Direction vectors must be perpendicular");
            camera.vTo = vTo.normalize();
            camera.vUp = vUp.normalize();
            camera.vRight = vTo.crossProduct(vUp).normalize();
            return this;
        }

        /**
         * Sets the ViewPlane size
         *
         * @param width  the ViewPlane width
         * @param height the ViewPlane height
         * @return this Builder instance
         * @throws IllegalArgumentException if width or height are non-positive
         */
        public Builder setVpSize(double width, double height) {
            if (width <= 0 || height <= 0)
                throw new IllegalArgumentException("ViewPlane size must be positive");
            camera.vpWidth = width;
            camera.vpHeight = height;
            return this;
        }

        /**
         * Sets the ViewPlane distance
         *
         * @param distance the distance between camera and ViewPlane
         * @return this Builder instance
         * @throws IllegalArgumentException if distance is non-positive
         */
        public Builder setVpDistance(double distance) {
            if (distance <= 0)
                throw new IllegalArgumentException("ViewPlane distance must be positive");
            camera.vpDistance = distance;
            return this;
        }

        /**
         * Sets the RayTracer for the camera
         *
         * @param rayTracer the RayTracer instance
         * @return this Builder instance
         * @throws IllegalArgumentException if rayTracer is null
         */
        public Builder setRayTracer(RayTracerBase rayTracer) {
            if (rayTracer == null)
                throw new IllegalArgumentException("RayTracer cannot be null");
            camera.rayTracer = rayTracer;
            return this;
        }

        /**
         * Sets the ImageWriter for the camera
         *
         * @param imageWriter the ImageWriter instance
         * @return this Builder instance
         * @throws IllegalArgumentException if imageWriter is null
         */
        public Builder setImageWriter(ImageWriter imageWriter) {
            if (imageWriter == null)
                throw new IllegalArgumentException("ImageWriter cannot be null");
            camera.imageWriter = imageWriter;
            return this;
        }

        /**
         * Builds the Camera instance
         *
         * @return a new Camera instance
         * @throws MissingResourceException if any required field is missing
         */
        public Camera build() {
            if (camera.vTo == null || camera.vUp == null || camera.vRight == null)
                throw new MissingResourceException("Rendering data is missing", Camera.class.getName(), "Camera directions");
            if (camera.vpDistance == 0)
                throw new MissingResourceException("Rendering data is missing", Camera.class.getName(), "ViewPlane distance");
            if (camera.vpWidth == 0 || camera.vpHeight == 0)
                throw new MissingResourceException("Rendering data is missing", Camera.class.getName(), "ViewPlane size");
            if (camera.rayTracer == null)
                throw new MissingResourceException("Rendering data is missing", Camera.class.getName(), "RayTracer");
            if (camera.imageWriter == null)
                throw new MissingResourceException("Rendering data is missing", Camera.class.getName(), "ImageWriter");

            return (Camera) camera.clone();
        }
    }

    @Override
    protected Object clone() {
        try {
            return super.clone();
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException("Clone not supported", e);
        }
    }

    public Point getLocation() {
        return location;
    }

    public Vector getvTo() {
        return vTo;
    }

    public Vector getvUp() {
        return vUp;
    }

    public Vector getvRight() {
        return vRight;
    }

    public double getVpDistance() {
        return vpDistance;
    }

    public double getVpWidth() {
        return vpWidth;
    }

    public double getVpHeight() {
        return vpHeight;
    }

    public RayTracerBase getRayTracer() {
        return rayTracer;
    }

    public ImageWriter getImageWriter() {
        return imageWriter;
    }

    /**
     * Chaining functions
     */
    public void printGrid(int interval, Color color) {

        if (this.imageWriter == null)
            throw new MissingResourceException("missing resource", ImageWriter.class.getName(), "");

        imageWriter.printGrid(interval, color);
    }

    /**
     * write to image
     *
     * @return
     */
    public Camera writeToImage() {
        if (this.imageWriter == null)
            throw new MissingResourceException("missing resource", ImageWriter.class.getName(), "");
        imageWriter.writeToImage();
        return this;
    }

    public void renderImage() {
        try {
            if (imageWriter == null) {
                throw new MissingResourceException("missing resource", ImageWriter.class.getName(), "");
            }
            if (rayTracer == null) {
                throw new MissingResourceException("missing resource", RayTracerBase.class.getName(), "");
            }
            int nX = imageWriter.getNx();
            int nY = imageWriter.getNy();
            for (int i = 0; i < nY; i++) {
                for (int j = 0; j < nX; j++) {
                    castRay(nX, nY, i, j);
                }
            }
        } catch (MissingResourceException ex) {
            throw new UnsupportedOperationException("Not implemented yet" + ex.getClassName());
        }
    }

    /**
     * Cast ray from camera in order to color a pixel
     *
     * @param nX   - resolution on X axis (number of pixels in row)
     * @param nY   - resolution on Y axis (number of pixels in column)
     * @param icol - pixel's column number (pixel index in row)
     * @param jrow - pixel's row number (pixel index in column)
     */
    private void castRay(int nX, int nY, int icol, int jrow) {
        Ray ray = constructRay(nX, nY, jrow, icol);
        Color pixelColor = rayTracer.traceRay(ray);
        imageWriter.writePixel(jrow, icol, pixelColor);
    }

}
