/**
 * Algorithm analysis lab. Each method RETURNS the number of times its loop body runs.
 * 1) For each method, predict the iteration count formula and the Big-O BEFORE running.
 * 2) Run: java ComplexityLoops   - the table shows counts for several n so you can check
 *    your formula (double n: does the count x2? x4? +1?).
 * Answers are in Test2_Cheat_Sheet.md (section 3) and printed at the end when you pass "answers".
 */
public class ComplexityLoops {

    static long a(int n) {                 // A
        long c = 0;
        for (int i = 0; i < n; i++) {
            c++;
        }
        return c;
    }

    static long b(int n) {                 // B
        long c = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                c++;
            }
        }
        return c;
    }

    static long c(int n) {                 // C: inner depends on outer
        long c = 0;
        for (int i = 1; i <= n; i++) {
            for (int j = 1; j < i; j++) {
                c++;
            }
        }
        return c;
    }

    static long d(int n) {                 // D: variable doubles
        long c = 0;
        int count = 1;
        while (count < n) {
            count *= 2;
            c++;
        }
        return c;
    }

    static long e(int n) {                 // E: linear outer, doubling inner
        long c = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 1; j < n; j *= 2) {
                c++;
            }
        }
        return c;
    }

    static long f(int n) {                 // F: inner is a constant
        long c = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < 20; j++) {
                c++;
            }
        }
        return c;
    }

    static long g(int n) {                 // G: sequential loops (not nested!)
        long c = 0;
        for (int i = 0; i < n; i++) {
            c++;
        }
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                c++;
            }
        }
        return c;
    }

    static long h(int n) {                 // H: tricky - n + n/2 + n/4 + ...
        long c = 0;
        for (int i = n; i >= 1; i /= 2) {
            for (int j = 0; j < i; j++) {
                c++;
            }
        }
        return c;
    }

    static long i(int n) {                 // I: three nested
        long c = 0;
        for (int x = 0; x < n; x++) {
            for (int y = 0; y < n; y++) {
                for (int z = 0; z < n; z++) {
                    c++;
                }
            }
        }
        return c;
    }

    static long j(int n) {                 // J: step of 3 (still linear)
        long c = 0;
        for (int x = 0; x < n; x += 3) {
            c++;
        }
        return c;
    }

    // recursive ones: count how many CALLS happen
    static long calls;

    static long recLinear(int n) {         // K: f(n) calls f(n-1)
        calls++;
        if (n <= 0) {
            return 0;
        }
        return recLinear(n - 1);
    }

    static long recHalf(int n) {           // L: f(n) calls f(n/2)
        calls++;
        if (n <= 1) {
            return 0;
        }
        return recHalf(n / 2);
    }

    static long recFib(int n) {            // M: two calls each time
        calls++;
        if (n <= 2) {
            return 1;
        }
        return recFib(n - 1) + recFib(n - 2);
    }

    static long recTwoHalves(int n) {      // N: two calls on half the size (merge sort shape without the merge)
        calls++;
        if (n <= 1) {
            return 0;
        }
        recTwoHalves(n / 2);
        recTwoHalves(n / 2);
        return 0;
    }

    public static void main(String[] args) {
        int[] sizes = {10, 20, 40, 80, 160};
        String[] names = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J"};
        System.out.printf("%-4s", "n");
        for (String s : names) {
            System.out.printf("%10s", s);
        }
        System.out.println();
        for (int n : sizes) {
            System.out.printf("%-4d%10d%10d%10d%10d%10d%10d%10d%10d%10d%10d%n", n,
                a(n), b(n), c(n), d(n), e(n), f(n), g(n), h(n), i(n), j(n));
        }
        System.out.println("\nrecursive call counts (K linear, L half, M fib, N two halves)");
        int[] rsizes = {10, 20, 30};
        for (int n : rsizes) {
            calls = 0;
            recLinear(n);
            long k = calls;
            calls = 0;
            recHalf(n);
            long l = calls;
            calls = 0;
            recFib(n);
            long m = calls;
            calls = 0;
            recTwoHalves(n);
            long nn = calls;
            System.out.printf("n=%-4d K=%-6d L=%-6d M=%-10d N=%d%n", n, k, l, m, nn);
        }
        if (args.length > 0 && args[0].equals("answers")) {
            System.out.println("\nANSWERS");
            System.out.println("A n -> O(n)            B n^2 -> O(n^2)        C n(n-1)/2 -> O(n^2)");
            System.out.println("D ceil(log2 n) -> O(log n)   E n*log2 n -> O(n log n)   F 20n -> O(n)");
            System.out.println("G n + n^2 -> O(n^2)    H ~2n -> O(n)          I n^3 -> O(n^3)   J n/3 -> O(n)");
            System.out.println("K n+1 calls -> O(n)    L log2 n calls -> O(log n)");
            System.out.println("M ~2^n calls -> O(2^n) N ~2n calls -> O(n)  (without a merge step)");
        }
    }
}
