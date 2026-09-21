/**
 * Abstract class Mammal subtype of Animal
 */
public abstract class Mammal extends Animal{
    // data member
    private int gestation;
    /**
     * Constructor with 4 parameters
     * @param t for tag
     * @param n for name
     * @param w for weight
     * @param g for the gestation period
     */
    protected Mammal(String t, String n, double w, int g){
        super(t, n, w);
        gestation = g;
    }
    /**
     * Getter for the gestation
     * @return the gestation period of this mammal
     */
    public int getGestation(){
        return gestation;
    }
    /**
     * Setter for the gestation period
     * @param g teh new value of the gestation period
     */
    public void setGestation(int g){
        gestation = g;
    }
    /**
     * toString
     * @return a formatted string with the tag, name, weight, and the gestation period
     */
    public String toString(){
        return String.format("%s\t%-10d", super.toString(), gestation);
    }
}