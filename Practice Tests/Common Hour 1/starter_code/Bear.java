/**
 * class Bear a subtype of the class Mammal
 */
public class Bear extends Mammal{
    // data member
    private int hibernation;
    /**
     * Constructor with 5 parameters
     * @param t for the tag
     * @param n for the name
     * @param w for the weight
     * @param g for the gestation period
     * @param h for the hiberbnation period
     */
    public Bear(String t, String n, double w, int g, int h){
        super(t, n, w, g);
        hibernation = h;
    }
    /**
     * Getter for the hibernation period
     * @return the hibernation period of the bear
     */
    public int getHibernation(){ return hibernation;}
    /**
     * Setter for the hibernation
     * @param h the new value for the hibernation period
     */
    public void setHibernation(int h){ hibernation = h;}
    /**
     * toString
     * @return formatted string with the type, tag, name, weight, gestation period, and hibernation
     */
    public String toString(){
        return String.format("%-15s\t%s\t%-10d", "Bear", super.toString(), hibernation);
    }
    /**
     * Method to clone a Bear object
     * @return a deep copy of this object
     */
    public Object clone(){
        return new Bear(getTag(), getName(), getWeight(), getGestation(), hibernation);
    }
}