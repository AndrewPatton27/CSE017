import java.util.Arrays;

public class TestShapes {

    public static void main(String[] args) {
        Shape[] shapes = new Shape[8];

        shapes[0] = new Circle("Black", 2.5);
        shapes[1] = new Triangle("Green", 6.0, 6.0, 6.0);
        shapes[2] = new Rectangle("Red", 5.0, 3.0);
        shapes[3] = new Pentagon("Yellow", 7.0);

        System.out.println(((Shape)shapes[0]).toString());

        shapes[4] = (Circle) shapes[0].clone();
        shapes[5] = (Triangle) shapes[1].clone();
        shapes[6] = (Rectangle) shapes[2].clone();
        shapes[7] = (Pentagon) shapes[3].clone();

        // 4. Modify the clones so they diverge from their originals,
        //    proving clone() made independent copies rather than aliases.
        ((Circle) shapes[4]).scale(2.0);           // radius 2.5 -> 5.0
        ((Triangle) shapes[5]).setColor("Orange");
        ((Rectangle) shapes[6]).setLength(10.0);
        ((Pentagon) shapes[7]).setSide(4.0);

        // 5. Display all 8 shapes before sorting.
        System.out.println("Before sorting");
        printShapes(shapes);

        // 6. Arrays.sort() sorts a Shape[] using Shape's natural ordering
        //    because Shape implements Comparable<Shape>; that ordering is
        //    "by area", as defined in Shape.compareTo().
        Arrays.sort(shapes);

        // 7. Display all 8 shapes after sorting (now ascending by area).
        System.out.println();
        System.out.println("After sorting");
        printShapes(shapes);

        // 8. Compute and display the average perimeter of all 8 shapes.
        double avgPerimeter = getAveragePerimeter(shapes);
        System.out.println();
        System.out.printf("Average Perimeter = %.2f%n", avgPerimeter);
    }

    /**
     * Prints a column header followed by each shape's toString(). "list"
     * is declared Shape[], but s.toString() is still dynamically bound to
     * whichever concrete class each element actually is (Circle, Triangle,
     * Rectangle, or Pentagon), so each row shows that shape's own
     * dimensions.
     */
    private static void printShapes(Shape[] list) {
        System.out.printf("%-16s%-16s%-32s%-16s%-16s\n",
                "Shape", "Color", "Dimensions", "Area", "Perimeter");
        for (Shape s : list) {
            System.out.println(s);
        }
    }

    /**
     * Static helper (part of the assignment's required output, not of the
     * Shape hierarchy itself): averages getPerimeter() across every
     * element of list. Like printShapes(), it calls getPerimeter()
     * polymorphically -- dynamic binding picks the correct override for
     * each shape's actual runtime type.
     */
    public static double getAveragePerimeter(Shape[] list) {
        double total = 0;
        for (Shape s : list) {
            total += s.getPerimeter();
        }
        return total / list.length;
    }
}
