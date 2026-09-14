public class Faculty extends Employee {
    private String rank;

    public Faculty(int id, String name, String adress, String phoneNumber, String email, String position, double salary, String rank) {
        super(id, name, adress, phoneNumber, email, position, salary);
        this.rank = rank;
    }

    public String getRank() {
        return rank;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }

    @Override
    public String toString() {
        return super.toString() + "\nrank: " + rank;
    }
}
