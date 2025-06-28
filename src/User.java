// This class stores the user's data and actions

public class User {
    private String pin;       // User's 4-digit PIN
    private double balance;   // User's current balance

    // Constructor: called when creating a new User
    public User(String pin, double balance) {
        this.pin = pin;
        this.balance = balance;
    }

    // Get the current PIN
    public String getPin() {
        return pin;
    }

    // Change the PIN
    public void setPin(String newPin) {
        this.pin = newPin;
    }

    // Get the current balance
    public double getBalance() {
        return balance;
    }

    // Add money to balance
    public void deposit(double amount) {
        balance += amount;
    }

    // Try to withdraw money — return true if successful, false if not enough money
    public boolean withdraw(double amount) {
        if (amount <= balance) {
            balance -= amount;
            return true;
        }
        return false;
    }

    // Set a new balance (optional — not used right now)
    public void setBalance(double balance) {
        this.balance = balance;
    }
}
