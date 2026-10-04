// a single path through the maze, stored as a manually managed array of coordinates
public class Path {

    private Coordinate[] path;
    private int size;

    // creates the array with capacity 200 and sets size to 0
    public Path() {
        // TODO
    }

    public int size() {
        // TODO
        return 0;
    }

    // adds the coordinate at index size and increments size
    public void add(Coordinate c) {
        // TODO
    }

    // removes the coordinate at index size-1 by decrementing size
    public void remove() {
        // TODO
    }

    // returns the coordinate at index, throws ArrayIndexOutOfBoundsException if index is not valid
    public Coordinate get(int index) {
        // TODO
        return null;
    }

    // returns a deep copy of this path
    @Override
    public Path clone() {
        // TODO
        return null;
    }

    @Override
    public String toString() {
        // TODO
        return null;
    }
}
