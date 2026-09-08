/**
 * Represents a checking account. Adds no additional fields to
 * {@link BankAccount} beyond number, owner, and balance.
 */
public class Checking extends BankAccount {

    /**
     * Creates a checking account with an auto-generated account number.
     *
     * @param owner   name of the account owner
     * @param balance starting balance of the account
     */

    public Checking(String owner, double balance) {
        super(owner, balance);
    }

    /**
     * Creates a checking account with an explicit account number.
     *
     * @param number  the account number; must be exactly 10 digits
     * @param owner   name of the account owner
     * @param balance starting balance of the account
     * @throws BadFormatException if the super constructor throws it
     *         (i.e. {@code number} is not exactly 10 digits)
     */
    public Checking(long number, String owner, double balance) throws BadFormatException {
        super(number, owner, balance);
    }

    /**
     * @return a comma-separated line describing this checking account,
     *         in the format: {@code Checking,number,owner,balance}
     */
    @Override
    public String fileString() {
        return String.format("Checking,%d,%s,%s", getNumber(), getOwner(), getBalance());
    }

    /**
     * @return a human-readable, formatted description of this checking account
     */
    @Override
    public String toString() {
        return String.format("%-16s", "Checking") + super.toString();
    }
}
