public class Rectangle extends Shape {

    private double length;
    private double width;

    public Rectangle() {
        super("None", "Rectangle");
        length = 0.0;
        width  = 0.0;
    }

    public Rectangle(String c, double l, double w) {
        super(c, "Rectangle");
        length = l;
        width  = w;
    }

    public double getLength() {
        return length;
    }

    public double getWidth() {
        return width;
    }

    public void setLength(double l) {
        length = l;
    }

    public void setWidth(double w) {
        width = w;
    }

    @Override
    public String toString() {
        return formatString();
    }

    public String getDimensions() {
        String dims = String.format("l:%.2f  w:%.2f", length, width);
        return dims;
    }

    @Override
    public double getArea() {
        return length*width;
    }

    @Override
    public double getPerimeter() {
        return 2 * (length+width);
    }

    @Override
    public Object clone() {
        return new Rectangle(getColor(), length, width);
    }

    @Override
    public void scale(double f) {
        length *= f;
        width  *= f;
    }
}
