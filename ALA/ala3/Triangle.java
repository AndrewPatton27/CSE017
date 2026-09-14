public class Triangle extends Shape {

    private double side1, side2, side3;

    public Triangle() {
        super("None", "Triangle");
        side1 = 0.0;
        side2 = 0.0;
        side3 = 0.0;
    }

    public Triangle(String c, double s1, double s2, double s3) {
        super(c, "Triangle");
        side1 = s1;
        side2 = s2;
        side3 = s3;
    }

    public double getSide1() {
        return side1;
    }

    public double getSide2() {
        return side2;
    }

    public double getSide3() {
        return side3;
    }

    public void setSide1(double s1) {
        side1 = s1;
    }

    public void setSide2(double s2) {
        side2 = s2;
    }

    public void setSide3(double s3) {
        side3 = s3;
    }

    @Override
    public String toString() {
        return formatString();
    }

    public String getDimensions() {
        String dims = String.format("s1:%5.2f  s2:%5.2f  s3:%5.2f", side1, side2, side3);
        return dims;
    }


    @Override
    public double getArea() {
        double p = getPerimeter() / 2.0;
        return Math.sqrt(p * (p - side1) * (p - side2) * (p - side3));
    }

    @Override
    public double getPerimeter() {
        return side1+side2+side3;
    }

    @Override
    public Object clone() {
        return new Triangle(getColor(), side1, side2, side3);
    }

    @Override
    public void scale(double f) {
        side1 *= f;
        side2 *= f;
        side3 *= f;
    }
}
