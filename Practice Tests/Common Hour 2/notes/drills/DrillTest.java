package drills;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import Student;

/**
 * Checks every drill. Compile + run against drills/ (your work) or solutions/.
 *   javac -d out drills/*.java DrillTest.java && java -cp out DrillTest
 * Prints PASS / FAIL per check and a score at the end.
 */
public class DrillTest {
    static int pass = 0;
    static int total = 0;

    static void check(String name, Object actual, Object expected) {
        total++;
        boolean ok = actual == null ? expected == null : actual.equals(expected);
        if (ok) {
            pass++;
            System.out.println("PASS  " + name);
        } else {
            System.out.println("FAIL  " + name + "  expected " + expected + " but got " + actual);
        }
    }

    static List<Integer> ints(Integer... v) {
        return new ArrayList<>(Arrays.asList(v));
    }

    interface Section {
        void run();
    }

    /** Runs one section; an exception (usually a stub returning null) only skips the rest of that section. */
    static void section(String title, Section body) {
        System.out.println("== " + title);
        try {
            body.run();
        } catch (Throwable t) {
            System.out.println("ERROR " + t + "  (rest of this section skipped - finish the method above first)");
        }
    }

    public static void main(String[] args) {
        section("Recursion", DrillTest::recursion);
        section("Generic methods", DrillTest::genericMethods);
        section("Pair + MyStack", DrillTest::pairAndStack);
        section("Comparable / Comparator", DrillTest::comparators);
        section("Collections", DrillTest::collections);
        System.out.println("\nScore: " + pass + " / " + total);
    }

    static void recursion() {
        check("sumDigits(1234)", RecursionDrills.sumDigits(1234), 10);
        check("sumDigits(7)", RecursionDrills.sumDigits(7), 7);
        check("power(2,10)", RecursionDrills.power(2, 10), 1024);
        check("power(5,0)", RecursionDrills.power(5, 0), 1);
        check("power2(3,13)", RecursionDrills.power2(3, 13), 1594323L);
        check("power2(2,0)", RecursionDrills.power2(2, 0), 1L);
        check("reverse(abc)", RecursionDrills.reverse("abc"), "cba");
        check("reverse(empty)", RecursionDrills.reverse(""), "");
        check("isPalindrome(racecar)", RecursionDrills.isPalindrome("racecar"), true);
        check("isPalindrome(abba)", RecursionDrills.isPalindrome("abba"), true);
        check("isPalindrome(abca)", RecursionDrills.isPalindrome("abca"), false);
        check("countEven", RecursionDrills.countEven(new int[] {1, 2, 4, 7, 8, 10}), 4);
        check("countEven(empty)", RecursionDrills.countEven(new int[0]), 0);
        int[] sorted = {2, 5, 8, 12, 16, 23, 38, 56, 72, 91};
        check("binarySearch found", RecursionDrills.binarySearch(sorted, 23), 5);
        check("binarySearch first", RecursionDrills.binarySearch(sorted, 2), 0);
        check("binarySearch last", RecursionDrills.binarySearch(sorted, 91), 9);
        check("binarySearch missing", RecursionDrills.binarySearch(sorted, 50), -1);
        check("gcd(12,18)", RecursionDrills.gcd(12, 18), 6);
        check("gcd(17,5)", RecursionDrills.gcd(17, 5), 1);
        check("toBinary(10)", RecursionDrills.toBinary(10), "1010");
        check("toBinary(0)", RecursionDrills.toBinary(0), "0");
        List<String> words = new LinkedList<>(Arrays.asList("a", "b", "a", "c", "a"));
        check("countOccurrences", RecursionDrills.countOccurrences(words, "a"), 3);
        check("findAtLeast", RecursionDrills.findAtLeast(ints(5, 1, 9, 3, 7), 5), ints(5, 9, 7));
        check("findAtLeast none -> null", RecursionDrills.findAtLeast(ints(1, 2), 5), null);
        check("maxOf", RecursionDrills.maxOf(new LinkedList<>(ints(4, 17, 3, 9))), 17);
        boolean overflow = false;
        try {
            RecursionDrills.brokenFactorial(5);
        } catch (StackOverflowError e) {
            overflow = true;
        }
        check("brokenFactorial -> StackOverflowError", overflow, true);

    }

