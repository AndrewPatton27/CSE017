import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class Test {
    private static final int SIZE = 1000000;
    private static final int SAMPLES = 20;
    private static final int STEP = SIZE / SAMPLES;
    private static final int SEED = 17;

    public static void main(String[] args) {
        // create a list of random integers
        Random rand = new Random(SEED);
        ArrayList<Integer> list = new ArrayList<>();
        for (int i = 0; i < SIZE; i++) {
            list.add(rand.nextInt(SIZE) + 1);
        }
        System.out.println("Testing the offer/poll methods with a random list of integers");
        System.out.println();
        runTest(list);
        // sort the list and repeat
        Collections.sort(list);
        System.out.println("Testing the offer/poll methods with a sorted list of integers");
        System.out.println();
        runTest(list);
    }

    /**
     * Runs steps c-f of the write-up on the given list.
     * @param list the integers to add to and remove from the heap
     */
    public static void runTest(ArrayList<Integer> list) {
        PriorityQueue<Integer> heap = new PriorityQueue<>();
        // sampled numbers and iteration counts, every STEP-th operation
        int[] sampleNumbers = new int[SAMPLES];
        int[] sampleCounts = new int[SAMPLES];
        long total = 0;
        for (int i = 0; i < list.size(); i++) {
            int before = PriorityQueue.offerIterations;
            heap.offer(list.get(i));
            int count = PriorityQueue.offerIterations - before;
            total += count;
            if (i % STEP == 0) {
                sampleNumbers[i / STEP] = list.get(i);
                sampleCounts[i / STEP] = count;
            }
        }
        printTable("offer", sampleNumbers, sampleCounts, total / list.size());
        System.out.println("Heap Properties");
        System.out.println("Height: " + heap.height());
        System.out.println("The heap is balanced? " + heap.isBalanced());
        System.out.println();
        total = 0;
        for (int i = 0; i < list.size(); i++) {
            int before = PriorityQueue.pollIterations;
            int number = heap.poll();
            int count = PriorityQueue.pollIterations - before;
            total += count;
            if (i % STEP == 0) {
                sampleNumbers[i / STEP] = number;
                sampleCounts[i / STEP] = count;
            }
        }
        printTable("poll", sampleNumbers, sampleCounts, total / list.size());
    }

    /**
     * Prints one table of sampled iteration counts.
     * @param name offer or poll
     * @param numbers the sampled numbers
     * @param counts the iterations for each sampled number
     * @param average the average iterations over all operations
     */
    private static void printTable(String name, int[] numbers, int[] counts, long average) {
        System.out.println("The " + name + " method Iterations");
        System.out.printf("%-16s%s%n", "Number", "Iterations");
        for (int i = 0; i < numbers.length; i++) {
            System.out.printf("%-16d%d%n", numbers[i], counts[i]);
        }
        System.out.printf("%-16s%d%n", "Average", average);
        System.out.println();
    }

    /*
     * Discussion: iterations of offer and poll
     * - offer: with random input the average is about 2 iterations (a few
     *   samples reach 4-6, most are 0-2), because a new value only moves up
     *   past the few parents that are larger than it. With sorted input every
     *   new value is already larger than its parent, so the loop stops after
     *   1 comparison (average 0 after integer division). In the worst case
     *   offer is O(log n), but the average case is O(1).
     * - poll: the last leaf is moved to the root and sifts down, and it is
     *   usually small, so it falls almost to the bottom. That gives about 16-20
     *   iterations (average 18), close to the height log2(1,000,000) = 20,
     *   for both random and sorted input. poll is O(log n) on average and in
     *   the worst case.
     * - The numbers change a little between runs because the integers are random.
     */

    /*
     * Discussion: height and isBalanced of the MinHeap
     * - A heap is stored as a complete binary tree: every level is full except
     *   possibly the last, which fills from the left. So the height is
     *   floor(log2(n)) + 1, which is 20 for 1,000,000 nodes. It does not
     *   depend on the order of the values, so random and sorted input give
     *   the same height.
     * - For the same reason a heap is always balanced: the left and right
     *   subtrees of any node differ in height by at most 1, so isBalanced()
     *   returned true in both runs.
     * - height() is O(n) because it visits every node once. isBalanced() is
     *   O(n log n) because it calls height() at each node, and the sum of the
     *   subtree sizes over all nodes is n log n for a complete tree.
     */
}
