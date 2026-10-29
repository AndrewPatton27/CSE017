import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Scanner;
import java.util.Stack;

public class Test {
    public static void main(String[] args) {
        // two different list types (your methods must work for both)
        List<Order> a = new ArrayList<>();
        List<Order> b = new LinkedList<>();
        readOrders(a, "orders1.txt");
        readOrders(b, "orders2.txt");
        System.out.println("List A:");
        print(a);
        System.out.println("\nList B:");
        print(b);

        // Part 1: recursive search
        System.out.println();
        report("Ana", findOrders(a, "Ana"));
        report("Zoe", findOrders(b, "Zoe"));
        report("Ana", findOrders(b, "Ana"));

        // Part 1b: recursive total + count (same style, no list returned)
        System.out.printf("%nTotal of list A: $%.2f%n", total(a.iterator()));
        System.out.printf("Total of list B: $%.2f%n", total(b.iterator()));
        System.out.println("Orders over $100 in A: " + countAbove(a.iterator(), 100));
        System.out.println("Orders over $100 in B: " + countAbove(b.iterator(), 100));

        // Part 2: generic utility methods
        List<Order> unionList = new ArrayList<>();
        union(a, b, unionList);
        System.out.println("\nUnion (" + unionList.size() + "):");
        print(unionList);

        List<Order> noDup = new LinkedList<>();
        unionNoDuplicates(a, b, noDup);
        System.out.println("\nUnion without duplicates (" + noDup.size() + "):");
        print(noDup);

        List<Order> both = new ArrayList<>();
        intersection(a, b, both);
        System.out.println("\nIntersection (" + both.size() + "):");
        print(both);

        List<Order> onlyA = new ArrayList<>();
        difference(a, b, onlyA);
        System.out.println("\nIn A but not in B (" + onlyA.size() + "):");
        print(onlyA);

        // Part 3: comparators and Comparable
        unionList.sort(null);
        System.out.println("\nUnion sorted by price (natural order):");
        print(unionList);
        unionList.sort(new ComparatorByCustomer());
        System.out.println("\nUnion sorted by customer:");
        print(unionList);
        Collections.sort(unionList, new ComparatorByPrice());
        System.out.println("\nUnion sorted by price, highest first:");
        print(unionList);

        // Part 4: data structures
        System.out.println();
        Queue<Order> line = new LinkedList<>(noDup);
        System.out.println("Fulfilling the first 3 orders in arrival order (queue):");
        for (int i = 0; i < 3; i++) {
            System.out.println("  " + line.poll());
        }
        System.out.println("Orders still waiting: " + line.size());

        PriorityQueue<Order> rush = new PriorityQueue<>(new ComparatorByPrice());
        for (Order o : noDup) {
            rush.offer(o);
        }
        System.out.println("\nThree biggest orders (priority queue):");
        for (int i = 0; i < 3; i++) {
            System.out.println("  " + rush.poll());
        }

        System.out.println("\nCustomers in reverse order of first appearance (stack):");
        System.out.println("  " + customersReversed(noDup));
    }

    // ----------------------------------------------------------------- given

    /** Read orders from a CSV file: id,customer,item,price. */
    public static void readOrders(List<Order> list, String filename) {
        try {
            Scanner read = new Scanner(new File(filename));
            while (read.hasNextLine()) {
                String[] t = read.nextLine().split(",");
                list.add(new Order(Integer.parseInt(t[0]), t[1], t[2], Double.parseDouble(t[3])));
            }
            read.close();
        } catch (FileNotFoundException e) {
            System.out.println("File \"" + filename + "\" not found");
        }
    }

    /** Print one element per line. */
    public static <E> void print(List<E> list) {
        Iterator<E> iter = list.iterator();
        while (iter.hasNext()) {
            System.out.println(iter.next());
        }
    }

    private static void report(String customer, List<Order> found) {
        if (found == null) {
            System.out.println("No orders found for " + customer);
        } else {
            System.out.println(found.size() + " orders found for " + customer + ":");
            print(found);
        }
    }

    // ------------------------------------------------------------- YOUR WORK
    // Rules for Parts 1 and 2: the ONLY List methods you may use are
    //   size() O(1)   iterator() O(1)   add(E) O(1)   contains(E) O(n)
    // Write the time complexity above each method as  // O(...)

    /**
     * PART 1. Return the orders placed by this customer (same order as the list)
     * or null if there are none. MUST be recursive and use an iterator
     * (write a private helper).
     */
    public static List<Order> findOrders(List<Order> list, String customer) {
        // TODO: write this method
        return null;
    }


    /**
     * PART 1b. Recursive: the sum of the prices of all the orders the iterator has left.
     * (Base case: no more elements -> 0.)
     */
    public static double total(Iterator<Order> it) {
        // TODO: write this method
        return -1;
    }

    /** PART 1b. Recursive: how many remaining orders cost MORE than limit. */
    public static int countAbove(Iterator<Order> it, double limit) {
        // TODO: write this method
        return -1;
    }

    /** PART 2. Everything in list1 followed by everything in list2, put into list. */
    // O(?)  <- you fill this in
    public static <E> void union(List<E> list1, List<E> list2, List<E> list) {
        // TODO: write this method
        // (nothing to return)
    }

    /** PART 2. Union with NO duplicates at all (not even duplicates inside list1). */
    // O(?)  <- you fill this in
    public static <E> void unionNoDuplicates(List<E> list1, List<E> list2, List<E> list) {
        // TODO: write this method
        // (nothing to return)
    }

    /** PART 2. The elements of list1 that are also in list2 (keep list1's order and its duplicates). */
    // O(?)  <- you fill this in
    public static <E> void intersection(List<E> list1, List<E> list2, List<E> list) {
        // TODO: write this method
        // (nothing to return)
    }

    /** PART 2. The elements of list1 that are NOT in list2. */
    // O(?)  <- you fill this in
    public static <E> void difference(List<E> list1, List<E> list2, List<E> list) {
        // TODO: write this method
        // (nothing to return)
    }

    /**
     * PART 4. Using a Stack (java.util.Stack): the customer names in REVERSE order of
     * first appearance, each name once. Return them as a String like [Gus, Fay, Ana].
     * Hint: push a name only if the stack does not contain it yet (Stack is a List too).
     */
    public static String customersReversed(List<Order> list) {
        // TODO: write this method
        return null;
    }
}
