import java.util.Comparator;

public class ComparatorByRecipient implements Comparator<Message> {
    public int compare(Message m1, Message m2) {
        return m1.getRecipient().compareTo(m2.getRecipient());
    }  
}
