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
     * @param ray the ray to intersect with the object.
     * @return a list of points.
     */
    public List<Point> findIntersections(Ray ray) {
        var geoList = findGeoIntersections(ray);
        return geoList == null ? null
                : geoList.stream().map(gp -> gp.point).toList();
    }

    /**
     * This function returns a list of all the points where the ray intersects the surface of the sphere.
     *
     * @param ray the ray to intersect with the object.
     * @return a list of GeoPoints.
     */
    public List<GeoPoint> findGeoIntersections(Ray ray) {
        return findGeoIntersections(ray, Double.POSITIVE_INFINITY);
    }

    /**
     * This function finds the intersections of a ray with the Earth's surface, up to a maximum distance.
     * The function is declared as `public final`. The `final` keyword means that the function cannot be overridden by a subclass.
     *
     * @param ray         the ray to intersect with the object.
     * @param maxDistance the maximum distance to intersect with the object.
     * @return a list of GeoPoints.
     */
    public final List<GeoPoint> findGeoIntersections(Ray ray, double maxDistance) {
        return findGeoIntersectionsHelper(ray, maxDistance);
    }

    /**
     * This function finds the intersection points of the given ray with this GeoShape, up to the given maximum distance.
     *
     * @param ray         the ray to intersect with the object.
     * @param maxDistance the maximum distance to intersect with the object.
     * @return a list of GeoPoints.
     */
    protected abstract List<GeoPoint> findGeoIntersectionsHelper(Ray ray, double maxDistance);
}