/**
 * TOPIC 1 - Creating / Instantiating / Extending Classes
 * ------------------------------------------------------
 * Person = SUPERCLASS template (abstract so it can't be instantiated directly).
 * Copy this file and rename "Person" + its fields for any "base class" problem
 * (Shape, Vehicle, Account, Employee, Animal, ...).
 *
 * Checklist for a well-formed class (what graders look for):
 *   1. private data fields          (encapsulation)
 *   2. no-arg constructor + full-arg constructor
 *   3. getters / setters            (setters can validate)
 *   4. toString()                   (override from Object)
 *   5. equals(Object)               (override from Object, uses instanceof)
 */
public abstract class Person {
    // ---- 1. Data fields: private (use protected only if subclasses must touch them directly)
    private String name;
    private int id;

    // ---- 2. Constructors
    // No-arg constructor: default values. Java calls this automatically when a
    // subclass constructor doesn't call super(...) explicitly.
    public Person() {
        this("none", 0);           // "this(...)" = call another constructor of THIS class
    }

    public Person(String name, int id) {
        this.name = name;          // "this.name" = field, "name" = parameter
        this.id = id;
    }

    // ---- 3. Getters / setters
    public String getName() { return name; }
    public int getId()      { return id; }

    public void setName(String name) { this.name = name; }
    public void setId(int id) {
        if (id >= 0) this.id = id;  // setters can validate input
    }

    // ---- Abstract method: NO body. Every concrete subclass MUST override it.
    public abstract String getRole();

    // ---- 4. toString - subclasses call super.toString() and add their own fields
    @Override
    public String toString() {
        return String.format("%-10s %-15s %5d", getRole(), name, id);
    }

    // ---- 5. equals - parameter type MUST be Object to override (not Person)
    @Override
    public boolean equals(Object o) {
        if (o instanceof Person) {          // safe downcast check
            Person p = (Person) o;          // explicit downcast
            return this.id == p.id && this.name.equals(p.name);
        }
        return false;
    }
}
