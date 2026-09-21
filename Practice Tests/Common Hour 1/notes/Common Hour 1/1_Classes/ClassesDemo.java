/**
 * TOPIC 1 driver - instantiating objects, arrays of objects, polymorphism.
 * Compile & run:   javac *.java   then   java ClassesDemo
 */
public class ClassesDemo {
    public static void main(String[] args) {
        // ---- Instantiating: ClassName var = new ClassName(args);
        Student s1 = new Student("Drew", 101, 3.6, "CSB");
        Student s2 = new Student();                       // no-arg constructor
        Employee e1 = new Employee("Alex", 202, 55000);

        // Person p = new Person();   // COMPILE ERROR: Person is abstract

        // ---- Using getters / setters
        s2.setName("Sam");
        s2.setId(102);
        s2.setGpa(2.9);
        System.out.println(s2.getName() + " has GPA " + s2.getGpa());

        // ---- Array of the SUPER type holds any subclass (polymorphism)
        Person[] people = { s1, s2, e1, new Employee("Jordan", 203, 72000) };

        System.out.println("\n--- All people (dynamic binding picks each toString) ---");
        for (Person p : people) {
            System.out.println(p);            // println calls toString() automatically
        }

        // ---- Calling a subclass-only method: check with instanceof, then downcast
        System.out.println("\n--- Honors students ---");
        for (Person p : people) {
            if (p instanceof Student) {
                Student s = (Student) p;
                if (s.isHonors()) System.out.println(s.getName());
            }
        }

        // ---- equals vs ==
        Student copy = new Student("Drew", 101, 3.6, "CSB");
        System.out.println("\ns1 == copy      : " + (s1 == copy));       // false (different objects)
        System.out.println("s1.equals(copy) : " + s1.equals(copy));      // true  (same name & id)

        // ---- Reusable helper: total of a numeric field across an array
        System.out.printf("Total salary: $%,.2f%n", totalSalary(people));
    }

    /** Reusable pattern: loop over super-type array, act only on one subtype. */
    public static double totalSalary(Person[] list) {
        double total = 0;
        for (Person p : list) {
            if (p instanceof Employee) total += ((Employee) p).getSalary();
        }
        return total;
    }
}
