/**
 * A checking account. Adds no fields beyond the number, owner, and balance
 * inherited from {@link BankAccount}, and earns no interest.
 */
public class Checking extends BankAccount {

    /**
     * Creates a checking account with an auto-generated account number.
     *
     * @param owner   name of the account owner
     * @param balance starting balance
     */
    public Checking(String owner, double balance) {
        super(owner, balance);
    }

    /**
     * Creates a checking account with an account number read from a file.
     *
     * @param number  the account number, which must be exactly 10 digits
     * @param owner   name of the account owner
     * @param balance starting balance
     * @throws BadFormatException if the number is not exactly 10 digits
     */
    public Checking(long number, String owner, double balance) throws BadFormatException {
        super(number, owner, balance);
    }

    /**
     * Builds this account's CSV line for the accounts file.
     *
     * @return a line of the form {@code Checking,number,owner,balance}
     */
    @Override
    public String fileString() {
        return String.format("Checking,%d,%s,%s", getNumber(), getOwner(), getBalance());
    }

    /**
     * Builds a display row for this account, labeled with its type.
     *
     * @return the account formatted as one aligned line of text
     */
    @Override
    public String toString() {
        return String.format("%-16s", "Checking") + super.toString();
    }
}
