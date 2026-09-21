// base class for all account types (Checking, Savings, Investment extend this).
// implements Closeable and Comparable so accounts can be checked for closure and sorted
public abstract class BankAccount implements Closeable, Comparable<BankAccount> {

    private long number;
    private String owner;
    protected double balance;
    private static long nextNumber = 1111111111L;
    private static final double CLOSEABLE_BALANCE = 100;

    public BankAccount(String owner, double balance) {
        this(nextNumber++, owner, balance);
    }

    // used when loading an account from file with an existing number.
    // validates the number is 10 digits and bumps nextNumber past it if needed
    public BankAccount(long number, String owner, double balance) throws BadFormatException {
        if (! validAcctNumber(number)) {
            throw new BadFormatException("Invalid account number ( " + number + " ), must have 10 digits");
        }

        if (number >= nextNumber) { nextNumber = number + 1; }
        this.number = number;
        this.owner = owner;
        this.balance = balance;
    }

    // checks the account number is exactly 10 digits using regex
    protected boolean validAcctNumber(long n) {
        String number = Long.toString(n);

        return number.matches("\\d{10}");
    }

    public long getNumber() {
        return number;
    }

    public String getOwner() {
        return owner;
    }

    public double getBalance() {
        return balance;
    }

    public void setOwner(String o) {
        owner = o;
    }

    public void deposit(double amount) {
        balance += amount;
    }

    // can't withdraw a negative amount, or an amount >= the current balance
    public void withdraw(double amount) throws IllegalTransactionException {
        if (amount < 0) {
            throw new IllegalTransactionException("Withdrawal failed. The amount must be positive.");
        }
        if (amount >= balance) {
            throw new IllegalTransactionException("Withdrawal failed. Not enough credit in the account.");
        }
        balance -= amount;
    }

    @Override
    public boolean isCloseable() {
        return balance <= CLOSEABLE_BALANCE;
    }

    // sorts accounts by balance, smallest first
    @Override
    public int compareTo(BankAccount other) {
        return Double.compare(balance, other.getBalance());
    }

    public abstract String fileString();

    public String toString(){
        return String.format("%-10d\t%-30s\t$%-10.2f",number, owner, balance);
    }
}
