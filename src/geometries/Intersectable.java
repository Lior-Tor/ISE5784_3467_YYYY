package geometries;

import primitives.Point;
import primitives.Ray;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Abstract class Intersectable is a base class for geometries in the 3D space.
 */
public abstract class Intersectable {
    /**
     * Class GeoPoint is a wrapper class for a point and the geometry it belongs to.
     */
    public static class GeoPoint {
        /**
         * geometry.
         */
        public Geometry geometry;

        /**
         * point.
         */
        public Point point;

        /**
         * Constructor for the GeoPoint class receiving a geometry and a point.
         *
         * @param geometry the geometry.
         * @param point    the point.
         */
        public GeoPoint(Geometry geometry, Point point) {
            this.geometry = geometry;
            this.point = point;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof GeoPoint geoPoint)) return false;
            return Objects.equals(geometry, geoPoint.geometry) && Objects.equals(point, geoPoint.point);
        }

        @Override
        public String toString() {
            return "GeoPoint{" +
                    "geometry=" + geometry +
                    ", point=" + point +
                    '}';
        }
    }

    /**
     * This function returns a list of all the points where the ray intersects.
     *
     * @param ray The ray to intersect with the object.
     * @return A list of points.
     */
    public List<Point> findIntersections(Ray ray) {
        List<GeoPoint> intersections = findGeoIntersections(ray);
        return intersections == null ? null
                : intersections.stream().map(gp -> gp.point).collect(Collectors.toList());
    }

    /**
     * This function returns a list of all the points where the ray intersects the surface of the sphere.
     *
     * @param ray The ray to intersect with the object.
     * @return A list of GeoPoints.
     */
    public List<GeoPoint> findGeoIntersections(Ray ray) {
        return findGeoIntersectionHelper(ray);
    }

    /**
     * This function is here to help the findGeoIntersections function to find the list of GeoPoint intersections.
     *
     * @param ray The ray to intersect with the object.
     * @return A list of GeoPoints.
     */
    protected abstract List<GeoPoint> findGeoIntersectionHelper(Ray ray);
}