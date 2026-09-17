public class GCD {
    // time complexity O(n)
    public static int gcd_1(int m, int n) {
        int divisor = 1;
        for (int i = 2; i < m && i < n; i++) {
            if (m % i == 0 && n % i == 0)
                divisor = i;
        }
        return divisor;
    }

    // time complexity O(n)
    public static int gcd_2(int m, int n) {
        int divisor = 1;
        for (int i = n; i >= 1; i--) {
            if (m % i == 0 && n % i == 0) {
                divisor = i;
                break;
            }
        }
        return divisor;
    }

    // time complexity O(n)
    public static int gcd_3(int m, int n) {
        int divisor = 1;
        if (m % n == 0)
            return n;
        for (int i = n / 2; i >= 1; i--) {
            if (m % i == 0 && n % i == 0) {
                divisor = i;
                break;
            }
        }
        return divisor;
    }

    // time complexity O(log n)
    public static int gcd_4(int m, int n) {
        if (m % n == 0)
            return n;
        else
            return gcd_4(n, m % n);
    }
}
