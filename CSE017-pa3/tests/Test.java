public class Test {
    public static void main(String[] args){
        System.out.println("\nTest case 1: Finding one path in a maze (5 X 5)");
        Maze maze = new Maze(5, 5);
        maze.load("maze_1.txt");
        Coordinate start = new Coordinate(0,0);
        Coordinate end = new Coordinate(4,4);
        Path path = maze.findPath(start, end);
        System.out.println("Number of recursions to find one path: " + Maze.recursions);
        if(path == null || path.size() == 0){
            System.out.println("No path found");
        }
        else{
            maze.visualizePath(path);
        }
        System.out.println("\nTest case 2: Finding all paths in a maze (5 x 5)");
        Paths paths = maze.findAllPaths(start, end);
        System.out.println("Number of recursions to find all paths: " + Maze.recursions);
        if(paths == null || paths.size() == 0)
            System.out.println("No paths found");
        else{
            System.out.println(paths.size() + " path(s) found");
            for(int i=0; i<paths.size(); i++){
                System.out.println("Path " + (i+1));
                maze.visualizePath(paths.get(i));
            }
        }

        System.out.println("\nTest case 3: Finding a path in a maze (4 x 7)");
        maze = new Maze(4, 7);
        maze.load("maze_2.txt");
        start = new Coordinate(0,0);
        end = new Coordinate(3,6);
        path = maze.findPath(start, end);
        System.out.println("Number of recursions to find one path: " + Maze.recursions);
        if(path == null || path.size() == 0){
            System.out.println("No path found");
        }
        else{
            maze.visualizePath(path);
        }
        System.out.println("\nTest case 4: Finding all paths in a maze (4 x 7)");
        paths = maze.findAllPaths(start, end);
        System.out.println("Number of recursions to find all paths: " + Maze.recursions);
        if(paths == null || paths.size() == 0)
            System.out.println("No paths found");
        else{
            System.out.println(paths.size() + " path(s) found");
            for(int i=0; i<paths.size(); i++){
                System.out.println("Path " + (i+1));
                maze.visualizePath(paths.get(i));
            }
        }
       
        System.out.println("\nTest case 5: Performance for different sizes of mazes");
        System.out.printf("%-8s\t%-15s\t%-15s\n", "", "#recursions", "#recursions");
        System.out.printf("%-8s\t%-15s\t%-15s\t%-15s\n", "SIZE", "One Path", "All Paths", "Total Paths");
        String[] mazes = {"maze_3.txt", "maze_4.txt", "maze_5.txt"};
        for(int i=0; i<mazes.length; i++){
            System.out.printf("Maze %1dX%1d\t", (i+3),(i+3));
            maze = new Maze(i+3, i+3);
            maze.load(mazes[i]);
            start = new Coordinate(0,0);
            end = new Coordinate((i+2),(i+2));
            path = maze.findPath(start, end);
            System.out.printf("%-15d\t",  Maze.recursions);
            paths = maze.findAllPaths(start, end);
            System.out.printf("%-15d\t%-15d\n", Maze.recursions,paths.size());
        }
    }
}
