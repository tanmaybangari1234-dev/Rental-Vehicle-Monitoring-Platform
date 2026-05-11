import java.util.*;

// Module 5: Ride Matching - Matches riders with nearest available drivers
public class RideMatching {
    private List<Driver> availableDrivers;

    public RideMatching() {
        this.availableDrivers = new ArrayList<>();
    }

    public void addDriver(Driver driver) {
        if (driver.isAvailable() && driver.isVerified()) {
            availableDrivers.add(driver);
            System.out.println("[GoPro] Driver " + driver.getName() + " added to available pool.");
        }
    }

    public void removeDriver(Driver driver) {
        availableDrivers.remove(driver);
        System.out.println("[GoPro] Driver " + driver.getName() + " removed from available pool.");
    }

    public Driver findNearestDriver(String rideType, String location) {
        System.out.println("[GoPro] Searching for " + rideType + " driver near " + location + "...");
        Driver bestMatch = null;
        double bestRating = 0;

        for (Driver driver : availableDrivers) {
            if (driver.isAvailable() && driver.getRating() > bestRating) {
                bestMatch = driver;
                bestRating = driver.getRating();
            }
        }

        if (bestMatch != null) {
            System.out.println("[GoPro] Driver found: " + bestMatch.getName() + " (Rating: " + String.format("%.1f", bestMatch.getRating()) + ")");
        } else {
            System.out.println("[GoPro] No drivers available nearby. Please try again.");
        }
        return bestMatch;
    }

    public int getAvailableDriverCount() {
        return availableDrivers.size();
    }

    public void displayAvailableDrivers() {
        System.out.println("[GoPro] Available Drivers (" + availableDrivers.size() + "):");
        for (Driver d : availableDrivers) {
            System.out.println("  - " + d);
        }
    }

    public static void main(String[] args) {
        System.out.println("╔════════════════════════════════════════════╗");
        System.out.println("║   GoPro Ride Matching Module Demo          ║");
        System.out.println("╚════════════════════════════════════════════╝\n");

        // Create drivers
        Driver d1 = new Driver("D001", "Vijay Kumar", "9123456789", "DL-2026-001");
        Driver d2 = new Driver("D002", "Amit Singh", "9987654321", "KA-2026-002");
        Driver d3 = new Driver("D003", "Raj Patel", "9555555555", "GJ-2026-003");

        d1.verifyDriver();
        d2.verifyDriver();
        d3.verifyDriver();

        d1.goOnline("Connaught Place");
        d2.goOnline("MG Road");
        d3.goOnline("Airport Road");

        // Test: Ride matching
        System.out.println("--- Test 1: Add Drivers to Pool ---");
        RideMatching matching = new RideMatching();
        matching.addDriver(d1);
        matching.addDriver(d2);
        matching.addDriver(d3);

        System.out.println("\n--- Test 2: Display Available Drivers ---");
        matching.displayAvailableDrivers();

        System.out.println("\n--- Test 3: Find Nearest Driver ---");
        matching.findNearestDriver("SEDAN", "Connaught Place");
        matching.findNearestDriver("AUTO", "MG Road");

        System.out.println("\n--- Test 4: Remove Driver ---");
        matching.removeDriver(d3);
        System.out.println("\nAvailable drivers after removal: " + matching.getAvailableDriverCount());

        System.out.println("\n╔════════════════════════════════════════════╗");
        System.out.println("║   Ride Matching Demo Complete!             ║");
        System.out.println("╚════════════════════════════════════════════╝");
    }
}
