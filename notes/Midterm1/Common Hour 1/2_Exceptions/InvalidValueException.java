/**
 * TOPIC 2e - Creating an Exception Class (CHECKED)
 * ------------------------------------------------
 * extends Exception          -> CHECKED   (compiler forces catch-or-declare)
 * extends RuntimeException   -> UNCHECKED (see InvalidInputRuntimeException)
 *
 * Template: copy, rename (InvalidGPAException, NegativeAmountException,
 * InsufficientFundsException, ...) and change the default message.
 * Needs AT LEAST these two constructors:
 */
public class InvalidValueException extends Exception {

    public InvalidValueException() {
        super("Invalid value");            // default message
    }

    public InvalidValueException(String message) {
        super(message);                    // custom message -> e.getMessage()
    }
}
