import java.util.Arrays;

/**
 * TOPIC 5 driver - every subtopic labeled a-g
 * Compile & run:   javac *.java   then   java InterfacesDemo
 */
public class InterfacesDemo {

    public static void main(String[] args) throws CloneNotSupportedException {

        // ===== 5b - Cannot be instantiated =====
        // Shape s = new Shape();          // COMPILE ERROR: Shape is abstract; cannot be instantiated
        // Comparable c = new Comparable(); // same

        // ===== 5d + 5e - Interface as a TYPE; UPCASTING is automatic =====
        Shape s1 = new Circle(2);           // upcast: Circle -> Shape (implicit)
        Shape s2 = new Rectangle(3, 4);
        Shape[] shapes = { s1, s2, new Circle(1), new Rectangle(5, 5) };

        System.out.println("=== Polymorphism: same call, different behavior ===");
        for (Shape s : shapes) {
            // dynamic binding: JVM picks Circle's or Rectangle's getArea() at runtime
            System.out.printf("%-18s area=%7.2f  perimeter=%6.2f  | %s%n",
                    s, s.getArea(), s.getPerimeter(), s.describe());
        }
        System.out.printf("Total area (static interface method): %.2f%n", Shape.totalArea(shapes));
        System.out.println("Interface constant: " + Shape.PI_APPROX);

        // Through a Shape variable you can ONLY call Shape methods:
        // s2.isSquare();                  // COMPILE ERROR - declared type is Shape

        // ===== 5f + 5g - DOWNCASTING safely with instanceof =====
        System.out.println("\n=== instanceof + downcasting ===");
        for (Shape s : shapes) {
            if (s instanceof Rectangle) {
                Rectangle r = (Rectangle) s;         // explicit downcast
                System.out.println(r + " isSquare? " + r.isSquare());
            }
            if (s instanceof Drawable) {             // instanceof works with interfaces too
                ((Drawable) s).draw();               // cast to another interface
            }
        }
        // Unsafe downcast (no instanceof) -> ClassCastException at RUNTIME (compiles fine)
        try {
            Circle bad = (Circle) s2;                // s2 is really a Rectangle
        } catch (ClassCastException e) {
            System.out.println("ClassCastException: Rectangle is not a Circle");
        }
        // instanceof truth table for c = new Circle(1):
        Object c = new Circle(1);
        System.out.println("c instanceof Circle     = " + (c instanceof Circle));
        System.out.println("c instanceof Shape      = " + (c instanceof Shape));
        System.out.println("c instanceof Drawable   = " + (c instanceof Drawable));
        System.out.println("c instanceof Comparable = " + (c instanceof Comparable));
        System.out.println("c instanceof Rectangle  = " + (c instanceof Rectangle));
        System.out.println("null instanceof Shape   = " + (null instanceof Shape));   // always false

        // ===== 5c - Comparable =====
        System.out.println("\n=== Comparable / compareTo ===");
        Student[] roster = {
            new Student("Sam",  104, 2.9, new Address("Bethlehem"), new int[]{80, 75}),
            new Student("Drew", 101, 3.6, new Address("Allentown"), new int[]{95, 90}),
            new Student("Alex", 103, 3.8, new Address("Easton"),    new int[]{88, 92}),
        };
        System.out.println("Drew.compareTo(Sam) = " + roster[1].compareTo(roster[0]) + "  (negative -> Drew first)");
        Arrays.sort(roster);                      // uses compareTo
        printArray("Sorted by id (Arrays.sort):", roster);

        Circle[] circles = { new Circle(3), new Circle(1), new Circle(2) };
        selectionSort(circles);                   // our own generic sort
        System.out.println("Circles via selectionSort: " + Arrays.toString(circles));
        System.out.println("Max student by compareTo: " + findMax(roster).getName());

        // ===== 5c - Cloneable: shallow vs deep =====
        System.out.println("\n=== Shallow copy ===");
        Student original = new Student("Drew", 101, 3.6, new Address("Allentown"), new int[]{95, 90});
        Student shallow = original.shallowCopy();
        shallow.setName("Shal");                  // String field -> independent
        shallow.getAddress().setCity("Boston");   // SHARED Address -> changes original too!
        shallow.getScores()[0] = 0;               // SHARED array -> changes original too!
        System.out.println("original: " + original);
        System.out.println("shallow:  " + shallow);
        System.out.println("same Address object? " + (original.getAddress() == shallow.getAddress()));

        System.out.println("\n=== Deep copy ===");
        Student original2 = new Student("Drew", 101, 3.6, new Address("Allentown"), new int[]{95, 90});
        Student deep = (Student) original2.clone();   // clone() returns Object -> downcast
        deep.setName("Deep");
        deep.getAddress().setCity("Boston");      // independent
        deep.getScores()[0] = 0;                  // independent
        System.out.println("original: " + original2);
        System.out.println("deep:     " + deep);
        System.out.println("same Address object? " + (original2.getAddress() == deep.getAddress()));
        System.out.println("deep != original2: " + (deep != original2)
                + ", deep.equals(original2): " + deep.equals(original2));
    }

    // ================================================================
    // REUSABLE GENERIC HELPERS - work for ANY class that implements Comparable
    // (Student, Circle, String, Integer, Double, ...)
    // ================================================================

    /** Selection sort using compareTo (ascending). */
    public static <E extends Comparable<E>> void selectionSort(E[] list) {
        for (int i = 0; i < list.length - 1; i++) {
            int minIndex = i;
            for (int j = i + 1; j < list.length; j++) {
                if (list[j].compareTo(list[minIndex]) < 0) minIndex = j;
            }
            E temp = list[i];
            list[i] = list[minIndex];
            list[minIndex] = temp;
        }
    }

    /** Largest element according to compareTo. */
    public static <E extends Comparable<E>> E findMax(E[] list) {
        E max = list[0];
        for (E item : list) {
            if (item.compareTo(max) > 0) max = item;
        }
        return max;
    }

    /** Print any array of objects (uses each object's toString). */
    public static void printArray(String title, Object[] list) {
        System.out.println(title);
        for (Object o : list) System.out.println("  " + o);
    }
}
