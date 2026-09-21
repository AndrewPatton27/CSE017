/**
 * Implementing interfaces (5a) - "implements", then define every method.
 * Also implements Comparable<Circle> (5c) so Circles can be sorted.
 */
public class Circle implements Shape, Drawable, Comparable<Circle> {
    private double radius;

    public Circle(double radius) { this.radius = radius; }

    public double getRadius() { return radius; }

    @Override public double getArea()      { return Math.PI * radius * radius; }
    @Override public double getPerimeter() { return 2 * Math.PI * radius; }
    @Override public void draw()           { System.out.println("Drawing a circle of radius " + radius); }

    @Override
    public int compareTo(Circle other) {
        return Double.compare(this.radius, other.radius);   // safe for doubles
    }

    @Override
    public String toString() { return "Circle(r=" + radius + ")"; }
}
