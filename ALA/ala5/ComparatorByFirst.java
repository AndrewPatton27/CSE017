import java.util.Comparator;

public class ComparatorByFirst<E1 extends Comparable<E1>, E2> implements Comparator<Pair<E1, E2>> {
    @Override
    public int compare(Pair<E1, E2> p1, Pair<E1, E2> p2) {
        return p1.getFirst().compareTo(p2.getFirst());
    }
}
