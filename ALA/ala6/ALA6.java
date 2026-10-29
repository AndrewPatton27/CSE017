import java.util.Scanner;

/**
 * CSE017 ALA 6: Using Data Structures
 * Runs Part 1 (Stack / postfix) then Part 2 (PriorityQueue / printer).
 */
public class ALA6 {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        Part1.run(input);
        System.out.println();
        System.out.println();
        Part2.run("requests.txt");
        input.close();
    }
}
