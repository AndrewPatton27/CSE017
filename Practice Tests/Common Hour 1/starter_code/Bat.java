/**
 * Class Bat is a subtype of Mammal and CanFly
 */
public class Bat extends Mammal implements CanFly{
    // data member
    private int flyingSpeed;
    /**
     * Constructor with 5 parameters
     * @param t for the tag
     * @param n for the name
     * @param w for the weight
     * @param g for the gestation period
     * @param fs for the flying speed
     */
    public Bat(String t, String n, double w, int g, int fs){
        super(t, n, w, g);
        flyingSpeed = fs;
    }
    /**
     * Getter for the flying speed
     * @return the fyling speed of the bat
     */
    public int getFlyingSpeed(){ return flyingSpeed;}
    /**
     * Setter for the flying speed
     * @param fs teh new value for the flying speed
     */
    public void setFlyingSpeed(int fs){ flyingSpeed = fs;}
    /**
     * toString
     * @return formatted string with type, tag, name, weight, gestation period, and flying speed
     */
    public String toString(){
        return String.format("%-15s\t%s\t%-10d","Bat", super.toString(), flyingSpeed);
    }
    /**
     * Method to clone a Bat object
     * @return a deep copy of this object
     */
    public Object clone(){
        return new Bat(getTag(), getName(), getWeight(), getGestation(), flyingSpeed);
    }
    /**
     * Implementation of flies from the interface CanFly
     * @return formatted string with the type, name, and flying speed of the bat
     */
    public String flies(){
        return String.format("%-15s\t%-20s\t%-10d", "Bat", getName(), getFlyingSpeed());
    }
}