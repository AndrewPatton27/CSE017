import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * TOPIC 3 - File IO (every subtopic, labeled a-f)
 * Compile & run from THIS folder:   javac *.java   then   java FileIODemo
 * (uses students.txt and products.csv in the same folder)
 *
 * THE PATTERN (for both reading and writing):   OPEN  ->  USE  ->  CLOSE
 *
 * 3f - new Scanner(File) and new PrintWriter(File) both throw
 *      FileNotFoundException, which is CHECKED -> you MUST catch it or declare it.
 *      (new File(...) itself throws NOTHING - it's just a path/metadata object.)
 *
 * Imports you'll need:
 *   import java.io.File;
 *   import java.io.FileNotFoundException;
 *   import java.io.PrintWriter;
 *   import java.util.Scanner;
 */
public class FileIODemo {

    public static void main(String[] args) {
        printFileProperties("students.txt");
        printFileProperties(".");                     // "." = current folder
        printFileProperties("doesNotExist.txt");

        readTokens("students.txt");
        readLines("students.txt");

        ArrayList<String[]> rows = readCSV("products.csv", true);
        for (String[] r : rows) System.out.println(String.join(" | ", r));

        writeNumbers("numbers.txt", 5);
        appendLine("numbers.txt", "appended line");
        readLines("numbers.txt");

        // Declare-rule version: caller handles the exception
        try {
            copyFileUpperCase("students.txt", "students_upper.txt");
            readLines("students_upper.txt");
            copyFileUpperCase("missing.txt", "out.txt");     // throws
        } catch (FileNotFoundException e) {
            System.out.println("Caller caught: " + e.getMessage());
        }

        System.out.println("Lines in students.txt: " + countLines("students.txt"));
        System.out.printf("Average GPA: %.2f%n", averageColumn("students.txt", 3));
    }

    // ================================================================
    // 3a - FILE PROPERTIES with class File (no exception, no opening)
    // ================================================================
    public static void printFileProperties(String filename) {
        File file = new File(filename);
        System.out.println("\n=== Properties of \"" + filename + "\" ===");
        if (!file.exists()) {
            System.out.println("Does not exist");
            return;
        }
        System.out.println("getName():         " + file.getName());
        System.out.println("getAbsolutePath(): " + file.getAbsolutePath());
        System.out.println("isFile():          " + file.isFile());
        System.out.println("isDirectory():     " + file.isDirectory());
        System.out.println("canRead()/canWrite(): " + file.canRead() + "/" + file.canWrite());
        if (file.isFile()) {
            System.out.println("length():          " + file.length() + " bytes");
            System.out.println("lastModified():    " + new java.util.Date(file.lastModified()));
        }
        if (file.isDirectory()) {
            File[] items = file.listFiles();
            System.out.println("listFiles():       " + items.length + " items");
            for (File f : items) System.out.println("   " + (f.isDirectory() ? "[dir] " : "      ") + f.getName());
        }
        // Other File methods: getParent(), delete(), renameTo(File), mkdir(), createNewFile()*
        // (*createNewFile throws IOException - checked)
    }

    // ================================================================
    // 3b + 3c - OPEN for reading, READ token-by-token, CLOSE  (catch rule)
    // Scanner methods: hasNext(), next(), nextInt(), nextDouble(), nextBoolean(),
    //                  hasNextInt(), hasNextDouble(), hasNextLine(), nextLine()
    // Template: change the next*() calls to match one line of your file.
    // ================================================================
    public static void readTokens(String filename) {
        System.out.println("\n=== readTokens(" + filename + ") ===");
        try {
            Scanner read = new Scanner(new File(filename));     // OPEN (may throw)
            int count = 0;
            while (read.hasNext()) {                             // until end of file
                String first = read.next();
                String last  = read.next();
                int id       = read.nextInt();
                double gpa   = read.nextDouble();
                System.out.printf("%-8s %-8s %4d %.2f%n", first, last, id, gpa);
                count++;
            }
            read.close();                                        // CLOSE
            System.out.println(count + " records read");
        } catch (FileNotFoundException e) {
            System.out.println("Cannot open " + filename);
        }
    }

    // Line-by-line reading (use when lines have spaces / variable fields)
    public static void readLines(String filename) {
        System.out.println("\n=== readLines(" + filename + ") ===");
        try {
            Scanner read = new Scanner(new File(filename));
            int lineNum = 1;
            while (read.hasNextLine()) {
                String line = read.nextLine();
                System.out.println(lineNum++ + ": " + line);
            }
            read.close();
        } catch (FileNotFoundException e) {
            System.out.println("Cannot open " + filename);
        }
    }

    // Line + split (combines File IO with Topic 4 regex). Returns every row as String[].
    // Convert fields with Integer.parseInt(...) / Double.parseDouble(...).
    public static ArrayList<String[]> readCSV(String filename, boolean skipHeader) {
        ArrayList<String[]> rows = new ArrayList<>();
        try {
            Scanner read = new Scanner(new File(filename));
            if (skipHeader && read.hasNextLine()) read.nextLine();
            while (read.hasNextLine()) {
                String line = read.nextLine().trim();
                if (line.isEmpty()) continue;
                rows.add(line.split(","));        // change delimiter regex as needed: "\\s+", "[,;]", ...
            }
            read.close();
        } catch (FileNotFoundException e) {
            System.out.println("Cannot open " + filename);
        }
        return rows;
    }

    // ================================================================
    // 3d + 3e - OPEN for writing, WRITE, CLOSE  (catch rule)
    // PrintWriter methods: print(), println(), printf(), close(), flush()
    // !! new PrintWriter(file) CREATES the file if missing and OVERWRITES it if it exists.
    // !! Forgetting close() can leave the file EMPTY (data stays in the buffer).
    // ================================================================
    public static void writeNumbers(String filename, int howMany) {
        try {
            PrintWriter write = new PrintWriter(new File(filename));   // OPEN (may throw)
            for (int i = 1; i <= howMany; i++) {
                write.printf("Line %d: %.3f%n", i, Math.random());      // WRITE
            }
            write.close();                                              // CLOSE
            System.out.println("\nWrote " + howMany + " lines to " + filename);
        } catch (FileNotFoundException e) {
            System.out.println("Cannot write to " + filename);
        }
    }

    // APPEND instead of overwrite (bonus - wrap a FileOutputStream with append=true)
    public static void appendLine(String filename, String text) {
        try {
            PrintWriter write = new PrintWriter(new FileOutputStream(new File(filename), true));
            write.println(text);
            write.close();
        } catch (FileNotFoundException e) {
            System.out.println("Cannot append to " + filename);
        }
    }

    // ================================================================
    // 3f - DECLARE RULE version: no try-catch, "throws FileNotFoundException".
    // Read one file and write a transformed copy - a very common exam question.
    // ================================================================
    public static void copyFileUpperCase(String inName, String outName) throws FileNotFoundException {
        Scanner read = new Scanner(new File(inName));
        PrintWriter write = new PrintWriter(new File(outName));
        while (read.hasNextLine()) {
            write.println(read.nextLine().toUpperCase());   // <- change the transformation here
        }
        read.close();
        write.close();
    }

    // Reusable: count lines
    public static int countLines(String filename) {
        int count = 0;
        try {
            Scanner read = new Scanner(new File(filename));
            while (read.hasNextLine()) { read.nextLine(); count++; }
            read.close();
        } catch (FileNotFoundException e) {
            return -1;
        }
        return count;
    }

    // Reusable: average of a numeric column (0-based) in a whitespace-separated file
    public static double averageColumn(String filename, int col) {
        double sum = 0;
        int n = 0;
        try {
            Scanner read = new Scanner(new File(filename));
            while (read.hasNextLine()) {
                String[] parts = read.nextLine().trim().split("\\s+");
                if (parts.length > col) {
                    try {
                        sum += Double.parseDouble(parts[col]);
                        n++;
                    } catch (NumberFormatException e) { /* skip non-numeric */ }
                }
            }
            read.close();
        } catch (FileNotFoundException e) {
            System.out.println("Cannot open " + filename);
        }
        return n == 0 ? 0 : sum / n;
    }
}
