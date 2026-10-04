// a point (row, col) in the maze
public class Coordinate {

    public int row;
    public int col;

    public Coordinate(int row, int col) {
        this.row = row;
        this.col = col;
    }

    // true if both coordinates have the same row and column
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Coordinate) {
            Coordinate o = (Coordinate) obj;
            return row == o.row && col == o.col;
        }
        return false;
    }

    @Override
    public String toString() {
        return "(" + row + "," + col + ")";
    }
}
