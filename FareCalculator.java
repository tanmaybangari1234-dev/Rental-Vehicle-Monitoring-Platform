// Module 6: Fare Calculation
public class FareCalculator {
    // Base fares per ride type
    private static final double BIKE_BASE = 20.0;
    private static final double AUTO_BASE = 30.0;
    private static final double MINI_BASE = 50.0;
    private static final double SEDAN_BASE = 80.0;
    private static final double SUV_BASE = 120.0;

    // Per km rate per ride type
    private static final double BIKE_PER_KM = 8.0;
    private static final double AUTO_PER_KM = 12.0;
    private static final double MINI_PER_KM = 15.0;
    private static final double SEDAN_PER_KM = 20.0;
    private static final double SUV_PER_KM = 25.0;

    // Surge multiplier
    private double surgeMultiplier;

    public FareCalculator() {
        this.surgeMultiplier = 1.0;
    }

    public double calculateFare(String rideType, double distanceKm) {
        double baseFare;
        double perKmRate;

        switch (rideType.toUpperCase()) {
            case "BIKE":  baseFare = BIKE_BASE;  perKmRate = BIKE_PER_KM;  break;
            case "AUTO":  baseFare = AUTO_BASE;  perKmRate = AUTO_PER_KM;  break;
            case "MINI":  baseFare = MINI_BASE;  perKmRate = MINI_PER_KM;  break;
            case "SEDAN": baseFare = SEDAN_BASE; perKmRate = SEDAN_PER_KM; break;
            case "SUV":   baseFare = SUV_BASE;   perKmRate = SUV_PER_KM;   break;
            default:
                System.out.println("[GoPro] Unknown ride type: " + rideType);
                return 0;
        }

        double fare = (baseFare + (perKmRate * distanceKm)) * surgeMultiplier;
        System.out.println("[GoPro] Fare for " + rideType + " (" + distanceKm + " km): Rs." + String.format("%.2f", fare)
                + (surgeMultiplier > 1 ? " (Surge: " + surgeMultiplier + "x)" : ""));
        return fare;
    }

    public void setSurge(double multiplier) {
        this.surgeMultiplier = multiplier;
        System.out.println("[GoPro] Surge pricing set to " + multiplier + "x");
    }

    public void removeSurge() {
        this.surgeMultiplier = 1.0;
        System.out.println("[GoPro] Surge pricing removed.");
    }

    public void showEstimates(double distanceKm) {
        System.out.println("[GoPro] ===== Fare Estimates for " + distanceKm + " km =====");
        String[] types = {"BIKE", "AUTO", "MINI", "SEDAN", "SUV"};
        for (String type : types) {
            calculateFare(type, distanceKm);
        }
    }

    public static void main(String[] args) {
        System.out.println("╔════════════════════════════════════════════╗");
        System.out.println("║   GoPro Fare Calculator Module             ║");
        System.out.println("╚════════════════════════════════════════════╝\n");

        FareCalculator calculator = new FareCalculator();

        // Test 1: Show fare estimates for a 15 km ride
        System.out.println("--- Test 1: Fare Estimates for 15 km ---");
        calculator.showEstimates(15);

        System.out.println("\n--- Test 2: Individual Fare Calculations ---");
        calculator.calculateFare("SEDAN", 10);
        calculator.calculateFare("AUTO", 8);
        calculator.calculateFare("BIKE", 5);

        // Test 3: Surge pricing
        System.out.println("\n--- Test 3: Surge Pricing (2.5x) ---");
        calculator.setSurge(2.5);
        calculator.calculateFare("SEDAN", 20);

        // Test 4: Different distances with surge
        System.out.println("\n--- Test 4: Fare Estimates with 2.5x Surge ---");
        calculator.showEstimates(10);

        // Test 5: Remove surge
        System.out.println("\n--- Test 5: Remove Surge Pricing ---");
        calculator.removeSurge();
        calculator.calculateFare("SUV", 25);

        System.out.println("\n╔════════════════════════════════════════════╗");
        System.out.println("║   FareCalculator Demo Complete!            ║");
        System.out.println("╚════════════════════════════════════════════╝");
    }
}
