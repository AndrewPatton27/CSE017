import java.io.File;
import java.io.FileNotFoundException;
import java.util.PriorityQueue;
import java.util.Scanner;

/**
 * Part 2: simulating a printer queue with java.util.PriorityQueue
 */
class Part2 {
    private static final int PRINTER_SPEED = 10_000; // bytes per second

    public static void run(String fileName) {
        PriorityQueue<PrintRequest> queue = new PriorityQueue<>();

        try (Scanner fileIn = new Scanner(new File(fileName))) {
            while (fileIn.hasNextLine()) {
                String line = fileIn.nextLine().trim();
                if (line.isEmpty()) continue;
                try {
                    String[] parts = line.split("\\s+");
                    int id = Integer.parseInt(parts[0]);
                    String group = parts[1];
                    long size = Long.parseLong(parts[2]);
                    queue.offer(new PrintRequest(id, group, size));
                } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
                    System.out.println("Skipping invalid line: " + line);
                } catch (IllegalArgumentException e) {
                    System.out.println("Skipping line (" + e.getMessage() + "): " + line);
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("File " + fileName + " not found.");
            return;
        }

        System.out.println("Summary of the printed requests");
        System.out.println("-------------------------------------------------------------");
        System.out.printf("%-12s%-12s%-12s%s%n", "User ID", "Group", "Size", "Completion time");

        long totalSeconds = 0;
        while (!queue.isEmpty()) {
            PrintRequest r = queue.poll();
            long seconds = Math.round((double) r.getSize() / PRINTER_SPEED);
            totalSeconds += seconds;
            System.out.println(r + formatTime(seconds));
        }

        System.out.println();
        System.out.println("Total Printing time: " + formatTime(totalSeconds));
    }

    /** Formats seconds as dd:hh:mm:ss. */
    public static String formatTime(long seconds) {
        long days = seconds / 86_400;
        long hours = (seconds % 86_400) / 3_600;
        long minutes = (seconds % 3_600) / 60;
        long secs = seconds % 60;
        return String.format("%02d:%02d:%02d:%02d", days, hours, minutes, secs);
    }
}
