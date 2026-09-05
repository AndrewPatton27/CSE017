public class Savings extends BankAccount {
    private double yearlyInterestRate;

    public Savings(String owner, double balance, double yInterestRate) {
        super(owner, balance);
        this.yearlyInterestRate = yInterestRate;
    }

    public Savings(long number, String owner, double balance, double yInterestRate) {
        super(number, owner, balance);
        this.yearlyInterestRate = yInterestRate;
    }

    public double getYearlyInterest() {
        return yearlyInterestRate;
    }

    public double applyMonthlyInterest() {
        double interest = balance * (yearlyInterestRate / 100) / 12;
        deposit(interest);
        return interest;
    }

    public void setYearlyInterest(double y) {
        yearlyInterestRate = y;
    }

    public String toString() {
        return String.format("%-16s", "Savings") + super.toString()
                + "     " + String.format("%-5.2f", yearlyInterestRate);
    }
}
