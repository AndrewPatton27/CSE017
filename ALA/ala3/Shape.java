public abstract class Shape implements Comparable<Shape>, Cloneable, Scalable {
    private String color;
    private String shapeName;

    protected Shape() {
        color = "None";
        shapeName = "Generic Shape";
    }

    protected Shape(String c, String name) {
        color = c;
        shapeName = name;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String c) {
        color = c;
    }

    public String getName() { return shapeName; }
    public void setName(String name) { shapeName = name; }

    @Override
    public String toString() {
        return String.format("%-16s%-16s", getName(), color);
    }

    protected String formatString() {
        return String.format("%-16s%-16s%-32s%-16.2f%-16.2f",
                getName(), getColor(), getDimensions(), getArea(), getPerimeter());
    }

    @Override
    public int compareTo(Shape s) {
        return Double.compare(getArea(), s.getArea());
    }

    public abstract double getArea();

    public abstract String getDimensions();

    public abstract double getPerimeter();

    public abstract Object clone();

    public abstract void scale(double f);
}
