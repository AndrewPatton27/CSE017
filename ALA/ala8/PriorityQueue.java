import java.util.NoSuchElementException;
import java.util.Comparator;
import java.util.ArrayList;
/**
 * Class Priority Queue that implements a min heap
 */
public class PriorityQueue<E> {
    // ArrayList where the nodes of the heap are stored
    private ArrayList<E> list;
    private Comparator<E> comp;

    
    // Iteration counters for offer(E) and poll()
    public static int offerIterations = 0;
    public static int pollIterations = 0;
    /**
     * Default Constructor
     */
    public PriorityQueue(){
        list = new ArrayList<>();
        comp = null;
    }
    /**
     * Constructor with one arg
     * @param c the comparator object used to order the nodes of the heap
     */
    public PriorityQueue(Comparator<E> c){
        list = new ArrayList<>();
        comp = c;
    }
    /**
     * Method size
     * @return the number of nodes in the heap
     */
    public int size(){
        return list.size(); 
    }
    /**
     * Method isEmpty
     * @return true if the heap is empty, false otherwise
     */
    public boolean isEmpty(){
        return list.isEmpty();
    }
    /**
     * Method to empty the heap
     */
    public void clear(){
        list.clear(); 
    }
    /**
     * Method toString
     * @return a formatted string containing the values of the nodes of the heap
     */
    public String toString(){
        return list.toString();
    }
    /**
     * Method getRoot
     * @return the value of the root
     */
    public E peek(){
        if (list.isEmpty()){
            throw new NoSuchElementException();
        }
        return list.get(0);
    }
    /**
     * Method offer
     * @param value to be added to the heap
     * reconstructs the heap to keep the MinHeap properties
     */
    public void offer(E value) {
        list.add(value);
        int currentIndex = list.size()-1; 
        while(currentIndex > 0) {
            offerIterations++;

            int parentIndex = (currentIndex-1)/2;
            E current = list.get(currentIndex);
            E parent = list.get(parentIndex);
            int comparison = (comp == null)? ((Comparable)(current)).compareTo(parent): comp.compare(current, parent);
            if(comparison < 0) {
                list.set(currentIndex, parent);
                list.set(parentIndex, current);
            }
            else
                break;
            currentIndex = parentIndex;
        }
    }
    /**
     * Method poll
     * @return the value of the root, null if the heap is empty
     * reconstructs the heap to keep the MinHeap properties
     */
    public E poll() {
        if(list.isEmpty()) 
            return null;
        E removedItem = list.get(0);
        list.set(0, list.get(list.size()-1));
        list.remove(list.size()-1);
        int currentIndex = 0;
        while (currentIndex < list.size()) {
            pollIterations++;
            int left = 2 * currentIndex + 1;
            int right = 2 * currentIndex + 2;
            if (left >= list.size()) 
                break;
            int minIndex = left;
            E min = list.get(minIndex);
            if (right < list.size()){
                int comparison = (comp == null)? ((Comparable)(min)).compareTo(list.get(right)): comp.compare(min, list.get(right));
                if(comparison > 0)
                    minIndex = right;
            }
            E current = list.get(currentIndex);
            min = list.get(minIndex); 
            int comparison = (comp == null)? ((Comparable)(current)).compareTo(min): comp.compare(current, min);           
            if(comparison > 0){
                list.set(minIndex, current);
                list.set(currentIndex, min);
                currentIndex = minIndex;
            }
            else
                break;
        }
        return removedItem;
    }

    /**
     * Method height
     * @return the height of the binary tree representing the heap
     */
    // Time complexity: O(n), every node is visited once
    public int height() {
        return height(0); // empty heap -> 0
    }

    /**
     * Recursive helper for height
     * @param i index of the root of the subtree
     * @return the height of the subtree rooted at index (0 if empty)
     */
    private int height(int i) {
        if (i >= list.size())
            return 0;

        if (list.get(i) == null) 
            return 0;


        int left = height(2 * i + 1);
        int right = height(2 * i + 2);
        return 1 + Math.max(left, right);
    }

    /**
     * Method isBalanced
     * @return true if at every node the heights of the left and right subtrees differ by at most 1
     */
    // Time complexity: O(n log n), height() is called at every node and costs the size of that subtree
    public boolean isBalanced() {
        return isBalanced(0);
    }

    /**
     * Recursive helper for isBalanced
     * @param i index of the root of the subtree
     * @return true if the subtree rooted at i is balanced (empty subtree is balanced)
     */
    private boolean isBalanced(int i) {
        if (i >= list.size())
            return true;
        int left = 2 * i + 1;
        int right = 2 * i + 2;
        if (Math.abs(height(left) - height(right)) > 1)
            return false;
        return isBalanced(left) && isBalanced(right);
    }
}
