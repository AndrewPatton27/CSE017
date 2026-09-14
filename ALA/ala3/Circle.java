public class Circle extends Shape {
    private double radius;

    public Circle() {
        super("None", "Circle");
        radius = 0.0;
    }

    public Circle(String c, double r) {
        super(c, "Circle");
        radius = r;
    }

    public double getRadius() {
        return radius;
    }

    public void setRadius(double r) {
        radius = r;
    }

    @Override
    public String toString() {
        return formatString();
    }

    public String getDimensions() {
        return String.format("r: %.2f", radius);
    }

    @Override
    public double getArea() {
        return Math.PI * radius * radius;
    }

    @Override
    public double getPerimeter() {
        return 2 * Math.PI * radius;
    }

    @Override
    public Object clone() {
        return new Circle(getColor(), radius);
    }

    @Override
    public void scale(double f) {
        radius *= f;
    }
}
