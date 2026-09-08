/**
 * Thrown when a transaction cannot legally be performed on an account, such
 * as withdrawing a negative amount or an amount that would empty or overdraw
 * the balance. Checked, since it extends {@code Exception}.
 */
public class IllegalTransactionException extends Exception {

    /**
     * Creates an IllegalTransactionException with a generic detail message.
     */
    public IllegalTransactionException() {
        this("Error: Illegal Transaction");
    }

    /**
     * Creates an IllegalTransactionException describing the refused transaction.
     *
     * @param message text explaining why the transaction is not allowed
     */
    public IllegalTransactionException(String message) {
        super(message);
    }
}
