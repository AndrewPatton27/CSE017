/**
 * TOPIC 5c - Comparable (compareTo) and Cloneable (clone: shallow vs deep)
 * ------------------------------------------------------------------------
 * Template for "make this class sortable and copyable".
 *
 * COMPARABLE<E>:  int compareTo(E other)
 *     < 0  -> this comes BEFORE other
 *     = 0  -> equal
 *     > 0  -> this comes AFTER other
 *   Enables  java.util.Arrays.sort(array)  and  Collections.sort(list).
 *
 * CLONEABLE: MARKER interface (no methods). It just gives permission for
 *   Object.clone() to work; without it super.clone() throws
 *   CloneNotSupportedException (a CHECKED exception).
 *
 *   SHALLOW copy = super.clone() only
 *       primitives & Strings -> independent (Strings are immutable anyway)
 *       arrays / object refs -> SHARED with the original (both point to same object)
 *   DEEP copy = super.clone() + clone every mutable reference field
 */
public class Student implements Comparable<Student>, Cloneable {
    private String name;
    private int id;
    private double gpa;
    private Address address;   // mutable object field
    private int[] scores;      // array field (arrays are objects too!)

    public Student(String name, int id, double gpa, Address address, int[] scores) {
        this.name = name;
        this.id = id;
        this.gpa = gpa;
        this.address = address;
        this.scores = scores;
    }

    public String getName()     { return name; }
    public int getId()          { return id; }
    public double getGpa()      { return gpa; }
    public Address getAddress() { return address; }
    public int[] getScores()    { return scores; }
    public void setName(String name) { this.name = name; }
    public void setId(int id)        { this.id = id; }

    // ---------------- Comparable ----------------
    // Natural order = by id (ascending). Swap in whichever version you need:
    @Override
    public int compareTo(Student other) {
        return this.id - other.id;                              // int field, ascending
        // return other.id - this.id;                           // int field, DESCENDING
        // return Double.compare(this.gpa, other.gpa);          // double field
        // return this.name.compareTo(other.name);              // String field (alphabetical)
        // Tie-breaker example:
        // int c = this.name.compareTo(other.name);
        // return (c != 0) ? c : this.id - other.id;
    }

    // ---------------- Cloneable: DEEP copy (the usual "correct" answer) ----------------
    @Override
    public Object clone() throws CloneNotSupportedException {
        Student copy = (Student) super.clone();   // copies all fields bit-for-bit (shallow)
        copy.address = this.address.clone();      // new Address object
        copy.scores = this.scores.clone();        // new array (int[] has its own clone())
        return copy;
    }

    // ---------------- SHALLOW copy (for comparison) ----------------
    public Student shallowCopy() throws CloneNotSupportedException {
        return (Student) super.clone();           // address & scores are SHARED
    }

    // equals using instanceof + downcast (5f/5g)
    @Override
    public boolean equals(Object o) {
        if (o instanceof Student) {
            Student s = (Student) o;
            return this.id == s.id;
        }
        return false;
    }

    @Override
    public String toString() {
        return String.format("%-6s id=%-4d gpa=%.1f city=%-10s scores=%s",
                name, id, gpa, address, java.util.Arrays.toString(scores));
    }
}
