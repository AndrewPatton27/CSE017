import java.util.Iterator;
/**
 * Interface Collection
 */
public interface Collection<E>{
    /**
     * Method to add an new element to the collection
     * @param element the value to add
     * @return true if this collection changed as a result of the call
     */
    public boolean add(E element);
    /**
     * Method to add a collection to this collection
     * @param c the collection to be added
     * @return true if this collection changed as a result of the call
     */
    public boolean addAll(Collection<? extends E> c);
    /**
     * Method to clear this collection
     */
    public void clear();
    /**
     * Method to search for an object in this collection
     * @param o the object being searched in this collection
     * @return true if this collection contains the given object
     */
    public boolean contains(Object o);
    /**
     * Method to search for a given collection in this collection
     * @param c collection whose presence in this collection is to be tested
     * @return true if this collection contains all of the elements in the given collection
     */
    public boolean containsAll(Collection<?> c);
    /**
     * Method to compare two collections
     * @param o the object to be compared to this collection
     * @return true if this collection is equal to the given object
     */
    public boolean equals(Object o);
    /**
     * Method to check if this collection is empty
     * @return true if this collection is empty
     */
    public boolean isEmpty();
    /**
     * Method to get an iterator for this collection
     * @return an iterator over the elements of this collection
     */
    public Iterator<E> iterator();
    /**
     * Method to remove a given object from this collection
     * @param o the object to find and remove
     * @return true if o was found and removed
     */
    public boolean remove(Object o);
    /**
     * Method to remove all this collection's elements that are also contained in the given collection
     * @param c collection containing elements to be removed from this collection
     * @return true if this collection changed as a result of the call
     */
    public boolean removeAll(Collection<?> c);
    /**
     * Method to retain only the elements in this collection that are contained in the given collection
     * @param c collection containing elements to be retained in this collection
     * @return true if this collection changed as a result of the call
     */
    public boolean retainAll(Collection<?> c);
    /**
     * Method to get the size of the collection
     * @return number of elements in this collection
     */
    public int size();
    /**
     * Method to get an array containing all the elements of this collection
     * @return an aray of type Object containing all of the elements in this collection
     */
    public Object[] toArray();
}