import java.util.Comparator;

/** Orders by customer name A-Z. */
public class ComparatorByCustomer implements Comparator<Order> {
    @Override
    public int compare(Order o1, Order o2) {
        return o1.getCustomer().compareTo(o2.getCustomer());
    }
}
