import java.time.LocalDateTime;
import java.util.*;

// Module 12: Notification Service
public class Notification {
    private String notificationId;
    private String userId;
    private String title;
    private String message;
    private String type;     // RIDE_UPDATE, PAYMENT, PROMO, SAFETY, GENERAL
    private boolean isRead;
    private LocalDateTime timestamp;

    // Stores all notifications
    private static List<Notification> allNotifications = new ArrayList<>();

    public Notification(String notificationId, String userId, String title, String message, String type) {
        this.notificationId = notificationId;
        this.userId = userId;
        this.title = title;
        this.message = message;
        this.type = type;
        this.isRead = false;
        this.timestamp = LocalDateTime.now();
    }

    public void send() {
        allNotifications.add(this);
        System.out.println("[GoPro] NOTIFICATION to " + userId + ": [" + type + "] " + title + " - " + message);
    }

    public void markAsRead() {
        this.isRead = true;
    }

    public static void sendRideUpdate(String userId, String bookingId, String status) {
        Notification n = new Notification(
            "N" + System.currentTimeMillis(), userId,
            "Ride Update", "Your ride " + bookingId + " is now " + status, "RIDE_UPDATE"
        );
        n.send();
    }

    public static void sendPaymentNotification(String userId, double amount, String status) {
        Notification n = new Notification(
            "N" + System.currentTimeMillis(), userId,
            "Payment " + status, "Rs." + amount + " payment " + status.toLowerCase(), "PAYMENT"
        );
        n.send();
    }

    public static void sendPromoNotification(String userId, String promoCode, int discount) {
        Notification n = new Notification(
            "N" + System.currentTimeMillis(), userId,
            "New Offer!", "Use code " + promoCode + " for " + discount + "% off!", "PROMO"
        );
        n.send();
    }

    public static void showNotifications(String userId) {
        System.out.println("[GoPro] ===== Notifications for " + userId + " =====");
        int unread = 0;
        for (Notification n : allNotifications) {
            if (n.userId.equals(userId)) {
                String readStatus = n.isRead ? "  " : "* ";
                System.out.println("  " + readStatus + "[" + n.type + "] " + n.title + ": " + n.message);
                if (!n.isRead) unread++;
            }
        }
        System.out.println("  Unread: " + unread);
    }

    @Override
    public String toString() {
        return "Notification{'" + title + "': " + message + ", read=" + isRead + "}";
    }

    public static void main(String[] args) {
        System.out.println("╔════════════════════════════════════════════╗");
        System.out.println("║   GoPro Notification Module Demo           ║");
        System.out.println("╚════════════════════════════════════════════╝\n");

        // Test 1: Send notifications
        System.out.println("--- Test 1: Send Notifications ---");
        Notification.sendRideUpdate("U001", "R001", "COMPLETED");
        Notification.sendPaymentNotification("U001", 290.0, "SUCCESS");
        Notification.sendPromoNotification("U001", "GOPRO50", 50);

        // Test 2: Create and send custom notification
        System.out.println("\n--- Test 2: Custom Notification ---");
        Notification custom = new Notification("N100", "U001", "Safety Alert", "Your ride is arriving in 2 minutes", "GENERAL");
        custom.send();

        // Test 3: Show notifications
        System.out.println("\n--- Test 3: View Notifications ---");
        Notification.showNotifications("U001");

        System.out.println("\n╔════════════════════════════════════════════╗");
        System.out.println("║   Notification Demo Complete!              ║");
        System.out.println("╚════════════════════════════════════════════╝");
    }
}
