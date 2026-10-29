import java.util.Arrays;
import java.util.Comparator;
import java.util.Iterator;
import java.util.ListIterator;
import java.util.NoSuchElementException;
import java.util.Objects;

/**
 * Generic class to implement an array based list.
 *
 * @param <E> the type of the elements in the list
 */
public class ArrayList<E> implements List<E>, Cloneable {
    /** Default length of the array. */
    private static final int DEFAULT_CAPACITY = 10;
    /** Factor by which the array grows when it is full. */
    private static final double GROWTH_FACTOR = 1.5;

    /** Array for the list elements. */
    private E[] elements;
    /** Size of the list. */
    private int size;

    /**
     * Default constructor creates the array with a default length of 10
     * and sets size to 0.
     */
    // Time complexity: O(1)
    @SuppressWarnings("unchecked")
    public ArrayList() {
        elements = (E[]) new Object[DEFAULT_CAPACITY];
        size = 0;
    }

    /**
     * Constructor with one parameter creates the array with length equal
     * to capacity and sets size to 0.
     *
     * @param capacity initial capacity of the array elements
     */
    // Time complexity: O(n), n = capacity
    @SuppressWarnings("unchecked")
    public ArrayList(int capacity) {
        elements = (E[]) new Object[capacity];
        size = 0;
    }

    /**
     * Method to add a new element to this list.
     *
     * @param item the value to add
     * @return true if this list changed as a result of the call
     */
    // Time complexity: O(1) amortized
    @Override
    public boolean add(E item) {
        add(size, item);
        return true;
    }

    /**
     * Method to add an element at a given index.
     *
     * @param index the position at which element will be inserted
     * @param item the value of the element to be added
     * @throws IndexOutOfBoundsException if index is negative or greater
     *         than this list's size
     */
    // Time complexity: O(n)
    @Override
    public void add(int index, E item) {
        if (index > size || index < 0) {
            throw new ArrayIndexOutOfBoundsException();
        }
        ensureCapacity();
        for (int i = size - 1; i >= index; i--) {
            elements[i + 1] = elements[i];
        }
        elements[index] = item;
        size++;
    }

    /**
     * Method to add a collection to this list.
     *
     * @param c the collection to be added
     * @return true if this list changed as a result of the call
     */
    // Time complexity: O(m) amortized, m = size of c
    @Override
    public boolean addAll(Collection<? extends E> c) {
        boolean changed = false;
        // add every element of c at the end of this list
        for (Iterator<? extends E> i = c.iterator(); i.hasNext();) {
            if (add(i.next())) {
                changed = true;
            }
        }
        return changed;
    }

    /**
     * Method to add an element as the first element of this list.
     *
     * @param e element to be added
     */
    // Time complexity: O(n)
    @Override
    public void addFirst(E e) {
        // insert at index 0; add(int, E) shifts the elements right
        // and grows the array if needed
        add(0, e);
    }

    /**
     * Method to add an element as the last element of this list.
     *
     * @param e to be added
     */
    // Time complexity: O(1) amortized
    @Override
    public void addLast(E e) {
        // add(E) appends at index size and grows the array if needed
        add(e);
    }

    /**
     * Check if the index is valid.
     *
     * @param index to be checked
     * @throws ArrayIndexOutOfBoundsException if index is out of bounds
     */
    private void checkIndex(int index) {
        if (index < 0 || index >= size) {
            throw new ArrayIndexOutOfBoundsException(
                "Index out of bounds. Must be between 0 and " + (size - 1));
        }
    }

    /**
     * Method to clear this list.
     */
    // Time complexity: O(1)
    @Override
    public void clear() {
        size = 0;
    }

    /**
     * Clone this list.
     *
     * @return a deep copy of this list
     */
    // Time complexity: O(n)
    @Override
    public Object clone() {
        // new list with its own array, so changes to one list
        // don't affect the other
        List<E> c = new ArrayList<>();

        // copy every element (deep copy: each element is cloned if it can be)
        for (Iterator<E> i = iterator(); i.hasNext();) {
            c.add(cloneElement(i.next()));
        }

        return c;
    }

