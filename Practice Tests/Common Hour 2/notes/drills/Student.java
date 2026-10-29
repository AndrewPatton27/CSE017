/** Student with natural ordering by id (Comparable) - same as the lecture. */
public class Student implements Comparable<Student> {
    private int id;
    private String name;
    private double gpa;

    public Student(int id, String name, double gpa) {
        this.id = id;
        this.name = name;
        this.gpa = gpa;
    }

    public int getID() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getGPA() {
        return gpa;
    }

    /** Natural ordering: by id, ascending. */
    @Override
    public int compareTo(Student s) {
        // TODO: write this method
        return 0;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof Student) {
            return id == ((Student) o).id;
        }
        return false;
    }

    @Override
    public int hashCode() {
        return id;
    }

    @Override
    public String toString() {
        return id + " " + name + " " + gpa;
    }
}
