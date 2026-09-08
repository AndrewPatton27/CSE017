/**
 * Represents an investment account, which gains or loses value depending on
 * a risk factor and one of three investment types: "Growth", "Property", or
 * "Shares".
 */
public class Investment extends BankAccount {

    /** Type of investment: "Property", "Growth", or "Shares". */
    private String type;

    /**
     * Creates an investment account with an auto-generated account number.
     *
     * @param owner   name of the account owner
     * @param balance starting balance of the account
     * @param type    investment type; must be "Growth", "Property", or "Shares"
     * @throws BadFormatException if {@code type} is not one of the accepted values
     */
    public Investment(String owner, double balance, String type) throws BadFormatException {
        super(owner, balance);
        setType(type);
    }

    /**
     * Creates an investment account with an explicit account number.
     *
     * @param number  the account number; must be exactly 10 digits
     * @param owner   name of the account owner
     * @param balance starting balance of the account
     * @param type    investment type; must be "Growth", "Property", or "Shares"
     * @throws BadFormatException if the super constructor throws it
     *         (i.e. {@code number} is not exactly 10 digits), or if
     *         {@code type} is not one of the accepted values
     */
    public Investment(long number, String owner, double balance, String type) throws BadFormatException {
        super(number, owner, balance);
        setType(type);
    }

    /**
     * @return the investment type
     */
    public String getType() {
        return type;
    }

    /**
     * Sets the investment type.
     *
     * @param type the new investment type; must be "Growth", "Property", or "Shares"
     * @throws BadFormatException if {@code type} is not one of the accepted values
     */
    public void setType(String type) throws BadFormatException {
        if (!type.equals("Growth") && !type.equals("Property") && !type.equals("Shares")) {
            throw new BadFormatException("Bad Investment type: \"" + type + "\", should be [Property|Growth|Shares]");
        }
        this.type = type;
    }

    /**
     * Computes a profit (risk &gt;= 0.5) or loss (risk &lt; 0.5) on the
     * account balance and applies it to the balance.
     *
     * @param risk risk factor used to determine profit vs. loss
     * @return the amount of profit (positive) or loss (negative) applied
     */
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

    /**
     * @return a comma-separated line describing this investment account,
     *         in the format: {@code Investment,number,owner,balance,type}
     */
    @Override
    public String fileString() {
        return String.format("Investment,%d,%s,%s,%s", getNumber(), getOwner(), getBalance(), type);
    }

    /**
     * @return a human-readable, formatted description of this investment account
     */
    @Override
    public String toString() {
        return String.format("%-16s", "Investment") + super.toString()
                + "     " + String.format("%-10s", type);
    }
}
