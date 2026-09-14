public class Airplane {
	char[][] seatMap;
	int rows, cols;

	public Airplane() {
		this(9, 8);
	}

	public Airplane(int rows, int cols) {
		this.rows = rows;
		this.cols = cols;
		seatMap = new char[rows][cols];
		for (char[] r : seatMap) {
			for (int i = 0; i < r.length; i++) {
				r[i] = '.';
			}
		}
	}

	public void readMap(String filename) {}

	// Throws InvalidSeatException
	public boolean checkSeatNumber(String seatNumber) throws InvalidSeatException {
		if (seatNumber.matches("[1-9][A-H]")) {
			return true;
		}
		throw new InvalidSeatException();
	}

	public boolean reserveSeat(String seatNumber) {
		return true;
	}

	// Throws InvalidSeatException
	// public boolean freeSeat(String seatNumber) throws InvalidSeatException {
	// 	// return true;
	// 	// throw new InvalidSeatException();
	// }

	public boolean saveMap(String filename) {
		return true;
	}

	// Takes seatnumber
	// Returns row at index 0 and col at index 1
	public int[] getIndex(String seat) throws InvalidSeatException {
		if(!checkSeatNumber(seat)) {throw new InvalidSeatException();}

		int[] output = {Character.getNumericValue(seat.getIndex(0)), 2};
		return output;
	}

	public String toString() {
		String s = "ABCDEFGH\n";

		for (char[] r : seatMap) {
			for (char c : r) {
				s += c;
			}
			s += "\n";
		}
		return s;
	}
}