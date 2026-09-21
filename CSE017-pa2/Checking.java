// checking account, no extra fields beyond BankAccount, no interest
public class Checking extends BankAccount {

    public Checking(String owner, double balance) {
        super(owner, balance);
    }

    public Checking(long number, String owner, double balance) throws BadFormatException {
        super(number, owner, balance);
    }

    @Override
    public String fileString() {
        return String.format("Checking,%d,%s,%s", getNumber(), getOwner(), getBalance());
    }

    @Override
    public String toString() {
        return String.format("%-16s", "Checking") + super.toString();
    }
}
