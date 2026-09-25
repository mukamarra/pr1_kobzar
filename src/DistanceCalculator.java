import model.CartesianPoint2D;
import model.CartesianPoint3D;
import model.PolarPoint;
import model.SphericalPoint;

public class DistanceCalculator {

    // 1. Пряма евклідова відстань у 2D (декартова)
    public static double distance(CartesianPoint2D a, CartesianPoint2D b) {
        double dx = b.getX() - a.getX();
        double dy = b.getY() - a.getY();
        return Math.sqrt(dx * dx + dy * dy);
    }

    // 2. Пряма відстань у 2D за теоремою косинусів (полярна)
    public static double distance(PolarPoint a, PolarPoint b) {
        double r1 = a.getRadius();
        double r2 = b.getRadius();
        return Math.sqrt(r1 * r1 + r2 * r2 - 2 * r1 * r2 * Math.cos(b.getAngle() - a.getAngle()));
    }

    // 3. Пряма евклідова відстань у 3D (декартова)
    public static double distance(CartesianPoint3D a, CartesianPoint3D b) {
        double dx = b.getX() - a.getX();
        double dy = b.getY() - a.getY();
        double dz = b.getZ() - a.getZ();
        return Math.sqrt(dx * dx + dy * dy + dz * dz);
    }

    // 4. Пряма відстань у 3D (хорда сфери)
    public static double distanceChord(SphericalPoint a, SphericalPoint b) {
        double r1 = a.getRadius();
        double r2 = b.getRadius();
        double phi1 = a.getPolarAngle();
        double phi2 = b.getPolarAngle();
        double theta1 = a.getAzimuth();
        double theta2 = b.getAzimuth();

        return Math.sqrt(r1 * r1 + r2 * r2 - 2 * r1 * r2 *
                (Math.sin(phi1) * Math.sin(phi2) * Math.cos(theta1 - theta2) + Math.cos(phi1) * Math.cos(phi2)));
    }

    // 5. Дугова відстань по поверхні сфери (велике коло)
    public static double distanceArc(SphericalPoint a, SphericalPoint b) {
        double phi1 = a.getPolarAngle();
        double phi2 = b.getPolarAngle();
        double thetaDiff = a.getAzimuth() - b.getAzimuth();

        double cosVal = Math.sin(phi1) * Math.sin(phi2) * Math.cos(thetaDiff) + Math.cos(phi1) * Math.cos(phi2);
        
        // Захист від виходу за межі [-1.0, 1.0] через похибки чисел з рухомою комою
        cosVal = Math.max(-1.0, Math.min(1.0, cosVal));
        return a.getRadius() * Math.acos(cosVal);
    }
}