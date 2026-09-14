public class Person {
    private int id;
    private String name;
    private String adress;
    private String phoneNumber;
    private String email;

    public Person(int id, String name, String adress, String phoneNumber, String email) {
        this.id = id;
        this.name = name;
        this.adress = adress;
        this.phoneNumber = phoneNumber;
        this.email = email;
    }

    public int getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public String getAdress() {
        return adress;
    }
    public String getPhoneNumber() {
        return phoneNumber;
    }
    public String getEmail() {
        return email;
    }
    public String toString() {
        return "ID: " + id + "\nName: " + name + "\nAdress: " + adress + "\nPhone Number: " + phoneNumber + "\nEmail: " + email;
    }
    public void setId(int id) {
        this.id = id;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setAdress(String adress) {
        this.adress = adress;
    }
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    public int compaireTo(Person other) {
        if (id > other.getId()) {
            return 1;
        } else if (id < other.getId()) {
            return -1;
        } else {
            return 0;
        }
    }
}
