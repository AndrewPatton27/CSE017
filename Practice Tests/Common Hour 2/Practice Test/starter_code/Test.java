import java.util.List;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Iterator;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Test{
    public static void main(String[] args){
        // create two types of lists
        List<Message>[] messages = new List[2];
        messages[0] = new ArrayList<Message>();
        messages[1] = new LinkedList<Message>();
        // fill the two lists with data from text files
        readMessages(messages[0], "list1.txt");
        readMessages(messages[1], "list2.txt");
        // Test the search method findMessages
        Date d = new Date("01/26/2022");
        List<Message> list = findMessages(messages[0], d);
        if(list == null){
            System.out.println("No messages found for the date " + d);
        }
        else{
            System.out.println(list.size() + " messages found for the date " + d + ":");
            print(list);
            System.out.println();
        }
        d = new Date("01/22/2022");
        list = findMessages(messages[1], d);
        if(list == null){
            System.out.println("No messages found for the date " + d);
        }
        else{
            System.out.println(list.size() + " messages found for the date " + d + ":");
            print(list);
            System.out.println();
        }
        // Test the method union
        List<Message> unionList =  new ArrayList<>();
        union(messages[0], messages[1], unionList);
        System.out.println("Union of the two lists:");
        print(unionList);
        System.out.println();
        // Test the method intersection
        List<Message> intersectionList =  new LinkedList<>();
        intersection(messages[0], messages[1], intersectionList);
        System.out.println("Intersection of the two lists:");
        print(intersectionList);
        System.out.println();
        // Test the method unionNoDuplicates
        List<Message> unionNoDuplicatesList =  new ArrayList<>();
        unionNoDuplicates(messages[0], messages[1], unionNoDuplicatesList);
        System.out.println("Union of the two lists without duplicates:");
        print(unionNoDuplicatesList);
        System.out.println();
        // Test the comparator classes
        unionList.sort(null);
        System.out.println("Union List sorted by sender:");
        print(unionList);
        System.out.println();
        unionList.sort(new ComparatorByRecipient());
        System.out.println("Union List sorted by recipient:");
        print(unionList);
        System.out.println();
        unionList.sort(new ComparatorByDate());
        System.out.println("Union List sorted by date:");
        print(unionList);
        System.out.println();
    }
    /**
     * Method to read a list of message from a text file
     * @param list where the read messsages are stored
     * @param filename the name of the file to read the messages from
     */
    public static void readMessages(List<Message> list, String filename){
        try{
            Scanner read = new Scanner(new File(filename));
            while(read.hasNextLine()){
                String line = read.nextLine();
                String[] tokens = line.split(",");
                Date date = new Date(tokens[0]);
                list.add(new Message(date, tokens[1], tokens[2], tokens[3]));
            }
            read.close();
        }
        catch(FileNotFoundException e){
            System.out.println("File \"" + filename + "\" not found");
        }
    }
    /**
     * Method to combine the elements of two lists
     * @param list1 the first list
     * @param list2 the second list
     * @param list the list that will contain the union of list1 and list2
     */
    // O(n)
    public static <E> void union(List<E> list1, List<E> list2, List<E> list){
        Iterator<E> i1 = list1.iterator();
        Iterator<E> i2 = list2.iterator();

        while (i1.hasNext()) {
            list.add(i1.next());
        }
        while (i2.hasNext()) {
            list.add(i2.next());
        }
    }
    /**
     * Method to determine the instersection of two lists
     * @param list1 the first list
     * @param list2 the second list
     * @param list the list containing the elements that are in both list1 and list2
     */
    // O(n^2)
    public static <E> void intersection(List<E> list1, List<E> list2, List<E> list){
        Iterator<E> i1 = list1.iterator();

        while (i1.hasNext()) {
            E element = i1.next();
            if (list2.contains(element)) {
                list.add(element);
            }
        }
    }

    /**
     * Method to combine the elements of two list without the duplicates
     * @param list1 the first list
     * @param list2 the second list
     * @param list the list containing the elements in list1 and list2 without any duplicates
     */
    // combines list1 and list2 in list without duplicates
    // O(n^2)
    public static <E> void unionNoDuplicates(List<E> list1, List<E> list2, List<E> list){
        Iterator<E> i1 = list1.iterator();

        while (i1.hasNext()) {
            E element = i1.next();
            if (!list.contains(element)) { // incase list1 has duplicates within its self
                list.add(element);
            }
        }

        Iterator<E> i2 = list2.iterator();

        while (i2.hasNext()) {
            E element = i2.next();
            if (!list.contains(element)) {
                list.add(element);
            }
        }
    }

    /**
     * Method to search for messages sent on a given date
     * @param list the list of all the message
     * @param d the given data
     * @return list of messages whose dates are equal to d
     *         null if no messages were found
     * must be recursive and use an iterator
     * Use a helper method if you need to
     */
    // returns the list of  messages sent at the date d, null if no messages were found
    // O(n)
    public static ArrayList<Message> findMessages(List<Message> list, Date d) {
        ArrayList<Message> validMessages = new ArrayList<Message>();

        validMessages = findMessagesHelper(list.iterator(), d, validMessages);
        
        return validMessages.size() > 0 ? validMessages : null;
    }

    private static ArrayList<Message> findMessagesHelper(Iterator<Message> iList, Date d, ArrayList<Message> validMessages) {
        if (! iList.hasNext()) {
            return validMessages;
        }

        Message m = iList.next();
        if(m.getDate().equals(d)) {
            validMessages.add(m);
        }

        return findMessagesHelper(iList, d, validMessages);
    }

    /**
     * Method to print a list one element per line
     * @param list to be printed
     */
    public static <E> void print(List<E> list){
        Iterator<E> iter = list.iterator();
        while(iter.hasNext()){
            System.out.println(iter.next());
        }
    }
}   