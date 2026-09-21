/**
 * SUBCLASS template #2 - "Employee IS-A Person"
 * Shows a second subclass so polymorphism has something to choose between.
 */
public class Employee extends Person {
    private double salary;

    public Employee() {
        super();
        salary = 0;
    }

    public Employee(String name, int id, double salary) {
        super(name, id);
        this.salary = salary;
    }

    public double getSalary() { return salary; }
    public void setSalary(double salary) { this.salary = salary; }

    @Override
    public String getRole() { return "Employee"; }

    @Override
    public String toString() {
        return super.toString() + String.format(" $%,10.2f", salary);
    }
}
