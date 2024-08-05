package renderer;

import geometries.Intersectable.GeoPoint;
import lighting.LightSource;
import primitives.*;
import scene.Scene;

import java.util.List;

import static primitives.Util.alignZero;

/**
 * This class is responsible for calculating the color of a point on a geometry
 * by calculating the color of the light sources that affect it.
 */
public class SimpleRayTracer extends RayTracerBase {
    /**
     * The maximum level of calculation for the color of a point.
     */
    private static final int MAX_CALC_COLOR_LEVEL = 10;

    /**
     * The minimum value for the calculation of the color of a point.
     */
    private static final double MIN_CALC_COLOR_K = 0.001;

    /**
     * The initial value for the calculation of the color of a point.
     */
    private static final Double3 INITIAL_K = Double3.ONE;

    /**
     * The delta value for the calculations.
     */
    private static final double DELTA = 0.1;

    /**
     * Constructor for the SimpleRayTracer class.
     *
     * @param scene the scene.
     */
    public SimpleRayTracer(Scene scene) {
        super(scene);
    }

    @Override
    public Color traceRay(Ray ray) {
        GeoPoint closestPoint = findClosestIntersection(ray);
        return closestPoint == null ? scene.getBackground() : calcColor(closestPoint, ray);
    }

    /**
     * Calculates the color of a point on a geometry, by calculating the color of the light sources that affect it.
     *
     * @param gp  the point on the geometry that the ray intersects with.
     * @param ray the ray that intersects the geometry.
     * @return the color of the intersected point.
     */
    private Color calcColor(GeoPoint gp, Ray ray) {
        return calcColor(gp, ray, MAX_CALC_COLOR_LEVEL, INITIAL_K).add(scene.getAmbientLight().getIntensity());
    }

    /**
     * Calculates the color of a point on a geometry, by calculating the color of the light sources that affect it.
     *
     * @param gp    the point on the geometry that the ray intersects with.
     * @param ray   the ray that intersects the geometry.
     * @param level the level of recursion.
     * @param k     the ratio of the current ray's color to the color of the previous ray.
     * @return the color of the intersected point.
     */
    private Color calcColor(GeoPoint gp, Ray ray, int level, Double3 k) {
        Color color = calcLocalEffects(gp, ray, k);
        return 1 == level ? color : color.add(calcGlobalEffects(gp, ray, level, k));
    }

    /**
     * Calculates the color of a point on a geometry, by calculating the color of the light sources that affect it.
     *
     * @param geoPoint the point on the geometry that the ray intersects with.
     * @param ray      the ray that intersects the geometry.
     * @return the color of the point.
     */
    private Color calcLocalEffects(GeoPoint geoPoint, Ray ray, Double3 k) {
        Vector v = ray.getDirection();
        Vector n = geoPoint.geometry.getNormal(geoPoint.point);
        double nv = alignZero(n.dotProduct(v));

        if (nv == 0) // If the camera is perpendicular to the normal vector
            return Color.BLACK;

        Material material = geoPoint.geometry.getMaterial();
        Color color = geoPoint.geometry.getEmission();

        for (LightSource lightSource : scene.getLights()) { // Iterate over all the light sources
            Vector l = lightSource.getL(geoPoint.point); // The vector from the point on the surface to the light source
            double nl = alignZero(n.dotProduct(l)); // The dot product of the normal vector and the vector to the light source
            if (nl * nv > 0) { // sign(nl) == sing(nv)
                Double3 ktr = transparency(geoPoint, l, n, lightSource, nv);
                if (!(ktr.product(k).lowerThan(MIN_CALC_COLOR_K))) { // If the color is not too dark
                    Color intensity = lightSource.getIntensity(geoPoint.point).scale(ktr);
                    color = color.add(intensity.scale(calcDiffusive(material.kD, nl)),
                            intensity.scale(calcSpecular(material, l, n, v)));
                }
            }
        }

        return color;
    }

    /**
     * Calculates the global effects (reflections and refractions) at the intersection point.
     *
     * @param gp    the intersection point.
     * @param ray   the ray that intersects the geometry.
     * @param level the level of recursion.
     * @param k     the current attenuation coefficient.
     * @return the color contribution from global effects.
     */
    private Color calcGlobalEffects(GeoPoint gp, Ray ray, int level, Double3 k) {
        Material material = gp.geometry.getMaterial();
        Vector direction = ray.getDirection();
        Vector normal = gp.geometry.getNormal(gp.point);
        return calcGlobalEffect(constructRefractedRay(gp, direction, normal), material.kT, level, k)
                .add(calcGlobalEffect(constructReflectedRay(gp, direction, normal), material.kR, level, k));
    }

