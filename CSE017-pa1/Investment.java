public class Investment extends BankAccount {
    private String type; // "Property", "Growth", or "Shares"

    public Investment(String owner, double balance, String type) {
        super(owner, balance);
        this.type = type;
    }

    public Investment(long number, String owner, double balance, String type) {
        super(number, owner, balance);
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public double getProfitOrLoss(double risk) {
        double profitOrLoss;
        if (risk >= 0.5) {
            profitOrLoss = balance * 0.05;
        } else {
            profitOrLoss = -(balance * 0.02);
        }
        balance += profitOrLoss;
        return profitOrLoss;
    }

    public String toString() {
        return String.format("%-16s", "Investment") + super.toString()
                + "     " + String.format("%-10s", type);
    }
}
