/**
 * Class to represent a customer order.
 * Natural ordering (Comparable): by price, cheapest first.
 * equals: two orders are equal if customer, item and price are all equal.
 */
public class Order implements Comparable<Order> {
    private int id;
    private String customer;
    private String item;
    private double price;

    /**
     * Constructor.
     * @param id the order number
     * @param customer who ordered
     * @param item what was ordered
     * @param price the price in dollars
     */
    public Order(int id, String customer, String item, double price) {
        this.id = id;
        this.customer = customer;
        this.item = item;
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public String getCustomer() {
        return customer;
    }

    public String getItem() {
        return item;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return String.format("#%-4d %-8s %-12s $%8.2f", id, customer, item, price);
    }

    /**
     * Natural ordering by price.
     * @param o the order being compared to this order
     * @return negative if this order is cheaper, 0 if same price, positive if more expensive
     */
    @Override
    public int compareTo(Order o) {
        return Double.compare(this.price, o.price);
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof Order) {
            Order other = (Order) o;
            return customer.equals(other.customer) && item.equals(other.item) && price == other.price;
        }
        return false;
    }

    @Override
    public int hashCode() {
        return customer.hashCode() + item.hashCode();
    }
}
