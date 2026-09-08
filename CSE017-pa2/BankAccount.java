/**
 * Base class for every kind of bank account, extended by {@link Checking},
 * {@link Savings}, and {@link Investment}. Implements {@link Closeable} so an
 * account can report whether its balance is low enough to close, and
 * {@link Comparable} so accounts can be sorted by balance.
 */
public abstract class BankAccount implements Closeable, Comparable<BankAccount> {

    /** Unique account number, always exactly 10 digits. */
    private long number;

    /** Name of the account owner. */
    private String owner;

    /** Current balance, readable and writable by subclasses. */
    protected double balance;

    /** Next account number to hand out when one is not supplied. */
    private static long nextNumber = 1111111111L;

    /** An account may be closed once its balance drops to this amount or below. */
    private static final double CLOSEABLE_BALANCE = 100;

    /**
     * Creates an account, assigning it the next available account number.
     *
     * @param owner   name of the account owner
     * @param balance starting balance
     */
    public BankAccount(String owner, double balance) {
        this(nextNumber++, owner, balance);
    }

    /**
     * Creates an account with a given number, as when reading one back from
     * a file, and advances the auto-assigned number past it if needed.
     *
     * @param number  the account number, which must be exactly 10 digits
     * @param owner   name of the account owner
     * @param balance starting balance
     * @throws BadFormatException if the number is not exactly 10 digits
     */
    public BankAccount(long number, String owner, double balance) throws BadFormatException {
        if (! validAcctNumber(number)) {
            throw new BadFormatException("Invalid account number ( " + number + " ), must have 10 digits");
        }

        if (number >= nextNumber) { nextNumber = number + 1; }
        this.number = number;
        this.owner = owner;
        this.balance = balance;
    }

    /**
     * Tests an account number against the 10-digit format using a regex.
     *
     * @param n the account number to check
     * @return true if it is exactly 10 digits, false otherwise
     */
    protected boolean validAcctNumber(long n) {
        String number = Long.toString(n);

        return number.matches("\\d{10}");
    }

    /**
     * Returns the number identifying this account.
     *
     * @return the 10-digit account number
     */
    public long getNumber() {
        return number;
    }

    /**
     * Returns the name on this account.
     *
     * @return the owner's name
     */
    public String getOwner() {
        return owner;
    }

    /**
     * Returns how much money the account currently holds.
     *
     * @return the current balance
     */
    public double getBalance() {
        return balance;
    }

    /**
     * Changes the name on this account.
     *
     * @param o the new owner name
     */
    public void setOwner(String o) {
        owner = o;
    }

    /**
     * Adds money to the balance.
     *
     * @param amount the amount to deposit
     */
    public void deposit(double amount) {
        balance += amount;
    }

    /**
     * Takes money out of the balance, refusing any withdrawal that would
     * empty or overdraw the account.
     *
     * @param amount the amount to withdraw
     * @throws IllegalTransactionException if {@code amount} is negative, or is
     *         greater than or equal to the current balance
     */
    public void withdraw(double amount) throws IllegalTransactionException {
        if (amount < 0) {
            throw new IllegalTransactionException("Withdrawal failed. The amount must be positive.");
        }
        if (amount >= balance) {
            throw new IllegalTransactionException("Withdrawal failed. Not enough credit in the account.");
        }
        balance -= amount;
    }

    /**
     * Reports whether this account's balance has fallen low enough to close.
     *
     * @return true if the balance is $100 or less, false otherwise
     */
    @Override
    public boolean isCloseable() {
        return balance <= CLOSEABLE_BALANCE;
    }

    /**
     * Orders accounts by balance, smallest first, so an array of them can be
     * sorted with {@code Arrays.sort}.
     *
     * @param other the account to compare against
     * @return negative, zero, or positive as this balance is less than, equal
     *         to, or greater than the other account's
     */
    @Override
    public int compareTo(BankAccount other) {
        return Double.compare(balance, other.getBalance());
    }

    /**
     * Builds this account's CSV line for the accounts file. Each subclass
     * supplies its own type name and extra fields.
     *
     * @return a comma-separated line describing this account
     */
    public abstract String fileString();

    /**
     * Builds the number, owner, and balance portion of a display row, which
     * subclasses prefix with the account type.
     *
     * @return the shared part of the account's display line
     */
    public String toString(){
        return String.format("%-10d\t%-30s\t$%-10.2f",number, owner, balance);
    }
}
