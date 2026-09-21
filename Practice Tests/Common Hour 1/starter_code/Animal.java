/**
 * Abstract class Animal
 */
public abstract class Animal implements Comparable<Animal>, Cloneable{
    // Data members
    private String tag;
    private String name;
    private double weight;
    /**
     * Constructor with three parameters
     * @param t tag of the animal
     * @param n name of the animal
     * @param w weight of the animal
     */
    protected Animal(String t, String n, double w){
        tag = t;
        name = n;
        weight = w;
    }
    /**
     * Getter for the name
     * @return name of the animal
     */
    public String getName(){
        return name;
    }
    /**
     * Getter for the weight
     * @return weight of the animal
     */
    public double getWeight(){
        return weight;
    }
    /**
     * Getter for the tag
     * @return tag of the animal
     */
    public String getTag(){
        return tag;
    }
    /**
     * Setter for the name
     * @param n the new value for name
     */
    public void setName(String n){
        name = n;
    }
    /**
     * Setter for the weight
     * @param w the new value for weight
     */
    public void setWeight(double w){
        weight = w;
    }
    /**
     * Setter for the tag
     * @param t the new value for tag
     */
    public void setTag(String t){
        tag = t;
    }
    /**
     * toString()
     * @return formatted string with the attributes tag, name, and weight
     */
    public String toString(){
        return String.format("%-10s\t%-20s\t%-10s", tag, name, formatWeight());
    }
    /**
     * Format the weight
     * @return the weight of the animal with a unit (using the metric system)
     */
    private String formatWeight(){
        String s = "";
        if (weight < 1000){
            s = String.format("%10.2fg", weight);
        }
        else{
            s = String.format("%10.2fKg", weight/1000);
        }
        return s;
    }

    /**
     * Orders animals by weight
     * @param a the animal to compare to
     * @return negative, zero, or positive if this animal weighs less than, the same as, or more than a
     */
    @Override
    public int compareTo(Animal a){
        return ((Double) this.getWeight()).compareTo(a.getWeight());
    }

    /**
     * Deep copy of the animal, implemented by the concrete classes
     * @return a copy of this animal
     */
    @Override
    public abstract Object clone();
}