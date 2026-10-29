import java.util.Comparator;

/** Orders by price, MOST EXPENSIVE first (the opposite of the natural order). */
public class ComparatorByPrice implements Comparator<Order> {
    @Override
    public int compare(Order o1, Order o2) {
        return Double.compare(o2.getPrice(), o1.getPrice());
    }
}
