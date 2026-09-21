# Common Hour 1 - Sample Code Index

One folder per topic from *Test_1_Covered topics.pdf*. Each folder compiles on its own:

```
cd 2_Exceptions
javac *.java
java ExceptionsDemo
```

No `package` lines, so any file can be copied straight into another project.

| Topic | Subtopic | Where |
|---|---|---|
| **1 Classes** | create / fields / constructors / getters-setters | `1_Classes/Person.java` |
| | extend, `super(...)`, override `toString` | `1_Classes/Student.java`, `Employee.java` |
| | instantiate, array of super type, `equals` vs `==` | `1_Classes/ClassesDemo.java` |
| **2 Exceptions** | a. try-catch (+ multiple catches, finally) | `ExceptionsDemo.tryCatchBasics / multipleCatchBlocks / tryCatchFinally` |
| | b. `throw` | `ExceptionsDemo.safeSqrtDeclare` |
| | c. Declare rule (`throws`, no try-catch) | `ExceptionsDemo.safeSqrtDeclare` |
| | d. Catch rule (try-catch, no `throws`) | `ExceptionsDemo.safeDivideCatch` |
| | e. Custom exception classes | `InvalidValueException.java` (checked), `InvalidInputRuntimeException.java` (unchecked) |
| | f. Checked vs unchecked | hierarchy at top of `ExceptionsDemo.java`, `parsePositive` |
| | reusable: re-prompt until valid int | `ExceptionsDemo.readIntInRange` |
| **3 File IO** | a. File properties | `FileIODemo.printFileProperties` |
| | b/c. Open, read (tokens / lines / CSV), close | `FileIODemo.readTokens / readLines / readCSV` |
| | d/e. Open, write, close (+ append) | `FileIODemo.writeNumbers / appendLine` |
| | f. `FileNotFoundException` (catch AND declare versions) | every method above + `copyFileUpperCase` |
| | reusable: count lines, average a column | `FileIODemo.countLines / averageColumn` |
| **4 Regex** | a. `matches()` and `split()` (+ replaceAll) | `RegexDemo.main` |
| | b. Simple regex cheat sheet + ready-made validators | top of `RegexDemo.java` |
| | reusable: word count, sum numbers, parse/validate a record | `RegexDemo.countWords / sumNumbers / parseRecord` |
| **5 Interfaces** | a. implement (not extend) | `Shape.java`, `Drawable.java`, `Circle.java`, `Rectangle.java` |
| | b. cannot instantiate | top of `InterfacesDemo.main` |
| | c. `Comparable` / `compareTo` | `Student.compareTo`, `Circle.compareTo`, `Arrays.sort` |
| | c. `Cloneable` shallow vs deep | `Student.shallowCopy` vs `Student.clone`, `Address.java` |
| | d. interface as a type | `Shape[] shapes` in `InterfacesDemo` |
| | e. upcasting (automatic) | `Shape s1 = new Circle(2);` |
| | f/g. downcasting + `instanceof` | `InterfacesDemo` "instanceof + downcasting" section |
| | reusable generic sort / max for any Comparable | `InterfacesDemo.selectionSort / findMax` |

## Quick exam gotchas
- Catch blocks: **specific before general**, or it won't compile.
- Checked = must catch or declare (`Exception`, `IOException`, `FileNotFoundException`, `CloneNotSupportedException`, your `extends Exception` class). Unchecked = `RuntimeException` and its subclasses, plus `Error`.
- `new File(...)` never throws; `new Scanner(file)` and `new PrintWriter(file)` throw `FileNotFoundException`.
- `PrintWriter` **overwrites** an existing file; forgetting `close()` can leave it empty.
- `matches()` must match the **whole** string. Escape `.` `+` `|` `?` `*` in `split` (`"\\."`).
- Upcast = automatic. Downcast = explicit `(Type)` and should be guarded by `instanceof`, or you risk `ClassCastException` at runtime.
- `Cloneable` is a **marker** interface (no methods). `super.clone()` = shallow; clone each mutable field yourself for deep.
- A class **extends one** class but can **implement many** interfaces.
