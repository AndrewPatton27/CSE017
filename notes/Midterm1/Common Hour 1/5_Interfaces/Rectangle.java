/** Second implementing class - only Shape, so it is NOT Drawable. */
public class Rectangle implements Shape {
    private double width, height;

    public Rectangle(double width, double height) {
        this.width = width;
        this.height = height;
    }

    @Override public double getArea()      { return width * height; }
    @Override public double getPerimeter() { return 2 * (width + height); }

    // Rectangle-only method: needs a DOWNCAST to call through a Shape variable
    public boolean isSquare() { return width == height; }

    @Override
    public String describe() {                    // overriding the default method
        return "Rectangle " + width + "x" + height;
    }

    @Override
    public String toString() { return "Rectangle(" + width + "x" + height + ")"; }
}
