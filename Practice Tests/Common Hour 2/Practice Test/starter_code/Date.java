import java.util.InputMismatchException;
/**
 * Class to represent a date
 */
public class Date implements Comparable<Date>{
    private int day, month, year;
    /**
     * Constructor with 3 parameters
     * @param mm the month (1 to 12)
     * @param dd the day (1 to 31)
     * @param yyyy the year
     */
    public Date(int mm, int dd, int yyyy){
        if(mm < 1 || mm > 12){
            throw new InputMismatchException("Invalid month (1 to 12): " + mm);
        }
        if(dd < 1 || dd > 31){
            throw new InputMismatchException("Invalid day (1 to 31): " + dd);
        }
        Integer y = yyyy;
        if(!y.toString().matches("\\d{4}")){
            throw new InputMismatchException("Invalid year (4 digits): " + yyyy);
        }
        day = dd;
        month = mm;
        year = yyyy;
    }
    /**
     * Constructor with one parameter
     * @param d string representing a date
     */
    public Date(String d){
        if(d.matches("\\d{1,2}/\\d{1,2}/\\d{4}")){
            String[] tokens = d.split("/");
            month = Integer.parseInt(tokens[0]);
            day = Integer.parseInt(tokens[1]);
            year = Integer.parseInt(tokens[2]);
        }
        else{
            throw new InputMismatchException("Invalid date (mm/dd/yyyy)");
        }
    }
    /**
     * getter for day
     * @return the day of this date
     */
    public int getDay(){ return day;} 
    /**
     * getter for month
     * @return the month of this date
     */
    public int getMonth(){ return month;}
    /**
     * getter for year
     * @return the year of this date
     */
    public int getYear(){ return year;}
    /**
     * setter for day
     * @param dd the new day for this date
     */
    public void setDay(int dd){ 
        if(dd < 1 || dd > 31){
            throw new InputMismatchException("Invalid day (1 to 31): " + dd);
        }
        day = dd;
    }
    /**
     * setter for month
     * @param mm the new month for this date
     */
    public void setMonth(int mm){ 
        if(mm < 1 || mm > 12){
            throw new InputMismatchException("Invalid month (1 to 12): " + mm);
        }
        month = mm;
    }
    /**
     * setter for year
     * @param yyyy the new year of this date
     */
    public void setYear(int yyyy){ 
        Integer y = yyyy;
        if(!y.toString().matches("\\d{4}")){
            throw new InputMismatchException("Invalid year (4 digits): " + yyyy);
        }
        year = yyyy;
    }
    /**
     * toString method
     * @return formatted string of this date
     */
    public String toString(){ 
        return month + "/" + day + "/" + year;
    }
    /**
     * compareTo method
     * @param d the date object being compared to this date
     * @return 0 if the two dates are identical
     *         > 0 if this date is before d
     *         < 0 if this date is after d
     */
    public int compareTo(Date d){
        if (this.year != d.year){
            return this.year - d.year;
        }
        else if (this.month != d.month){
            return this.month - d.month;
        }
        else{
            return this.day - d.day;
        }
    }
    /**
     * equals method
     * @param o the object being compared to this date
     * @return true if this date and o are identical, false otherwise
     */
    public boolean equals(Object o){
        if(o instanceof Date){
            Date d = (Date) o;
            return this.year == d.year && 
                   this.month == d.month && 
                   this.day == d.day;
        }
        return false;
    }
}