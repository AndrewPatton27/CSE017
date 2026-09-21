/**
 * TOPIC 5a/5b - Defining an interface
 * ------------------------------------
 * - keyword "interface" instead of "class"
 * - methods are implicitly  public abstract   (no body)
 * - fields are implicitly   public static final (constants only)
 * - may also have  default  and  static  methods (with bodies)
 * - NO constructors, CANNOT be instantiated:   new Shape()  -> compile error
 * - a class IMPLEMENTS it (not extends) and must define every abstract method
 *   (or else the class must be declared abstract)
 *
 * Template: rename Shape / its methods for Payable, Drawable, Edible, Swimmable, ...
 */
public interface Shape {
    double PI_APPROX = 3.14159;            // constant (public static final automatically)

    double getArea();                       // abstract (public abstract automatically)
    double getPerimeter();

    // default method: inherited by implementing classes, can be overridden
    default String describe() {
        return String.format("%s with area %.2f", getClass().getSimpleName(), getArea());
    }

    // static method: called on the interface itself -> Shape.totalArea(arr)
    static double totalArea(Shape[] shapes) {
        double sum = 0;
        for (Shape s : shapes) sum += s.getArea();
        return sum;
    }
}
