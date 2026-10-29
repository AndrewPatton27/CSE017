import java.util.ArrayList;

/**
 * Generic stack backed by an ArrayList (end of the list = top).
 * pop/peek on an empty stack throw RuntimeException("empty stack").
 * @param <E> element type
 */
public class MyStack<E> {
    private ArrayList<E> elements = new ArrayList<>();

    public int size() {
        return elements.size();
    }

    public boolean isEmpty() {
        // TODO: write this method
        return false;
    }

    public void push(E item) {
        // TODO: write this method
        // (nothing to return)
    }

    public E peek() {
        // TODO: write this method
        return null;
    }

    public E pop() {
        // TODO: write this method
        return null;
    }

    @Override
    public String toString() {
        return "Stack: " + elements;
    }
}
