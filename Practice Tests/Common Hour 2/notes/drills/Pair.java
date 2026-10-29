/**
 * Generic class with two type parameters (Generics lecture, slide 20).
 * @param <E1> type of the first value
 * @param <E2> type of the second value
 */
public class Pair<E1, E2> {
    private E1 first;
    private E2 second;

    public Pair(E1 first, E2 second) {
        this.first = first;
        this.second = second;
    }

    public E1 getFirst() {
        return first;
    }

    public E2 getSecond() {
        return second;
    }

    /** Returns a NEW pair with the values swapped: Pair<E2, E1>. */
    public Pair<E2, E1> swapped() {
        // TODO: write this method
        return null;
    }

    @Override
    public String toString() {
        return "(" + first + ", " + second + ")";
    }
}
