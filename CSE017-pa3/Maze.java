import java.io.FileNotFoundException;

// a maze of characters: 'S' start, 'E' exit, '.' free way, '#' wall or closed way
public class Maze {

    private char[][] maze;
    private int rows;
    private int cols;

    // counts the recursive calls made by findPath and findAllPaths
    public static int recursions = 0;

    // creates the two dimensional array maze with the given dimensions
    public Maze(int rows, int cols) {
        // TODO
    }

    // reads (rows x cols) characters from the file into maze (row-major format)
    // Big-O: TODO
    public void load(String filename) throws FileNotFoundException {
        // TODO
    }

    // returns the character at row, col, throws ArrayIndexOutOfBoundsException if out of bounds
    // Big-O: TODO
    public char getPoint(int row, int col) {
        // TODO
        return ' ';
    }

    // sets the location row, col to c, throws ArrayIndexOutOfBoundsException if out of bounds
    // Big-O: TODO
    public void setPoint(int row, int col, char c) {
        // TODO
    }

    // true if the coordinate is within the bounds of the maze
    // Big-O: TODO
    public boolean isValid(Coordinate c) {
        // TODO
        return false;
    }

    // returns the path from start to end, null if no path was found
    // Big-O: TODO
    public Path findPath(Coordinate start, Coordinate end) {
        // TODO
        return null;
    }

    // recursive helper: finds a single path from current to end, the path is returned in currentPath
    // Big-O: TODO
    private boolean findPath(Coordinate current, Coordinate end, Path currentPath, boolean[][] visited) {
        // TODO
        return false;
    }

    // returns the list of all possible paths from start to end
    // Big-O: TODO
    public Paths findAllPaths(Coordinate start, Coordinate end) {
        // TODO
        return null;
    }

    // recursive helper: finds all paths from current to end, the paths found are returned in allPaths
    // Big-O: TODO
    private void findAllPaths(Coordinate current, Coordinate end, Paths allPaths, boolean[][] visited, Path currentPath) {
        // TODO
    }

    // formatted string of the maze organized in rows and columns
    // Big-O: TODO
    @Override
    public String toString() {
        // TODO
        return null;
    }

    // returns a deep copy of this maze
    // Big-O: TODO
    @Override
    public Maze clone() {
        // TODO
        return null;
    }

    // clones this maze, fills the cells of the given path with '*' in the clone, and displays the clone
    // Big-O: TODO
    public void visualizePath(Path path) {
        // TODO
    }
}
