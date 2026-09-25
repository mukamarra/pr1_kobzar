package model;

public final class SphericalPoint {
    private final double radius;
    private final double polarAngle; // phi (кут від осі Z)
    private final double azimuth;    // theta (азимут у площині XY)

    public SphericalPoint(double radius, double polarAngle, double azimuth) {
        this.radius = radius;
        this.polarAngle = polarAngle;
        this.azimuth = azimuth;
    }

    public double getRadius() {
        return radius;
    }

    public double getPolarAngle() {
        return polarAngle;
    }

    public double getAzimuth() {
        return azimuth;
    }

    // Статичний фабричний метод конвертації з CartesianPoint3D
    public static SphericalPoint fromCartesian(CartesianPoint3D p) {
        double r = Math.sqrt(p.getX() * p.getX() + p.getY() * p.getY() + p.getZ() * p.getZ());
        if (r == 0.0) {
            return new SphericalPoint(0.0, 0.0, 0.0);
        }

        // Обмежуємо значення для arccos, щоб уникнути NaN через похибки float
        double cosPhi = Math.max(-1.0, Math.min(1.0, p.getZ() / r));
        double polarAngle = Math.acos(cosPhi);
        double azimuth = Math.atan2(p.getY(), p.getX());

        return new SphericalPoint(r, polarAngle, azimuth);
    }

    @Override
    public String toString() {
        return String.format("Spherical(r=%.4f, phi=%.4f rad, theta=%.4f rad)", radius, polarAngle, azimuth);
    }
}