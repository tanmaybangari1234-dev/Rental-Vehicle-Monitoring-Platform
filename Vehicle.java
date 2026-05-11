public class Vehicle {
    private String vehicleId;
    private String driverId;
    private String vehicleType; // BIKE, AUTO, MINI, SEDAN, SUV
    private String vehicleNumber;
    private String model;
    private String color;
    private boolean isActive;

    public Vehicle(String vehicleId, String driverId, String vehicleType, String vehicleNumber, String model, String color) {
        this.vehicleId = vehicleId;
        this.driverId = driverId;
        this.vehicleType = vehicleType;
        this.vehicleNumber = vehicleNumber;
        this.model = model;
        this.color = color;
        this.isActive = true;
    }

    public void activateVehicle() {
        this.isActive = true;
        System.out.println("[GoPro] Vehicle " + vehicleNumber + " (" + model + ") activated.");
    }

    public void deactivateVehicle() {
        this.isActive = false;
        System.out.println("[GoPro] Vehicle " + vehicleNumber + " deactivated.");
    }

    public void updateVehicleDetails(String model, String color) {
        this.model = model;
        this.color = color;
        System.out.println("[GoPro] Vehicle details updated: " + model + " - " + color);
    }

    // Getters
    public String getVehicleId() { return vehicleId; }
    public String getDriverId() { return driverId; }
    public String getVehicleType() { return vehicleType; }
    public String getVehicleNumber() { return vehicleNumber; }
    public String getRegistrationNumber() { return vehicleNumber; }  // Alias for database
    public String getModel() { return model; }
    public String getModelName() { return model; }  // Alias for database
    public String getColor() { return color; }
    public boolean isActive() { return isActive; }
    public boolean isVerified() { return isActive; }  // Alias for database

    // Setters
    public void setVerified(boolean verified) { this.isActive = verified; }

    @Override
    public String toString() {
        return "Vehicle{type='" + vehicleType + "', number='" + vehicleNumber + "', model='" + model + "', color='" + color + "'}";
    }

    public static void main(String[] args) {
        System.out.println("╔════════════════════════════════════════════╗");
        System.out.println("║   GoPro Vehicle Module Demo                ║");
        System.out.println("╚════════════════════════════════════════════╝\n");

        // Test 1: Create vehicles
        System.out.println("--- Test 1: Vehicle Creation ---");
        Vehicle sedan = new Vehicle("V001", "D001", "SEDAN", "DL-01-AB-1234", "Maruti Dzire", "White");
        Vehicle bike = new Vehicle("V002", "D002", "BIKE", "KA-01-CD-5678", "Honda Activa", "Black");

        // Test 2: Display vehicles
        System.out.println("\n--- Test 2: Vehicle Details ---");
        System.out.println(sedan);
        System.out.println(bike);

        // Test 3: Update vehicle details
        System.out.println("\n--- Test 3: Update Vehicle Details ---");
        sedan.updateVehicleDetails("Maruti Swift", "Silver");

        // Test 4: Deactivate vehicle
        System.out.println("\n--- Test 4: Deactivate Vehicle ---");
        bike.deactivateVehicle();

        // Test 5: Reactivate vehicle
        System.out.println("\n--- Test 5: Reactivate Vehicle ---");
        bike.activateVehicle();

        System.out.println("\n╔════════════════════════════════════════════╗");
        System.out.println("║   Vehicle Module Demo Complete!            ║");
        System.out.println("╚════════════════════════════════════════════╝");
    }
}
