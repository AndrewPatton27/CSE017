public class BankManager{
	public static void main(String[] args){

		// Creating a new empty Bank instance
		System.out.println("Test case 1: Creating an empty bank instance");
		Bank myBank = new Bank();
		System.out.println("There are " + myBank.size() + " accounts in the bank");
		
		// Adding accounts to the bank
		System.out.println("\nTest case 2: Adding new accounts to the bank");
		myBank.add(new Checking("William Burst", 10000.0));
		myBank.add(new Savings("Lara Stevens", 75000.0, 9.25));
		Checking checking = new Checking("Nathan Steward", 24900.9);
		myBank.add(checking);
		myBank.add(new Investment("Tracey Bold", 19000.0, "Property"));
		myBank.add(new Savings("Isabel Truman", 89250.0, 10.5));
		Savings savings = new Savings("Andrew Sullivan", 12734.0, 12.1);
		myBank.add(savings);
		myBank.add(new Investment("Abigail Clark", 11255.0, "Shares"));
		myBank.add(new Checking("George Duck", 29990.0));
		Savings toRemove = new Savings("Emma Stevens", 31250.0, 8.89);
		myBank.add(toRemove);
		Investment investment = new Investment("Lily Daves", 8800.0, "Growth");
		myBank.add(investment);
		System.out.println("There are " + myBank.size() + " accounts in the bank");
		System.out.println(myBank.toString());

		// Finding an existing checking account in the bank
		System.out.println("Test case 3: finding an account successful");
		BankAccount found = myBank.find(checking.getNumber());
		if(found != null){
			System.out.println("Account found: " + found.toString());
		}
		else{
			System.out.println("Account not found.");
		}

		// Withdrawing an acceptable amount from the checking account
		System.out.println("\nTest case 4: withdraw from account successful");
		boolean success = found.withdraw(1000);
		if(success){
			System.out.println("Withdrawal completed successfully");
			System.out.println("New balance: " + String.format("$%.2f",found.getBalance()));
		}
		else{
			System.out.println("Withdrawal failed. There is not enough credit in the account");
		}

		// Withdrawing an amount above the balance
		System.out.println("\nTest case 5: withdraw from account failed");
		success = found.withdraw(1_000_000);
		if(success){
			System.out.println("Withdrawal completed successfully");
			System.out.println("New balance: " + String.format("$%.2f",found.getBalance()));
		}
		else{
			System.out.println("Withdrawal failed. There is not enough credit in the account");
		}

		// Deposit an amount in an account
		System.out.println("\nTest case 6: deposit money in an account");
		found.deposit(2000);
		System.out.println("Deposit completed successfully");
		System.out.println("New balance: " + String.format("$%.2f",found.getBalance()));

		 // Apply the monthly interest on a savings account
		System.out.println("\nTest case 7: Apply the monthly interest on a savings account");
		found = myBank.find(savings.getNumber());
		if(found != null){
			System.out.println("Account found: " + found.toString());
		}
		else{
			System.out.println("Account not found.");
		}
		double interest = savings.applyMonthlyInterest();
		System.out.println("The amount " + String.format("$%.2f",interest) + " was added to the account");
		System.out.println("New balance: " + String.format("$%.2f",found.getBalance()));

		// apply profit on an investment account
		System.out.println("\nTest case 8: get the profit on an investment account");
		found = myBank.find(investment.getNumber());
		if(found != null){
			System.out.println("Account found: " + found.toString());
		}
		else{
			System.out.println("Account not found.");
		}
		double profitOrLoss = investment.getProfitOrLoss(0.75);
		if(profitOrLoss > 0){
			System.out.println("Profit: the amount " + String.format("$%.2f", profitOrLoss) + " was added to the account" + investment.getNumber());
			System.out.println("New balance: " + String.format("$%.2f",found.getBalance()));
		}
		else{
			System.out.println("Loss: the amount " + String.format("$%.2f", -profitOrLoss) + " was deducted from the account");
			System.out.println("New balance: " + String.format("$%.2f",found.getBalance()));
		}

		// apply loss on an investment account
		System.out.println("\nTest case 9: get the loss on an investment account");
		found = myBank.find(investment.getNumber());
		if(found != null){
			System.out.println("Account found: " + found.toString());
		}
		else{
			System.out.println("Account not found.");
		}
		profitOrLoss = investment.getProfitOrLoss(0.25);
		if(profitOrLoss > 0){
			System.out.println("Profit: the amount " + String.format("$%.2f", profitOrLoss) + " was added to the account" + investment.getNumber());
			System.out.println("New balance: " + String.format("$%.2f",found.getBalance()));
		}
		else{
			System.out.println("Loss: the amount " + String.format("$%.2f", -profitOrLoss) + " was deducted from the account");
			System.out.println("New balance: " + String.format("$%.2f",found.getBalance()));
		}

		// Finding an account that is not in the bank
		System.out.println("\nTest case 10: finding an account (fail)");
		found = myBank.find(999999999L);
		if(found != null){
			System.out.println("Account found: " + found.toString());
		}
		else{
			System.out.println("Account not found.");
		}

		// Removing an existent account
		System.out.println("\nTest case 11: removing an account (success)");
		found = myBank.remove(toRemove.getNumber());
		if(found != null){
			System.out.println("Account found and removed: " + found.toString());
		}
		else{
			System.out.println("Account not found.");
		}

		// Removing an account that is not in the bank
		System.out.println("Test case 12: removing an account (fail)");
		found = myBank.remove(999999999L);
		if(found != null){
			System.out.println("Account found: " + found.toString());
		}
		else{
			System.out.println("Account not found.");
		}
		
		// View the list of the accounts after the updates
		System.out.println("\nTest case 13: viewing the list of accounts");
		System.out.println("There are " + myBank.size() + " accounts in the bank");
		System.out.println(myBank.toString());

		// Sorting the bank accounts by balance
		System.out.println("\nTest case 14: sorting the bank accounts by balance");
		myBank.sort();
		System.out.println(myBank.toString());
	}
}
