/**
 * TOPIC 2e/2f - Creating an Exception Class (UNCHECKED)
 * Only difference from the checked version: extends RuntimeException.
 * Methods that throw this do NOT need a throws clause or try-catch.
 */
public class InvalidInputRuntimeException extends RuntimeException {

    public InvalidInputRuntimeException() {
        super("Invalid input");
    }

    public InvalidInputRuntimeException(String message) {
        super(message);
    }
}
