import java.util.Scanner;

// Class to represent a user's bank account
class BankAccount {
    private double balance;

    // Constructor to initialize the account with a starting balance
    public BankAccount(double initialBalance) {
        if (initialBalance >= 0) {
            this.balance = initialBalance;
        } else {
            System.out.println("Initial balance can't be negative. Setting balance to 0.");
            this.balance = 0;
        }
    }

    // Method to deposit an amount to the account
    public void deposit(double amount) {
        if (amount > 0) {
            balance += amount;
            System.out.println("Successfully deposited: $" + amount);
        } else {
            System.out.println("Invalid deposit amount.");
        }
    }

    // Method to withdraw an amount from the account
    public void withdraw(double amount) {
        if (amount > 0 && amount <= balance) {
            balance -= amount;
            System.out.println("Successfully withdrew: $" + amount);
        } else if (amount > balance) {
            System.out.println("Insufficient balance to withdraw: $" + amount);
        } else {
            System.out.println("Invalid withdrawal amount.");
        }
    }

    // Method to check the account balance
    public double checkBalance() {
        return balance;
    }
}

// Class to represent the ATM machine
class ATM {
    private BankAccount account;
    private final String PIN = "2024"; // Set the PIN

    // Constructor to initialize the ATM with a bank account
    public ATM(BankAccount account) {
        this.account = account;
    }

    // Method to display the ATM menu and handle user input
    public void start() {
        Scanner scanner = new Scanner(System.in);
        String enteredPin;

        // Show the ATM Menu and prompt for PIN
        System.out.println("\n===== ATM Menu =====");
        System.out.print("Enter the PIN to access the ATM: ");
        enteredPin = scanner.nextLine();

        // Check if the entered PIN is correct
        if (!enteredPin.equals(PIN)) {
            System.out.println("Incorrect PIN. Access denied.");
            scanner.close();
            return; // Exit if PIN is incorrect
        }

        int choice;
        do {
            System.out.println("\n===== ATM Menu =====");
            System.out.println("1. Check Balance");
            System.out.println("2. Deposit");
            System.out.println("3. Withdraw");
            System.out.println("4. Exit");
            System.out.print("Choose an option: ");
            choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    // Check Balance
                    System.out.println("Current Balance: $" + account.checkBalance());
                    break;

                case 2:
                    // Deposit
                    System.out.print("Enter deposit amount: ");
                    double depositAmount = scanner.nextDouble();
                    account.deposit(depositAmount);
                    break;

                case 3:
                    // Withdraw
                    System.out.print("Enter withdrawal amount: ");
                    double withdrawAmount = scanner.nextDouble();
                    account.withdraw(withdrawAmount);
                    break;

                case 4:
                    // Exit
                    System.out.println("Thank you for using the ATM. Goodbye!");
                    break;

                default:
                    System.out.println("Invalid option. Please choose again.");
            }
        } while (choice != 4);

        scanner.close();
    }
}

// Main class to test the ATM system
public class ATMSystem {
    public static void main(String[] args) {
        // Create a bank account with an initial balance
        BankAccount account = new BankAccount(1000.0);

        // Create the ATM and pass the bank account to it
        ATM atm = new ATM(account);

        // Start the ATM interface
        atm.start();
    }
}
