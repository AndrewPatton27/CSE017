/**
 * Class to represent a message
 */
public class Message implements Comparable<Message>{
    private Date date;
    private String text;
    private String sender;
    private String recipient;
    /**
     * Constructor with 4 parameters
     * @param d the date of teh message
     * @param t the contents of the message
     * @param s the sender of the message
     * @param r teh recipient pf the message
     */
    public Message(Date d, String t, String s, String r){
        date = d;
        text = t;
        sender = s;
        recipient = r;
    }
    /**
     * getter for date
     * @return the date of this messsage
     */
    public Date getDate(){ return date;}
    /**
     * getter for text
     * @return the text of this messsage
     */
    public String getText(){ return text;}
    /**
     * getter for sender
     * @return the sender of this messsage
     */
    public String getSender(){ return sender;}
    /**
     * getter for recipient
     * @return the recipient of this messsage
     */
    public String getRecipient(){ return recipient;}
    /**
     * setter for date
     * @param d the new date for this message
     */
    public void setDate(Date d){ date = d;}
    /**
     * setter for text
     * @param d the new text for this message
     */
    public void setText(String t){ text = t;}
    /**
     * setter for sender
     * @param d the new sender for this message
     */
    public void setSender(String s){ sender = s;}
    /**
     * setter for recipient
     * @param d the new recipient for this message
     */
    public void setRecipent(String r){ recipient = r;}
    /**
     * toString method
     * @return formatted string of this message
     */
    public String toString(){
        return String.format("%s\t%-30s\t%-10s\t%-10s", date.toString(), text, sender, recipient);
    }
    /**
     * compareTo method
     * @param d the message object being compared to this message
     * @return 0 if the two messages have the same sender
     *         > 0 if this message's sender is before m's sender in alphabetical order
     *         < 0 if this message's sender  is after m's sender in alphabetical order
     */
    public int compareTo(Message m){
        return this.sender.compareTo(m.sender);
    }
    /**
     * equals method
     * @param o the object being compared to this message
     * @return true if the senders of this mesage and o are equal, false otherwise
     */
    public boolean equals(Object o){
        if(o instanceof Message){
            Message m = (Message) o;
            return this.sender.equals(m.sender);
        }
        return false;
    }

}