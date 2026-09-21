// thrown when a transaction can't happen on an account (negative withdrawal,
// or withdrawing more than the balance). checked exception since it extends Exception
public class IllegalTransactionException extends Exception {

    public IllegalTransactionException() {
        this("Error: Illegal Transaction");
    }

    public IllegalTransactionException(String message) {
        super(message);
    }
}
