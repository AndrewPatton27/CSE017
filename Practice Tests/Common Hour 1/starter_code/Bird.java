/**
 * Abstract class Bird subtype of Animal and CanFly
 */
public abstract class Bird extends Animal implements CanFly{
    // data members
    private int incubation;
    private int flyingSpeed;
    
    /**
     * Constructor with 5 parameters
     * @param t for the tag
     * @param n for the name
     * @param w for the weight
     * @param inc for the incubation period
     * @param fs for the flying speed
     */
    protected Bird(String t, String n, double w, int inc, int fs){
        super(t, n, w);
        flyingSpeed = fs;
        incubation = inc;
    }
    /**
     * Getter for the flying speed
     * @return the flying speed of the bird
     */
    public int getFlyingSpeed(){
        return flyingSpeed;
    }
    /**
     * Getter for the incubation period
     * @return the incubation period of the bird
     */
    public int getIncubation(){
        return incubation;
    }
    /**
     * Stter for the flying speed
     * @param fs the new value for the flying speed
     */
    public void setFlyingSpeed(int fs){ flyingSpeed = fs;}
    /**
     * Setter for the incubation period
     * @param inc the new value for the incubation period
     */
    public void setIncubation(int inc) { incubation = inc;}
    /**
     * toString
     * @return formatted string with tag, name, weight, incubation period, and flying speed
     */
    public String toString(){
        return String.format("%s\t%-10d\t%-10d", super.toString(),  incubation, flyingSpeed);
    }
}