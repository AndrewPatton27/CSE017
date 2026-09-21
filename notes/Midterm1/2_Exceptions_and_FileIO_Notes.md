# CSE 017 — Useful Java Classes: Exceptions, File IO, and Regex — Study Notes

## Key Terms

- **Exception**: a runtime error thrown by the program that, if unhandled, stops it immediately.
- **Try block**: code where an exception might occur (might be thrown).
- **Catch block**: code that runs only when the exception it's declared for is thrown; like a method with one `Throwable` parameter, and it never returns to the try block.
- **Throwable**: the superclass of all exception classes in Java.
- **Checked exception**: must be caught or declared (subclasses of `Exception`, excluding `RuntimeException`).
- **Unchecked exception**: does not have to be caught or declared (subclasses of `RuntimeException`, and `Error`).
- **Finally block**: extra block after try/catch that always executes, exception or not (unless the program exits from inside try/catch first).
- **File (class)**: wrapper for file *metadata* — name, size, location, exists, isFile, isDirectory, etc.
- **Scanner**: reads text (keyboard input or from a `File`).
- **PrintWriter**: writes text output (console or file).
- **Regular expression (regex)**: a pattern describing a string, used for matching/validating/splitting text.
- **Marker interface**: an empty interface (e.g., `Cloneable`) used only to signal a capability, not to declare methods.
- **Shallow copy**: copies field values as-is (primitives by value, references still point to the same objects).
- **Deep copy**: creates fully independent copies of referenced objects too.

## Exception Hierarchy

```
Object
 └── Throwable
      ├── Error                     (unchecked)
      └── Exception                 (checked, unless it's a RuntimeException)
           ├── IOException          (checked)
           ├── ClassNotFoundException (checked)
           └── RuntimeException     (unchecked)
                ├── ArithmeticException
                ├── InputMismatchException
                ├── NullPointerException
                └── ArrayIndexOutOfBoundsException
```

`Throwable`: `-message: String`, `+Throwable()`, `+Throwable(String)`, `+getMessage(): String`, `+toString(): String`, `+printStackTrace(): void`

## Algorithm / Control Flow: try-catch

1. Start the try block.
2. If no exception is thrown → complete the try block, **skip** all catch blocks, continue after.
3. If an exception is thrown → leave the try block immediately, execute the matching catch block, then continue after the catch block.

### Example: basic try/catch

```java
import java.util.Scanner;
public class TryCatch{
  public static void main(String[] args) {
    int[] a = {10, 20, 30, 40};
    int x, y;
    Scanner keyboard = new Scanner(System.in);
    try{
      System.out.println("Enter a number: ");
      x = keyboard.nextInt();
      System.out.println("Enter a number: ");
      y = keyboard.nextInt();
      System.out.println(x + " + " + y + " = " + (x + y));
      a[y] = a[y] * 2;
      System.out.println("a[" + y + "] = " + a[y]);
    }
    catch(Exception e){
      System.out.println("An exception happened. Exiting the program");
    }
  }
}
```

### Example: multiple / specific catch blocks

```java
try{
  x = keyboard.nextInt();
  y = keyboard.nextInt();
  a[y] = a[y] * 2;
}
catch(InputMismatchException e){
  System.out.println("Input Mismatch Exception: Input must be an integer");
}
catch(ArrayIndexOutOfBoundsException e){
  System.out.println("Array Index Exception: " + e.getMessage());
}
```

**Ordering Rule for multiple catch blocks: Specific before General.** A subclass exception's catch block must come before its superclass's, or the subclass block becomes unreachable (compile error).

```java
// WRONG — unreachable catch:
try { ... }
catch (Exception e) { ... }                 // general first: unreachable code below
catch (InputMismatchException e) { ... }    // ✗ never reached

// RIGHT — specific first:
try { ... }
catch (InputMismatchException e) { ... }    // specific first
catch (Exception e) { ... }                 // general last
```

### Throwing exceptions explicitly

```java
throw new Exception("Something went wrong");
// e.getMessage() in the catch block returns "Something went wrong"
```

### Creating a custom exception class

```java
public class InvalidGPAException extends Exception {
    public InvalidGPAException() {
        super("Invalid GPA Exception");
    }
    public InvalidGPAException(String message) {
        super(message);
    }
}
```
An exception class needs **at least two constructors**: a no-arg constructor and one taking a `String` message. This is because you can only `throw` an object whose class is a subclass of `Throwable`.

### Catch-or-Declare Rule (checked exceptions only)

- **Catch Rule**: the exception is thrown and caught in the same method via try-catch. ("Handling right now, right here!")
- **Declare Rule**: the method doesn't catch it — it declares `throws ExceptionType` so the caller must deal with it. ("I'm not handling this here. Whoever calls me must deal with it.")
- A method may mix both rules (catch some, declare others).
- This rule applies **only to checked exceptions** — unchecked exceptions (`RuntimeException`/`Error` subclasses) don't need to be caught or declared.

