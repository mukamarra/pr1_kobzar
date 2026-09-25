package model;

public final class CartesianPoint3D {
    private final double x;
    private final double y;
    private final double z;

    public CartesianPoint3D(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getZ() {
        return z;
    }

    // Статичний фабричний метод конвертації зі SphericalPoint
    public static CartesianPoint3D fromSpherical(SphericalPoint p) {
        double r = p.getRadius();
        double phi = p.getPolarAngle();
        double theta = p.getAzimuth();

        double x = r * Math.sin(phi) * Math.cos(theta);
        double y = r * Math.sin(phi) * Math.sin(theta);
        double z = r * Math.cos(phi);

        return new CartesianPoint3D(x, y, z);
    }

    @Override
    public String toString() {
        return String.format("Cartesian3D(x=%.4f, y=%.4f, z=%.4f)", x, y, z);
    }
}