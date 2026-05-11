import java.time.LocalDateTime;
import java.util.*;

// Module 13: Promo Code / Coupon Management
public class PromoCode {
    private String code;
    private int discountPercent;
    private double maxDiscount;
    private double minOrderAmount;
    private int usageLimit;
    private int timesUsed;
    private boolean isActive;
    private LocalDateTime expiryDate;

    // Store all promo codes
    private static List<PromoCode> allPromoCodes = new ArrayList<>();

    public PromoCode(String code, int discountPercent, double maxDiscount, double minOrderAmount, int usageLimit, LocalDateTime expiryDate) {
        this.code = code;
        this.discountPercent = discountPercent;
        this.maxDiscount = maxDiscount;
        this.minOrderAmount = minOrderAmount;
        this.usageLimit = usageLimit;
        this.timesUsed = 0;
        this.isActive = true;
        this.expiryDate = expiryDate;
    }

    public void addPromo() {
        allPromoCodes.add(this);
        System.out.println("[GoPro] Promo code '" + code + "' created: " + discountPercent + "% off (max Rs." + maxDiscount + ")");
    }

    public double applyPromo(double fareAmount) {
        if (!isActive) {
            System.out.println("[GoPro] Promo code '" + code + "' is not active.");
            return 0;
        }
        if (timesUsed >= usageLimit) {
            System.out.println("[GoPro] Promo code '" + code + "' usage limit reached.");
            return 0;
        }
        if (fareAmount < minOrderAmount) {
            System.out.println("[GoPro] Minimum order Rs." + minOrderAmount + " required for code '" + code + "'.");
            return 0;
        }
        if (LocalDateTime.now().isAfter(expiryDate)) {
            System.out.println("[GoPro] Promo code '" + code + "' has expired.");
            return 0;
        }

        double discount = (fareAmount * discountPercent) / 100;
        discount = Math.min(discount, maxDiscount);
        timesUsed++;
        System.out.println("[GoPro] Promo '" + code + "' applied! Discount: Rs." + String.format("%.2f", discount)
                + " | New fare: Rs." + String.format("%.2f", (fareAmount - discount)));
        return discount;
    }

    public void deactivate() {
        this.isActive = false;
        System.out.println("[GoPro] Promo code '" + code + "' deactivated.");
    }

    public static void showAllPromos() {
        System.out.println("[GoPro] ===== Available Promo Codes =====");
        for (PromoCode p : allPromoCodes) {
            if (p.isActive) {
                System.out.println("  " + p.code + " - " + p.discountPercent + "% off (max Rs." + p.maxDiscount
                        + ") | Min: Rs." + p.minOrderAmount + " | Used: " + p.timesUsed + "/" + p.usageLimit);
            }
        }
    }

    // Getters
    public String getCode() { return code; }
    public int getDiscountPercent() { return discountPercent; }
    public boolean isActive() { return isActive; }

    @Override
    public String toString() {
        return "PromoCode{'" + code + "', " + discountPercent + "% off, active=" + isActive + "}";
    }

    public static void main(String[] args) {
        System.out.println("╔════════════════════════════════════════════╗");
        System.out.println("║   GoPro Promo Code Module Demo             ║");
        System.out.println("╚════════════════════════════════════════════╝\n");

        // Test 1: Create promo codes
        System.out.println("--- Test 1: Create Promo Codes ---");
        PromoCode promo1 = new PromoCode("GOPRO50", 50, 100, 100, 10, LocalDateTime.now().plusDays(30));
        PromoCode promo2 = new PromoCode("FIRSTRIDE", 20, 50, 50, 1, LocalDateTime.now().plusDays(7));
        PromoCode promo3 = new PromoCode("FLAT50", 0, 50, 200, 5, LocalDateTime.now().plusDays(15));

        promo1.addPromo();
        promo2.addPromo();
        promo3.addPromo();

        // Test 2: Show all promos
        System.out.println("\n--- Test 2: Show All Promo Codes ---");
        PromoCode.showAllPromos();

        // Test 3: Apply promo
        System.out.println("\n--- Test 3: Apply Promo Codes ---");
        promo1.applyPromo(290);
        promo2.applyPromo(100);
        promo3.applyPromo(300);

        // Test 4: Usage limit check
        System.out.println("\n--- Test 4: Usage Limit Check ---");
        promo2.applyPromo(100);

        // Test 5: Minimum amount check
        System.out.println("\n--- Test 5: Minimum Amount Check ---");
        promo1.applyPromo(50);

        System.out.println("\n╔════════════════════════════════════════════╗");
        System.out.println("║   Promo Code Demo Complete!                ║");
        System.out.println("╚════════════════════════════════════════════╝");
    }
}
