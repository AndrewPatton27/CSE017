import java.util.InputMismatchException;

// thrown when account data doesn't match the format we expect (bad account
// number, bad number/balance token, or an investment type that isn't
// Growth/Property/Shares). extends InputMismatchException so it's unchecked
public class BadFormatException extends InputMismatchException {

    public BadFormatException() {
        super();
    }

    public BadFormatException(String message) {
        super(message);
    }
}
