import java.util.Arrays;
import java.util.Comparator;
import java.util.Iterator;
import java.util.ListIterator;
import java.util.NoSuchElementException;
import java.util.Objects;

/**
 * LinkedList Generic Class.
 *
 * @param <E> the type of the elements in the list
 */
public class LinkedList<E> implements List<E>, Cloneable {
    /** First node of the list. */
    private Node head;
    /** Last node of the list. */
    private Node tail;
    /** Number of nodes in the list. */
    private int size;

    /**
     * Inner class Node.
     */
    private class Node {
        /** Value stored in the node. */
        private E value;
        /** Link to the node after this one (null for the tail). */
        private Node next;
        /** Link to the node before this one (null for the head). */
        private Node previous;

        /**
         * Constructor creating a node with no links.
         *
         * @param initialValue the value stored in the node
         */
        Node(E initialValue) {
            value = initialValue;
            next = null;
            previous = null;
        }
    }

    /**
     * Default Constructor.
     * Creates an empty linked list.
     */
    // Time complexity: O(1)
    public LinkedList() {
        head = null;
        tail = null;
        size = 0;
    }

    /**
     * Method to add a new element to this list.
     *
     * @param item the value to add
     * @return true if this list changed as a result of the call
     */
    // Time complexity: O(1)
    @Override
    public boolean add(E item) {
        addLast(item);
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
        // index must be between 0 and size
        if (index < 0 || index > size) {
            throw new ArrayIndexOutOfBoundsException();
        }
        if (index == 0) {
            addFirst(item);
        } else if (index == size) {
            addLast(item);
        } else {
            Node current = head;
            Node previous = null;
            for (int i = 0; i < index; i++) {
                previous = current;
                current = current.next;
            }
            Node newNode = new Node(item);
            previous.next = newNode;
            newNode.next = current;
            newNode.previous = previous;
            // the node after the new one must point back to it
            current.previous = newNode;
            size++;
        }
    }

    /**
     * Method to add a collection to this list.
     *
     * @param c the collection to be added
     * @return true if this list changed as a result of the call
     */
    // Time complexity: O(m), m = size of c
    @Override
    public boolean addAll(Collection<? extends E> c) {
        boolean changed = false;
        // add every element of c at the end of this list
        for (Iterator<? extends E> i = c.iterator(); i.hasNext();) {
            add(i.next());
            changed = true;
        }
        return changed;
    }

    /**
     * Method to add an element as the first element of this list.
     *
     * @param value element to be added
     */
    // Time complexity: O(1)
    @Override
    public void addFirst(E value) {
        Node newNode = new Node(value);
        if (head == null) {
            head = newNode;
            tail = newNode;
        } else {
            newNode.next = head;
            head.previous = newNode;
            head = newNode;
        }
        size++;
    }

    /**
     * Method to add an element as the last element of this list.
     *
     * @param item to be added
     */
    // Time complexity: O(1)
    @Override
    public void addLast(E item) {
        Node newNode = new Node(item);
        if (head == null) {
            head = newNode;
            tail = newNode;
        } else {
            tail.next = newNode;
            newNode.previous = tail;
            tail = newNode;
        }
        size++;
    }

