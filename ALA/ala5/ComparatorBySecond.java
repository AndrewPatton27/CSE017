import java.util.Comparator;

public class ComparatorBySecond<E1, E2 extends Comparable<E2>> implements Comparator<Pair<E1, E2>> {
    @Override
    public int compare(Pair<E1, E2> p1, Pair<E1, E2> p2) {
        return p1.getSecond().compareTo(p2.getSecond());
    }
}
