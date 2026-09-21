/**
 * Concrete class HummingBird a subtype of Bird
 */
public class HummingBird extends Bird{
    /**
     * Constructor with 5 parameters
     * @param t for tag
     * @param n for name
     * @param w for weight
     * @param inc for incubation period
     * @param fs for flying speed
     */
    public HummingBird(String t, String n, double w, int inc, int fs){
        super(t, n, w, inc, fs);
    }
    /**
     * toString
     * @return formatted string with the type, tag, name, weight, incubation period, and flying speed
     */
    public String toString(){
        return String.format("%-15s\t%s","HummingBird", super.toString());
    }
    /**
     * Methdo to clone a HummingBird object
     * @return a deep copy of this object
     */
    public Object clone(){
        return new HummingBird(getTag(), getName(), getWeight(), getIncubation(), getFlyingSpeed());
    }
    /**
     * Implements the method flies from the interface CanFly
     * @return a formatted string with the type, the name, and the flying speed
     */
    public String flies(){
        return String.format("%-15s\t%-20s\t%-10d", "HummingBird", getName(), getFlyingSpeed());
    }
}