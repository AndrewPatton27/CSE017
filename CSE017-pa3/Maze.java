import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

// a maze of characters: 'S' start, 'E' exit, '.' free way, '#' wall
public class Maze implements Cloneable {

    private char[][] maze;
    private int rows;
    private int cols;

    // counts the recursive calls made by findPath and findAllPaths
    public static long recursions = 0;

    // creates the two dimensional array maze with the given dimensions
    // Big-O: O(n)
    public Maze(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
        maze = new char[rows][cols];
    }

    // reads (rows x cols) characters from the file into maze (row-major format)
    // Big-O: O(n)
    public void load(String filename) {
        try {
            Scanner input = new Scanner(new File(filename));
            for (int r = 0; r < rows; r++) {
                String line = input.nextLine().replace(" ", "");
                for (int c = 0; c < cols; c++) {
                    maze[r][c] = line.charAt(c);
                }
            }
            input.close();
        } catch (FileNotFoundException e) {
            System.out.println("File " + filename + " not found.");
        }
    }

    // returns the character at row, col, throws ArrayIndexOutOfBoundsException if out of bounds
    // Big-O: O(1)
    public char getPoint(int row, int col) throws ArrayIndexOutOfBoundsException {
        if (!isValid(new Coordinate(row, col))) {
            throw new ArrayIndexOutOfBoundsException("Point (" + row + "," + col + ") is out of bounds for a " + rows + "x" + cols + " maze");
        }
        return maze[row][col];
    }

    // sets the location row, col to c, throws ArrayIndexOutOfBoundsException if out of bounds
    // Big-O: O(1)
    public void setPoint(int row, int col, char c) throws ArrayIndexOutOfBoundsException {
        if (!isValid(new Coordinate(row, col))) {
            throw new ArrayIndexOutOfBoundsException("Point (" + row + "," + col + ") is out of bounds for a " + rows + "x" + cols + " maze");
        }
        maze[row][col] = c;
    }

    // true if the coordinate is within the bounds of the maze
    // Big-O: O(1)
    private boolean isValid(Coordinate c) {
        return c.row() >= 0 && c.row() < rows && c.col() >= 0 && c.col() < cols;
    }

    // returns the path from start to end, null if no path was found
    // Big-O: O(4^n)
    /**
 * @breif
 * @params
 * @return
 */
    public Path findPath(Coordinate start, Coordinate end) {
        recursions = 0;
        Path currentPath = new Path();
        boolean[][] visited = new boolean[rows][cols];
        if (findPath(start, end, currentPath, visited)) {
            return currentPath;
        }
        return null;
    }

    // recursive helper: finds a single path from current to end, the path is returned in currentPath
    // Big-O: O(4^n)
    private boolean findPath(Coordinate current, Coordinate end, Path currentPath, boolean[][] visited) {
        recursions++;
        // base cases
        if (!isValid(current)) {
            return false;
        }
        if (getPoint(current.row(), current.col()) == '#') {
            return false;
        }
        if (visited[current.row()][current.col()]) {
            return false;
        }
        if (current.equals(end)) {
            currentPath.add(current);
            return true;
        }
        visited[current.row()][current.col()] = true;
        currentPath.add(current);
        // right
        if (findPath(new Coordinate(current.row(), current.col() + 1), end, currentPath, visited)) {
            return true;
        }
        // left
        if (findPath(new Coordinate(current.row(), current.col() - 1), end, currentPath, visited)) {
            return true;
        }
        // up
        if (findPath(new Coordinate(current.row() - 1, current.col()), end, currentPath, visited)) {
            return true;
        }
        // down
        if (findPath(new Coordinate(current.row() + 1, current.col()), end, currentPath, visited)) {
            return true;
        }
        // no direction worked, backtrack: remove from the path and unmark as visited
        currentPath.remove();
        visited[current.row()][current.col()] = false;
        return false;
    }

    // returns the list of all possible paths from start to end
    // Big-O: O(4^n)
    public Paths findAllPaths(Coordinate start, Coordinate end) {
        recursions = 0;
        Paths allPaths = new Paths();
        boolean[][] visited = new boolean[rows][cols];
        findAllPaths(start, end, allPaths, visited, new Path());
        return allPaths;
    }

    // recursive helper: finds all paths from current to end, the paths found are returned in allPaths
    // Big-O: O(4^n)
    private void findAllPaths(Coordinate current, Coordinate end, Paths allPaths, boolean[][] visited, Path currentPath) {
        recursions++;
        // base cases
        if (!isValid(current)) {
            return;
        }
        if (getPoint(current.row(), current.col()) == '#') {
            return;
        }
        if (visited[current.row()][current.col()]) {
            return;
        }
        currentPath.add(current);
        visited[current.row()][current.col()] = true;
        if (current.equals(end)) {
            // store a copy, the current path keeps changing as we backtrack
            allPaths.addPath(currentPath.clone());
        } else {
            findAllPaths(new Coordinate(current.row(), current.col() + 1), end, allPaths, visited, currentPath);
            findAllPaths(new Coordinate(current.row(), current.col() - 1), end, allPaths, visited, currentPath);
            findAllPaths(new Coordinate(current.row() - 1, current.col()), end, allPaths, visited, currentPath);
            findAllPaths(new Coordinate(current.row() + 1, current.col()), end, allPaths, visited, currentPath);
        }
        // backtrack so other paths can use this coordinate
        currentPath.remove();
        visited[current.row()][current.col()] = false;
    }

    // formatted string of the maze organized in rows and columns
    // Big-O: O(n)
    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                result.append(maze[r][c]).append("  ");
            }
            result.append("\n");
        }
        return result.toString();
    }

    // returns a deep copy of this maze
    // Big-O: O(n)
    @Override
    public Maze clone() {
        Maze copy = new Maze(rows, cols);
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                copy.maze[r][c] = maze[r][c];
            }
        }
        return copy;
    }

    // clones this maze, fills the cells of the given path with '*' in the clone, and displays the clone
    // Big-O: O(n)
    public void visualizePath(Path path) {
        Maze copy = clone();
        for (int i = 0; i < path.size(); i++) {
            Coordinate c = path.get(i);
            // keep the 'S' and 'E' markers visible
            if (copy.getPoint(c.row(), c.col()) == '.') {
                copy.setPoint(c.row(), c.col(), '*');
            }
        }
        System.out.println("Maze with solution path");
        System.out.println(copy);
    }
}

// Test case 5 discussion (performance for different sizes of mazes)
//
//   size     one path    all paths    total paths
//   3 x 3          17          157             12
//   4 x 4          25        4,349            184
//   5 x 5          45      326,397          8,512
//
// findPath stops as soon as it finds a path. In these open mazes the depth first search heads almost
// straight to the exit, so the number of recursions grows slowly (17, 25, 45), roughly in proportion
// to the number of cells. (In the worst case, a maze with many dead ends, findPath can take
// exponential time because cells are unmarked when it backtracks, which is why its bound is O(4^n).)
//
// findAllPaths cannot stop early. It backtracks and unmarks every cell so that it can find every
// simple path from start to end, and the number of paths grows exponentially with the size of the
// maze (12, 184, 8,512). The recursions grow with it (157, 4,349, 326,397): going from 3x3 to 4x4
// multiplies the work by about 28 and going from 4x4 to 5x5 by about 75, which is roughly 1.6 times
// more work for every extra cell. This is the exponential growth predicted by O(4^n) (n = number of
// cells), so findAllPaths quickly becomes impractical as the maze gets bigger while findPath stays fast.
