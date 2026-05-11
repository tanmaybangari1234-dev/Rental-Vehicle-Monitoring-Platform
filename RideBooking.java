import java.time.LocalDateTime;

// Module 4: Ride Booking
public class RideBooking {
    private String bookingId;
    private String userId;
    private String driverId;
    private String pickup;
    private String destination;
    private String rideType; // BIKE, AUTO, MINI, SEDAN, SUV
    private String status;   // REQUESTED, CONFIRMED, IN_PROGRESS, COMPLETED, CANCELLED
    private LocalDateTime bookingTime;
    private double fare;

    public RideBooking(String bookingId, String userId, String pickup, String destination, String rideType) {
        this.bookingId = bookingId;
        this.userId = userId;
        this.pickup = pickup;
        this.destination = destination;
        this.rideType = rideType;
        this.status = "REQUESTED";
        this.bookingTime = LocalDateTime.now();
        this.driverId = null;
        this.fare = 0.0;
        System.out.println("[GoPro] Ride booked! ID: " + bookingId + " | " + pickup + " -> " + destination + " | Type: " + rideType);
    }

    // Overloaded constructor for 4 parameters (from database)
    public RideBooking(String bookingId, String userId, String pickup, String destination) {
        this(bookingId, userId, pickup, destination, "AUTO");  // Default ride type
    }

    public void confirmRide(String driverId, double fare) {
        this.driverId = driverId;
        this.fare = fare;
        this.status = "CONFIRMED";
        System.out.println("[GoPro] Ride " + bookingId + " CONFIRMED. Driver: " + driverId + " | Fare: Rs." + fare);
    }

    public void startRide() {
        if ("CONFIRMED".equals(status)) {
            this.status = "IN_PROGRESS";
            System.out.println("[GoPro] Ride " + bookingId + " STARTED. " + pickup + " -> " + destination);
        } else {
            System.out.println("[GoPro] Cannot start ride. Current status: " + status);
        }
    }

    public void completeRide() {
        if ("IN_PROGRESS".equals(status)) {
            this.status = "COMPLETED";
            System.out.println("[GoPro] Ride " + bookingId + " COMPLETED. Fare: Rs." + fare);
        } else {
            System.out.println("[GoPro] Cannot complete ride. Current status: " + status);
        }
    }

    public void cancelRide(String reason) {
        if ("REQUESTED".equals(status) || "CONFIRMED".equals(status)) {
            this.status = "CANCELLED";
            System.out.println("[GoPro] Ride " + bookingId + " CANCELLED. Reason: " + reason);
        } else {
            System.out.println("[GoPro] Cannot cancel ride. Current status: " + status);
        }
    }

    // Getters
    public String getBookingId() { return bookingId; }
    public String getUserId() { return userId; }
    public String getDriverId() { return driverId; }
    public String getPickup() { return pickup; }
    public String getPickupLocation() { return pickup; }  // Alias for database
    public String getDestination() { return destination; }
    public String getDropoffLocation() { return destination; }  // Alias for database
    public String getRideType() { return rideType; }
    public String getStatus() { return status; }
    public String getBookingStatus() { return status; }  // Alias for database
    public LocalDateTime getBookingTime() { return bookingTime; }
    public double getFare() { return fare; }
    public double getFareAmount() { return fare; }  // Alias for database

    // Setters
    public void setDriverId(String driverId) { this.driverId = driverId; }
    public void setBookingStatus(String status) { this.status = status; }
    public void setFareAmount(double fare) { this.fare = fare; }

    @Override
    public String toString() {
        return "RideBooking{id='" + bookingId + "', " + pickup + " -> " + destination + ", status='" + status + "', fare=Rs." + fare + "}";
    }

    public static void main(String[] args) {
        System.out.println("╔════════════════════════════════════════════╗");
        System.out.println("║   GoPro Ride Booking Module Demo           ║");
        System.out.println("╚════════════════════════════════════════════╝\n");

        // Test 1: Book a ride
        System.out.println("--- Test 1: Book Ride ---");
        RideBooking ride1 = new RideBooking("R001", "U001", "Connaught Place", "India Gate", "SEDAN");
        RideBooking ride2 = new RideBooking("R002", "U001", "MG Road", "Koramangala", "AUTO");

        // Test 2: Confirm ride
        System.out.println("\n--- Test 2: Confirm Ride ---");
        ride1.confirmRide("D001", 290.0);

        // Test 3: Start ride
        System.out.println("\n--- Test 3: Start Ride ---");
        ride1.startRide();

        // Test 4: Complete ride
        System.out.println("\n--- Test 4: Complete Ride ---");
        ride1.completeRide();

        // Test 5: Cancel ride
        System.out.println("\n--- Test 5: Cancel Ride ---");
        ride2.cancelRide("User requested cancellation");

        System.out.println("\n╔════════════════════════════════════════════╗");
        System.out.println("║   Ride Booking Demo Complete!              ║");
        System.out.println("╚════════════════════════════════════════════╝");
    }
}
