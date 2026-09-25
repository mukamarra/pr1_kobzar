import model.CartesianPoint2D;
import model.CartesianPoint3D;
import model.PolarPoint;
import model.SphericalPoint;

import java.util.Random;

public class Benchmark {
    private static final int N = 100_000;

    public static void run2D() {
        Random random = new Random();

        PolarPoint[] p1 = new PolarPoint[N];
        PolarPoint[] p2 = new PolarPoint[N];
        CartesianPoint2D[] c1 = new CartesianPoint2D[N];
        CartesianPoint2D[] c2 = new CartesianPoint2D[N];

        // Генерація даних та попередня конвертація (не входять у заміри часу)
        for (int i = 0; i < N; i++) {
            double r1 = random.nextDouble() * 100.0;
            double r2 = random.nextDouble() * 100.0;
            double angle1 = random.nextDouble() * 2 * Math.PI - Math.PI;
            double angle2 = random.nextDouble() * 2 * Math.PI - Math.PI;

            p1[i] = new PolarPoint(r1, angle1);
            p2[i] = new PolarPoint(r2, angle2);

            c1[i] = CartesianPoint2D.fromPolar(p1[i]);
            c2[i] = CartesianPoint2D.fromPolar(p2[i]);
        }

        // Підхід А: Полярні координати (косинуси)
        long start = System.nanoTime();
        double sum = 0;
        for (int i = 0; i < N; i++) {
            sum += DistanceCalculator.distance(p1[i], p2[i]);
        }
        long polarTime = System.nanoTime() - start;

        // Підхід Б: Декартові координати
        start = System.nanoTime();
        sum = 0;
        for (int i = 0; i < N; i++) {
            sum += DistanceCalculator.distance(c1[i], c2[i]);
        }
        long cartesianTime = System.nanoTime() - start;

        System.out.printf("2D Полярнi (теорема косинусiв) : %,12d нс (%.2f мс)%n", polarTime, polarTime / 1_000_000.0);
        System.out.printf("2D Декартовi                   : %,12d нс (%.2f мс)%n", cartesianTime, cartesianTime / 1_000_000.0);
    }

    public static void run3D() {
        Random random = new Random();

        SphericalPoint[] s1 = new SphericalPoint[N];
        SphericalPoint[] s2 = new SphericalPoint[N];
        CartesianPoint3D[] c1 = new CartesianPoint3D[N];
        CartesianPoint3D[] c2 = new CartesianPoint3D[N];

        // Генерація пар з однаковими радіусами для пари
        for (int i = 0; i < N; i++) {
            double r = random.nextDouble() * 100.0 + 1.0;
            double phi1 = random.nextDouble() * Math.PI;
            double phi2 = random.nextDouble() * Math.PI;
            double theta1 = random.nextDouble() * 2 * Math.PI - Math.PI;
            double theta2 = random.nextDouble() * 2 * Math.PI - Math.PI;

            s1[i] = new SphericalPoint(r, phi1, theta1);
            s2[i] = new SphericalPoint(r, phi2, theta2);

            c1[i] = CartesianPoint3D.fromSpherical(s1[i]);
            c2[i] = CartesianPoint3D.fromSpherical(s2[i]);
        }

        // Підхід А: Сферична (хорда)
        long start = System.nanoTime();
        double sum = 0;
        for (int i = 0; i < N; i++) {
            sum += DistanceCalculator.distanceChord(s1[i], s2[i]);
        }
        long chordTime = System.nanoTime() - start;

        // Підхід Б: Сферична (дуга)
        start = System.nanoTime();
        sum = 0;
        for (int i = 0; i < N; i++) {
            sum += DistanceCalculator.distanceArc(s1[i], s2[i]);
        }
        long arcTime = System.nanoTime() - start;

        // Підхід В: Декартова 3D
        start = System.nanoTime();
        sum = 0;
        for (int i = 0; i < N; i++) {
            sum += DistanceCalculator.distance(c1[i], c2[i]);
        }
        long cartesian3DTime = System.nanoTime() - start;

        System.out.printf("3D Сферична (хорда)            : %,12d нс (%.2f мс)%n", chordTime, chordTime / 1_000_000.0);
        System.out.printf("3D Сферична (дуга)             : %,12d нс (%.2f мс)%n", arcTime, arcTime / 1_000_000.0);
        System.out.printf("3D Декартова                   : %,12d нс (%.2f мс)%n", cartesian3DTime, cartesian3DTime / 1_000_000.0);
    }
}