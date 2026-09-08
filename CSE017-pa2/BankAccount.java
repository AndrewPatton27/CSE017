/**
 * Abstract base class representing a generic bank account. Concrete bank
 * account types ({@link Checking}, {@link Savings}, {@link Investment})
 * extend this class. Implements {@link Closeable} (an account is closeable
 * when its balance is $100 or less) and {@link Comparable} so that accounts
 * can be sorted (by balance) using {@code java.util.Arrays.sort}.
 */
public abstract class BankAccount implements Closeable, Comparable<BankAccount> {

    /** Unique account number; must be exactly 10 digits when supplied explicitly. */
    private long number;

    /** Name of the account owner. */
    private String owner;

    /** Current balance of the account. Accessible to subclasses. */
    protected double balance;

    /** Next account number to auto-assign, starting at 1111111111. */
    private static long nextNumber = 1111111111L;

    /**
     * Creates a bank account with an auto-generated account number.
     *
     * @param owner   name of the account owner
     * @param balance starting balance of the account
     */
    public BankAccount(String owner, double balance) {
        this(nextNumber++, owner, balance);
    }

    /**
     * Creates a bank account with an explicit account number, e.g. when
     * reading accounts back in from a file.
     *
     * @param number  the account number; must be exactly 10 digits
     * @param owner   name of the account owner
     * @param balance starting balance of the account
     * @throws BadFormatException if {@code number} is not made up of exactly
     *         10 digits (validated with a regex via {@code matches()})
     */
    public BankAccount(long number, String owner, double balance) throws BadFormatException {
        // throw BadFormatException if invalid, otherwise assign fields and
        if (! validAcctNumber(number)) {
            throw new BadFormatException("Invalid account number ( " + number + " ), must have 10 digits");
        }

        // update nextNumber if needed
        if (number >= nextNumber) { nextNumber = number + 1; }
        this.number = number;
        this.owner = owner;
        this.balance = balance;
    }

    /**
     * Checks that an account number is made of exactly 10 digits.
     *
     * @param n the account number to validate
     * @return true if the number has exactly 10 digits, false otherwise
     */
    protected boolean validAcctNumber(long n) {
        String number = Long.toString(n);

        return number.matches("\\d{10}");
    }

    /**
     * @return the account number
     */
    public long getNumber() {
        return number;
    }

    /**
     * @return the owner's name
     */
    public String getOwner() {
        return owner;
    }

    /**
     * @return the current balance
     */
    public double getBalance() {
        return balance;
    }

    /**
     * Sets the account number.
     *
     * @param n the new account number
     */
    public void setNumber(long n) {
        number = n;
    }

    /**
     * Sets the owner's name.
     *
     * @param o the new owner name
     */
    public void setOwner(String o) {
        owner = o;
    }

    /**
     * Deposits the given amount into the account.
     *
     * @param amount the amount to deposit
     */
    public void deposit(double amount) {
        balance += amount;
    }

    /**
     * Withdraws the given amount from the account.
     *
     * @param amount the amount to withdraw
     * @throws IllegalTransactionException if {@code amount} is greater than
     *         or equal to the current balance
     */
    public void withdraw(double amount) throws IllegalTransactionException {
        if (amount >= balance) {
            throw new IllegalTransactionException("Withdrawal failed. Not enough credit in the account.");
        }
        balance -= amount;
    }

    /**
     * Determines whether this account is eligible for closure.
     *
     * @return true if the balance is less than or equal to $100, false otherwise
     */
    @Override
    public boolean isCloseable() {
        return balance <= 100;
    }

    /**
     * Compares this account to another by balance, so accounts can be
     * sorted with {@code java.util.Arrays.sort}.
     *
     * @param other the other account to compare to
     * @return a negative integer, zero, or a positive integer as this
     *         account's balance is less than, equal to, or greater than
     *         {@code other}'s balance
     */
    @Override
    public int compareTo(BankAccount other) {
        if (balance < other.getBalance()) {
            return -1;
        } else if (balance > other.getBalance()) {
            return 1;
        }
        return 0;
    }

    /**
     * Builds the single CSV-formatted line (matching the format of
     * accounts.txt) used to write this account to a file. Each subclass
     * formats its own type-specific fields.
     *
     * @return a comma-separated line describing this account
     */
    public abstract String fileString();

    /**
     * @return a human-readable, formatted description of this account
     */
    public String toString(){
        return String.format("%-10d\t%-30s\t$%-10.2f",number, owner, balance);
    }
}
