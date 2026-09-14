public class Student extends Person {
    private String major;

    public Student(int id, String name, String adress, String phoneNumber, String email, String major) {
        super(id, name, adress, phoneNumber, email);
        this.major = major;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    @Override
    public String toString() {
        return "\nMajor: " + major + "\n" + super.toString();
    }
    
}
