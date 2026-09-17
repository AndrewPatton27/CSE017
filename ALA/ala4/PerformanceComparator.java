import java.util.Random;
import java.util.function.IntBinaryOperator;

public class PerformanceComparator {

    public static int[][] generateRandomPairs(int count, int max) {
        int[][] pairs = new int[2][count];
        Random random = new Random();
        for (int i = 0; i < count; i++) {
            pairs[0][i] = random.nextInt(max) + 1;
            pairs[1][i] = random.nextInt(max) + 1;
        }
        return pairs;
    }

    public static void compareExecutionTimes(int[][] pairs) {
        System.out.println("Comparing the gcd methods using the execution time");
        System.out.printf("%-10s%-10s%-10s%-10s%-10s%-10s%n",
                "Number1", "Number2", "gcd_1", "gcd_2", "gcd_3", "gcd_4");

        IntBinaryOperator[] gcdMethods = new IntBinaryOperator[] {
                GCD::gcd_1, GCD::gcd_2, GCD::gcd_3, GCD::gcd_4
        };

        for (int i = 0; i < pairs[0].length; i++) {
            int m = pairs[0][i];
            int n = pairs[1][i];

            long[] times = new long[gcdMethods.length];
            for (int j = 0; j < gcdMethods.length; j++) {
                long start = System.nanoTime();
                gcdMethods[j].applyAsInt(m, n);
                times[j] = System.nanoTime() - start;
            }

            System.out.printf("%-10d%-10d%-10d%-10d%-10d%-10d%n",
                    m, n, times[0], times[1], times[2], times[3]);
        }
    }

    public static void compareIterations(int[][] pairs) {
        System.out.println("Comparing the gcd methods using the number of iterations");
        System.out.printf("%-10s%-10s%-10s%-10s%-10s%-10s%n",
                "Number1", "Number2", "gcd_1", "gcd_2", "gcd_3", "gcd_4");

        IntBinaryOperator[] iterationMethods = new IntBinaryOperator[] {
                PerformanceComparator::gcd_1_iterations,
                PerformanceComparator::gcd_2_iterations,
                PerformanceComparator::gcd_3_iterations,
                PerformanceComparator::gcd_4_iterations
        };

        for (int i = 0; i < pairs[0].length; i++) {
            int m = pairs[0][i];
            int n = pairs[1][i];

            int[] iterations = new int[iterationMethods.length];
            for (int j = 0; j < iterationMethods.length; j++) {
                iterations[j] = iterationMethods[j].applyAsInt(m, n);
            }

            System.out.printf("%-10d%-10d%-10d%-10d%-10d%-10d%n",
                    m, n, iterations[0], iterations[1], iterations[2], iterations[3]);
        }
    }

    public static int gcd_1_iterations(int m, int n) {
        int iterations = 0;
        for (int i = 2; i < m && i < n; i++) {
            iterations++;
        }
        return iterations;
    }

    public static int gcd_2_iterations(int m, int n) {
        int iterations = 0;
        for (int i = n; i >= 1; i--) {
            iterations++;
            if (m % i == 0 && n % i == 0) {
                break;
            }
        }
        return iterations;
    }

    public static int gcd_3_iterations(int m, int n) {
        if (m % n == 0) {
            return 0;
        }
        int iterations = 0;
        for (int i = n / 2; i >= 1; i--) {
            iterations++;
            if (m % i == 0 && n % i == 0) {
                break;
            }
        }
        return iterations;
    }

    public static int gcd_4_iterations(int m, int n) {
        if (m % n == 0) {
            return 1;
        }
        return 1 + gcd_4_iterations(n, m % n);
    }
}
