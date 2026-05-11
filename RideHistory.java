import java.util.*;

// Module 11: Ride History
public class RideHistory {
    private List<RideBooking> rideRecords;

    public RideHistory() {
        this.rideRecords = new ArrayList<>();
    }

    public void addRide(RideBooking ride) {
        rideRecords.add(ride);
        System.out.println("[GoPro] Ride " + ride.getBookingId() + " added to history.");
    }

    public void showAllRides() {
        System.out.println("[GoPro] ===== Ride History (" + rideRecords.size() + " rides) =====");
        if (rideRecords.isEmpty()) {
            System.out.println("  No rides yet.");
        } else {
            for (RideBooking ride : rideRecords) {
                System.out.println("  " + ride);
            }
        }
    }

    public void showRidesByStatus(String status) {
        System.out.println("[GoPro] ===== " + status + " Rides =====");
        for (RideBooking ride : rideRecords) {
            if (ride.getStatus().equals(status)) {
                System.out.println("  " + ride);
            }
        }
    }

    public double getTotalSpent() {
        double total = 0;
        for (RideBooking ride : rideRecords) {
            if ("COMPLETED".equals(ride.getStatus())) {
                total += ride.getFare();
            }
        }
        System.out.println("[GoPro] Total spent: Rs." + String.format("%.2f", total));
        return total;
    }

    public int getTotalRides() {
        return rideRecords.size();
    }

    public int getCompletedRides() {
        int count = 0;
        for (RideBooking ride : rideRecords) {
            if ("COMPLETED".equals(ride.getStatus())) count++;
        }
        return count;
    }

    public static void main(String[] args) {
        System.out.println("╔════════════════════════════════════════════╗");
        System.out.println("║   GoPro Ride History Module Demo           ║");
        System.out.println("╚════════════════════════════════════════════╝\n");

        // Create and add rides
        System.out.println("--- Test 1: Add Rides to History ---");
        RideHistory history = new RideHistory();
        
        RideBooking r1 = new RideBooking("R001", "U001", "Connaught Place", "India Gate", "SEDAN");
        r1.confirmRide("D001", 290);
        r1.startRide();
        r1.completeRide();
        
        RideBooking r2 = new RideBooking("R002", "U001", "MG Road", "Koramangala", "AUTO");
        r2.confirmRide("D002", 90);
        r2.startRide();
        r2.completeRide();
        
        RideBooking r3 = new RideBooking("R003", "U001", "Airport", "City Center", "SEDAN");
        r3.cancelRide("User requested");
        
        history.addRide(r1);
        history.addRide(r2);
        history.addRide(r3);

        // Test 2: Show all rides
        System.out.println("\n--- Test 2: Show All Rides ---");
        history.showAllRides();

        // Test 3: Show rides by status
        System.out.println("\n--- Test 3: Show Completed Rides ---");
        history.showRidesByStatus("COMPLETED");

        // Test 4: Calculate total spent
        System.out.println("\n--- Test 4: Total Spent ---");
        history.getTotalSpent();

        // Test 5: Statistics
        System.out.println("\n--- Test 5: Ride Statistics ---");
        System.out.println("Total Rides: " + history.getTotalRides());
        System.out.println("Completed Rides: " + history.getCompletedRides());

        System.out.println("\n╔════════════════════════════════════════════╗");
        System.out.println("║   Ride History Demo Complete!              ║");
        System.out.println("╚════════════════════════════════════════════╝");
    }
}
