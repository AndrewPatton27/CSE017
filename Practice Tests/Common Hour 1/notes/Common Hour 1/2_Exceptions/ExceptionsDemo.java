import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * TOPIC 2 - Exception Handling (every subtopic, labeled a-f)
 * Compile & run:   javac *.java   then   java ExceptionsDemo
 *
 * HIERARCHY (memorize):
 *   Throwable
 *    +-- Error                          UNCHECKED
 *    +-- Exception                      CHECKED   (IOException, FileNotFoundException,
 *         |                                        CloneNotSupportedException, your own)
 *         +-- RuntimeException          UNCHECKED (ArithmeticException, NullPointerException,
 *                                                  ArrayIndexOutOfBoundsException,
 *                                                  InputMismatchException, NumberFormatException,
 *                                                  ClassCastException, IllegalArgumentException)
 */
public class ExceptionsDemo {

    public static void main(String[] args) {
        tryCatchBasics();
        multipleCatchBlocks(new int[]{10, 20, 30}, 5, 0);
        tryCatchFinally();

        // --- 2c Declare rule: the method says "throws", so the CALLER must handle it
        try {
            System.out.println("sqrt = " + safeSqrtDeclare(16));
            System.out.println("sqrt = " + safeSqrtDeclare(-4));   // throws
            System.out.println("never printed");
        } catch (InvalidValueException e) {
            System.out.println("Caller caught: " + e.getMessage());
        }

        // --- 2d Catch rule: method handles it itself, caller needs nothing
        System.out.println("safeDivideCatch(10, 0) = " + safeDivideCatch(10, 0));

        // --- 2f Unchecked: no try-catch required to COMPILE (but it still crashes if thrown)
        System.out.println("parsePositive(\"42\") = " + parsePositive("42"));
        try {
            parsePositive("-3");
        } catch (InvalidInputRuntimeException e) {
            System.out.println("Unchecked caught: " + e.getMessage());
        }

        // --- Reusable: re-prompt until valid input
        // int age = readIntInRange(new Scanner(System.in), "Enter age: ", 0, 120);
        Scanner fake = new Scanner("abc -5 25");   // simulated keyboard input
        int n = readIntInRange(fake, "Enter 0-100: ", 0, 100);
        System.out.println("Got valid value: " + n);

        // --- Throwable methods you can call on any exception
        try {
            Object o = "hello";
            Integer i = (Integer) o;               // ClassCastException
        } catch (ClassCastException e) {
            System.out.println("getMessage(): " + e.getMessage());
            System.out.println("toString():   " + e);          // ClassName: message
            // e.printStackTrace();                // prints full stack trace
        }
    }

    // ================================================================
    // 2a - TRY-CATCH BLOCK
    //  - no exception  -> whole try runs, catch is SKIPPED
    //  - exception     -> try is abandoned at that line, matching catch runs,
    //                    then execution continues AFTER the catch (never back into try)
    // ================================================================
    public static void tryCatchBasics() {
        System.out.println("\n=== 2a try-catch ===");
        try {
            System.out.println("Before");
            int x = 10 / 0;                        // ArithmeticException
            System.out.println("Skipped");         // never runs
        } catch (ArithmeticException e) {
            System.out.println("Caught: " + e.getMessage());   // "/ by zero"
        }
        System.out.println("After try-catch - program continues");
    }

    // Multiple catch blocks - ORDER: SPECIFIC first, GENERAL (Exception) last,
    // otherwise compile error "exception has already been caught".
    public static void multipleCatchBlocks(int[] arr, int index, int divisor) {
        System.out.println("\n=== multiple catch blocks ===");
        try {
            int result = arr[index] / divisor;
            System.out.println("Result: " + result);
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Bad index: " + e.getMessage());
        } catch (ArithmeticException e) {
            System.out.println("Math error: " + e.getMessage());
        } catch (Exception e) {                    // catch-all goes LAST
            System.out.println("Something else: " + e);
        }
        // Java 7+ multi-catch (same handling for several types):
        // catch (ArrayIndexOutOfBoundsException | ArithmeticException e) { ... }
    }

    // finally ALWAYS runs (exception or not) - used for cleanup like close()
    public static void tryCatchFinally() {
        System.out.println("\n=== try-catch-finally ===");
        for (String s : new String[]{"5", "five"}) {
            try {
                int v = Integer.parseInt(s);        // NumberFormatException on "five"
                System.out.println("Parsed " + v);
            } catch (NumberFormatException e) {
                System.out.println("Not a number: " + s);
            } finally {
                System.out.println("finally runs for \"" + s + "\"");
            }
        }
    }

    // ================================================================
    // 2b - THROW STATEMENT:   throw new ExceptionType("message");
    // 2c - DECLARE RULE:      "throws" in header, NO try-catch inside.
    //                          Caller is now responsible.
    // ================================================================
    public static double safeSqrtDeclare(double x) throws InvalidValueException {
        if (x < 0) {
            throw new InvalidValueException("Cannot take sqrt of negative: " + x);
        }
        return Math.sqrt(x);
    }

    // ================================================================
    // 2d - CATCH RULE: try-catch INSIDE the method, NO throws clause.
    // ================================================================
    public static double safeDivideCatch(double a, double b) {
        try {
            if (b == 0) throw new InvalidValueException("Division by zero");
            return a / b;
        } catch (InvalidValueException e) {
            System.out.println("Handled inside method: " + e.getMessage());
            return 0;
        }
    }

    // ================================================================
    // 2f - UNCHECKED: throws RuntimeException subclass, no throws clause needed.
    // (You MAY still write "throws InvalidInputRuntimeException" - it's optional.)
    // ================================================================
    public static int parsePositive(String s) {
        int v = Integer.parseInt(s);          // may itself throw NumberFormatException (unchecked)
        if (v <= 0) throw new InvalidInputRuntimeException("Must be positive: " + v);
        return v;
    }

    // Mixing both rules: catch one exception, declare another
    public static int mixed(String s) throws InvalidValueException {
        try {
            int v = Integer.parseInt(s);
            if (v < 0) throw new InvalidValueException("negative");   // declared -> goes to caller
            return v;
        } catch (NumberFormatException e) {                            // caught here
            return -1;
        }
    }

    // ================================================================
    // REUSABLE: read an int from a Scanner, re-prompt on bad input.
    // Works with keyboard (new Scanner(System.in)) or a file Scanner.
    // ================================================================
    public static int readIntInRange(Scanner in, String prompt, int min, int max) {
        while (true) {
            System.out.print(prompt);
            try {
                int v = in.nextInt();                          // InputMismatchException if not int
                if (v < min || v > max)
                    throw new InvalidValueException(v + " is out of range [" + min + ", " + max + "]");
                System.out.println(v);
                return v;
            } catch (InputMismatchException e) {
                System.out.println("not an integer: " + in.next());   // in.next() discards bad token
            } catch (InvalidValueException e) {
                System.out.println(e.getMessage());
            }
        }
    }
}
