public abstract class BankAccount {
    private long number;
    private String owner;
    protected double balance;
    private static long nextNumber = 1111111111L;

    public BankAccount(String owner, double balance) {
        this.number = nextNumber++;
        this.owner = owner;
        this.balance = balance;
    }

    public BankAccount(long number, String owner, double balance) {
        this.number = number;
        this.owner = owner;
        this.balance = balance;
        if (number >= nextNumber) {
            nextNumber = number + 1;
        }
    }

    public long getNumber() {
        return number;
    }

    public String getOwner() {
        return owner;
    }

    public double getBalance() {
        return balance;
    }

    public void setNumber(long n) {
        number = n;
    }

    public void setOwner(String o) {
        owner = o;
    }

    public void deposit(double amount) {
        balance += amount;
    }

    // return false if not enough funds
    public boolean withdraw(double amount) {
        if (amount < balance) {
            balance -= amount;
            return true;
        }
        return false;
    }

    // toString method from Assignment Description 
    public String toString(){
        return String.format("%-10d\t%-30s\t$%-10.2f",number, owner, balance);
    }
}
