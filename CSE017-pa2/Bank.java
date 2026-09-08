import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Scanner;

/**
 * A bank holding two fixed-size groups of accounts: those still open and
 * those that have been closed. Accounts can be loaded from and saved to a
 * text file, searched, sorted, updated by type, and closed once their
 * balance falls low enough.
 */
public class Bank {

    /** Maximum number of accounts either array can hold. */
    private static final int CAPACITY = 50;

    /** Open (active) bank accounts. */
    private BankAccount[] accounts;

    /** How many slots of {@link #accounts} are in use. */
    private int count;

    /** Closed bank accounts. */
    private BankAccount[] closed;

    /** How many slots of {@link #closed} are in use. */
    private int closedCount;

    /**
     * Creates an empty bank with room for {@value #CAPACITY} open accounts
     * and {@value #CAPACITY} closed ones.
     */
    public Bank() {
        accounts = new BankAccount[CAPACITY];
        count = 0;

        // initialize the new closed/closedCount fields here too
        closed = new BankAccount[CAPACITY];
        closedCount = 0;
    }

    /**
     * Creates a bank and fills it with the accounts stored in a file.
     *
     * @param filename path to the file holding the account data
     */
    public Bank(String filename) {
        this();
        read(filename);
    }

    /**
     * Loads accounts from a file, one per line, splitting each on commas and
     * building a {@link Checking}, {@link Savings}, or {@link Investment}
     * from the first token. A line ending in "closed" is filed under
     * {@link #closed} rather than {@link #accounts}. Bad lines are reported
     * and skipped rather than stopping the read.
     *
     * @param filename path to the file holding the account data
     */
    private void read(String filename) {
        try (Scanner input = new Scanner(new File(filename))) {
            while (input.hasNextLine()) {
                String line = input.nextLine();
                if (line.isBlank()) { continue; }

                String[] attributes = line.split(",");
                String type = attributes[0];

                // The first token must name one of the three account types
                if (!type.equals("Checking") && !type.equals("Savings") && !type.equals("Investment")) {
                    printError(line, "Invalid type of account: " + type
                            + ", should be [Checking|Savings|Investment]");
                    continue;
                }

                // A line whose last token is "closed" belongs in the closed array
                boolean isClosed = attributes[attributes.length - 1].equals("closed");

                // Checking stores 4 fields; Savings and Investment store 5. A
                // closed account carries one extra token at the end.
                int expected = type.equals("Checking") ? 4 : 5;
                if (isClosed) { expected++; }
                if (attributes.length != expected) {
                    printError(line, "Invalid number of fields: expected " + expected
                            + ", found " + attributes.length);
                    continue;
                }

                // The account number must parse as a long
                long number;
                try {
                    number = Long.parseLong(attributes[1]);
                } catch (NumberFormatException e) {
                    printError(line, "Invalid format for the account number \"" + attributes[1]
                            + "\", must be a long integer");
                    continue;
                }

                // The balance must parse as a double
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

    /**
     * Reports a line of the accounts file that could not be read.
     *
     * @param line    the raw line that failed
     * @param message what was wrong with it
     */
    private void printError(String line, String message) {
        System.out.println("Error at line: " + line);
        System.out.println(message);
        System.out.println();
    }

    /**
     * Writes every open and closed account to a file in the same CSV format
     * it was read from, marking each closed account with a trailing
     * "closed" token. Overwrites whatever the file held.
     *
     * @param filename path to the file to write
     */
    public void save(String filename) {
        try (PrintWriter output = new PrintWriter(filename)) {
            for (int i = 0; i < count; i++) {
                output.println(accounts[i].fileString());
            }
            // Closed accounts get an extra "closed" token so read() can tell them apart
            for (int i = 0; i < closedCount; i++) {
                output.println(closed[i].fileString() + ",closed");
            }
        } catch (FileNotFoundException e) {
            System.out.println("Could not write to file " + filename);
        }
    }

    /**
     * Returns how many accounts are still open.
     *
     * @return the number of open accounts
     */
    public int size() {
        return count;
    }

    /**
     * Returns how many accounts have been closed.
     *
     * @return the number of closed accounts
     */
    public int closedSize() {
        return closedCount;
    }

    /**
     * Files an account with the open accounts.
     *
     * @param ba the account to add
     */
    public void add(BankAccount ba) {
        accounts[count] = ba;
        count++;
    }

    /**
     * Searches the open accounts for one with a given number.
     *
     * @param number the account number to look for
     * @return the matching account, or null if there is none
     */
    public BankAccount find(long number) {
        for (int i = 0; i < count; i++) {
            if (accounts[i].getNumber() == number) {
                return accounts[i];
            }
        }
        return null;
    }

    /**
     * Takes an account out of the open accounts, closing the gap it leaves.
     *
     * @param number the account number to remove
     * @return the account removed, or null if there was no match
     */
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

    /**
     * Files an account with the closed accounts.
     *
     * @param ba the account being closed
     */
    private void addClosed(BankAccount ba) {
        closed[closedCount] = ba;

        closedCount++;
    }

    /**
     * Puts the open accounts in order of balance, smallest first, using the
     * ordering defined by {@link BankAccount#compareTo(BankAccount)}.
     */
    public void sort() {
        // Only the first count slots hold accounts; the rest are null
        Arrays.sort(accounts, 0, count);
    }

    /**
     * Credits a month of interest to every open savings account and reports
     * how many were updated.
     */
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

    /**
     * Applies a gain or loss to every open investment account and reports
     * how many were updated.
     *
     * @param risk risk factor deciding profit or loss for each account
     */
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

    /**
     * Prints the open checking accounts and how many there are.
     */
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

    /**
     * Prints the open savings accounts and how many there are.
     */
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

    /**
     * Prints the open investment accounts and how many there are.
     */
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

    /**
     * Prints the open accounts whose balance is low enough to close, and how
     * many there are.
     */
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

    /**
     * Prints the accounts that have already been closed, and how many there
     * are.
     */
    public void viewClosed() {
        for (int i = 0; i < closedCount; i++) {
            System.out.println(closed[i]);
        }
        System.out.printf("%d accounts are closed\n", closedCount);
    }

    /**
     * Moves every open account with a low enough balance into the closed
     * accounts, and reports how many were moved.
     */
    public void closeAccounts() {
        int initClosedNum = closedCount;
        for (int i = 0; i < count; i++) {
            BankAccount ba = accounts[i];

            if (! ba.isCloseable()) { continue; }

            addClosed(ba);

            // Remove from accounts: shift everything after i left by one
            for (int j = i; j < count - 1; j++) {
                accounts[j] = accounts[j + 1];
            }
            count--;
            accounts[count] = null;

            // Re-check index i, which now holds the account shifted into it
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

    /**
     * Builds a table of the open accounts under a column header.
     *
     * @return the listing as one multi-line string
     */
    public String toString() {
        String s = "";
        s += String.format("%-16s%-16s%-32s%-11s     %s", "Type", "Number", "Owner", "Balance", "Interest/Type") + "\n";
        for (int i = 0; i < count; i++) {
            s += (accounts[i].toString()) + "\n";
        }
        return s;
    }
}
