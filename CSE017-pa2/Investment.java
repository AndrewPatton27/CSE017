// investment account, gains or loses value based on risk. type has to be
// Growth, Property, or Shares
public class Investment extends BankAccount {

    private String type;

    public Investment(String owner, double balance, String type) throws BadFormatException {
        super(owner, balance);
        setType(type);
    }

    public Investment(long number, String owner, double balance, String type) throws BadFormatException {
        super(number, owner, balance);
        setType(type);
    }

    public String getType() {
        return type;
    }

    // rejects anything that isn't Growth, Property, or Shares
    public void setType(String type) throws BadFormatException {
        if (!type.equals("Growth") && !type.equals("Property") && !type.equals("Shares")) {
            throw new BadFormatException("Bad Investment type: \"" + type + "\", should be [Property|Growth|Shares]");
        }
        this.type = type;
    }

    // 5% gain if risk is 0.5 or higher, otherwise a 2% loss
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

    @Override
    public String fileString() {
        return String.format("Investment,%d,%s,%s,%s", getNumber(), getOwner(), getBalance(), type);
    }

    @Override
    public String toString() {
        return String.format("%-16s", "Investment") + super.toString()
                + "     " + String.format("%-10s", type);
    }
}
