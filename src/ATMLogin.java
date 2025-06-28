// Import required libraries
import javax.swing.*; // For GUI popups
import java.io.*;     // For reading files

public class ATMLogin {

    // This is the main method - program starts running from here
    public static void main(String[] args) {

        // Show a dialog box asking for the PIN
        String pinInput = JOptionPane.showInputDialog(
            null,                            // No parent window
            "Enter your 4-digit PIN:",       // Message to user
            "ATM Login",                     // Title of the popup
            JOptionPane.PLAIN_MESSAGE        // No icon
        );

        // If user cancels or closes the popup, just stop the program
        if (pinInput == null) return;

        // Read the actual stored PIN from the file pin.txt
        String storedPin = readFromFile("pin.txt", "1234"); // Default PIN is 1234

        // Read the user's balance from the file balance.txt
        String balanceText = readFromFile("balance.txt", "0.0"); // Default balance is ₹0.0
        double balance = Double.parseDouble(balanceText); // Convert string to number

        // Compare the entered PIN with the stored PIN
        if (pinInput.equals(storedPin)) {
            // If PIN matches, create a User object with PIN and balance
            User user = new User(storedPin, balance);

            // Open the ATM main menu screen
            ATMMainMenu.showMenu(user);
        } else {
            // If PIN is wrong, show an error popup
            JOptionPane.showMessageDialog(
                null,
                "Invalid PIN",
                "Error",
                JOptionPane.ERROR_MESSAGE
            );
        }
    }

    // This method reads one line of text from a file (like pin.txt or balance.txt)
    // If the file doesn't exist or fails to open, it returns a default value
    public static String readFromFile(String fileName, String defaultValue) {
        try {
            BufferedReader br = new BufferedReader(new FileReader(fileName));
            return br.readLine(); // Read first line from file
        } catch (Exception e) {
            return defaultValue; // If any error, return default value
        }
    }
}
