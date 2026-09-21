// savings account, earns interest on the balance over time
public class Savings extends BankAccount {

    private double yearlyInterestRate;

    public Savings(String owner, double balance, double yInterestRate) {
        super(owner, balance);
        this.yearlyInterestRate = yInterestRate;
    }

    public Savings(long number, String owner, double balance, double yInterestRate) throws BadFormatException {
        super(number, owner, balance);
        this.yearlyInterestRate = yInterestRate;
    }

    public double getYearlyInterest() {
        return yearlyInterestRate;
    }

    public void setYearlyInterest(double y) {
        yearlyInterestRate = y;
    }

    // adds one month of interest (yearly rate / 12) to the balance
    public double applyMonthlyInterest() {
        double interest = balance * (yearlyInterestRate / 100) / 12;
        deposit(interest);
        return interest;
    }

    @Override
    public String fileString() {
        return String.format("Savings,%d,%s,%s,%s", getNumber(), getOwner(), getBalance(), yearlyInterestRate);
    }

    @Override
    public String toString() {
        return String.format("%-16s", "Savings") + super.toString()
                + "     " + String.format("%-5.2f", yearlyInterestRate);
    }
}
