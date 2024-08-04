package primitives;

/**
 * Class Vector is the class representing a vector in the 3D space.
 * Vector - represents a "straight".
 */
public class Vector extends Point {
    /**
     * Unit vector along the X axis.
     */
    public static final Vector X = new Vector(1, 0, 0);

    /**
     * Unit vector along the Y axis.
     */
    public static final Vector Y = new Vector(0, 1, 0);

    /**
     * Unit vector along the Z axis.
     */
    public static final Vector Z = new Vector(0, 0, 1);

    /**
     * Constructor for vector by 3 double parameters.
     * If the vector isZero = (0,0,0) => throw an Exception.
     *
     * @param x coordinate x.
     * @param y coordinate y.
     * @param z coordinate z.
     */
    public Vector(double x, double y, double z) {
        super(x, y, z);
        Double3 isZero = new Double3(x, y, z);
        if (isZero.equals(Double3.ZERO))
            throw new IllegalArgumentException("Vector 0 is illegal");
    }

    /**
     * Constructor for vector by a Double3 parameter.
     * If the vector isZero = (0,0,0) => throw an Exception.
     *
     * @param xyz variable of type Double3 which represent the coordinate of the Vector.
     */
    public Vector(final Double3 xyz) {
        super(xyz);
        if (xyz.equals(Double3.ZERO))
            throw new IllegalArgumentException("Vector 0 is illegal");
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Vector vector)) return false;
        return xyz.equals(vector.xyz);
    }

    @Override
    public String toString() {
        return "Vector{" +
                "xyz=" + xyz +
                '}';
    }

    /**
     * Calculate the length of the vector squared.
     *
     * @return the vector's length squared.
     */
    public double lengthSquared() {
        return this.dotProduct(this);
    }

    /**
     * Calculate the length of the vector.
     * The length of the vector is the square root of the length squared.
     *
     * @return the vector's length.
     */
    public double length() {
        return Math.sqrt(this.lengthSquared());
    }

    /**
     * Add function : add.
     *
     * @param vec the vector to add.
     * @return the vector result of the addition.
     */
    public Vector add(Vector vec) {
        Double3 temp = this.xyz.add(vec.xyz);
        return new Vector(temp);
    }

    /**
     * Scale function : multiply.
     *
     * @param scalar the scalar to multiply with.
     * @return the Vector multiply by the scalar.
     */
    public Vector scale(double scalar) {
        return new Vector(this.xyz.scale(scalar));
    }

    /**
     * dotProduct function : scalar product.
     * The scalar product of two vectors is the sum of the products of their corresponding coordinates.
     *
     * @param vec vector to scalar product by.
     * @return the vector result of the scalar product.
     */
    public double dotProduct(Vector vec) {
        Double3 temp1 = this.xyz;
        Double3 temp2 = vec.xyz;
        return (temp1.d1 * temp2.d1 + temp1.d2 * temp2.d2 + temp1.d3 * temp2.d3); // Algebraic formula of scalar product
    }

    /**
     * Calculate the cross product of two vectors.
     * The cross product of two vectors is a vector that is perpendicular to the plane formed by the two vectors.
     *
     * @param vec the other vector.
     * @return the cross product vector.
     */
    public Vector crossProduct(Vector vec) {
        /* There are three units vectors i(1,0,0), j(0,1,0) and k(0,0,1) */
        Double3 temp1 = this.xyz;
        Double3 temp2 = vec.xyz;
        double x = temp1.d2 * temp2.d3 - temp1.d3 * temp2.d2; // Calculate determinant of j and k columns
        double y = -(temp1.d1 * temp2.d3 - temp1.d3 * temp2.d1); // Calculate determinant of i and k columns
        double z = temp1.d1 * temp2.d2 - temp1.d2 * temp2.d1; // Calculate determinant of i and j columns
        return new Vector(x, y, z);
    }

    /**
     * Normalize the vector.
     * The normalized vector is the vector divided by its length.
     * If the vector isZero = (0,0,0) => throw an Exception.
     *
     * @return the normalized vector.
     */
    public Vector normalize() {
        Double3 temp = this.xyz;
        double x = temp.d1 / this.length();
        double y = temp.d2 / this.length();
        double z = temp.d3 / this.length();

        Vector vec; // The normalized vector
        try {
            vec = new Vector(x, y, z); // Create the normalized vector
        } catch (IllegalArgumentException ex) {
            return null;
        }

        return vec; // Return the normalized vector
    }

    /**
     * Check if the vector is orthogonal to another vector.
     * Two vectors are orthogonal if their scalar product is 0.
     *
     * @param other the other vector.
     * @return true if the vectors are orthogonal, false otherwise.
     */
    public boolean isOrthogonal(Vector other) {
        return Util.isZero(this.dotProduct(other));
    }
}