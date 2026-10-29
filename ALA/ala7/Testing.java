import java.io.File;
import java.io.FileNotFoundException;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Random;
import java.util.Scanner;

public class Testing {

    private static final int TRIALS = 20;
    private static final String ROW_FORMAT = "%-24s%-14s%-14s%-12s%-12s%-12s%-12s%n";  // header row
    private static final String DATA_FORMAT = "%-24s%-14d%-14d%-12d%-12d%-12d%-12d%n"; // data rows

    /** Custom comparator: orders strings by length. */
    private static class LengthComparator implements Comparator<String> {
        public int compare(String a, String b) {
            return a.length() - b.length();
        }
    }

    public static void main(String[] args) {
        ArrayList<String> animalAL = new ArrayList<>();
        LinkedList<String> animalLL = new LinkedList<>();

        if (!loadAnimals("animals.txt", animalAL, animalLL))
            return;

        runPerformanceTest(animalAL, animalLL);
        runSortTest(animalAL, animalLL);
    }

    /** Reads each line of the file into both lists. Returns false if the file is missing. */
    private static boolean loadAnimals(String fileName, ArrayList<String> al, LinkedList<String> ll) {
        try (Scanner in = new Scanner(new File(fileName))) {
            while (in.hasNextLine()) {
                String line = in.nextLine();
                al.add(line);
                ll.add(line);
            }
            return true;
        } catch (FileNotFoundException e) {
            System.out.println(fileName + " not found");
            return false;
        }
    }

    /** Tests contains, remove, and add(int, E) on random animals and prints the iteration counts. */
    private static void runPerformanceTest(ArrayList<String> al, LinkedList<String> ll) {
        Random rand = new Random();
        long[] totals = new long[6];

        System.out.println("Performance of the search/remove/add operations");
        System.out.printf(ROW_FORMAT, "Animal", "contains(AL)", "contains(LL)",
                "remove(AL)", "remove(LL)", "add(AL)", "add(LL)");

        for (int t = 0; t < TRIALS; t++) {
            int index = rand.nextInt(al.size());
            String name = al.get(index);
            int[] counts = new int[6];

            resetCounters();
            al.contains(name);
            ll.contains(name);
            counts[0] = ArrayList.cIterations;
            counts[1] = LinkedList.cIterations;

            resetCounters();
            al.remove(name);
            ll.remove(name);
            counts[2] = ArrayList.rIterations;
            counts[3] = LinkedList.rIterations;

            // add the name back at the same index (restores the lists)
            resetCounters();
            al.add(index, name);
            ll.add(index, name);
            counts[4] = ArrayList.aIterations;
            counts[5] = LinkedList.aIterations;

            printRow(name, counts);
            for (int i = 0; i < counts.length; i++)
                totals[i] += counts[i];
        }

        int[] averages = new int[totals.length];
        for (int i = 0; i < totals.length; i++)
            averages[i] = (int) (totals[i] / TRIALS);
        printRow("Average", averages);
        System.out.println();
    }

    /** Sorts each list by natural order and by length, printing the lists and iteration counts. */
    private static void runSortTest(ArrayList<String> al, LinkedList<String> ll) {
        Comparator<String> byLength = new LengthComparator();

        resetCounters();
        al.sort();
        int alNatural = ArrayList.sIterations;
        printList("ArrayList sorted alphabetically (natural ordering)", al.iterator());

        ArrayList.sIterations = 0;
        al.sort(byLength);
        int alCustom = ArrayList.sIterations;
        printList("ArrayList sorted by length (custom ordering)", al.iterator());

        ll.sort();
        int llNatural = LinkedList.sIterations;
        printList("LinkedList sorted alphabetically (natural ordering)", ll.iterator());

        LinkedList.sIterations = 0;
        ll.sort(byLength);
        int llCustom = LinkedList.sIterations;
        printList("LinkedList sorted by length (custom ordering)", ll.iterator());

        System.out.println("Number of iterations");
        System.out.printf("%-28s%-26s%s%n", "Data Structure", "Natural Ordering", "Custom Ordering");
        System.out.printf("%-28s%-26d%d%n", "ArrayList", alNatural, alCustom);
        System.out.printf("%-28s%-26d%d%n", "LinkedList", llNatural, llCustom);
    }

    private static void resetCounters() {
        ArrayList.cIterations = ArrayList.rIterations = ArrayList.aIterations = ArrayList.sIterations = 0;
        LinkedList.cIterations = LinkedList.rIterations = LinkedList.aIterations = LinkedList.sIterations = 0;
    }

    private static void printRow(String label, int[] c) {
        System.out.printf(DATA_FORMAT, label, c[0], c[1], c[2], c[3], c[4], c[5]);
    }

    private static void printList(String title, Iterator<String> it) {
        System.out.println(title);
        while (it.hasNext())
            System.out.println(it.next());
        System.out.println();
    }
}
