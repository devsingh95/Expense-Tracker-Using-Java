import java.io.*;
import java.util.*;

// Class to store single expense
class Expense {
    String category;
    double amount;
    String date;
    String note;

    Expense(String category, double amount, String date, String note) {
        this.category = category;
        this.amount = amount;
        this.date = date;
        this.note = note;
    }

    // Format expense as string for saving to file
    public String toString() {
        return category + "," + amount + "," + date + "," + note;
    }
}

// Class to manage all expenses
class ExpenseManager {
    private List<Expense> expenses = new ArrayList<>();
    private String filename = "expenses.txt";

    // Load expenses from file
    void loadFromFile() {
        try (Scanner fileScanner = new Scanner(new File(filename))) {
            while (fileScanner.hasNextLine()) {
                String line = fileScanner.nextLine();
                String[] parts = line.split(",", 4);
                if (parts.length == 4) {
                    String category = parts[0];
                    double amount = Double.parseDouble(parts[1]);
                    String date = parts[2];
                    String note = parts[3];
                    expenses.add(new Expense(category, amount, date, note));
                }
            }
        } catch (FileNotFoundException e) {
            // File not found, first run
        }
    }

    // Add a new expense
    void addExpense() {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter Category (Food, Travel, Shopping, etc.): ");
        String category = sc.nextLine();
        System.out.print("Enter Amount: ₹");
        double amount = sc.nextDouble();
        sc.nextLine(); // consume newline
        System.out.print("Enter Date (DD/MM/YYYY): ");
        String date = sc.nextLine();
        System.out.print("Enter Note: ");
        String note = sc.nextLine();

        expenses.add(new Expense(category, amount, date, note));
        System.out.println("\n✅ Expense Added Successfully!");
    }

    // View all expenses
    void viewAllExpenses() {
        if (expenses.isEmpty()) {
            System.out.println("\nNo expenses found!");
            return;
        }
        System.out.println("\n---- All Expenses ----");
        System.out.printf("%-12s %-10s %-12s %s\n", "Category", "Amount", "Date", "Note");
        System.out.println("---------------------------------------------");
        for (Expense e : expenses)
            System.out.printf("%-12s ₹%-9.2f %-12s %s\n", e.category, e.amount, e.date, e.note);
    }

    // Show total spending
    void showSummary() {
        if (expenses.isEmpty()) {
            System.out.println("\nNo expenses available!");
            return;
        }
        double total = 0;
        for (Expense e : expenses) total += e.amount;
        System.out.println("\n💰 Total Expenses: ₹" + total);
    }

    // Save expenses to file
    void saveToFile() {
        try (PrintWriter writer = new PrintWriter(new File(filename))) {
            for (Expense e : expenses)
                writer.println(e.toString());
            System.out.println("📁 Data saved to " + filename);
        } catch (IOException e) {
            System.out.println("Error saving file.");
        }
    }
}

// Main class
public class ExpenseTracker {
    public static void main(String[] args) {
        ExpenseManager manager = new ExpenseManager();
        manager.loadFromFile();
        Scanner sc = new Scanner(System.in);
        int choice;

        do {
            System.out.println("\n====== EXPENSE TRACKER ======");
            System.out.println("1. Add Expense");
            System.out.println("2. View All Expenses");
            System.out.println("3. Show Summary");
            System.out.println("4. Save & Exit");
            System.out.print("Choose an option: ");
            choice = sc.nextInt();
            sc.nextLine(); // consume newline

            switch (choice) {
                case 1:
                    manager.addExpense();
                    break;
                case 2:
                    manager.viewAllExpenses();
                    break;
                case 3:
                    manager.showSummary();
                    break;
                case 4:
                    manager.saveToFile();
                    System.out.println("\nExiting... Goodbye!");
                    break;
                default:
                    System.out.println("❌ Invalid choice! Try again.");
            }
        } while (choice != 4);
    }
}