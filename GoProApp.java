import java.time.LocalDateTime;
import java.util.*;

/*
 * ╔══════════════════════════════════════════════════════════════╗
 * ║                     GO PRO - Ride Hailing App               ║
 * ║                  "Your Ride, Your Way!"                     ║
 * ╠══════════════════════════════════════════════════════════════╣
 * ║  15 MODULES:                                                ║
 * ║   1.  User Registration & Authentication   (User.java)      ║
 * ║   2.  Driver Registration & Verification   (Driver.java)    ║
 * ║   3.  Vehicle Management                   (Vehicle.java)   ║
 * ║   4.  Ride Booking                         (RideBooking.java)║
 * ║   5.  Ride Matching                        (RideMatching.java)║
 * ║   6.  Fare Calculation                     (FareCalculator.java)║
 * ║   7.  Payment Processing                   (Payment.java)   ║
 * ║   8.  Wallet Management                    (Wallet.java)    ║
 * ║   9.  Ride Tracker (GPS)                   (RideTracker.java)║
 * ║  10.  Rating & Review                      (RatingReview.java)║
 * ║  11.  Ride History                         (RideHistory.java)║
 * ║  12.  Notification Service                 (Notification.java)║
 * ║  13.  Promo Code / Coupon                  (PromoCode.java) ║
 * ║  14.  SOS / Emergency                      (SOSEmergency.java)║
 * ║  15.  Admin Dashboard                      (AdminDashboard.java)║
 * ╚══════════════════════════════════════════════════════════════╝
 */