    /**
     * Calculates the color contribution from a single global effect (reflection or refraction).
     *
     * @param ray   the secondary ray (reflection or refraction).
     * @param kx    the attenuation coefficient for the effect.
     * @param level the level of recursion.
     * @param k     the current attenuation coefficient.
     * @return the color contribution from the global effect.
     */
    private Color calcGlobalEffect(Ray ray, Double3 kx, int level, Double3 k) {
        Double3 kkx = k.product(kx);
        if (kkx.lowerThan(MIN_CALC_COLOR_K)) {
            return Color.BLACK; // Return no contribution if the combined coefficient is too small
        }

        GeoPoint gp = findClosestIntersection(ray);
        return gp == null ? scene.background // If no intersection found, return background color
                : calcColor(gp, ray, level - 1, kkx).scale(kx);
    }

    /**
     * Calculates the diffusive component of the light intensity at a point on a surface.
     *
     * @param kD the diffusive reflection coefficient.
     * @param nl the dot product of the normal and the light direction.
     * @return the diffusion coefficient.
     */
    private Double3 calcDiffusive(Double3 kD, double nl) {
        return kD.scale(Math.abs(nl));
    }

    /**
     * Calculates the specular component of the light intensity at a point on a surface.
     *
     * @param mat the material of the surface.
     * @param l   the vector from the point on the surface to the light source.
     * @param n   the normal vector of the surface.
     * @param v   the vector from the point on the surface to the camera.
     * @return the specular coefficient.
     */
    private Double3 calcSpecular(Material mat, Vector l, Vector n, Vector v) {
        Vector r = l.subtract(n.scale(2 * (l.dotProduct(n))));
        return mat.kS.scale(Math.pow(Math.max(v.scale(-1).dotProduct(r), 0), mat.nShininess));
    }

    /**
     * Checks if a point on a surface is unshaded.
     *
     * @param gp          the point on the geometry.
     * @param lightSource the light source.
     * @param l           the vector from the point on the surface to the light source.
     * @param n           the normal vector of the surface at the intersection point.
     * @param nl          the dot product of the normal vector and the vector from the light source to the point.
     * @return true if the point is unshaded, and false if it is shaded.
     */
    private boolean unshaded(GeoPoint gp, LightSource lightSource, Vector l, Vector n, double nl) {
        return transparency(gp, l, n, lightSource, nl) == Double3.ZERO;
    }

    /**
     * Calculates the transparency of a point on a surface.
     *
     * @param geoPoint    the point on the geometry.
     * @param l           the vector from the point on the surface to the light source.
     * @param n           the normal vector of the surface at the intersection point.
     * @param lightSource the light source.
     * @param nl          the dot product of the normal vector and the vector from the light source to the point.
     * @return the transparency of the point.
     */
    private Double3 transparency(GeoPoint geoPoint, Vector l, Vector n, LightSource lightSource, double nl) {
        Vector lightDirection = l.scale(-1); // From point to light source
        Ray lightRay = new Ray(geoPoint.point, lightDirection, n);
        List<GeoPoint> intersections = scene.getGeometries().findGeoIntersections(lightRay, lightSource.getDistance(geoPoint.point));

        if (intersections == null)
            return Double3.ONE;

        Double3 ktr = Double3.ONE;
        double distance = lightSource.getDistance(geoPoint.point);

        for (GeoPoint gp : intersections) {
            if (alignZero(gp.point.distance(geoPoint.point) - distance) <= 0) {
                ktr = ktr.product(gp.geometry.getMaterial().kT);
                if (ktr.lowerThan(MIN_CALC_COLOR_K)) return Double3.ZERO;
            }
        }

        return ktr;
    }

    /**
     * Constructs a reflected ray.
     *
     * @param geoPoint  the point on the surface.
     * @param direction the direction of the incoming ray.
     * @param normal    the normal vector of the surface.
     * @return the reflected ray.
     */
    private Ray constructReflectedRay(GeoPoint geoPoint, Vector direction, Vector normal) {
        double nv = normal.dotProduct(direction); // The dot product of the normal vector and the direction vector

        if (nv == 0) {
            return null;
        }

        Vector reflectedDirection = direction.subtract(normal.scale(2 * nv)); // The reflected direction
        return new Ray(geoPoint.point, reflectedDirection, normal);
    }

    /**
     * Constructs a refracted ray.
     *
     * @param geoPoint  the intersection point.
     * @param direction the direction of the incoming ray.
     * @param normal    the normal vector of the surface.
     * @return the refracted ray.
     */
    private Ray constructRefractedRay(GeoPoint geoPoint, Vector direction, Vector normal) {
        return new Ray(geoPoint.point, direction, normal);
    }

    /**
     * Finds the closest intersection point of a ray with the geometries in the scene.
     *
     * @param ray the ray.
     * @return the closest intersection point.
     */
    private GeoPoint findClosestIntersection(Ray ray) {
        return ray.findClosestGeoPoint(scene.geometries.findGeoIntersections(ray));
    }
}