/**
 * Exception thrown when a transaction requested on a {@link BankAccount}
 * cannot be legally performed - specifically, when {@link BankAccount#withdraw(double)}
 * is called with an amount greater than or equal to the account balance.
 * <p>
 * Extends {@code Exception} (a checked exception) as required by the
 * assignment write-up.
 */
public class IllegalTransactionException extends Exception {

    /**
     * Creates an IllegalTransactionException with no detail message.
     */
    public IllegalTransactionException() {
        this("Error: Illegal Transaction");
    }

    /**
     * Creates an IllegalTransactionException with the given detail message.
     *
     * @param message description of why the transaction is illegal
     */
    public IllegalTransactionException(String message) {
        super(message);
    }
}
