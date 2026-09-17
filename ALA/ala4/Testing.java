import java.util.Scanner;

public class Testing {

    private static final int PAIR_COUNT = 20;
    private static final int MAX_VALUE = 1_000_000;

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        // testCount(input);
        // testPerm(input);
        testRunTimes();

        input.close();
    }

    private static void testCount(Scanner input) {
        System.out.println("Enter a string for count:");
        String str = input.nextLine();

        System.out.println("Enter a character:");
        char c = input.nextLine().charAt(0);

        int occurrences = Recursion.count(str, c);
        System.out.println(c + " appears " + occurrences
                + " times in \"" + str + "\"");

        System.out.println();
    }

    private static void testPerm(Scanner input) {
        System.out.println("Enter a string for permutations:");
        String s = input.nextLine();
        Recursion.permutations(s);
    }

    private static void testRunTimes() {
        int[][] pairs = PerformanceComparator.generateRandomPairs(PAIR_COUNT, MAX_VALUE);

        System.out.println();
        PerformanceComparator.compareExecutionTimes(pairs);

        System.out.println();
        PerformanceComparator.compareIterations(pairs);
    }

    /*
     * Discussion (Part 2, item 4):
     *
     * TODO: Compare the theoretical Big-O time complexity of gcd_1..gcd_4
     * (from the comments in GCD.java) against the experimental execution
     * time and iteration count results from compareExecutionTimes() and
     * compareIterations(). Discuss whether the experimental results agree
     * with the theoretical analysis, and explain any discrepancies.
     */
}
