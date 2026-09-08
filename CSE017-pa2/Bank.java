import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Scanner;

/**
 * Represents a bank holding a collection of {@link BankAccount}s. Supports
 * reading/writing accounts from/to a text file, finding, removing, and
 * sorting accounts, applying interest/profit-or-loss updates, and closing
 * accounts that are eligible for closure.
 */
public class Bank {

    /** Max amount of open bank accounts. */
    private static final int CAPACITY = 50;

    /** Open (active) bank accounts. */
    private BankAccount[] accounts;

    /** Current number of open bank accounts in {@link #accounts}. */
    private int count;

    /** Closed bank accounts. */
    private BankAccount[] closed;

    /** Current number of closed bank accounts in {@link #closed}. */
    private int closedCount;

    /**
     * Creates an empty bank with capacity for {@value #CAPACITY} open
     * accounts and {@value #CAPACITY} closed accounts.
     */
    public Bank() {
        accounts = new BankAccount[CAPACITY];
        count = 0;

        // initialize the new closed/closedCount fields here too
        closed = new BankAccount[CAPACITY];
        closedCount = 0;
    }

    /**
     * Creates a bank and populates it by reading account data from the
     * given file.
     *
     * @param filename path to the file containing account data
     */
    public Bank(String filename) {
        this();
        read(filename);
    }

    /**
     * Reads bank account information from {@code filename}, line by line.
     * Each line is split using the delimiter ",", the tokens are parsed
     * into account attributes, and a new account (an instance of
     * {@link Checking}, {@link Savings}, or {@link Investment}, depending
     * on the first token) is created and added to the bank. If the last
     * token on a line is the string "closed", the created account is added
     * to {@link #closed} instead of {@link #accounts}. Handles any
     * exceptions thrown by the account constructors as well as any format
     * errors in the file (see output.reference for the expected error
     * messages).
     *
     * @param filename path to the file containing account data
     */
    private void read(String filename) {
        try {
            Scanner input = new Scanner(new File(filename));
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

                // A line whose last token is "closed" belongs in the closed array
                boolean isClosed = attributes[attributes.length - 1].equals("closed");

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
            input.close();
        } catch (FileNotFoundException e) {
            System.out.println("File " + filename + " not found.");
        }
    }

    /**
     * Prints a formatting error found while reading a line of the accounts file.
     *
     * @param line    the raw line that could not be turned into an account
     * @param message description of what was wrong with the line
     */
    private void printError(String line, String message) {
        System.out.println("Error at line: " + line);
        System.out.println(message);
        System.out.println();
    }

    /**
     * Writes the bank account information of every account in
     * {@link #accounts} and {@link #closed} to {@code filename}, using the
     * same CSV format as accounts.txt (via {@link BankAccount#fileString()}).
     * Closed accounts get an additional "," + "closed" token appended.
     *
     * @param filename path to the file to write account data to
     */
    public void save(String filename) {
        try {
            PrintWriter output = new PrintWriter(filename);
            for (int i = 0; i < count; i++) {
                output.println(accounts[i].fileString());
            }
            // Closed accounts get an extra "closed" token so read() can tell them apart
            for (int i = 0; i < closedCount; i++) {
                output.println(closed[i].fileString() + ",closed");
            }
            output.close();
        } catch (FileNotFoundException e) {
            System.out.println("Could not write to file " + filename);
        }
    }

    /**
     * @return the number of open accounts in the bank
     */
    public int size() {
        return count;
    }

    /**
     * @return the number of closed accounts in the bank
     */
    public int closedSize() {
        return closedCount;
    }

    /**
     * Adds the given account to the bank's open accounts.
     *
     * @param ba the account to add
     */
    public void add(BankAccount ba) {
        accounts[count] = ba;
        count++;
    }

    /**
     * Finds an open account by account number.
     *
     * @param number the account number to search for
     * @return the matching account, or null if not found
     */
    public BankAccount find(long number) {
        // // Only works for current implementation of BankAcount number because in order based on number
        // long firstBANumber = 1111111111L;
        // int baIndex =  (int)(number-firstBANumber);
        // try {
        //     return accounts[baIndex];
        // }
        // catch (IndexOutOfBoundsException e) {return null;}
        // Alternitive solution
        for (int i = 0; i < count; i++) {
            if (accounts[i].getNumber() == number) {
                return accounts[i];
            }
        }
        return null;
    }

    /**
     * Removes and returns an open account by account number.
     *
     * @param number the account number to remove
     * @return the removed account, or null if not found
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
     * Adds the given account to the {@link #closed} array and increments
     * {@link #closedCount}.
     *
     * @param ba the account to mark as closed
     */
    private void addClosed(BankAccount ba) {
        closed[closedCount] = ba;

        closedCount++;
    }

    /**
     * Sorts the open accounts by balance using {@code java.util.Arrays.sort},
     * relying on {@link BankAccount#compareTo(BankAccount)}.
     */
    public void sort() {
        // Only the first count slots hold accounts; the rest are null
        Arrays.sort(accounts, 0, count);
    }

    /**
     * Invokes {@link Savings#applyMonthlyInterest()} on every open account
     * that is an instance of {@link Savings}.
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
     * Invokes {@link Investment#getProfitOrLoss(double)} with the given
     * risk on every open account that is an instance of {@link Investment}.
     *
     * @param risk risk factor to apply to each investment account
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
     * Prints the open accounts of type {@link Checking} only, followed by
     * the number of accounts printed.
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
     * Prints the open accounts of type {@link Savings} only, followed by
     * the number of accounts printed.
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
     * Prints the open accounts of type {@link Investment} only, followed by
     * the number of accounts printed.
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
     * Prints the open accounts eligible for closure (i.e.
     * {@link BankAccount#isCloseable()} returns true), followed by the
     * number of accounts found.
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
     * Prints the already-closed accounts stored in {@link #closed}.
     */
    public void viewClosed() {
        for (int i = 0; i < closedCount; i++) {
            System.out.println(closed[i]);
        }
        System.out.printf("%d accounts are closed\n", closedCount);
    }

    /**
     * Identifies the closeable accounts among {@link #accounts}, removes
     * them from {@link #accounts}, and adds them to {@link #closed} (via
     * {@link #addClosed(BankAccount)}). Prints the number of accounts
     * moved from {@link #accounts} to {@link #closed}.
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
     * @return a formatted, human-readable listing of the open accounts in the bank
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
