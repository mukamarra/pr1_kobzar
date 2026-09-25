
import model.CartesianPoint2D;
import model.CartesianPoint3D;
import model.PolarPoint;
import model.SphericalPoint;

public class Main {
    public static void main(String[] args) {
        System.out.println("===============================================================================");
        System.out.println("                 РЕЗУЛЬТАТИ ПЕРЕВIРКИ КОРЕКТНОСТI ПЕРЕТВОРЕНЬ                  ");
        System.out.println("===============================================================================");
        
        System.out.println("--- 2D Перетворення (Полярна <-> Декартова) ---");
        test2D(0.0, 0.0);
        test2D(5.0, Math.PI / 4);
        test2D(10.0, Math.PI);
        test2D(3.0, -Math.PI / 2);

        System.out.println("\n--- 3D Перетворення (Сферична <-> Декартова) ---");
        test3D(0.0, 0.0, 0.0);
        test3D(5.0, Math.PI / 3, Math.PI / 4);
        test3D(7.0, Math.PI / 2, Math.PI / 6);
        test3D(10.0, Math.PI / 4, -Math.PI / 3);

        System.out.println("\n===============================================================================");
        System.out.println("                   РЕЗУЛЬТАТИ БЕНЧМАРКIНГУ (100 000 ПАР)                      ");
        System.out.println("===============================================================================");
        System.out.println("-> Тестування 2D:");
        Benchmark.run2D();

        System.out.println("\n-> Тестування 3D:");
        Benchmark.run3D();
        System.out.println("===============================================================================");
    }

    private static void test2D(double r, double angle) {
        PolarPoint pOrigin = new PolarPoint(r, angle);
        CartesianPoint2D cartesian = CartesianPoint2D.fromPolar(pOrigin);
        PolarPoint pRestored = PolarPoint.fromCartesian(cartesian);

        boolean passed = isClose(pOrigin.getRadius(), pRestored.getRadius()) &&
                (isClose(pOrigin.getRadius(), 0.0) || isAngleClose(pOrigin.getAngle(), pRestored.getAngle()));

        System.out.printf("%s -> %s -> %s | Успiх: %s%n",
                pOrigin, cartesian, pRestored, passed ? "OK" : "FAIL");
    }

    private static void test3D(double r, double phi, double theta) {
        SphericalPoint sOrigin = new SphericalPoint(r, phi, theta);
        CartesianPoint3D cartesian = CartesianPoint3D.fromSpherical(sOrigin);
        SphericalPoint sRestored = SphericalPoint.fromCartesian(cartesian);

        boolean passed = isClose(sOrigin.getRadius(), sRestored.getRadius()) &&
                (isClose(sOrigin.getRadius(), 0.0) ||
                        (isAngleClose(sOrigin.getPolarAngle(), sRestored.getPolarAngle()) &&
                         isAngleClose(sOrigin.getAzimuth(), sRestored.getAzimuth())));

        System.out.printf("%s -> %s -> %s | Успiх: %s%n",
                sOrigin, cartesian, sRestored, passed ? "OK" : "FAIL");
    }

    private static boolean isClose(double a, double b) {
        return Math.abs(a - b) < 1e-7;
    }

    private static boolean isAngleClose(double a, double b) {
        double diff = Math.abs(a - b);
        return diff < 1e-7 || Math.abs(diff - 2 * Math.PI) < 1e-7;
    }
}