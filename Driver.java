public class Driver {
    private String driverId;
    private String name;
    private String phone;
    private String licenseNumber;
    private boolean isAvailable;
    private boolean isVerified;
    private double rating;
    private int totalTrips;
    private String currentLocation;

    public Driver(String driverId, String name, String phone, String licenseNumber) {
        this.driverId = driverId;
        this.name = name;
        this.phone = phone;
        this.licenseNumber = licenseNumber;
        this.isAvailable = false;
        this.isVerified = false;
        this.rating = 5.0;
        this.totalTrips = 0;
        this.currentLocation = "Unknown";
    }

    public void verifyDriver() {
        if (licenseNumber != null && !licenseNumber.isEmpty()) {
            this.isVerified = true;
            System.out.println("[GoPro] Driver " + name + " verified successfully.");
        } else {
            System.out.println("[GoPro] Driver verification failed. Invalid license.");
        }
    }

    public void goOnline(String location) {
        if (isVerified) {
            this.isAvailable = true;
            this.currentLocation = location;
            System.out.println("[GoPro] Driver " + name + " is now ONLINE at " + location);
        } else {
            System.out.println("[GoPro] Driver not verified. Cannot go online.");
        }
    }

    public void goOffline() {
        this.isAvailable = false;
        System.out.println("[GoPro] Driver " + name + " is now OFFLINE.");
    }

    public void updateRating(double newRating) {
        this.rating = ((this.rating * totalTrips) + newRating) / (totalTrips + 1);
        this.totalTrips++;
        System.out.println("[GoPro] Driver " + name + " rating updated to " + String.format("%.1f", rating));
    }

    public void updateLocation(String location) {
        this.currentLocation = location;
    }

    // Getters
    public String getDriverId() { return driverId; }
    public String getName() { return name; }
    public String getPhone() { return phone; }
    public String getLicenseNumber() { return licenseNumber; }
    public boolean isAvailable() { return isAvailable; }
    public boolean isVerified() { return isVerified; }
    public double getRating() { return rating; }
    public int getTotalTrips() { return totalTrips; }
    public String getCurrentLocation() { return currentLocation; }

    // Setters
    public void setAvailable(boolean available) { this.isAvailable = available; }
    public void setVerified(boolean verified) { this.isVerified = verified; }
    public void setRating(double rating) { this.rating = rating; }
    public void setTotalTrips(int totalTrips) { this.totalTrips = totalTrips; }
    public void setCurrentLocation(String location) { this.currentLocation = location; }

    @Override
    public String toString() {
        return "Driver{name='" + name + "', available=" + isAvailable + ", rating=" + String.format("%.1f", rating) + ", trips=" + totalTrips + "}";
    }

    public static void main(String[] args) {
        System.out.println("╔════════════════════════════════════════════╗");
        System.out.println("║   GoPro Driver Module Demo                 ║");
        System.out.println("╚════════════════════════════════════════════╝\n");

        // Test 1: Create drivers
        System.out.println("--- Test 1: Driver Creation ---");
        Driver driver1 = new Driver("D001", "Vijay Kumar", "9123456789", "DL-2026-001");
        Driver driver2 = new Driver("D002", "Amit Singh", "9987654321", "KA-2026-002");

        // Test 2: Verify drivers
        System.out.println("\n--- Test 2: Driver Verification ---");
        driver1.verifyDriver();
        driver2.verifyDriver();

        // Test 3: Go online
        System.out.println("\n--- Test 3: Driver Goes Online ---");
        driver1.goOnline("Connaught Place, Delhi");
        driver2.goOnline("MG Road, Bangalore");

        // Test 4: Update rating
        System.out.println("\n--- Test 4: Update Rating ---");
        driver1.updateRating(4.8);
        driver1.updateRating(4.9);

        // Test 5: Go offline
        System.out.println("\n--- Test 5: Driver Goes Offline ---");
        driver1.goOffline();

        System.out.println("\n╔════════════════════════════════════════════╗");
        System.out.println("║   Driver Module Demo Complete!             ║");
        System.out.println("╚════════════════════════════════════════════╝");
    }
}
