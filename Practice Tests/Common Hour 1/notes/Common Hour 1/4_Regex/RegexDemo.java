/**
 * TOPIC 4 - Regular Expressions with String.matches() and String.split()
 * Compile & run:   javac RegexDemo.java   then   java RegexDemo
 *
 * ============================ CHEAT SHEET ============================
 * In Java strings every regex backslash is DOUBLED:  \d  ->  "\\d"
 *
 *  .        any single character            \.     a literal dot
 *  \d  \D   digit / NOT digit                [0-9]  same as \d
 *  \w  \W   word char [a-zA-Z0-9_] / NOT     \s \S  whitespace / NOT
 *  [abc]    ONE of a, b, c                   [^abc] ONE char that is NOT a, b, c
 *  [a-z]    range                            [a-zA-Z] letters
 *  X|Y      X or Y                           (XY)   group
 *
 *  QUANTIFIERS (apply to the thing just before):
 *  X?  0 or 1       X*  0 or more      X+  1 or more
 *  X{n} exactly n   X{n,} n or more    X{n,m} between n and m
 *
 *  matches()  -> true only if the WHOLE string fits the pattern (no ^ $ needed)
 *  split()    -> String[] of pieces BETWEEN matches of the pattern
 *  Also: replaceAll(regex, repl), replaceFirst(regex, repl)
 *
 *  Special characters that need escaping to be literal:  . $ | ( ) [ ] { } ^ ? * + \
 *    "a.b".split(".")    -> []  (every char is a delimiter!)   use "\\."
 *    "a|b".split("|")    -> splits every char                  use "\\|"
 *    "a+b".split("+")    -> PatternSyntaxException             use "\\+" or "[+]"
 *    (inside [ ] most specials are literal: "[.+*?]" is fine)
 * =====================================================================
 */
public class RegexDemo {

    // ---------- Reusable validators: copy the one you need ----------
    public static final String INTEGER      = "[+-]?\\d+";
    public static final String DECIMAL      = "[+-]?\\d*\\.?\\d+";      // 3, 3.5, .5, -2.25
    public static final String SSN          = "\\d{3}-\\d{2}-\\d{4}";   // 440-02-4534
    public static final String PHONE        = "\\(?\\d{3}\\)?[- ]?\\d{3}-\\d{4}"; // (610) 555-1234 / 610-555-1234
    public static final String ZIP          = "\\d{5}(-\\d{4})?";       // 18015 or 18015-1234
    public static final String DATE_MMDDYYYY= "(0[1-9]|1[0-2])/(0[1-9]|[12]\\d|3[01])/\\d{4}";
    public static final String NAME         = "[A-Z][a-z]+";            // Capitalized word
    public static final String FULL_NAME    = "[A-Z][a-z]+ [A-Z][a-z]+";
    public static final String EMAIL        = "[\\w.]+@[\\w]+(\\.[a-zA-Z]+)+";
    public static final String LEHIGH_EMAIL = "[a-z]{3}\\d{3}@lehigh\\.edu"; // abc123@lehigh.edu
    public static final String JAVA_FILE    = ".*\\.java";
    public static final String PASSWORD     = "\\w{8,}";                // 8+ word chars
    public static final String HEX_COLOR    = "#[0-9a-fA-F]{6}";
    public static final String TIME_24      = "([01]\\d|2[0-3]):[0-5]\\d";

    /** Reusable: does s match regex? (just wraps matches, handy in tests) */
    public static boolean isValid(String s, String regex) {
        return s != null && s.matches(regex);
    }

