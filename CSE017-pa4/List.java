import java.util.ListIterator;
import java.util.Comparator;
/**
 * Interface List
 */
public interface List<E> extends Collection<E>{
    /**
     * Method to add an element at a given index
     * @param index the position at which element will be inserted
     * @param element the value of the element to be added
     * @throws IndexOutOfBoundsException if index is negative or greater than this list's size
     */
    public void add(int index, E element);
    /**
     * Method to add an element as the first element of this list
     * @param element to be added
     */
    public void addFirst(E element);
    /**
     * Method to add an element as the last element of this list
     * @param element to be added
     */
    public void addLast(E element);
    /**
     * return the element at the specified position
     * @param index the position of the element to get
     * @return the value of the element at the given position
     * @throws IndexOutOfBoundsException if index is out of bounds (index < 0 || index >= size())
     */
    public E get(int index);
    /**
     * Method to get the first element of this list
     * @return the value of the first element in this list
     * @throws NoSuchElementException if this list is empty
     */
    public E getFirst();
    /**
     * Method to get the last element of this list
     * @return the value of the last element in this list
     * @throws NoSuchElementException if this list is empty
     */
    public E getLast();
    /**
     * Method to get the index of the first occurrence of the given object
     * @param o element to search for
     * @return the index of the first occurrence of o, -1 if o was not found
     * @throws ClassCastException if the type of the given object is not compatible with this list's type
     * @throws NullPointerException if the given object is null
     */
    public int indexOf(Object o);
    /**
     * Method to get the index of the last occurrence of the given object
     * @param o element to search for
     * @return the index of the last occurrence of o, -1 if o was not found
     * @throws ClassCastException if the type of the given object is not compatible with this list's type
     * @throws NullPointerException if the given object is null
     */
    public int lastIndexOf(Object o);
    /**
     * Method to get a list iterator over the elements of this list
     * @return a ListIterator object starting at the first element of this list
     */
    public ListIterator<E> listIterator();
    /**
     * Method to get a list iterator over the elements of this list
     * @param index the position at which the list iterator should start
     * @return a ListIterator object starting at the element at the given index
     *         the given index indicates the first element that would be returned by an initial call to next()
     *         an initial call to previous() would return the element at the given index minus one
     * @throws IndexOutOfBoundsException if the index is out of bounds (index < 0 || index > size())
     */
    public ListIterator<E> listIterator(int index);
    /**
     * Method to remove an element at a given index
     * @param index of the element to be removed
     * @return the value of the element that was removed
     * @throws IndexOutOfBoundsException is the index is out of bounds (index < 0 || index >= size)
     */
    public E remove(int index);
    /**
     * Method to remove the first element of this list
     * @return the value of the removed element
     * @throws NoSuchElementException if this list is empty
     */
    public E removeFirst();
    /**
     * Method to remove the last element of this list
     * @return the value of the removed element
     * @throws NoSuchElementException if this list is empty
     */
    public E removeLast();
    /**
     * Method to return a reversed copy of this list
     * @return a reversed-ordered copy of this list
     */
    public List<E> reversed();
    /**
     * Method to update the value of the element at a given index
     * @param index the position of the element to be modified
     * @param element the new value of the element to be modified
     * @return the old value of the modified element
     * @throws IndexOutOfBoundsException is the index is out of bounds (index < 0 || index >= size) 
     * @throws NullPointerException if the given element is null
     */
    public E set(int index, E element);
    /**
     * Method to sort this list
     * @param c the comparator used to sort the list
     *          if c is null, the method uses the natural ordering to sort the list
     */
    public void sort(Comparator<? super E> c);
    /**
     * Method to extract a sublist form this list
     * @param from the index at which the sublist should start (inclusive)
     * @param to the index at which the sublit should stop (exclusive)
     * @return a copy of the specified range within this list
     * @throws IndexOutOfBoundsException if the indices from and to are illegal
     *         (from < 0 || to > size || from > to)
     */
    public List<E> subList(int from, int to);
}