import javax.swing.*;
import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;

public class ATMMainMenu {

    private static final String BALANCE_FILE = "balance.txt";
    private static final String PIN_FILE = "pin.txt";
    private static final String TRANSACTION_FILE = "transactions.txt";

    public static void showMenu(User user) {
        while (true) {
            // Show simple menu options
            String[] options = {
                "View Balance",
                "Deposit",
                "Withdraw",
                "Change PIN",
                "Mini Statement",
                "Exit"
            };

            // Show menu and get user choice
            int choice = JOptionPane.showOptionDialog(
                    null,
                    "Choose an option:",
                    "ATM Menu",
                    JOptionPane.DEFAULT_OPTION,
                    JOptionPane.PLAIN_MESSAGE,
                    null,
                    options,
                    options[0]
            );

            // If user closes the menu window
            if (choice == JOptionPane.CLOSED_OPTION || choice == 5) {
                saveToFile(BALANCE_FILE, String.valueOf(user.getBalance()));
                saveToFile(PIN_FILE, user.getPin());
                JOptionPane.showMessageDialog(null, "Thank you for using the ATM!");
                break;
            }

            // Handle options with classic switch
            switch (choice) {
                case 0: // View Balance
                    JOptionPane.showMessageDialog(null, "Your balance is: ₹" + user.getBalance());
                    break;

                case 1: // Deposit
                    String depositInput = JOptionPane.showInputDialog("Enter amount to deposit:");
                    if (depositInput != null) {
                        try {
                            double amt = Double.parseDouble(depositInput);
                            user.deposit(amt);
                            saveTransaction("Deposited", amt);
                            JOptionPane.showMessageDialog(null, "₹" + amt + " deposited.");
                        } catch (Exception e) {
                            JOptionPane.showMessageDialog(null, "Invalid amount.");
                        }
                    }
                    break;

                case 2: // Withdraw
                    String withdrawInput = JOptionPane.showInputDialog("Enter amount to withdraw:");
                    if (withdrawInput != null) {
                        try {
                            double amt = Double.parseDouble(withdrawInput);
                            if (user.withdraw(amt)) {
                                saveTransaction("Withdrawn", amt);
                                JOptionPane.showMessageDialog(null, "₹" + amt + " withdrawn.");
                            } else {
                                JOptionPane.showMessageDialog(null, "Insufficient balance.");
                            }
                        } catch (Exception e) {
                            JOptionPane.showMessageDialog(null, "Invalid amount.");
                        }
                    }
                    break;

                case 3: // Change PIN
                    String oldPin = JOptionPane.showInputDialog("Enter current PIN:");
                    if (oldPin != null && oldPin.equals(user.getPin())) {
                        String newPin = JOptionPane.showInputDialog("Enter new PIN:");
                        if (newPin != null && newPin.length() == 4) {
                            user.setPin(newPin);
                            JOptionPane.showMessageDialog(null, "PIN changed successfully.");
                        } else {
                            JOptionPane.showMessageDialog(null, "PIN must be 4 digits.");
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "Incorrect current PIN.");
                    }
                    break;

                case 4: // Mini Statement
                    showMiniStatement();
                    break;

                default:
                    break;
            }
        }
    }

    // Save balance or PIN to file
    public static void saveToFile(String fileName, String content) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(fileName))) {
            bw.write(content);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Failed to save to " + fileName);
        }
    }

    // Save a transaction to file
    public static void saveTransaction(String type, double amount) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(TRANSACTION_FILE, true))) {
            String time = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            bw.write(type + " ₹" + amount + " on " + time);
            bw.newLine();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Failed to save transaction.");
        }
    }

    // Show last 5 transactions
    public static void showMiniStatement() {
        LinkedList<String> lastFive = new LinkedList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(TRANSACTION_FILE))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (lastFive.size() == 5) lastFive.removeFirst();
                lastFive.add(line);
            }
            String message = String.join("\n", lastFive);
            JOptionPane.showMessageDialog(null, message.isEmpty() ? "No transactions found." : message, "Mini Statement", JOptionPane.INFORMATION_MESSAGE);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error reading mini statement.");
        }
    }
}