    /**
     * Clears the list.
     * Resets the size to 0 and head and tail to null.
     */
    // Time complexity: O(1)
    @Override
    public void clear() {
        head = null;
        tail = null;
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
        // new list with its own nodes, so changes to one list
        // don't affect the other
        List<E> c = new LinkedList<>();

        // copy every element in order (deep copy: each element is cloned
        // if it can be)
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
    // Time complexity: O(n)
    @Override
    public E get(int index) {
        // index must be a valid position
        if (index < 0 || index >= size) {
            throw new ArrayIndexOutOfBoundsException();
        }
        Node node;
        // start from whichever end is closer to index
        if (index < size / 2) {
            node = head;
            for (int i = 0; i < index; i++) {
                node = node.next;
            }
        } else {
            node = tail;
            for (int i = size - 1; i > index; i--) {
                node = node.previous;
            }
        }
        return node.value;
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
        if (head == null) {
            throw new NoSuchElementException();
        }
        return head.value;
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
        if (head == null) {
            throw new NoSuchElementException();
        }
        return tail.value;
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
        // walk from the head; i is the index of node
        int i = 0;
        for (Node node = head; node != null; node = node.next) {
            // first match from the front is the answer
            if (node.value.equals(o)) {
                return i;
            }
            i++;
        }
        // not found
        return -1;
    }

    /**
     * Check if the list is empty.
     *
     * @return true if the list is empty, false otherwise
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
        return new LinkedListIterator();
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
        // walk backward from the tail so the first match is the last
        // occurrence
        int i = size - 1;
        for (Node node = tail; node != null; node = node.previous) {
            if (node.value.equals(o)) {
                return i;
            }
            i--;
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
        return new LinkedListListIterator();
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
    // Time complexity: O(n)
    @Override
    public ListIterator<E> listIterator(int index) {
        return new LinkedListListIterator(index);
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
        // index must be a valid position
        if (index < 0 || index >= size) {
            throw new ArrayIndexOutOfBoundsException();
        }
        // the ends have their own methods
        if (index == 0) {
            return removeFirst();
        }
        if (index == size - 1) {
            return removeLast();
        }
        // walk to the node at index
        Node current = head;
        for (int i = 0; i < index; i++) {
            current = current.next;
        }
        // link both neighbors to each other
        unlink(current);
        return current.value;
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
        // find the first node that holds o
        Node current = head;
        while (current != null && !current.value.equals(o)) {
            current = current.next;
        }
        // not found
        if (current == null) {
            return false;
        }
        // link both neighbors to each other (also handles head and tail)
        unlink(current);
        return true;
    }

    /**
     * Helper that removes a node from the list.
     * Fixes head, tail, both neighbors' links, and decrements the size.
     *
     * @param node the node to unlink (must be in this list)
     */
    private void unlink(Node node) {
        // node before it (or head if it was first)
        if (node.previous == null) {
            head = node.next;
        } else {
            node.previous.next = node.next;
        }
        // node after it (or tail if it was last)
        if (node.next == null) {
            tail = node.previous;
        } else {
            node.next.previous = node.previous;
        }
        size--;
    }

    /**
     * Method to remove all this list's elements that are also contained
     * in the given collection.
     *
     * @param c collection containing elements to be removed from this list
     * @return true if this list changed as a result of the call
     */
    // Time complexity: O(n*m), m = size of c
    @Override
    public boolean removeAll(Collection<?> c) {
        boolean changed = false;
        // check each node of THIS list; remove it if c contains its value
        // (removes every occurrence)
        Node current = head;
        while (current != null) {
            // save next first, because unlink changes the links
            Node next = current.next;
            if (c.contains(current.value)) {
                unlink(current);
                changed = true;
            }
            current = next;
        }
        return changed;
    }

    /**
     * Method to remove the first element of this list.
     *
     * @return the value of the removed element
     * @throws NoSuchElementException if this list is empty
     */
    // Time complexity: O(1)
    @Override
    public E removeFirst() {
        // nothing to remove if the list is empty
        if (head == null) {
            throw new NoSuchElementException();
        }
        E val = head.value;
        head = head.next;
        if (head == null) {
            // removed the only node in the list
            tail = null;
        } else {
            // the new head has nothing before it
            head.previous = null;
        }
        size--;
        return val;
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
        if (head == null) {
            throw new NoSuchElementException();
        }
        // a single node is both head and tail
        if (size == 1) {
            return removeFirst();
        }
        E value = tail.value;
        // tail.previous gives the new tail directly
        // (no traversal from head)
        tail = tail.previous;
        tail.next = null;
        size--;
        return value;
    }

    /**
     * Method to retain only the elements in this list that are contained
     * in the given collection.
     *
     * @param c collection containing elements to be retained in this list
     * @return true if this list changed as a result of the call
     */
    // Time complexity: O(n*m), m = size of c
    @Override
    public boolean retainAll(Collection<?> c) {
        boolean changed = false;
        // check each node of THIS list; remove it if c does not contain
        // its value
        Node current = head;
        while (current != null) {
            // save next first, because unlink changes the links
            Node next = current.next;
            if (!c.contains(current.value)) {
                unlink(current);
                changed = true;
            }
            current = next;
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
        List<E> reversed = new LinkedList<>();
        // walk backward from the tail using previous
        for (Node node = tail; node != null; node = node.previous) {
            reversed.add(node.value);
        }
        return reversed;
    }

    /**
     * Method to update the value of the element at a given index.
     *
     * @param index the position of the element to be modified
     * @param newVal the new value of the element to be modified
     * @return the old value of the modified element
     * @throws IndexOutOfBoundsException is the index is out of bounds
     *         (index &lt; 0 || index &gt;= size)
     * @throws NullPointerException if the given element is null
     */
    // Time complexity: O(n)
    @Override
    public E set(int index, E newVal) {
        // index must be a valid position
        if (index < 0 || index >= size) {
            throw new ArrayIndexOutOfBoundsException();
        }
        // the Javadoc in List.java requires a NullPointerException
        // for a null element
        Objects.requireNonNull(newVal);
        // walk to the node at index
        Node node = head;
        for (int i = 0; i < index; i++) {
            node = node.next;
        }
        // replace the value and return the old one
        E oldValue = node.value;
        node.value = newVal;
        return oldValue;
    }

    /**
     * Get the size of this list.
     *
     * @return the number of elements in this list
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
        // insertion sort: the nodes before i are already sorted
        // (an empty list has nothing to do)
        for (Node i = head == null ? null : head.next; i != null; i = i.next) {
            // value to insert into the sorted part
            E key = i.value;
            Node j = i.previous;
            // shift larger values one node to the right
            while (j != null && compare(c, j.value, key) > 0) {
                j.next.value = j.value;
                j = j.previous;
            }
            // drop key into the gap (the head if every value was shifted)
            Node gap = j == null ? head : j.next;
            gap.value = key;
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
     *         an empty list if from is equal to to
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
        List<E> sub = new LinkedList<>();
        // walk to the node at index from
        Node node = head;
        for (int i = 0; i < from; i++) {
            node = node.next;
        }
        // copy elements from index from (inclusive) to index to (exclusive);
        // empty if from == to
        for (int i = from; i < to; i++) {
            sub.add(node.value);
            node = node.next;
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
        // array length is the number of elements
        Object[] array = new Object[size];
        // fill it in order by walking the nodes
        int i = 0;
        for (Node node = head; node != null; node = node.next) {
            array[i] = node.value;
            i++;
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
        Node node = head;
        while (node.next != null) {
            output.append(node.value).append(' ');
            node = node.next;
        }
        output.append(node.value).append(']');
        return output.toString();
    }

    /**
     * Inner class that implements the interface Iterator.
     */
    private class LinkedListIterator implements Iterator<E> {
        /** Node whose value is returned by the next call to next(). */
        private Node current = head;

        /**
         * Check if the iterator has a next element.
         *
         * @return true if current is not null
         */
        @Override
        public boolean hasNext() {
            return current != null;
        }

        /**
         * Get the next element.
         *
         * @return the value of the node referenced by current and
         *         modifies current to hold the reference of the next node
         * @throws NoSuchElementException if current is null
         */
        @Override
        public E next() {
            if (current == null) {
                throw new NoSuchElementException();
            }
            E value = current.value;
            current = current.next;
            return value;
        }
    }

    /**
     * Inner class that implements the interface ListIterator.
     */
    private class LinkedListListIterator implements ListIterator<E> {
        /** Node whose value is returned by the next call to next(). */
        private Node current;
        /** Node whose value is returned by the next call to previous(). */
        private Node previous;

        /**
         * Default constructor.
         */
        LinkedListListIterator() {
            // start before the first element: next() returns the head
            current = head;
            previous = null;
        }

        /**
         * Constructor to initialize current to be the node at position index.
         *
         * @param index the position where the iterator starts
         * @throws IndexOutOfBoundsException if index &lt; 0 or index &gt; size
         */
        LinkedListListIterator(int index) {
            // index must be between 0 and size
            // (size means "after the last element")
            if (index < 0 || index > size) {
                throw new IndexOutOfBoundsException();
            }
            // walk to the node at index; next() will return its value
            current = head;
            for (int i = 0; i < index; i++) {
                current = current.next;
            }
            // node before current (the tail if the iterator starts at the end)
            previous = current == null ? tail : current.previous;
        }

        /**
         * Check if the iterator has a next element in the list.
         *
         * @return true if the end of the list is not reached
         */
        @Override
        public boolean hasNext() {
            // there is a next element while current is not past the end
            return current != null;
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
            // return the value at current, then move both references forward
            E value = current.value;
            previous = current;
            current = current.next;
            return value;
        }

        /**
         * Check if the iterator has a previous element in the list.
         *
         * @return true if the beginning of the list is not reached
         */
        @Override
        public boolean hasPrevious() {
            // there is a previous element as long as previous is not null
            return previous != null;
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
            // return the value at previous, then move both references
            // backward
            E value = previous.value;
            current = previous;
            previous = previous.previous;
            return value;
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
