import java.util.Comparator;
import java.util.Iterator;

/**
 * Generic class to implement an array based list
 */
public class ArrayList<E> {

    // Iteration counters: incremented once per loop iteration inside
    // contains, add(int, E), remove(Object), and sort
    public static int cIterations = 0; // contains
    public static int rIterations = 0; // remove(Object)
    public static int aIterations = 0; // add(int, E)
    public static int sIterations = 0; // sort

    // data member: array for the list elements
    private E[] elements;
    // data member: size of the list
    private int size;

    /**
     * Default constructor creates the array with a default length of 10 and sets size to 0
     */
    public ArrayList() {
        this(10);
    }

    /**
     * Constructor with one parameter creates the array with length equal to capacity and sets size to 0
     * @param capacity length of the array elements
     */
    public ArrayList(int capacity) {
        elements = (E[]) new Object[capacity]; // cannot instantiate E directly
        size = 0;
    }

    /**
     * Method to add a new item at the end of the list
     * @param item the value of the item to be added
     * @return true if item was added successfully, false otherwise
     */
    public boolean add(E item) {
        return add(size, item); // adding at the first free index
    }

    /**
     * Method to add a new item at a given position index
     * @param index the position where item should be added
     * @param item the value of the element to be added
     * @return true if item was added successfully, false otherwise
     * @throws ArrayIndexOutOfBoundsException if index < 0 or index > size
     */
    // Time complexity: O(n)
    public boolean add(int index, E item) {
        if (index > size || index < 0)
            throw new ArrayIndexOutOfBoundsException();
        ensureCapacity();
        for (int i = size - 1; i >= index; i--) {
            elements[i + 1] = elements[i];
            aIterations++;
        }
        elements[index] = item;
        size++;
        return true;
    }

    /**
     * Get the value of the element at index
     * @param index of the element being accessed
     * @return the value of the element at index
     * @throws ArrayIndexOutOfBoundsException if index < 0 or index >= size
     */
    public E get(int index) {
        checkIndex(index);
        return elements[index];
    }

    /**
     * Set the value of the element at index
     * @param index of the element being modified
     * @param newValue new value of the element at index
     * @return the old value of the element at index
     * @throws ArrayIndexOutOfBoundsException if index < 0 or index >= size
     */
    public E set(int index, E newValue) {
        checkIndex(index);
        E oldValue = elements[index];
        elements[index] = newValue;
        return oldValue;
    }

    /**
     * Get the size of the list
     * @return the number of elements in the list
     */
    public int size() {
        return size;
    }

    /**
     * Clear the list by setting size to 0
     */
    public void clear() {
        size = 0;
    }

    /**
     * Predicate to check if the list is empty
     * @return true if the list is empty, false otherwise
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Remove the element at a given index
     * @param index the position of the element to be removed
     * @return true if the element was removed successfully, false otherwise
     * @throws ArrayIndexOutOfBoundsException if index < 0 or index >= size
     */
    public boolean remove(int index) {
        checkIndex(index);
        for (int i = index; i < size - 1; i++)
            elements[i] = elements[i + 1];
        size--;
        return true;
    }

    /**
     * Search method
     * @param o the object to look for
     * @return true if o is found in the list, false otherwise
     */
    // Time complexity: O(n)
    public boolean contains(Object o) {
        for (Iterator<E> i = iterator(); i.hasNext(); ) {
            cIterations++;
            if (i.next().equals(o)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Remove method
     * @param o the object to remove
     * @return true if o was found and removed, false if not found
     */
    // Time complexity: O(n)
    public boolean remove(Object o) {
        for (int i = 0; i < size; i++) {
            if (elements[i].equals(o)) {
                for (int j = i; j < size - 1; j++) {
                    elements[j] = elements[j + 1];
                    rIterations++; // count the shifts
                }
                size--;
                return true;
            }
            rIterations++; // count the non-matching search steps
        }
        return false;
    }

    /**
     * Sort method (selection sort)
     * @param c comparator; if null use natural ordering (compareTo), else use c.compare
     */
    // Time complexity: O(n^2)
    public void sort(Comparator<E> c) {
        for (int i = 0; i < size - 1; i++) {
            int min = i;
            for (int j = i + 1; j < size; j++) {
                sIterations++;
                int cmpResult = (c == null)
                        ? ((Comparable<E>) elements[j]).compareTo(elements[min])
                        : c.compare(elements[j], elements[min]);
                if (cmpResult < 0)
                    min = j;
            }
            E temp = elements[i];
            elements[i] = elements[min];
            elements[min] = temp;
        }
    }

    /**
     * Sort using the natural ordering
     */
    public void sort() {
        sort(null);
    }

    /**
     * Resize the length of the array 'elements' to the size of the list
     */
    public void trimToSize() {
        if (size != elements.length) {
            E[] newElements = (E[]) new Object[size]; // capacity = size
            for (int i = 0; i < size; i++)
                newElements[i] = elements[i];
            elements = newElements;
        }
    }

    /**
     * Grow the length of the array 'elements' by 1.5 if it is full
     */
    private void ensureCapacity() {
        if (size >= elements.length) {
            int newCap = (int) (elements.length * 1.5);
            E[] newElements = (E[]) new Object[newCap];
            for (int i = 0; i < size; i++)
                newElements[i] = elements[i];
            elements = newElements;
        }
    }

    /**
     * Check if the index is valid
     * @param index to be checked
     * @throws ArrayIndexOutOfBoundsException if index is out of bounds
     */
    private void checkIndex(int index) {
        if (index < 0 || index >= size)
            throw new ArrayIndexOutOfBoundsException(
                    "Index out of bounds. Must be between 0 and " + (size - 1));
    }

    /**
     * @override toString() from class Object
     * @return a formatted string containing the elements of the list
     */
    public String toString() {
        String output = "[";
        for (int i = 0; i < size - 1; i++)
            output += elements[i] + " ";
        output += elements[size - 1] + "]";
        return output;
    }

    /**
     * @return iterator object pointing to the first element of the list
     */
    public Iterator<E> iterator() {
        return new ArrayIterator();
    }

    /**
     * Inner class to implement the interface Iterator<E>
     */
    private class ArrayIterator implements Iterator<E> {
        // index of the element at which the iterator is pointing
        private int current = 0;

        /**
         * @return true if current did not reach the end of the list, false otherwise
         */
        public boolean hasNext() {
            return current < size;
        }

        /**
         * @return the value of the current element and moves current to the next element
         * @throws ArrayIndexOutOfBoundsException if current is out of bounds
         */
        public E next() {
            if (current < 0 || current >= size)
                throw new ArrayIndexOutOfBoundsException("No more elements");
            return elements[current++];
        }
    }
}
