/**
 * An investment account, which gains or loses value according to a risk
 * factor. Its type must be one of Growth, Property, or Shares.
 */
public class Investment extends BankAccount {

    /** Type of investment: Growth, Property, or Shares. */
    private String type;

    /**
     * Creates an investment account with an auto-generated account number.
     *
     * @param owner   name of the account owner
     * @param balance starting balance
     * @param type    Growth, Property, or Shares
     * @throws BadFormatException if the type is none of those three
     */
    public Investment(String owner, double balance, String type) throws BadFormatException {
        super(owner, balance);
        setType(type);
    }

    /**
     * Creates an investment account with an account number read from a file.
     *
     * @param number  the account number, which must be exactly 10 digits
     * @param owner   name of the account owner
     * @param balance starting balance
     * @param type    Growth, Property, or Shares
     * @throws BadFormatException if the number is not exactly 10 digits, or
     *         the type is none of those three
     */
    public Investment(long number, String owner, double balance, String type) throws BadFormatException {
        super(number, owner, balance);
        setType(type);
    }

    /**
     * Returns the kind of investment this account holds.
     *
     * @return Growth, Property, or Shares
     */
    public String getType() {
        return type;
    }

    /**
     * Changes the kind of investment this account holds, rejecting any value
     * outside the three permitted types.
     *
     * @param type Growth, Property, or Shares
     * @throws BadFormatException if the type is none of those three
     */
    public void setType(String type) throws BadFormatException {
        if (!type.equals("Growth") && !type.equals("Property") && !type.equals("Shares")) {
            throw new BadFormatException("Bad Investment type: \"" + type + "\", should be [Property|Growth|Shares]");
        }
        this.type = type;
    }

    /**
     * Applies a gain or loss to the balance: 5% profit when the risk is 0.5
     * or higher, otherwise a 2% loss.
     *
     * @param risk risk factor deciding profit or loss
     * @return the amount applied, positive for a profit and negative for a loss
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
     * Builds this account's CSV line for the accounts file.
     *
     * @return a line of the form {@code Investment,number,owner,balance,type}
     */
    @Override
    public String fileString() {
        return String.format("Investment,%d,%s,%s,%s", getNumber(), getOwner(), getBalance(), type);
    }

    /**
     * Builds a display row for this account, labeled with its type and
     * ending with its investment type.
     *
     * @return the account formatted as one aligned line of text
     */
    @Override
    public String toString() {
        return String.format("%-16s", "Investment") + super.toString()
                + "     " + String.format("%-10s", type);
    }
}