public class GoProApp {
    public static void main(String[] args) {

        System.out.println("╔══════════════════════════════════════════════════╗");
        System.out.println("║          Welcome to GO PRO Ride App!            ║");
        System.out.println("║            Your Ride, Your Way!                 ║");
        System.out.println("╚══════════════════════════════════════════════════╝");
        System.out.println();

        // ==========================================
        // MODULE 1: User Registration & Authentication
        // ==========================================
        System.out.println("===== MODULE 1: User Registration =====");
        User user1 = new User("U001", "Rahul Sharma", "rahul@email.com", "9876543210", "pass123");
        User user2 = new User("U002", "Priya Patel", "priya@email.com", "9876543211", "pass456");
        user1.verifyOTP("123456");
        user1.login("rahul@email.com", "pass123");
        System.out.println("User: " + user1);
        System.out.println();

        // ==========================================
        // MODULE 2: Driver Registration & Verification
        // ==========================================
        System.out.println("===== MODULE 2: Driver Registration =====");
        Driver driver1 = new Driver("D001", "Vijay Kumar", "9988776655", "DL-1234567890");
        Driver driver2 = new Driver("D002", "Amit Singh", "9988776656", "DL-0987654321");
        driver1.verifyDriver();
        driver2.verifyDriver();
        driver1.goOnline("Connaught Place, Delhi");
        driver2.goOnline("MG Road, Bangalore");
        System.out.println("Driver: " + driver1);
        System.out.println();

        // ==========================================
        // MODULE 3: Vehicle Management
        // ==========================================
        System.out.println("===== MODULE 3: Vehicle Management =====");
        Vehicle v1 = new Vehicle("V001", "D001", "SEDAN", "DL-01-AB-1234", "Maruti Dzire", "White");
        Vehicle v2 = new Vehicle("V002", "D002", "BIKE", "KA-01-CD-5678", "Honda Activa", "Black");
        System.out.println("Vehicle 1: " + v1);
        System.out.println("Vehicle 2: " + v2);
        System.out.println();

        // ==========================================
        // MODULE 4: Ride Booking
        // ==========================================
        System.out.println("===== MODULE 4: Ride Booking =====");
        RideBooking ride1 = new RideBooking("R001", "U001", "Connaught Place", "India Gate", "SEDAN");
        System.out.println();

        // ==========================================
        // MODULE 5: Ride Matching
        // ==========================================
        System.out.println("===== MODULE 5: Ride Matching =====");
        RideMatching matching = new RideMatching();
        matching.addDriver(driver1);
        matching.addDriver(driver2);
        matching.displayAvailableDrivers();
        Driver matchedDriver = matching.findNearestDriver("SEDAN", "Connaught Place");
        System.out.println();

        // ==========================================
        // MODULE 6: Fare Calculation
        // ==========================================
        System.out.println("===== MODULE 6: Fare Calculation =====");
        FareCalculator fareCalc = new FareCalculator();
        fareCalc.showEstimates(10.5);
        double rideFare = fareCalc.calculateFare("SEDAN", 10.5);
        System.out.println();

        // Confirm the ride with matched driver and calculated fare
        ride1.confirmRide(matchedDriver != null ? matchedDriver.getDriverId() : "D001", rideFare);
        System.out.println();

        // ==========================================
        // MODULE 7: Payment Processing
        // ==========================================
        System.out.println("===== MODULE 7: Payment Processing =====");
        Payment payment1 = new Payment("P001", "R001", "U001", rideFare, "UPI");
        ride1.startRide();
        System.out.println();

        // ==========================================
        // MODULE 8: Wallet Management
        // ==========================================
        System.out.println("===== MODULE 8: Wallet Management =====");
        Wallet wallet = new Wallet("W001", "U001");
        wallet.addMoney(1000);
        wallet.addMoney(500);
        wallet.deductMoney(rideFare);
        wallet.showTransactionHistory();
        System.out.println();

        // ==========================================
        // MODULE 9: Ride Tracker (GPS)
        // ==========================================
        System.out.println("===== MODULE 9: Ride Tracking =====");
        RideTracker tracker = new RideTracker("R001", "Connaught Place", "India Gate", 10.5);
        tracker.startTracking();
        tracker.updateDriverLocation("Janpath Road", 3.5);
        tracker.updateDriverLocation("Rajpath", 7.0);
        tracker.updateDriverLocation("India Gate", 10.5);
        tracker.stopTracking();
        System.out.println();

        // Complete the ride and process payment
        ride1.completeRide();
        payment1.processPayment();
        System.out.println();

        // ==========================================
        // MODULE 10: Rating & Review
        // ==========================================
        System.out.println("===== MODULE 10: Rating & Review =====");
        RatingReview review1 = new RatingReview("REV001", "R001", "U001", "D001", 5, "Excellent driver! Very polite.");
        review1.submitReview();
        RatingReview review2 = new RatingReview("REV002", "R001", "D001", "U001", 4, "Good passenger.");
        review2.submitReview();
        RatingReview.getAverageRating("D001");
        RatingReview.showReviewsFor("D001");
        System.out.println();

        // ==========================================
        // MODULE 11: Ride History
        // ==========================================
        System.out.println("===== MODULE 11: Ride History =====");
        RideHistory history = new RideHistory();
        history.addRide(ride1);
        // Add another sample ride
        RideBooking ride2 = new RideBooking("R002", "U001", "MG Road", "Koramangala", "AUTO");
        ride2.confirmRide("D002", fareCalc.calculateFare("AUTO", 5.0));
        ride2.startRide();
        ride2.completeRide();
        history.addRide(ride2);
        history.showAllRides();
        history.getTotalSpent();
        System.out.println();

        // ==========================================
        // MODULE 12: Notification Service
        // ==========================================
        System.out.println("===== MODULE 12: Notifications =====");
        Notification.sendRideUpdate("U001", "R001", "COMPLETED");
        Notification.sendPaymentNotification("U001", rideFare, "SUCCESS");
        Notification.sendPromoNotification("U001", "GOPRO50", 50);
        Notification.showNotifications("U001");
        System.out.println();

        // ==========================================
        // MODULE 13: Promo Code / Coupon
        // ==========================================
        System.out.println("===== MODULE 13: Promo Codes =====");
        PromoCode promo1 = new PromoCode("GOPRO50", 50, 100, 100, 10, LocalDateTime.now().plusDays(30));
        PromoCode promo2 = new PromoCode("FIRSTRIDE", 20, 50, 50, 1, LocalDateTime.now().plusDays(7));
        promo1.addPromo();
        promo2.addPromo();
        PromoCode.showAllPromos();
        promo1.applyPromo(rideFare);
        System.out.println();

        // ==========================================
        // MODULE 14: SOS / Emergency
        // ==========================================
        System.out.println("===== MODULE 14: SOS Emergency =====");
        SOSEmergency sos = new SOSEmergency("U001", "9111222333");
        sos.shareLocation("R001", "Rajpath, Delhi");
        sos.triggerSOS("R001", "Rajpath, Delhi");
        sos.acknowledgeSOS();
        sos.resolveSOS("User confirmed safe. False alarm.");
        System.out.println();

        // ==========================================
        // MODULE 15: Admin Dashboard
        // ==========================================
        System.out.println("===== MODULE 15: Admin Dashboard =====");
        AdminDashboard admin = new AdminDashboard("A001", "Super Admin");
        admin.showDashboard(2, 2, 2, 0, rideFare + 90.0);
        List<Driver> allDrivers = new ArrayList<>();
        allDrivers.add(driver1);
        allDrivers.add(driver2);
        admin.viewAllDrivers(allDrivers);
        admin.generateRevenueReport(rideFare, rideFare * 5, rideFare * 22);
        System.out.println();

        // ==========================================
        // FINAL SUMMARY
        // ==========================================
        System.out.println("╔══════════════════════════════════════════════════╗");
        System.out.println("║      GO PRO - All 15 Modules Demo Complete!     ║");
        System.out.println("║            Thank you for using GoPro!           ║");
        System.out.println("╚══════════════════════════════════════════════════╝");
    }
}
