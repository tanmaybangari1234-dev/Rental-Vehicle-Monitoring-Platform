import java.time.LocalDateTime;

// Module 7: Payment Processing
public class Payment {
    private String paymentId;
    private String bookingId;
    private String userId;
    private double amount;
    private String paymentMethod; // CASH, UPI, CARD, WALLET
    private String status;        // PENDING, SUCCESS, FAILED, REFUNDED
    private LocalDateTime paymentTime;

    public Payment(String paymentId, String bookingId, String userId, double amount, String paymentMethod) {
        this.paymentId = paymentId;
        this.bookingId = bookingId;
        this.userId = userId;
        this.amount = amount;
        this.paymentMethod = paymentMethod;
        this.status = "PENDING";
        this.paymentTime = LocalDateTime.now();
    }

    public boolean processPayment() {
        System.out.println("[GoPro] Processing " + paymentMethod + " payment of Rs." + amount + "...");
        // Simulate payment processing
        this.status = "SUCCESS";
        this.paymentTime = LocalDateTime.now();
        System.out.println("[GoPro] Payment " + paymentId + " SUCCESS via " + paymentMethod + " | Amount: Rs." + amount);
        return true;
    }

    public void refundPayment() {
        if ("SUCCESS".equals(status)) {
            this.status = "REFUNDED";
            System.out.println("[GoPro] Payment " + paymentId + " REFUNDED. Rs." + amount + " returned via " + paymentMethod);
        } else {
            System.out.println("[GoPro] Cannot refund. Payment status: " + status);
        }
    }

    public void failPayment(String reason) {
        this.status = "FAILED";
        System.out.println("[GoPro] Payment " + paymentId + " FAILED. Reason: " + reason);
    }

    // Getters
    public String getPaymentId() { return paymentId; }
    public String getBookingId() { return bookingId; }
    public double getAmount() { return amount; }
    public String getPaymentMethod() { return paymentMethod; }
    public String getStatus() { return status; }

    @Override
    public String toString() {
        return "Payment{id='" + paymentId + "', amount=Rs." + amount + ", method='" + paymentMethod + "', status='" + status + "'}";
    }

    public static void main(String[] args) {
        System.out.println("╔════════════════════════════════════════════╗");
        System.out.println("║   GoPro Payment Module Demo                ║");
        System.out.println("╚════════════════════════════════════════════╝\n");

        // Test 1: Create payments
        System.out.println("--- Test 1: Create Payments ---");
        Payment p1 = new Payment("P001", "R001", "U001", 290.0, "UPI");
        Payment p2 = new Payment("P002", "R002", "U001", 150.0, "CARD");
        Payment p3 = new Payment("P003", "R003", "U001", 75.0, "WALLET");

        // Test 2: Process payments
        System.out.println("\n--- Test 2: Process Payments ---");
        p1.processPayment();
        p2.processPayment();
        p3.processPayment();

        // Test 3: Refund payment
        System.out.println("\n--- Test 3: Refund Payment ---");
        p1.refundPayment();

        // Test 4: Fail payment
        System.out.println("\n--- Test 4: Fail Payment ---");
        Payment p4 = new Payment("P004", "R004", "U001", 200.0, "CASH");
        p4.failPayment("Invalid card details");

        // Test 5: Display payment status
        System.out.println("\n--- Test 5: Payment Status ---");
        System.out.println(p1);
        System.out.println(p4);

        System.out.println("\n╔════════════════════════════════════════════╗");
        System.out.println("║   Payment Module Demo Complete!            ║");
        System.out.println("╚════════════════════════════════════════════╝");
    }
}
