/**
 * A savings account, which earns interest on its balance at a fixed yearly
 * rate that can be applied one month at a time.
 */
public class Savings extends BankAccount {

    /** Yearly interest rate as a percentage, e.g. 9.25 for 9.25%. */
    private double yearlyInterestRate;

    /**
     * Creates a savings account with an auto-generated account number.
     *
     * @param owner         name of the account owner
     * @param balance       starting balance
     * @param yInterestRate yearly interest rate, as a percentage
     */
    public Savings(String owner, double balance, double yInterestRate) {
        super(owner, balance);
        this.yearlyInterestRate = yInterestRate;
    }

    /**
     * Creates a savings account with an account number read from a file.
     *
     * @param number        the account number, which must be exactly 10 digits
     * @param owner         name of the account owner
     * @param balance       starting balance
     * @param yInterestRate yearly interest rate, as a percentage
     * @throws BadFormatException if the number is not exactly 10 digits
     */
    public Savings(long number, String owner, double balance, double yInterestRate) throws BadFormatException {
        super(number, owner, balance);
        this.yearlyInterestRate = yInterestRate;
    }

    /**
     * Returns the interest rate applied to this account.
     *
     * @return the yearly interest rate, as a percentage
     */
    public double getYearlyInterest() {
        return yearlyInterestRate;
    }

    /**
     * Changes the interest rate applied to this account.
     *
     * @param y the new yearly interest rate, as a percentage
     */
    public void setYearlyInterest(double y) {
        yearlyInterestRate = y;
    }

    /**
     * Credits one month of interest, a twelfth of the yearly rate, to the
     * balance.
     *
     * @return the amount of interest deposited
     */
    public double applyMonthlyInterest() {
        double interest = balance * (yearlyInterestRate / 100) / 12;
        deposit(interest);
        return interest;
    }

    /**
     * Builds this account's CSV line for the accounts file.
     *
     * @return a line of the form {@code Savings,number,owner,balance,rate}
     */
    @Override
    public String fileString() {
        return String.format("Savings,%d,%s,%s,%s", getNumber(), getOwner(), getBalance(), yearlyInterestRate);
    }

    /**
     * Builds a display row for this account, labeled with its type and
     * ending with its interest rate.
     *
     * @return the account formatted as one aligned line of text
     */
    @Override
    public String toString() {
        return String.format("%-16s", "Savings") + super.toString()
                + "     " + String.format("%-5.2f", yearlyInterestRate);
    }
}
