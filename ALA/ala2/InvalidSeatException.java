public class InvalidSeatException extends Exception {
	public InvalidSeatException() {
		super("Invalid Seat Number");
	}

	public InvalidSeatException(String message) {
		super(message);
	}
}