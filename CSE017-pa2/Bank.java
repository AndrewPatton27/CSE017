import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Scanner;

// bank holding open and closed accounts. can load/save from a file, search,
// sort, update accounts by type, and close accounts once their balance is low
public class Bank {

    private static final int CAPACITY = 50;
    private BankAccount[] accounts;
    private int count;
    private BankAccount[] closed;
    private int closedCount;

    public Bank() {
        accounts = new BankAccount[CAPACITY];
        count = 0;
        closed = new BankAccount[CAPACITY];
        closedCount = 0;
    }

    public Bank(String filename) {
        this();
        read(filename);
    }

    // reads accounts from a file line by line, splits on commas, and builds
    // a Checking/Savings/Investment based on the first token. lines ending in
    // "closed" go in the closed array instead of accounts. bad lines get
    // an error printed and are skipped instead of crashing
    private void read(String filename) {
        try (Scanner input = new Scanner(new File(filename))) {
            while (input.hasNextLine()) {
                String line = input.nextLine();
                if (line.isBlank()) { continue; }

                String[] attributes = line.split(",");
                String type = attributes[0];

                if (!type.equals("Checking") && !type.equals("Savings") && !type.equals("Investment")) {
                    printError(line, "Invalid type of account: " + type
                            + ", should be [Checking|Savings|Investment]");
                    continue;
                }

                boolean isClosed = attributes[attributes.length - 1].equals("closed");

                // Checking has 4 fields, Savings/Investment have 5, closed adds one more
                int expected = type.equals("Checking") ? 4 : 5;
                if (isClosed) { expected++; }
                if (attributes.length != expected) {
                    printError(line, "Invalid number of fields: expected " + expected
                            + ", found " + attributes.length);
                    continue;
                }

                long number;
                try {
                    number = Long.parseLong(attributes[1]);
                } catch (NumberFormatException e) {
                    printError(line, "Invalid format for the account number \"" + attributes[1]
                            + "\", must be a long integer");
                    continue;
                }

                double balance;
                try {
                    balance = Double.parseDouble(attributes[3]);
                } catch (NumberFormatException e) {
                    printError(line, "Invalid format for the balance \"" + attributes[3]
                            + "\", must be a double");
                    continue;
                }

                try {
                    BankAccount ba;
                    if (type.equals("Savings")) {
                        ba = new Savings(number, attributes[2], balance, Double.parseDouble(attributes[4]));
                    }
                    else if (type.equals("Investment")) {
                        ba = new Investment(number, attributes[2], balance, attributes[4]);
                    }
                    else {
                        ba = new Checking(number, attributes[2], balance);
                    }

                    if (isClosed) { addClosed(ba); }
                    else { add(ba); }
                }
                catch (BadFormatException e) {
                    printError(line, e.getMessage());
                }
                catch (NumberFormatException e) {
                    printError(line, "Invalid format for the interest rate \"" + attributes[4]
                            + "\", must be a double");
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("File " + filename + " not found.");
        }
    }

    private void printError(String line, String message) {
        System.out.println("Error at line: " + line);
        System.out.println(message);
        System.out.println();
    }

    // writes open and closed accounts back to the file in the same csv format
    public void save(String filename) {
        try (PrintWriter output = new PrintWriter(filename)) {
            for (int i = 0; i < count; i++) {
                output.println(accounts[i].fileString());
            }
            // closed accounts get an extra "closed" token so read() can tell them apart
            for (int i = 0; i < closedCount; i++) {
                output.println(closed[i].fileString() + ",closed");
            }
        } catch (FileNotFoundException e) {
            System.out.println("Could not write to file " + filename);
        }
    }

    public int size() {
        return count;
    }

    public int closedSize() {
        return closedCount;
    }

    public void add(BankAccount ba) {
        accounts[count] = ba;
        count++;
    }

    public BankAccount find(long number) {
        for (int i = 0; i < count; i++) {
            if (accounts[i].getNumber() == number) {
                return accounts[i];
            }
        }
        return null;
    }

    // shifts everything after the removed account left by one to close the gap
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

    private void addClosed(BankAccount ba) {
        closed[closedCount] = ba;
        closedCount++;
    }

    public void sort() {
        Arrays.sort(accounts, 0, count);
    }

    public void updateSavings() {
        int updated = 0;
        for (int i = 0; i < count; i++) {
            if (accounts[i] instanceof Savings) {
                ((Savings) accounts[i]).applyMonthlyInterest();
                updated++;
            }
        }
        System.out.printf("%d Savings accounts updated\n", updated);
    }

    public void updateInvestment(double risk) {
        int updated = 0;
        for (int i = 0; i < count; i++) {
            if (accounts[i] instanceof Investment) {
                ((Investment) accounts[i]).getProfitOrLoss(risk);
                updated++;
            }
        }
        System.out.printf("%d Investment accounts were updated\n", updated);
    }

    public void viewChecking() {
        int found = 0;
        for (int i = 0; i < count; i++) {
            if (accounts[i] instanceof Checking) {
                System.out.println(accounts[i]);
                found++;
            }
        }
        System.out.printf("There are %d Checking accounts\n", found);
    }

    public void viewSavings() {
        int found = 0;
        for (int i = 0; i < count; i++) {
            if (accounts[i] instanceof Savings) {
                System.out.println(accounts[i]);
                found++;
            }
        }
        System.out.printf("There are %d Savings accounts\n", found);
    }

    public void viewInvestment() {
        int found = 0;
        for (int i = 0; i < count; i++) {
            if (accounts[i] instanceof Investment) {
                System.out.println(accounts[i]);
                found++;
            }
        }
        System.out.printf("There are %d Investment accounts\n", found);
    }

    public void viewCloseable() {
        int found = 0;
        for (int i = 0; i < count; i++) {
            if (accounts[i].isCloseable()) {
                System.out.println(accounts[i]);
                found++;
            }
        }
        if (found == 0) {
            System.out.println("There are no closeable accounts");
        }
        else {
            System.out.printf("%d accounts are closeable\n", found);
        }
    }

    public void viewClosed() {
        for (int i = 0; i < closedCount; i++) {
            System.out.println(closed[i]);
        }
        System.out.printf("%d accounts are closed\n", closedCount);
    }

    // moves every closeable account out of accounts and into closed. since
    // removing shifts everything left, i has to step back one so it doesn't
    // skip the account that just shifted into its spot
    public void closeAccounts() {
        int initClosedNum = closedCount;
        for (int i = 0; i < count; i++) {
            BankAccount ba = accounts[i];

            if (! ba.isCloseable()) { continue; }

            addClosed(ba);

            for (int j = i; j < count - 1; j++) {
                accounts[j] = accounts[j + 1];
            }
            count--;
            accounts[count] = null;

            i--;
        }

        int justClosed = closedCount - initClosedNum;
        if (justClosed == 0) {
            System.out.println("No account closed: there are no closeable accounts");
        }
        else {
            System.out.printf("%d accounts were closed\n", justClosed);
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
