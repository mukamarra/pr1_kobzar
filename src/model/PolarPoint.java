package model;

public final class PolarPoint {
    private final double radius;
    private final double angle; // у радіанах

    public PolarPoint(double radius, double angle) {
        this.radius = radius;
        this.angle = angle;
    }

    public double getRadius() {
        return radius;
    }

    public double getAngle() {
        return angle;
    }

    // Статичний фабричний метод конвертації з CartesianPoint2D
    public static PolarPoint fromCartesian(CartesianPoint2D p) {
        double r = Math.sqrt(p.getX() * p.getX() + p.getY() * p.getY());
        double angle = Math.atan2(p.getY(), p.getX());
        return new PolarPoint(r, angle);
    }

    @Override
    public String toString() {
        return String.format("Polar(r=%.4f, theta=%.4f rad)", radius, angle);
    }
}