/**
 * Behavior shared by any object that can be checked for eligibility to be
 * closed, independent of where it sits in a class hierarchy.
 */
public interface Closeable {

    /**
     * Reports whether this object is eligible to be closed.
     *
     * @return true if it may be closed, false otherwise
     */
    boolean isCloseable();
}
