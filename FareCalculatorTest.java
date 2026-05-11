// Standalone test program for FareCalculator
public class FareCalculatorTest {
    public static void main(String[] args) {
        System.out.println("╔════════════════════════════════════════════╗");
        System.out.println("║   FareCalculator Standalone Demo           ║");
        System.out.println("╚════════════════════════════════════════════╝\n");

        FareCalculator calculator = new FareCalculator();

        // Test 1: Show fare estimates for a 15 km ride
        System.out.println("--- Test 1: Fare Estimates for 15 km ---");
        calculator.showEstimates(15);

        System.out.println("\n--- Test 2: Individual Fare Calculations ---");
        double fare1 = calculator.calculateFare("SEDAN", 10);
        double fare2 = calculator.calculateFare("AUTO", 8);
        double fare3 = calculator.calculateFare("BIKE", 5);

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
        System.out.println("║   Demo Complete!                           ║");
        System.out.println("╚════════════════════════════════════════════╝");
    }
}
