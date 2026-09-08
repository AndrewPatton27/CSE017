/**
 * Represents behavior common to any object that can be evaluated for
 * closure/eligibility for closing, independent of its position in the
 * {@link BankAccount} class hierarchy.
 */
public interface Closeable {

    /**
     * Determines whether this object is eligible to be closed.
     *
     * @return true if this object may be closed, false otherwise
     */
    boolean isCloseable();
}