    /**
     * Helper for the deep copy in clone().
     *
     * @param e the element to copy
     * @return a clone of e if e is Cloneable and has a public clone() method,
     *         otherwise e itself (immutable types such as String and Integer
     *         need no copy)
     */
    @SuppressWarnings("unchecked")
    private E cloneElement(E e) {
        if (e instanceof Cloneable) {
            try {
                return (E) e.getClass().getMethod("clone").invoke(e);
            } catch (ReflectiveOperationException ex) {
                // no accessible clone(): fall back to sharing the reference
                return e;
            }
        }
        return e;
    }

    /**
     * Method to search for an object in this list.
     *
     * @param o the object being searched in this list
     * @return true if this list contains the given object
     */
    // Time complexity: O(n)
    @Override
    public boolean contains(Object o) {
        for (Iterator<E> iter = iterator(); iter.hasNext();) {
            E element = iter.next();
            if (element.equals(o)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Method to search for a given collection in this list.
     *
     * @param c collection whose presence in this list is to be tested
     * @return true if this list contains all of the elements in the given
     *         collection
     */
    // Time complexity: O(n*m), m = size of c
    @Override
    public boolean containsAll(Collection<?> c) {
        // check every element of c; one missing element means false
        for (Iterator<?> i = c.iterator(); i.hasNext();) {
            if (!contains(i.next())) {
                return false;
            }
        }
        // all of c's elements were found
        return true;
    }

    /**
     * Grow the length of the array 'elements' by 1.5 if it is full.
     */
    @SuppressWarnings("unchecked")
    private void ensureCapacity() {
        if (size >= elements.length) {
            int newCap = (int) (elements.length * GROWTH_FACTOR);
            E[] newElements = (E[]) new Object[newCap];
            for (int i = 0; i < size; i++) {
                newElements[i] = elements[i];
            }
            elements = newElements;
        }
    }

    /**
     * Method to compare two lists.
     *
     * @param o the object to be compared to this list
     * @return true if this list is equal to the given object, false otherwise
     */
    // Time complexity: O(n)
    @Override
    public boolean equals(Object o) {
        // same object
        if (this == o) {
            return true;
        }
        // must be a List
        if (!(o instanceof List)) {
            return false;
        }
        List<?> other = (List<?>) o;
        // different sizes can't be equal
        if (size != other.size()) {
            return false;
        }
        Iterator<?> otherIter = other.iterator();
        // compare element by element, in order
        for (Iterator<E> selfIter = iterator(); selfIter.hasNext();) {
            // null-safe equals
            if (!Objects.equals(selfIter.next(), otherIter.next())) {
                return false;
            }
        }
        return true;
    }

    /**
     * Hash code consistent with equals.
     *
     * @return the hash code of the elements of this list
     */
    @Override
    public int hashCode() {
        return Arrays.hashCode(toArray());
    }

    /**
     * Return the element at the specified position.
     *
     * @param index the position of the element to get
     * @return the value of the element at the given position
     * @throws IndexOutOfBoundsException if index is out of bounds
     *         (index &lt; 0 || index &gt;= size())
     */
    // Time complexity: O(1)
    @Override
    public E get(int index) {
        checkIndex(index);
        return elements[index];
    }

    /**
     * Method to get the first element of this list.
     *
     * @return the value of the first element in this list
     * @throws NoSuchElementException if this list is empty
     */
    // Time complexity: O(1)
    @Override
    public E getFirst() {
        // nothing to return if the list is empty
        if (size == 0) {
            throw new NoSuchElementException();
        }
        // the first element is at index 0
        return elements[0];
    }

    /**
     * Method to get the last element of this list.
     *
     * @return the value of the last element in this list
     * @throws NoSuchElementException if this list is empty
     */
    // Time complexity: O(1)
    @Override
    public E getLast() {
        // nothing to return if the list is empty
        if (size == 0) {
            throw new NoSuchElementException();
        }
        // the last element is at index size-1
        // (size is the count, not the last index)
        return elements[size - 1];
    }

    /**
     * Method to get the index of the first occurrence of the given object.
     *
     * @param o element to search for
     * @return the index of the first occurrence of o, -1 if o was not found
     * @throws ClassCastException if the type of the given object is not
     *         compatible with this list's type
     * @throws NullPointerException if the given object is null
     */
    // Time complexity: O(n)
    @Override
    public int indexOf(Object o) {
        // the Javadoc in List.java requires a NullPointerException for null
        Objects.requireNonNull(o);
        // index tracks the position of the element the iterator is on
        int index = 0;
        for (Iterator<E> iter = iterator(); iter.hasNext(); index++) {
            // first match from the front is the answer
            if (iter.next().equals(o)) {
                return index;
            }
        }
        // not found
        return -1;
    }

    /**
     * Method to check if this list is empty.
     *
     * @return true if this list is empty
     */
    // Time complexity: O(1)
    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Method to get an iterator for this list.
     *
     * @return an iterator over the elements starting at the beginning
     *         of this list
     */
    // Time complexity: O(1)
    @Override
    public Iterator<E> iterator() {
        return new ArrayIterator();
    }

    /**
     * Method to get the index of the last occurrence of the given object.
     *
     * @param o element to search for
     * @return the index of the last occurrence of o, -1 if o was not found
     * @throws ClassCastException if the type of the given object is not
     *         compatible with this list's type
     * @throws NullPointerException if the given object is null
     */
    // Time complexity: O(n)
    @Override
    public int lastIndexOf(Object o) {
        // the Javadoc in List.java requires a NullPointerException for null
        Objects.requireNonNull(o);
        // scan from the end so the first match is the last occurrence
        for (int i = size - 1; i >= 0; i--) {
            if (elements[i].equals(o)) {
                return i;
            }
        }
        // not found
        return -1;
    }

    /**
     * Method to get a list iterator over the elements of this list.
     *
     * @return a ListIterator object starting at the first element
     *         of this list
     */
    // Time complexity: O(1)
    @Override
    public ListIterator<E> listIterator() {
        return new ArrayListIterator();
    }

    /**
     * Method to get a list iterator over the elements of this list.
     *
     * @param index the position at which the list iterator should start
     * @return a ListIterator object starting at the element at the given
     *         index; the given index indicates the first element that would
     *         be returned by an initial call to next(), and an initial call
     *         to previous() would return the element at the given index
     *         minus one
     * @throws IndexOutOfBoundsException if the index is out of bounds
     *         (index &lt; 0 || index &gt; size())
     */
    // Time complexity: O(1)
    @Override
    public ListIterator<E> listIterator(int index) {
        return new ArrayListIterator(index);
    }

    /**
     * Method to remove an element at a given index.
     *
     * @param index of the element to be removed
     * @return the value of the element that was removed
     * @throws IndexOutOfBoundsException is the index is out of bounds
     *         (index &lt; 0 || index &gt;= size)
     */
    // Time complexity: O(n)
    @Override
    public E remove(int index) {
        checkIndex(index);
        E value = elements[index];
        for (int i = index; i < size - 1; i++) {
            elements[i] = elements[i + 1];
        }
        size--;
        return value;
    }

    /**
     * Method to remove a given object from this list.
     *
     * @param o the object to find and remove
     * @return true if o was found and removed
     */
    // Time complexity: O(n)
    @Override
    public boolean remove(Object o) {
        for (int i = 0; i < size; i++) {
            if (elements[i].equals(o)) {
                remove(i);
                return true;
            }
        }
        return false;
    }

    /**
     * Method to remove all this list's elements that are also contained
     * in the given collection.
     *
     * @param c collection containing elements to be removed from this list
     * @return true if this list changed as a result of the call
     */
    // Time complexity: O(n*(n+m)), m = size of c
    @Override
    public boolean removeAll(Collection<?> c) {
        boolean changed = false;
        // check each element of THIS list from the end, so removing an
        // element never shifts one that has not been checked yet
        for (int i = size - 1; i >= 0; i--) {
            if (c.contains(elements[i])) {
                remove(i);
                changed = true;
            }
        }
        return changed;
    }

    /**
     * Method to remove the first element of this list.
     *
     * @return the value of the removed element
     * @throws NoSuchElementException if this list is empty
     */
    // Time complexity: O(n)
    @Override
    public E removeFirst() {
        // nothing to remove if the list is empty
        if (size == 0) {
            throw new NoSuchElementException();
        }

        // remove(int) shifts the rest left and returns the removed value
        return remove(0);
    }

    /**
     * Method to remove the last element of this list.
     *
     * @return the value of the removed element
     * @throws NoSuchElementException if this list is empty
     */
    // Time complexity: O(1)
    @Override
    public E removeLast() {
        // nothing to remove if the list is empty
        if (size == 0) {
            throw new NoSuchElementException();
        }

        // last element is at size-1; no shifting needed
        return remove(size - 1);
    }

    /**
     * Method to retain only the elements in this list that are contained
     * in the given collection.
     *
     * @param c collection containing elements to be retained in this list
     * @return true if this list changed as a result of the call
     */
    // Time complexity: O(n*(n+m)), m = size of c
    @Override
    public boolean retainAll(Collection<?> c) {
        boolean changed = false;
        // check each element of THIS list from the end, so removing an
        // element never shifts one that has not been checked yet
        for (int i = size - 1; i >= 0; i--) {
            if (!c.contains(elements[i])) {
                remove(i);
                changed = true;
            }
        }
        return changed;
    }

    /**
     * Method to return a reversed copy of this list.
     *
     * @return a reversed-ordered copy of this list
     */
    // Time complexity: O(n)
    @Override
    public List<E> reversed() {
        // new list, so this list stays unchanged
        List<E> reversed = new ArrayList<>();
        // add the elements from the last index down to 0
        for (int i = size - 1; i >= 0; i--) {
            reversed.add(elements[i]);
        }
        return reversed;
    }

    /**
     * Method to update the value of the element at a given index.
     *
     * @param index the position of the element to be modified
     * @param element the new value of the element to be modified
     * @return the old value of the modified element
     * @throws IndexOutOfBoundsException is the index is out of bounds
     *         (index &lt; 0 || index &gt;= size)
     * @throws NullPointerException if the given element is null
     */
    // Time complexity: O(1)
    @Override
    public E set(int index, E element) {
        checkIndex(index);
        // the Javadoc in List.java requires a NullPointerException
        // for a null element
        Objects.requireNonNull(element);
        E oldValue = elements[index];
        elements[index] = element;
        return oldValue;
    }

    /**
     * Method to get the size of the list.
     *
     * @return number of elements in this list
     */
    // Time complexity: O(1)
    @Override
    public int size() {
        return size;
    }

    /**
     * Method to sort this list.
     *
     * @param c the comparator used to sort the list; if c is null, the method
     *          uses the natural ordering to sort the list
     */
    // Time complexity: O(n^2)
    @Override
    public void sort(Comparator<? super E> c) {
        // insertion sort: elements[0..i-1] is already sorted
        for (int i = 1; i < size; i++) {
            // element to insert into the sorted part
            E key = elements[i];
            int j = i - 1;
            // shift larger elements one slot to the right
            while (j >= 0 && compare(c, elements[j], key) > 0) {
                elements[j + 1] = elements[j];
                j--;
            }

            // drop key into the gap
            elements[j + 1] = key;
        }
    }

    /**
     * Compare two elements.
     *
     * @param c the comparator, or null to use the natural ordering
     * @param a the first element
     * @param b the second element
     * @return a negative number, zero or a positive number as a is less than,
     *         equal to or greater than b
     */
    @SuppressWarnings("unchecked")
    private int compare(Comparator<? super E> c, E a, E b) {
        if (c == null) {
            return ((Comparable<E>) a).compareTo(b);
        }
        return c.compare(a, b);
    }

    /**
     * Method to extract a sublist from this list.
     *
     * @param from the index at which the sublist should start (inclusive)
     * @param to the index at which the sublist should stop (exclusive)
     * @return a copy of the specified range within this list;
     *         an empty list if from equals to
     * @throws IndexOutOfBoundsException if the indices from and to are
     *         illegal (from &lt; 0 || to &gt; size || from &gt; to)
     */
    // Time complexity: O(n)
    @Override
    public List<E> subList(int from, int to) {
        // illegal range
        if (from < 0 || to > size || from > to) {
            throw new ArrayIndexOutOfBoundsException(
                "Index out of bounds in subList");
        }
        List<E> sub = new ArrayList<>();

        // copy elements from index from (inclusive) to index to (exclusive);
        // empty if from == to
        for (int i = from; i < to; i++) {
            sub.add(elements[i]);
        }

        return sub;
    }

    /**
     * Method to get the elements of this list in an array of objects.
     *
     * @return an array of type Object that contains the elements from
     *         this list
     */
    // Time complexity: O(n)
    @Override
    public Object[] toArray() {
        // array length is size, not elements.length
        // (the array may have spare capacity)
        Object[] array = new Object[size];

        for (int i = 0; i < size; i++) {
            array[i] = elements[i];
        }

        return array;
    }

    /**
     * toString method.
     *
     * @return a formatted string containing all the elements of the list
     */
    // Time complexity: O(n)
    @Override
    public String toString() {
        if (size == 0) {
            return "[]";
        }
        StringBuilder output = new StringBuilder("[");
        for (int i = 0; i < size - 1; i++) {
            output.append(elements[i]).append(' ');
        }
        output.append(elements[size - 1]).append(']');
        return output.toString();
    }

    /**
     * Method to resize the list capacity to the actual size of the list.
     */
    @SuppressWarnings("unchecked")
    public void trimToSize() {
        if (size != elements.length) {
            E[] newElements = (E[]) new Object[size];
            for (int i = 0; i < size; i++) {
                newElements[i] = elements[i];
            }
            elements = newElements;
        }
    }

    /**
     * Inner class to implement the interface Iterator.
     */
    private class ArrayIterator implements Iterator<E> {
        /** The index of the element at which the iterator is pointing. */
        private int current = 0;

        /**
         * Check if the iterator has a next element.
         *
         * @return true if current did not reach the end of the list,
         *         false otherwise
         */
        @Override
        public boolean hasNext() {
            return current < size;
        }

        /**
         * Get the next element.
         *
         * @return the value of the current element and moves the index
         *         current to the next element
         * @throws NoSuchElementException if current is out of bounds
         */
        @Override
        public E next() {
            if (current < 0 || current >= size) {
                throw new NoSuchElementException("No more elements");
            }
            return elements[current++];
        }
    }

    /**
     * Inner class to implement the interface ListIterator for the class
     * ArrayList.
     */
    private class ArrayListIterator implements ListIterator<E> {
        /** The index of the element returned by the next call to next(). */
        private int current;

        /**
         * Default constructor.
         */
        ArrayListIterator() {
            current = 0;
        }

        /**
         * Constructor to initialize current to index.
         *
         * @param index the position where the iterator starts
         * @throws IndexOutOfBoundsException if index &lt; 0 or index &gt; size
         */
        ArrayListIterator(int index) {
            // index must be between 0 and size
            // (size means "after the last element")
            if (index < 0 || index > size) {
                throw new IndexOutOfBoundsException();
            }
            // next() will return the element at index
            current = index;
        }

        /**
         * Check if the iterator has a next element in the list.
         *
         * @return true if the end of the list is not reached
         */
        @Override
        public boolean hasNext() {
            // there is a next element while current is before the end
            return current < size;
        }

        /**
         * Get the next element.
         *
         * @return the value of the next element pointed to by the iterator
         * @throws NoSuchElementException if there is no next element
         */
        @Override
        public E next() {
            // nothing left to return
            if (!hasNext()) {
                throw new NoSuchElementException();
            }

            // return the element at current, then move forward
            return elements[current++];
        }

        /**
         * Check if the iterator has a previous element in the list.
         *
         * @return true if the beginning of the list is not reached
         */
        @Override
        public boolean hasPrevious() {
            // there is a previous element as long as current is past index 0
            return current > 0;
        }

        /**
         * Get the previous element.
         *
         * @return the value of the element pointed to by the iterator
         * @throws NoSuchElementException if there is no previous element
         */
        @Override
        public E previous() {
            // nothing before the first element
            if (!hasPrevious()) {
                throw new NoSuchElementException();
            }

            // move back first, then return that element
            current--;
            return elements[current];
        }

        @Override
        public void add(E e) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }

        @Override
        public int nextIndex() {
            throw new UnsupportedOperationException();
        }

        @Override
        public int previousIndex() {
            throw new UnsupportedOperationException();
        }

        @Override
        public void set(E e) {
            throw new UnsupportedOperationException();
        }
    }
}