    public static void main(String[] args) {
        // ========== 4a - matches() ==========
        System.out.println("=== matches() ===");
        test("440-02-4534", SSN);
        test("440-2-4534", SSN);
        test("-42", INTEGER);
        test("3.14", DECIMAL);
        test("(610) 555-1234", PHONE);
        test("18015-1234", ZIP);
        test("02/25/2021", DATE_MMDDYYYY);
        test("13/25/2021", DATE_MMDDYYYY);
        test("Drew Patton", FULL_NAME);
        test("drew patton", FULL_NAME);
        test("abc123@lehigh.edu", LEHIGH_EMAIL);
        test("Test.java", JAVA_FILE);
        test("#1a2B3c", HEX_COLOR);
        test("23:59", TIME_24);

        // Simple regex examples from lecture
        System.out.println("\"2+3-5\".matches(\"\\\\d[+-]\\\\d[+-]\\\\d\") = " + "2+3-5".matches("\\d[+-]\\d[+-]\\d"));
        System.out.println("\"b\".matches(\"[abcd%]\")  = " + "b".matches("[abcd%]"));
        System.out.println("\"bb\".matches(\"[abcd%]\") = " + "bb".matches("[abcd%]") + "  (whole string must match!)");
        System.out.println("\"Java\".matches(\"J.*\")   = " + "Java".matches("J.*"));
        System.out.println("\"cat\".matches(\"cat|dog\") = " + "cat".matches("cat|dog"));

        // ========== 4a - split() ==========
        System.out.println("\n=== split() ===");
        show("02/25/2021", "/");                 // date parts
        show("Java,C?C#,C++", "[.,:;?]");        // any ONE of those punctuation marks
        show("one   two\tthree", "\\s+");        // any amount of whitespace
        show("a, b ,c ,  d", "\\s*,\\s*");       // comma with optional spaces around
        show("192.168.1.10", "\\.");             // literal dot must be escaped
        show("x1y22z333", "\\d+");               // split ON numbers -> letters
        show("x1y22z333", "\\D+");               // split ON non-digits -> numbers (note leading "")
        show("3+4*2-1", "[+\\-*/]");             // arithmetic operators
        show("key=value", "=", 2);               // limit: at most 2 pieces

        // ========== replaceAll / replaceFirst ==========
        System.out.println("\n=== replaceAll / replaceFirst ===");
        System.out.println("2+3-5".replaceFirst("[+-]", "%"));          // 2%3-5
        System.out.println("2+3-5".replaceAll("[+-]", "%"));            // 2%3%5
        System.out.println("too    many   spaces".replaceAll("\\s+", " "));
        System.out.println("Ph: 610-555-1234".replaceAll("\\D", ""));   // keep only digits

        // ========== Reusable combos ==========
        System.out.println("\n=== reusable helpers ===");
        System.out.println("word count: " + countWords("  The quick  brown fox. "));
        System.out.println("sum of numbers in text: " + sumNumbers("I have 3 bats, 12 balls and 2 gloves"));
        System.out.println("parse line: " + parseRecord("Drew Patton,101,3.6"));
    }

    /** Count words separated by any whitespace. */
    public static int countWords(String text) {
        String t = text.trim();
        if (t.isEmpty()) return 0;
        return t.split("\\s+").length;
    }

    /** Pull every integer out of a sentence and add them. */
    public static int sumNumbers(String text) {
        int sum = 0;
        for (String token : text.split("\\D+")) {    // split on non-digits
            if (token.matches("\\d+")) sum += Integer.parseInt(token);
        }
        return sum;
    }

    /** Split + validate each field (typical "read a line from a file" task). */
    public static String parseRecord(String line) {
        String[] f = line.split(",");
        if (f.length != 3) return "wrong number of fields";
        if (!f[0].matches(FULL_NAME)) return "bad name";
        if (!f[1].matches("\\d+")) return "bad id";
        if (!f[2].matches(DECIMAL)) return "bad gpa";
        return "name=" + f[0] + ", id=" + Integer.parseInt(f[1]) + ", gpa=" + Double.parseDouble(f[2]);
    }

    // ---- printing helpers ----
    private static void test(String s, String regex) {
        System.out.printf("%-20s matches %-40s -> %b%n", "\"" + s + "\"", regex, s.matches(regex));
    }

    private static void show(String s, String regex) {
        show(s, regex, 0);
    }

    private static void show(String s, String regex, int limit) {
        String[] parts = s.split(regex, limit);
        StringBuilder sb = new StringBuilder();
        for (String p : parts) sb.append("[").append(p).append("] ");
        System.out.printf("%-20s split(\"%s\") -> %d pieces: %s%n", "\"" + s + "\"", regex, parts.length, sb);
    }
}
