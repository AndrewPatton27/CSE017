public class Testing {
    static Student student = new Student(123456789, "Paul Leister", "972 4th Street, Allentown", "610-331-7177", "paul.leister@example.com",  "CSE");
    static Employee employee = new Employee(987654321, "John Doe", "123 Main Street, Anytown", "555-123-4567", "john.doe@example.com", "Software Engineer", 75000.0);
    static Faculty faculty = new Faculty(111111111, "Dr. Jane Smith", "456 Oak Avenue, Somewhere", "555-987-6543", "jane.smith@example.com", "Professor", 90000.0, "Tenured");
    public static void main(String[] args) {
        Person[] people = new Person[3];
        people[0] = student;
        people[1] = employee;
        people[2] = faculty;

        printArray(people);
    }
    private static void printArray(Object[] list) {
        for (Object obj : list) {
            System.out.println(obj.toString());
            System.out.println();
        }
    }
}
