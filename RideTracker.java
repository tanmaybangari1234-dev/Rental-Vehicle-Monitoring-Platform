// Module 9: Ride Tracker (GPS/Location Tracking)
public class RideTracker {
    private String bookingId;
    private String driverLocation;
    private String riderLocation;
    private String pickup;
    private String destination;
    private double distanceCovered;
    private double totalDistance;
    private boolean isTracking;

    public RideTracker(String bookingId, String pickup, String destination, double totalDistance) {
        this.bookingId = bookingId;
        this.pickup = pickup;
        this.destination = destination;
        this.totalDistance = totalDistance;
        this.distanceCovered = 0.0;
        this.driverLocation = pickup;
        this.riderLocation = pickup;
        this.isTracking = false;
    }

    public void startTracking() {
        this.isTracking = true;
        System.out.println("[GoPro] Live tracking started for ride " + bookingId);
        System.out.println("[GoPro] Route: " + pickup + " -> " + destination + " (" + totalDistance + " km)");
    }

    public void updateDriverLocation(String location, double kmCovered) {
        if (isTracking) {
            this.driverLocation = location;
            this.distanceCovered = kmCovered;
            double remaining = totalDistance - distanceCovered;
            int etaMinutes = (int) (remaining * 2); // ~2 min per km estimate
            System.out.println("[GoPro] Driver at: " + location + " | " + String.format("%.1f", distanceCovered) + "/" + totalDistance + " km | ETA: " + etaMinutes + " min");
        }
    }

    public void stopTracking() {
        this.isTracking = false;
        System.out.println("[GoPro] Tracking stopped for ride " + bookingId + ". Distance: " + distanceCovered + " km");
    }

    public double getDistanceCovered() { return distanceCovered; }
    public double getTotalDistance() { return totalDistance; }
    public String getDriverLocation() { return driverLocation; }
    public boolean isTracking() { return isTracking; }

    @Override
    public String toString() {
        return "RideTracker{ride='" + bookingId + "', progress=" + distanceCovered + "/" + totalDistance + " km, tracking=" + isTracking + "}";
    }

    public static void main(String[] args) {
        System.out.println("╔════════════════════════════════════════════╗");
        System.out.println("║   GoPro Ride Tracker Module Demo           ║");
        System.out.println("╚════════════════════════════════════════════╝\n");

        // Test 1: Create tracker
        System.out.println("--- Test 1: Create Ride Tracker ---");
        RideTracker tracker = new RideTracker("R001", "Connaught Place", "India Gate", 10.5);

        // Test 2: Start tracking
        System.out.println("\n--- Test 2: Start Tracking ---");
        tracker.startTracking();

        // Test 3: Update driver location
        System.out.println("\n--- Test 3: Live Location Updates ---");
        tracker.updateDriverLocation("Janpath Road", 3.5);
        tracker.updateDriverLocation("Rajpath", 7.0);
        tracker.updateDriverLocation("India Gate", 10.5);

        // Test 4: Stop tracking
        System.out.println("\n--- Test 4: Stop Tracking ---");
        tracker.stopTracking();

        // Test 5: Display tracker status
        System.out.println("\n--- Test 5: Tracker Status ---");
        System.out.println(tracker);

        System.out.println("\n╔════════════════════════════════════════════╗");
        System.out.println("║   Ride Tracker Demo Complete!              ║");
        System.out.println("╚════════════════════════════════════════════╝");
    }
}
