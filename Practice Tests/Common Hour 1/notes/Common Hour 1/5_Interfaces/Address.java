/**
 * A mutable object that will be a FIELD inside Student - this is what makes
 * shallow vs deep copy matter. Implements Cloneable so Student's deep copy
 * can call address.clone().
 */
public class Address implements Cloneable {
    private String city;

    public Address(String city) { this.city = city; }

    public String getCity() { return city; }
    public void setCity(String city) { this.city = city; }

    @Override
    public Address clone() {          // covariant return type (Address instead of Object)
        try {
            return (Address) super.clone();   // only String/primitive fields -> shallow is enough
        } catch (CloneNotSupportedException e) {
            return null;              // can't happen: we implement Cloneable
        }
    }

    @Override
    public String toString() { return city; }
}
