import java.util.Iterator;
import java.util.Comparator;
import java.util.NoSuchElementException;

/**
    LinkedList Generic Class
 */
public class LinkedList<E> {
    // Data members
    private Node head, tail;
    private int size;

    // Iteration counters: incremented once per loop iteration inside
    // contains, add(int, E), remove(Object), and sort
    public static int cIterations = 0; // contains
    public static int rIterations = 0; // remove(Object)
    public static int aIterations = 0; // add(int, E)
    public static int sIterations = 0; // sort
    /**
        Inner class Node
    */
    private class Node {
        E value;
        Node next;
        Node(E initialValue) {
            value = initialValue;
            next = null;
        }
    }
    /**
        Default Constructor
        creates an empty linkedlist
    */
    public LinkedList() {
        head = tail = null;
        size = 0;
    }
    /**
        Adding a value at the head of the list
        @param value to be added
        @return true if the operation was successful
    */
    public boolean addFirst(E value) {
        Node newNode = new Node(value);
        if (head == null) {
            head = tail = newNode;
        }
        else {
            newNode.next = head;
            head = newNode;
        }
        size++;
        return true;
    }
    /**
        Adding a value at the tail of the list
        @param value to be added
        @return true if the operation was successful
    */
    public boolean addLast(E item) {
        Node newNode = new Node(item);
        if (head == null) {
            head = tail = newNode;
        }
        else{
            tail.next = newNode;
            tail = newNode;
        }
        size++;
        return true;
    }
    /**
        Adding a value at the tail of the list
        calls addLast
        @param value to be added
        @return true if the operation was successful
    */
    public boolean add(E item) {
        return addLast(item);
    }

    /**
        Get the value of the node at the head of the list
        @return value of the node at the head
        @throws NoSuchElementException if the list is empty
    */
    public E getFirst() {
        if (head == null)
            throw new NoSuchElementException();
        return head.value;
    }
    /**
        Get the value of the node at the tail of the list
        @return value of the node at the tail
        @throws NoSuchElementException if the list is empty
     */
    public E getLast() {
        if (head == null)
            throw new NoSuchElementException();
        return tail.value;
    }
    /**
        Removes the node at the head of the list
        @return true if the remove operation was successful and decrements the size
        @throws NoSuchElementException if the list is empty
     */
    public boolean removeFirst() {
        if (head == null)
            throw new NoSuchElementException();
        head = head.next;
        if (head == null) // removed the only node in the LL
            tail = null;
        size--;
        return true;
    }
    /**
        Removes the node at the tail of the list
        @return true if the remove operation was successful and decrements the size
        @throws NoSuchElementException if the list is empty
    */
    public boolean removeLast() {
        if (head == null)
            throw new NoSuchElementException();
        if (size == 1)
            return removeFirst();
        Node current = head;
        while(current.next != tail){
            current = current.next;
        }
        current.next = null;
        tail = current;
        size--;
        return true;
    }
    /**
        Search method
        @param o the object to look for
        @return true if o is found in the list, false otherwise
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
        Remove method
        @param o the object to remove
        @return true if o was found and removed, false if not found
    */
    // Time complexity: O(n)
    public boolean remove(Object o) {
        if (head == null)
            return false;

        if (head.value.equals(o))
            return removeFirst();

        for (Node node = head; node.next != null; node = node.next) {
            rIterations++;
            if (node.next.value.equals(o)) {
                if (node.next == tail)
                    tail = node;
                node.next = node.next.next;
                size--;
                return true;
            }
        }
        return false;
    }
    /**
        Insertion method
        @param index position where the new node is added
        @param item value of the new node
        @return true when the node is added
        @throws IndexOutOfBoundsException if index < 0 or index > size
    */
    // Time complexity: O(n)
    public boolean add(int index, E item) {
        if (index > size || index < 0)
            throw new IndexOutOfBoundsException();

        if (index == 0)
            return addFirst(item);
        if (index == size)
            return addLast(item);

        Node n = head;
        for (int i = 0; i < index - 1; i++) {
            aIterations++;
            n = n.next;
        }

        Node newN = new Node(item);
        newN.next = n.next;
        n.next = newN;
        size++;

        return true;
    }
    /**
        Sort method (selection sort)
        @param c comparator; if null use natural ordering (compareTo), else use c.compare
    */
    // Time complexity: O(n^2)
    public void sort(Comparator<E> c) {
        for (Node i = head; i != null && i.next != null; i = i.next) {
            Node min = i;
            for (Node j = i.next; j != null; j = j.next) {
                sIterations++;
                int cmpResult = (c == null)
                    ? ((Comparable<E>) j.value).compareTo(min.value)
                    : c.compare(j.value, min.value);
                if (cmpResult < 0)
                    min = j;
            }
            E temp = i.value;
            i.value = min.value;
            min.value = temp;
        }
    }

    /**
        Sort using the natural ordering
    */
    public void sort() {
        sort(null);
    }
    /**
        toString method
        @return a formatted string that contains the values of all the nodes in the list
    */
    public String toString() {
        String output = "[";
        Node node = head;
        while (node != null) {
            output += node.value + " ";
            node = node.next;
        }
        output += "]";
        return output;
    }
    /**
        clear method
        restes size to 0 and head and tail to null
    */
    public void clear() {
        head = tail = null;
        size = 0;
    }
    /**
        isEmpty method
        @return true if the list is empty
    */
    public boolean isEmpty() {
        return (size == 0);
    }
    /**
        size method
        @return the number of nodes in the list
    */
    public int size() {
        return size;
    }

    /**
        iterator method
        @override iterator() from the interface Collection
        @return an iterator object pointing to the first value in the list
    */
    public Iterator<E> iterator() {
        return new LinkedListIterator();
    }
    /**
        Inner class that implements the interface Iterator
    */
    private class LinkedListIterator implements Iterator<E> {
        private Node current = head;
        /**
            hasNext method
            @return true if the current is not null
         */
        public boolean hasNext() {
            return (current != null);
        }
        /**
            next method
            @return the value of the node referenced by current and 
                    modifies current to hold the reference of the next node
            @throws NoSuchElementException if current is null
         */
        public E next() {
            if (current == null)
                throw new NoSuchElementException();
            E value = current.value;
            current = current.next;
            return value;
        }
    }
}