// a single path through the maze, stored as a manually managed array of coordinates
public class Path {

    private Coordinate[] path;
    private int size;

    // creates the array with capacity 200 and sets size to 0
    public Path() {
        path = new Coordinate[200];
        size = 0;
    }

    public int size() {
        return size;
    }

    // adds the coordinate at index size and increments size
    public void add(Coordinate c) {
        path[size] = c;
        size ++;
    }

    // removes the coordinate at index size-1 by decrementing size
    public void remove() {
        size--;
    }

    // returns the coordinate at index, throws ArrayIndexOutOfBoundsException if index is not valid
    public Coordinate get(int index) throws ArrayIndexOutOfBoundsException {
        if (index < 0 || index >= size) {
            throw new ArrayIndexOutOfBoundsException("Index: " + index + " is not valid, valid indexes are 0 to " + (size - 1));
        }
        return path[index];
    }

    // returns a deep copy of this path
    // todo: can add path setter to make o(n)
    @Override
    public Path clone() {
        Path p = new Path();
        for (int i = 0; i < size; i++) {
            p.add(new Coordinate(path[i].row, path[i].col));
        }
        return p;
    }

    @Override
    public String toString() {
        String result = "";
        for (int i = 0; i < size; i++) {
            if (i > 0) {
                result += " -> ";
            }
            result += path[i];
        }
        return result;
    }
}
