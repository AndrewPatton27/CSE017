public class Pentagon extends Shape {

    private double side;

    public Pentagon() {
        super("None", "Pentagon");
        side = 0.0;
    }

    public Pentagon(String c, double s) {
        super(c, "Pentagon");
        side = s;
    }

    public double getSide() {
        return side;
    }

    public void setSide(double s) {
        side = s;
    }

    @Override
    public String toString() {
        return formatString();
    }

    public String getDimensions() {
        return String.format("s: %.2f", side);
    }

    @Override
    public double getArea() {
        return 0.25 * Math.sqrt(5 * (5 + 2 * Math.sqrt(5))) * side * side;
    }

    @Override
    public double getPerimeter() {
        return 5*side;
    }

    @Override
    public Object clone() {
        return new Pentagon(getColor(), side);
    }

    @Override
    public void scale(double f) {
        side *= f;
    }
}
