import java.util.InputMismatchException;

/**
 * Exception thrown when data supplied to a {@link BankAccount} (or one of
 * its subclasses) does not match the format required by the assignment.
 * Examples: an account number that is not exactly 10 digits, an account
 * number token that cannot be parsed as a {@code long}, or an
 * {@link Investment} type that is not one of "Growth", "Property", or
 * "Shares".
 * <p>
 * Extends {@link InputMismatchException} as required by the assignment
 * write-up.
 */
public class BadFormatException extends InputMismatchException {

    /**
     * Creates a BadFormatException with no detail message.
     */
    public BadFormatException() {
        super();
    }

    /**
     * Creates a BadFormatException with the given detail message.
     *
     * @param message description of the formatting error
     */
    public BadFormatException(String message) {
        super(message);
    }
}
