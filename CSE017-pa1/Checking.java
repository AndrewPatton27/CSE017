public class Checking extends BankAccount {

    public Checking(String owner, double balance) {
        super(owner, balance);
    }

    public Checking(long number, String owner, double balance) {
        super(number, owner, balance);
    }

    public String toString() {
        return String.format("%-16s", "Checking") + super.toString();
    }
}
