/**
 * Represents a savings account, which earns interest on its balance.
 */
public class Savings extends BankAccount {

    /** Yearly interest rate (as a percentage, e.g. 9.25 for 9.25%). */
    private double yearlyInterestRate;

    /**
     * Creates a savings account with an auto-generated account number.
     *
     * @param owner          name of the account owner
     * @param balance        starting balance of the account
     * @param yInterestRate  yearly interest rate (percentage)
     */
    public Savings(String owner, double balance, double yInterestRate) {
        super(owner, balance);
        this.yearlyInterestRate = yInterestRate;
    }

    /**
     * Creates a savings account with an explicit account number.
     *
     * @param number         the account number; must be exactly 10 digits
     * @param owner          name of the account owner
     * @param balance        starting balance of the account
     * @param yInterestRate  yearly interest rate (percentage)
     * @throws BadFormatException if the super constructor throws it
     *         (i.e. {@code number} is not exactly 10 digits)
     */
    public Savings(long number, String owner, double balance, double yInterestRate) throws BadFormatException {
        super(number, owner, balance);
        this.yearlyInterestRate = yInterestRate;
    }

    /**
     * @return the yearly interest rate (percentage)
     */
    public double getYearlyInterest() {
        return yearlyInterestRate;
    }

    /**
     * Sets the yearly interest rate.
     *
     * @param y the new yearly interest rate (percentage)
     */
    public void setYearlyInterest(double y) {
        yearlyInterestRate = y;
    }

    /**
     * Computes one month's worth of interest based on the yearly interest
     * rate and deposits it into the account.
     *
     * @return the amount of interest deposited
     */
    public double applyMonthlyInterest() {
        double interest = balance * (yearlyInterestRate / 100) / 12;
        deposit(interest);
        return interest;
    }

    /**
     * @return a comma-separated line describing this savings account, in
     *         the format: {@code Savings,number,owner,balance,interestRate}
     */
    @Override
    public String fileString() {
        return String.format("Savings,%d,%s,%s,%s", getNumber(), getOwner(), getBalance(), yearlyInterestRate);
    }

    /**
     * @return a human-readable, formatted description of this savings account
     */
    @Override
    public String toString() {
        return String.format("%-16s", "Savings") + super.toString()
                + "     " + String.format("%-5.2f", yearlyInterestRate);
    }
}
