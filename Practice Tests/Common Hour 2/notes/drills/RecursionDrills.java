import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Recursion drills. Every method must be recursive (no loops!).
 * Run DrillTest to check your work.
 */
public class RecursionDrills {

    /** Sum of the digits of n (n >= 0). sumDigits(1234) = 10. */
    public static int sumDigits(int n) {
        // TODO: write this method
        return -1;
    }

    /** base^exp for exp >= 0 (O(n) version, one multiplication per call). */
    public static int power(int base, int exp) {
        // TODO: write this method
        return -1;
    }

    /** base^exp in O(log n): square the half. power2(2, 10) = 1024. */
    public static long power2(long base, int exp) {
        // TODO: write this method
        return -1;
    }

    /** Reverse a string. reverse("abc") = "cba". */
    public static String reverse(String s) {
        // TODO: write this method
        return null;
    }

    /** True if s reads the same both ways. Use a helper with two indexes. */
    public static boolean isPalindrome(String s) {
        // TODO: write this method
        return false;
    }

    // helper (overloaded): carries the two ends
    private static boolean isPalindrome(String s, int first, int last) {
        // TODO: write this method
        return false;
    }

    /** Count the even numbers in the array. Helper carries the index. */
    public static int countEven(int[] a) {
        // TODO: write this method
        return -1;
    }

    private static int countEven(int[] a, int index) {
        // TODO: write this method
        return -1;
    }

    /** Recursive binary search on a SORTED array. Returns index or -1. */
    public static int binarySearch(int[] a, int key) {
        // TODO: write this method
        return -2;
    }

    private static int binarySearch(int[] a, int key, int first, int last) {
        // TODO: write this method
        return -2;
    }

    /** Greatest common divisor (Euclid). gcd(12, 18) = 6. */
    public static int gcd(int a, int b) {
        // TODO: write this method
        return -1;
    }

    /** Binary representation as a String. toBinary(10) = "1010", toBinary(0) = "0". */
    public static String toBinary(int n) {
        // TODO: write this method
        return null;
    }

    /** Number of times target appears in the list. Recursive + iterator. */
    public static int countOccurrences(List<String> list, String target) {
        // TODO: write this method
        return -1;
    }

    private static int countOccurrences(Iterator<String> it, String target) {
        // TODO: write this method
        return -1;
    }

    /**
     * Exam style (like findMessages in Practice Test 2): return a NEW list with
     * every element >= min, or null if there are none. Recursive + iterator.
     */
    public static List<Integer> findAtLeast(List<Integer> list, int min) {
        // TODO: write this method
        return null;
    }

    private static void findAtLeast(Iterator<Integer> it, int min, List<Integer> result) {
        // TODO: write this method
        // (nothing to return)
    }

    /** Largest value in a non-empty list using an iterator. */
    public static int maxOf(List<Integer> list) {
        // TODO: write this method
        return -1;
    }

    private static int maxOf(Iterator<Integer> it, int best) {
        // TODO: write this method
        return -1;
    }

    /**
     * No base case on purpose! Calling this throws StackOverflowError.
     * (Do not fix it. DrillTest catches the error to show what happens.)
     */
    public static int brokenFactorial(int n) {
        return n * brokenFactorial(n - 1);
    }
}
