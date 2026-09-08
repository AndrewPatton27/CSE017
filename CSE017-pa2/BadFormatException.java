import java.util.InputMismatchException;

/**
 * Thrown when account data does not match the required format: an account
 * number that is not exactly 10 digits, a number or balance token that will
 * not parse, or an investment type outside Growth, Property, and Shares.
 * Unchecked, since it extends {@link InputMismatchException}.
 */
public class BadFormatException extends InputMismatchException {

    /**
     * Creates a BadFormatException with no detail message.
     */
    public BadFormatException() {
        super();
    }

    /**
     * Creates a BadFormatException describing the formatting error.
     *
     * @param message text explaining what was wrong with the data
     */
    public BadFormatException(String message) {
        super(message);
    }
}
