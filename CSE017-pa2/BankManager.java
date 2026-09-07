public class BankManager{
    public static void main(String[] args){

        // Creating a new empty Bank instance
        System.out.println("Test case 1: Creating an empty bank instance");
        Bank myBank = new Bank();
        System.out.println("There are " + myBank.size() + " accounts in the bank");
        
        // Adding accounts to the bank
        System.out.println("\nTest case 2: Creating a bank from a text file");
        myBank = new Bank("accounts.txt");
        System.out.println(myBank.size() + " accounts read from the file \"accounts.txt\"");
        System.out.println(myBank.toString());

        // Finding an account in the bank
        System.out.println("Test case 3: finding an account (success)");
        long number = 6163767899L;
        BankAccount found = myBank.find(number);
        if(found != null){
            System.out.println("Account found: " + found.toString());
        }
        else{
            System.out.println("Account number " + number + " not found.");
        }

        // Withdrawing an acceptable amount from an account
        System.out.println("\nTest case 4: withdraw from account (success)");
        try{
            found.withdraw(100);
            System.out.println("Withdrawal completed successfully");
            System.out.println("New balance: " + String.format("$%.2f",found.getBalance()));
        }
        catch(IllegalTransactionException e){
            System.out.println(e.getMessage());
        }

        // Withdrawing an amount above the balance
        System.out.println("\nTest case 5: withdraw from account (fail)");
        try{
            found.withdraw(300);
            System.out.println("Withdrawal completed successfully");
            System.out.println("New balance: " + String.format("$%.2f",found.getBalance()));
        }
        catch(IllegalTransactionException e){
            System.out.println(e.getMessage());
        }

        // Deposit an amount in an account
        System.out.println("\nTest case 6: deposit money in an account");
        found.deposit(2000);
        System.out.println("Deposit completed successfully");
        System.out.println("New balance: " + String.format("$%.2f",found.getBalance()));

         // Apply the monthly interest on a savings account
        System.out.println("\nTest case 7: Apply the monthly interest on a savings account");
        number = 2636761959L;
        found = myBank.find(number);
        if(found != null){
            System.out.println("Account found: " + found.toString());
        }
        else{
            System.out.println("Account number " + number + " not found.");
        }
        double interest = ((Savings)found).applyMonthlyInterest();
        System.out.println("The amount " + String.format("$%.2f",interest) + " added to the account");
        System.out.println("New balance: " + String.format("$%.2f",found.getBalance()));

        // apply profit on an investment account
        System.out.println("\nTest case 8: get the profit on an investment account");
        number = 4175493256L;
        found = myBank.find(number);
        if(found != null){
            System.out.println("Account found: " + found.toString());
        }
        else{
            System.out.println("Account number " + number + " not found.");
        }
        double profitOrLoss = ((Investment)found).getProfitOrLoss(0.75);
        if(profitOrLoss > 0){
            System.out.println("Profit: the amount " + String.format("$%.2f", profitOrLoss) + " was added to the account");
            System.out.println("New balance: " + String.format("$%.2f",found.getBalance()));
        }
        else{
            System.out.println("Loss: the amount " + String.format("$%.2f", -profitOrLoss) + " was deducted from the account");
            System.out.println("New balance: " + String.format("$%.2f",found.getBalance()));
        }

        // apply loss on an investment account
        System.out.println("\nTest case 9: get the loss on an investment account");
        profitOrLoss = ((Investment)found).getProfitOrLoss(0.25);
        if(profitOrLoss > 0){
            System.out.println("Profit: the amount " + String.format("$%.2f", profitOrLoss) + " was added to the account");
            System.out.println("New balance: " + String.format("$%.2f",found.getBalance()));
        }
        else{
            System.out.println("Loss: the amount " + String.format("$%.2f", -profitOrLoss) + " was deducted from the account");
            System.out.println("New balance: " + String.format("$%.2f",found.getBalance()));
        }

        // Finding an account that is not in the bank
        System.out.println("\nTest case 10: finding an account (fail)");
        number = 999999999L;
        found = myBank.find(number);
        if(found != null){
            System.out.println("Account found: " + found.toString());
        }
        else{
            System.out.println("Account number " + number + " not found.");
        }

        // Removing an existent account
        System.out.println("\nTest case 11: removing an account (success)");
        number = 9191317817L;
        found = myBank.remove(number);
        if(found != null){
            System.out.println("Account found and removed: " + found.toString());
        }
        else{
            System.out.println("Account number " + number + " not found.");
        }

        // Removing an account that is not in the bank
        System.out.println("\nTest case 12: removing an account (fail)");
        number = 999999999L;
        found = myBank.remove(number);
        if(found != null){
            System.out.println("Account found: " + found.toString());
        }
        else{
            System.out.println("Account number " + number + " not found.");
        }
        
        // View the list of the accounts after the updates
        System.out.println("\nTest case 13: viewing the list of accounts");
        System.out.println("There are " + myBank.size() + " accounts in the bank");
        System.out.println(myBank.toString());

        // Sorting the bank accounts by balance
        System.out.println("Test case 14: sorting the bank accounts by balance");
        myBank.sort();
        System.out.println(myBank.toString());

        // Save the list of the accounts to a file
        System.out.println("\nTest case 15: Saving the list of accounts to the file \"accounts.txt\"");
        myBank.save("accounts.txt");
        System.out.println(myBank.size() + " accounts saved to the file \"accounts.txt\"");

        // Check if the file contains the updated list of accounts
        System.out.println("\nTest case 16: Reading the accounts from the updated file \"accounts.txt\"");
        myBank = new Bank("accounts.txt");
        System.out.println(myBank.size() + " accounts read from the file \"accounts.txt\"");
        System.out.println(myBank.toString());

        // 17: view the checking accounts only
        System.out.println("\nTest case 17: view the checking accounts only");
        myBank.viewChecking();

        // 18: view the savings accounts only
        System.out.println("\nTest case 18: view the savings accounts only");
        myBank.viewSavings();

        // 19: update all the savings accounts
        System.out.println("\nTest case 19: apply the monthly interest to all the savings accounts");
        myBank.updateSavings();

        // 20: view the updated savings accounts
        System.out.println("\nTest case 20: view the updated savings accounts");
        myBank.viewSavings();

        // 21: view the Investment accounts only
        System.out.println("\nTest case 21: view the investment accounts only");
        myBank.viewInvestment();

        // 22: apply profit to the investment accounts
        System.out.println("\nTest case 22: apply a profit to the investment accounts");
        myBank.updateInvestment(0.8);

        // 23: view the updated investment accounts
        System.out.println("\nTest case 23: view the updated investment accounts");
        myBank.viewInvestment();

        // 24: apply loss to the investment accounts
        System.out.println("\nTest case 24: apply a losss to the investment accounts");
        myBank.updateInvestment(0.2);

        // 25: view the updated investment accounts
        System.out.println("\nTest case 25: view the updated investment accounts");
        myBank.viewInvestment();

        // 25: view the closeable accounts
        System.out.println("\nTest case 26: view the list of closeable accounts");
        myBank.viewCloseable();

        // 27: close the closeable accounts
        System.out.println("\nTest case 27: close the closeable accounts");
        myBank.closeAccounts();

        // 28: view the closed accounts
        System.out.println("\nTest case 28: view the list of closed accounts");
        myBank.viewClosed();

        // 29: view the closeable accounts
        System.out.println("\nTest case 29: view the list of closeable accounts");
        myBank.viewCloseable();

        // 30: save the updated accounts to the file
        System.out.println("\nTest case 30: save the list of open and closed accounts to the file");
        myBank.save("accounts.txt");
        System.out.println(myBank.size() + " accounts were saved to the file");
        System.out.println(myBank.closedSize() + " closed accounts were saved to the file");

        // 31: load the file (contains closed accounts)
        System.out.println("\nTest case 31: read the list of open and closed accounts from the file");
        myBank = new Bank("accounts.txt");
        System.out.println(myBank.size() + " accounts were read from the file");
        System.out.println(myBank.closedSize() + " closed accounts were read from the file");

        // 32: view Closeablle accounts
        System.out.println("\nTest case 32: view the closeable accounts after reading from the file");
        myBank.viewCloseable();

        // 33: view closed accounts
        System.out.println("\nTest case 33: close the closeable accounts after reading from the file");
        myBank.closeAccounts();

        // 34: view the closed accounts
        System.out.println("\nTest case 34: view the closed accounts after reading from the file");
        myBank.viewClosed();

        // 35: view all accounts
        System.out.println("\nTest case 35: view the open accounts after reading from the file");
        System.out.println(myBank.toString());

        // 36: create a new bank with specific accounts
        System.out.println("\nTest case 36: creating a new bank with specific accounts");
        Bank newBank = new Bank();
        newBank.add(new Checking(1234567890L, "Alice Smith", 50.00));
        newBank.add(new Savings(9876543210L, "Bob Jones", 1000.00, 5.0));
        newBank.add(new Investment(5555555555L, "Carol White", 2000.00, "Growth"));
        System.out.println(newBank.size() + " accounts added to the new bank");
        System.out.println(newBank.toString());

        // 37: view closeable accounts in new bank
        System.out.println("\nTest case 37: view the closeable accounts in the new bank");
        newBank.viewCloseable();

        // 38: close closeable accounts in new bank
        System.out.println("\nTest case 38: close the closeable accounts in the new bank");
        newBank.closeAccounts();
        System.out.println("There are now " + newBank.size() + " open accounts and " + newBank.closedSize() + " closed accounts");

        // 39: apply monthly interest to savings in new bank
        System.out.println("\nTest case 39: apply the monthly interest to the savings accounts in the new bank");
        newBank.updateSavings();
        newBank.viewSavings();

        // 40: apply profit to investment accounts in new bank
        System.out.println("\nTest case 40: apply a profit to the investment accounts in the new bank");
        newBank.updateInvestment(0.7);
        newBank.viewInvestment();

    }
}