```java
// Catch Rule
public static double safeDivide(double a, double b){
    try{
        if (b == 0) throw new Exception();
        else return (a / b);
    }
    catch(Exception e){
        return 0;
    }
}

// Declare Rule
public static double safeDivide(double a, double b) throws Exception {
    if (b == 0) throw new Exception();
    else return (a / b);
}
```

### Finally block

```java
try {
    // block of statements
}
catch(SpecificException se) {
    // block of statements
}
catch(GeneralException e) {
    // block of statements
}
finally {
    // always executes (exception thrown or not),
    // except if the program already exited from inside try/catch
}
```

## File IO

Two aspects of file access: **properties** (metadata: name, size, location) and **contents** (reading/writing).

### File properties (class `File`)

```java
import java.io.File;
import java.util.Scanner;
public class ClassFile {
  public static void main(String[] args) {
    Scanner keyboard = new Scanner(System.in);
    System.out.println("Enter file name: ");
    String filename = keyboard.next();
    File file = new File(filename);
    if (!file.exists()) {
       System.out.println("File not found"); System.exit(0);
    }
    if (file.isFile()) {
       System.out.println(filename + " is a file.");
       System.out.println("Size of the file: " + file.length() + " bytes");
    }
    if (file.isDirectory()) {
       System.out.println(filename + " is a folder.");
       File[] list = file.listFiles();
       System.out.println("There are " + list.length + " items in the folder");
    }
  }
}
```

### Reading a text file — steps: open → read → close (must handle `FileNotFoundException`)

```java
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
public class Read {
  public static void main(String[] args) {
    File file = new File("students.txt");
    int count = 0;
    try {
      Scanner readFile = new Scanner(file);          // open
      System.out.println("File opened successfully.");
      while (readFile.hasNext()) {                    // check for end of file
        String fname = readFile.next();
        String lname = readFile.next();
        int id = readFile.nextInt();
        double gpa = readFile.nextDouble();
        System.out.println("Student " + (count + 1) + ": (" + fname + " " + lname +
                           ", " + id + ", " + gpa + " )");
        count++;
      }
      readFile.close();                                // close
      System.out.println(count + " students read from the file.");
    }
    catch (FileNotFoundException e) {
      System.out.println("Cannot open file \"students.txt\"");
    }
  }
}
```

### Writing to a text file — steps: open → write → close (must handle `FileNotFoundException`)

```java
import java.io.File;
import java.io.PrintWriter;
import java.io.FileNotFoundException;
public class Write {
  public static void main(String[] args) {
    File file = new File("numbers.txt");
    try {
       PrintWriter writeFile = new PrintWriter(file);   // open
       for (int i = 0; i < 1000; i++) {
         writeFile.println(Math.random());              // write
       }
       writeFile.close();                                // close
     }
     catch (FileNotFoundException e) {
       System.out.println("Cannot write to file.");
     }
   }
}
```
**Important behavior:** if `numbers.txt` does not exist, Java creates it. If it already exists, `PrintWriter` **overwrites it by default**.

## Class String & Regular Expressions

- `String` objects are **immutable**; `StringBuilder` is **mutable**.
- `String` has 13 constructors and 40 methods.
- Four `String` methods accept regex arguments: `replaceFirst`, `replaceAll`, `split`, `matches`.

### Regex basics
- `\d{3}-\d{2}-\d{4}` → 3 digits, dash, 2 digits, dash, 4 digits (Social Security Number pattern). In a Java string literal it must be escaped: `"\\d{3}-\\d{2}-\\d{4}"`.
- `[abcd%]` → matches any one of the characters a, b, c, d, or `%`.
- `.*\.java` → any number of any characters, followed by a literal `.java`.

### Regex method summary
| Method | Purpose |
|---|---|
| `String replaceFirst(String regex, String replacement)` | replace first match |
| `String replaceAll(String regex, String replacement)` | replace all matches |
| `String[] split(String regex)` | split into tokens |
| `boolean matches(String regex)` | whole string matches pattern? |

### Example

```java
public class Regex{
  public static void main(String[] args) {
    System.out.println("2+3-5".replaceFirst("[+-]", "%"));   // 2%3-5
    System.out.println("2+3-5".replaceAll("[+-]", "%"));     // 2%3%5
    String[] items = "02/25/2021".split("/");
    for (String item : items) System.out.println(item + " ");

    String[] tokens = "Java,C?C#,C++".split("[.,:;?]");
    for (String token : tokens) System.out.println(token + " ");

    System.out.println("2+3-5".matches("\\d[+-]\\d[+-]\\d"));           // true
    System.out.println("440-02-4534".matches("\\d{3}-\\d{2}-\\d{4}"));  // true
  }
}
```
