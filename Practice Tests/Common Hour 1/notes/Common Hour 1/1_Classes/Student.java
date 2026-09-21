/**
 * SUBCLASS template #1 - "Student IS-A Person"  ->  extends
 * Copy and rename for any subclass problem.
 *
 * Rules to remember:
 *   - A class can extend only ONE class.
 *   - super(...) must be the FIRST statement in a subclass constructor.
 *   - Private fields of the superclass are NOT directly accessible -> use getters.
 *   - Must override every abstract method from the superclass (or be abstract itself).
 */
public class Student extends Person {
    private double gpa;
    private String major;

    public Student() {
        super();                 // calls Person()
        gpa = 0.0;
        major = "Undeclared";
    }

    public Student(String name, int id, double gpa, String major) {
        super(name, id);         // calls Person(String, int) - must be first line
        this.gpa = gpa;
        this.major = major;
    }

    public double getGpa()   { return gpa; }
    public String getMajor() { return major; }
    public void setGpa(double gpa)     { this.gpa = gpa; }
    public void setMajor(String major) { this.major = major; }

    // Required: implement the abstract method
    @Override
    public String getRole() { return "Student"; }

    // Override + reuse the parent's version with super.method()
    @Override
    public String toString() {
        return super.toString() + String.format(" %5.2f %-10s", gpa, major);
    }

    // A method that exists ONLY in Student (you need a downcast to call it
    // through a Person variable - see ClassesDemo)
    public boolean isHonors() { return gpa >= 3.5; }
}
