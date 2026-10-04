/**
 * Generic class to model a pair of two values of any type
 */
public class Pair<E1, E2>{
    // Data members
    private E1 first;
    private E2 second;
    /**
     * Constructor
     * @param first the initial value of the data member first
     * @param second the initial value of the data member second
     */
    public Pair(E1 first, E2 second){
        this.first = first;
        this.second = second;
    }
    /**
     * Getter for first
     * @return the reference to the member first
     */
    public E1 getFirst(){ return first;}
    /**
     * Getter for second
     * @return the reference to the member second
     */
    public E2 getSecond(){ return second;}
    /**
     * Setter for first
     * @param first the new value of the member first
     */
    public void setFirst(E1 first){ this.first = first;}
    /**
     * Setter for second
     * @param second the new value of the member second
     */
    public void setSecond(E2 second){ this.second = second;}

    /**
     * toString method
     * @return a formatted string with the contents of the members first and second 
     * @override toString() from class Object
     */
    public String toString(){
        return "(" + first.toString() + ", " + second.toString() + ")"; 
    }
    /**
     * equals method
     * @param obj the object pair being compared to this pair
     * @return true if the two pairs are equal, false otherwise
     * @override equals(Object) from class Object
     */
    
    public boolean equals(Object obj){
        if(obj instanceof Pair){
            Pair<E1, E2> p = (Pair<E1, E2>) obj; // downcasting to Pair
            boolean eqFirst = this.first.equals(p.first);
            boolean eqSecond = this.second.equals(p.second);
            return eqFirst && eqSecond;
        }
        return false;
    }
}