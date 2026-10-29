import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

/**
 * Generic method drills. Remember the restrictions:
 * no new E(), no new E[n], and E must be a reference type.
 */
public class GenericsDrills {

    /** How many elements of arr equal key (use equals). */
    public static <E> int count(E[] arr, E key) {
        // TODO: write this method
        return -1;
    }

    /** Largest element. Needs a bound so we can call compareTo. arr is non-empty. */
    public static <E extends Comparable<E>> E max(E[] arr) {
        // TODO: write this method
        return null;
    }

    /** Swap arr[i] and arr[j]. */
    public static <E> void swap(E[] arr, int i, int j) {
        // TODO: write this method
        // (nothing to return)
    }

    /** Reverse the array in place (call your swap). */
    public static <E> void reverse(E[] arr) {
        // TODO: write this method
        // (nothing to return)
    }

    /** True if the list is in ascending order (ties allowed). Use an iterator. */
    public static <E extends Comparable<E>> boolean isSorted(List<E> list) {
        // TODO: write this method
        return false;
    }

    /** How many elements are strictly greater than key. */
    public static <E extends Comparable<E>> int countGreater(List<E> list, E key) {
        // TODO: write this method
        return -1;
    }

    /** New list with the duplicates removed (first occurrence kept). O(n^2). */
    public static <E> List<E> removeDuplicates(List<E> list) {
        // TODO: write this method
        return null;
    }

    /** Put all of list1 then all of list2 into out. O(n). */
    public static <E> void union(List<E> list1, List<E> list2, List<E> out) {
        // TODO: write this method
        // (nothing to return)
    }

    /** Elements of list1 that are also in list2 (list1 order, keeps list1 duplicates). O(n^2). */
    public static <E> void intersection(List<E> list1, List<E> list2, List<E> out) {
        // TODO: write this method
        // (nothing to return)
    }

    /** Elements of list1 that are NOT in list2. O(n^2). */
    public static <E> void difference(List<E> list1, List<E> list2, List<E> out) {
        // TODO: write this method
        // (nothing to return)
    }

    /** Insertion sort with a Comparator (this is how Arrays.sort(arr, c) is used). */
    public static <E> void insertionSort(E[] arr, Comparator<E> c) {
        // TODO: write this method
        // (nothing to return)
    }

    /**
     * Total of a list of ANY Number subtype. Wildcard practice:
     * List<? extends Number> can be read as Number but not added to.
     */
    public static double total(List<? extends Number> list) {
        // TODO: write this method
        return -1;
    }
}
