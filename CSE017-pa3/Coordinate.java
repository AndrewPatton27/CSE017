// a point (row, col) in the maze
public class Coordinate {

    private int row;
    private int col;

    public Coordinate(int row, int col) {
        this.row = row;
        this.col = col;
    }

    public int row() {
        return row;
    }

    public int col() {
        return col;
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
