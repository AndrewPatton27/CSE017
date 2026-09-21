/**
 * Concrete class BlueJay subtype of Bird
 */
public class BlueJay extends Bird{
    /**
     * Constructor with 5 parameters
     * @param t for tag
     * @param n for name
     * @param w for weight
     * @param inc for incubation period
     * @param fs for flying speed
     */
    public BlueJay(String t, String n, double w, int inc, int fs){
        super(t, n, w, inc, fs);
    }
    /**
     * toString
     * @return formatted string with type, tag, name, weight, incubation period, and flying speed
     */
    public String toString(){
        return String.format("%-15s\t%s", "Blue Jay", super.toString());
    }
    /**
     * Method to clone a BlueJay
     * @return a deep copy of this object
     */
    public Object clone(){
        return new BlueJay(getTag(), getName(), getWeight(), getIncubation(), getFlyingSpeed());
    }
    /**
     * Implementation of flies() from the interface CanFly
     * @return a formatted string with the type, the name, and the flying speed
     */
    public String flies(){
        return String.format("%-15s\t%-20s\t%-10d", "Blue Jay", getName(), getFlyingSpeed());
    }
}