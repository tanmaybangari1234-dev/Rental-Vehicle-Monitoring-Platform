import java.time.LocalDateTime;
import java.util.*;

// Module 8: Wallet Management
public class Wallet {
    private String walletId;
    private String userId;
    private double balance;
    private List<String> transactions;

    public Wallet(String walletId, String userId) {
        this.walletId = walletId;
        this.userId = userId;
        this.balance = 0.0;
        this.transactions = new ArrayList<>();
    }

    public void addMoney(double amount) {
        if (amount > 0) {
            this.balance += amount;
            String txn = "[+" + amount + "] Added to wallet | Balance: Rs." + balance + " | " + LocalDateTime.now();
            transactions.add(txn);
            System.out.println("[GoPro] Rs." + amount + " added to wallet. Balance: Rs." + balance);
        } else {
            System.out.println("[GoPro] Invalid amount.");
        }
    }

    public boolean deductMoney(double amount) {
        if (amount > 0 && balance >= amount) {
            this.balance -= amount;
            String txn = "[-" + amount + "] Deducted from wallet | Balance: Rs." + balance + " | " + LocalDateTime.now();
            transactions.add(txn);
            System.out.println("[GoPro] Rs." + amount + " deducted. Balance: Rs." + balance);
            return true;
        } else {
            System.out.println("[GoPro] Insufficient wallet balance. Current: Rs." + balance);
            return false;
        }
    }

    public double getBalance() {
        return balance;
    }

    public void showTransactionHistory() {
        System.out.println("[GoPro] ===== Wallet Transactions =====");
        if (transactions.isEmpty()) {
            System.out.println("  No transactions yet.");
        } else {
            for (String txn : transactions) {
                System.out.println("  " + txn);
            }
        }
        System.out.println("  Current Balance: Rs." + balance);
    }

    @Override
    public String toString() {
        return "Wallet{userId='" + userId + "', balance=Rs." + balance + "}";
    }

    public static void main(String[] args) {
        System.out.println("╔════════════════════════════════════════════╗");
        System.out.println("║   GoPro Wallet Module Demo                 ║");
        System.out.println("╚════════════════════════════════════════════╝\n");

        // Test 1: Create wallet
        System.out.println("--- Test 1: Create Wallet ---");
        Wallet wallet = new Wallet("W001", "U001");

        // Test 2: Add money
        System.out.println("\n--- Test 2: Add Money ---");
        wallet.addMoney(1000);
        wallet.addMoney(500);
        wallet.addMoney(250);

        // Test 3: Deduct money
        System.out.println("\n--- Test 3: Deduct Money ---");
        wallet.deductMoney(290);
        wallet.deductMoney(150);

        // Test 4: Insufficient balance
        System.out.println("\n--- Test 4: Insufficient Balance ---");
        wallet.deductMoney(5000);

        // Test 5: View transaction history
        System.out.println("\n--- Test 5: Transaction History ---");
        wallet.showTransactionHistory();

        System.out.println("\n╔════════════════════════════════════════════╗");
        System.out.println("║   Wallet Module Demo Complete!             ║");
        System.out.println("╚════════════════════════════════════════════╝");
    }
}
