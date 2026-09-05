public class Bank {
    // Max amount of bank accounts
    private static final int CAPACITY = 50;

    private BankAccount[] accounts;
    // Current amount of bank accounts
    private int count;

    public Bank() {
        accounts = new BankAccount[CAPACITY];
        count = 0;
    }

    public int size() {
        return count;
    }

    public void add(BankAccount ba) {
        accounts[count] = ba;
        count++;
    }

    public BankAccount find(long number) {
        // Only works for current implementation of BankAcount number because in order based on number
        long firstBANumber = 1111111111L;
        int baIndex =  (int)(number-firstBANumber);
        try {
            return accounts[baIndex];
        }
        catch (IndexOutOfBoundsException e) {return null;}
        // Alternitive solution
        // for (int i = 0; i < count; i++) {
        //     if (accounts[i].getNumber() == number) {
        //         return accounts[i];
        //     }
        // }
        // return null;
    }

    public BankAccount remove(long number) {
        for (int k = 0; k < count; k++) {
            if (accounts[k].getNumber() == number) {
                BankAccount removed = accounts[k];
                for (int i = k; i < count - 1; i++) {
                    accounts[i] = accounts[i + 1];
                }
                count--;
                accounts[count] = null;
                return removed;
            }
        }
        return null;
    }

    // insertionSort from assignent description
    public void sort() {
        for (int i = 1; i < count; i++) {
            // Insert element i in the sorted sub-list
            BankAccount current = accounts[i];
            int j = i;
            while (j > 0 && current.getBalance() < accounts[j - 1].getBalance()) {
                // Shift element (j-1) into element (j)
                accounts[j] = accounts[j - 1];
                j--;
            }
            // Insert current at position j
            accounts[j] = current;
        }
    }

    public String toString() {
        String s = "";
        s += String.format("%-16s%-16s%-32s%-11s     %s", "Type", "Number", "Owner", "Balance", "Interest/Type") + "\n";
        for (int i = 0; i < count; i++) {
            s += (accounts[i].toString()) + "\n";
        }
        return s;
    }
}