    static void genericMethods() {
        Integer[] nums = {3, 9, 4, 9, 1};
        String[] strs = {"pear", "apple", "fig"};
        check("count", GenericsDrills.count(nums, 9), 2);
        check("max Integer", GenericsDrills.max(nums), 9);
        check("max String", GenericsDrills.max(strs), "pear");
        GenericsDrills.swap(strs, 0, 2);
        check("swap", Arrays.toString(strs), "[fig, apple, pear]");
        GenericsDrills.reverse(nums);
        check("reverse", Arrays.toString(nums), "[1, 9, 4, 9, 3]");
        check("isSorted yes", GenericsDrills.isSorted(ints(1, 2, 2, 5)), true);
        check("isSorted no", GenericsDrills.isSorted(ints(1, 3, 2)), false);
        check("isSorted empty", GenericsDrills.isSorted(ints()), true);
        check("countGreater", GenericsDrills.countGreater(ints(1, 5, 7, 5, 9), 5), 2);
        check("removeDuplicates", GenericsDrills.removeDuplicates(ints(1, 2, 1, 3, 2)), ints(1, 2, 3));
        List<Integer> out = new ArrayList<>();
        GenericsDrills.union(ints(1, 2), ints(2, 3), out);
        check("union", out, ints(1, 2, 2, 3));
        out = new ArrayList<>();
        GenericsDrills.intersection(ints(1, 2, 2, 4), ints(2, 3, 4), out);
        check("intersection", out, ints(2, 2, 4));
        out = new ArrayList<>();
        GenericsDrills.difference(ints(1, 2, 3, 4), ints(2, 4), out);
        check("difference", out, ints(1, 3));
        String[] sorting = {"kiwi", "fig", "banana", "apple"};
        GenericsDrills.insertionSort(sorting, (x, y) -> x.length() - y.length());
        check("insertionSort by length", Arrays.toString(sorting), "[fig, kiwi, apple, banana]");
        check("total wildcard", GenericsDrills.total(Arrays.asList(1, 2, 3)), 6.0);
        check("total wildcard double", GenericsDrills.total(Arrays.asList(1.5, 2.5)), 4.0);

    }

    static void pairAndStack() {
        Pair<String, Integer> p = new Pair<>("age", 20);
        check("Pair.swapped", p.swapped().toString(), "(20, age)");
        check("Pair.swapped getFirst", p.swapped().getFirst(), 20);
        MyStack<String> st = new MyStack<>();
        st.push("a");
        st.push("b");
        check("MyStack peek", st.peek(), "b");
        check("MyStack pop", st.pop(), "b");
        check("MyStack size", st.size(), 1);
        check("MyStack isEmpty", st.isEmpty(), false);
        st.pop();
        check("MyStack isEmpty after", st.isEmpty(), true);
        String msg = null;
        try {
            st.pop();
        } catch (RuntimeException e) {
            msg = e.getMessage();
        }
        check("MyStack pop empty throws", msg, "empty stack");

    }

    static void comparators() {
        Student[] s = {new Student(22, "Eve", 3.2), new Student(11, "Zed", 3.9), new Student(33, "Amy", 2.8)};
        Arrays.sort(s);
        check("Student natural (id)", s[0].getID() + "," + s[1].getID() + "," + s[2].getID(), "11,22,33");
        Arrays.sort(s, new ComparatorByName());
        check("ComparatorByName", s[0].getName() + "," + s[1].getName() + "," + s[2].getName(), "Amy,Eve,Zed");
        Arrays.sort(s, new ComparatorByGPA());
        check("ComparatorByGPA (high first)", s[0].getName() + "," + s[1].getName() + "," + s[2].getName(), "Zed,Eve,Amy");

    }

    static void collections() {
        List<Integer> list = ints(4, -1, 5, -7, 0);
        CollectionsDrills.removeNegatives(list);
        check("removeNegatives", list, ints(4, 5, 0));
        check("reversedCopy", CollectionsDrills.reversedCopy(new LinkedList<>(ints(1, 2, 3))), ints(3, 2, 1));
        list = ints(1, 2, 3);
        CollectionsDrills.doubleAll(list);
        check("doubleAll", list, ints(2, 4, 6));
        List<String> w = new ArrayList<>(Arrays.asList("ccc", "a", "bb", "dd", "e"));
        CollectionsDrills.sortByLength(w);
        check("sortByLength", w, Arrays.asList("a", "e", "bb", "dd", "ccc"));
        check("largest", CollectionsDrills.largest(ints(3, 8, 2)), 8);
        check("isBalanced ok", CollectionsDrills.isBalanced("([]{})"), true);
        check("isBalanced mismatch", CollectionsDrills.isBalanced("(]"), false);
        check("isBalanced open left", CollectionsDrills.isBalanced("(("), false);
        check("isBalanced close first", CollectionsDrills.isBalanced(")("), false);
        check("isBalanced text", CollectionsDrills.isBalanced("a(b[c]d)e"), true);
        LinkedList<Integer> q = new LinkedList<>(ints(1, 2, 3, 4));
        CollectionsDrills.reverseQueue(q);
        check("reverseQueue", q, new LinkedList<>(ints(4, 3, 2, 1)));
        check("hotPotato", CollectionsDrills.hotPotato(Arrays.asList("A", "B", "C", "D", "E"), 3), "D");
        check("kLargest", CollectionsDrills.kLargest(new int[] {5, 1, 9, 3, 7, 8}, 3), ints(9, 8, 7));
        List<Task> tasks = Arrays.asList(new Task("laundry", 1), new Task("exam", 9), new Task("email", 5), new Task("art", 5));
        check("doTasks", CollectionsDrills.doTasks(tasks), Arrays.asList("exam", "art", "email", "laundry"));
        check("doTasksLeastUrgentFirst", CollectionsDrills.doTasksLeastUrgentFirst(tasks),
            Arrays.asList("laundry", "email", "art", "exam"));
        check("common", CollectionsDrills.common(ints(1, 2, 3, 4), ints(3, 4, 5)), ints(3, 4));

    }
}
