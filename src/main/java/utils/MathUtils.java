package utils;

import lombok.Data;
import models.Interval;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.stream.IntStream;

import static java.lang.Math.*;

@Data
public class MathUtils {

    private static final Random random = new Random();
    public static final Map<Double, Double> quantileFuncValues = new HashMap<>();

    /*
      The table of values for inverted Laplace integral function (normal quantile function).
      Keys are confidence levels p/2, values are corresponding arguments.
     */

    static {
        quantileFuncValues.put(0.25, 0.68);
        quantileFuncValues.put(0.375, 1.15);
        quantileFuncValues.put(0.4, 1.29);
        quantileFuncValues.put(0.475, 1.96);
    }

    public static int factorial(int n) {
        if (n < 0) throw new RuntimeException(
                "An argument for the 'factorial' method should be non-negative.");
        if (n == 0) return 1;
        return IntStream.rangeClosed(1, n).reduce(1, (int x, int y) -> x * y);
    }

    public static double laplaceFunction(double x) {
        return laplaceFunction(x, 8);
    }

    /**
     * Calculates the value of Laplace integral function using the Taylor series.
     * @param x Point of estimation.
     * @param degree Degree of Taylor polynomial.
     * @return Approximation for Laplace integral function.
     */

    public static double laplaceFunction(double x, int degree) {
        if (degree < 0) throw new RuntimeException(
                "An argument '' for the 'laplaceFunction' method should be non-negative.");
        return IntStream.rangeClosed(0, degree)
                .mapToDouble(lvl -> laplaceValue(x, lvl)).reduce(0, Double::sum) / sqrt(2 * Math.PI);
    }

    private static double laplaceValue(double x, int n) {
        return (pow(-1, n) * pow(x, 2 * n + 1)) /
               (pow(2, n) * factorial(n) * (2 * n + 1));
    }

    public static boolean isWithinInterval(double x, Interval interval) {
        return x >= interval.getLowerLimit() && x <= interval.getUpperLimit();
    }

    public static double round(double value, int valsAfterComma) {
        double scale = pow(10, valsAfterComma);
        return Math.round(value * scale) / scale;
    }

    public static double round(double value) {
        return MathUtils.round(value, 3);
    }

    public static double mean(double[] data) {
        double sum = Arrays.stream(data).sum();
        return sum / data.length;
    }

    public static double nextDoubleWithinInterval(Interval interval) {
        return interval.getLowerLimit() + interval.getLength() * random.nextDouble();
    }

    public static double getLowerLimit(int basicVal, double tolVal) {
        return basicVal - tolVal;
    }

    public static double getUpperLimit(int basicVal, double tolVal) {
        return basicVal + tolVal;
    }

    public static double percentage(double a, double b) {
        return (a / b) *  100;
    }

}
