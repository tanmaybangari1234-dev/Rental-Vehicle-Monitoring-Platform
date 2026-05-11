import java.util.Scanner;

// Module Runner - Execute any GoPro module standalone
public class ModuleRunner {
    public static void main(String[] args) {
        System.out.println("╔══════════════════════════════════════════════════╗");
        System.out.println("║         GoPro Module Runner                      ║");
        System.out.println("║      Run Any Module Standalone!                  ║");
        System.out.println("╚══════════════════════════════════════════════════╝\n");

        System.out.println("Available Modules:");
        System.out.println("1.  User Module              - User registration & login");
        System.out.println("2.  Driver Module            - Driver management");
        System.out.println("3.  Vehicle Module           - Vehicle management");
        System.out.println("4.  Ride Booking Module      - Book and manage rides");
        System.out.println("5.  Ride Matching Module     - Match riders with drivers");
        System.out.println("6.  Fare Calculator Module   - Calculate ride fares");
        System.out.println("7.  Wallet Module            - Manage wallet & balance");
        System.out.println("8.  Payment Module           - Process payments");
        System.out.println("9.  Notification Module      - Send notifications");
        System.out.println("10. Ride Tracker Module      - Track live rides");
        System.out.println("11. Rating & Review Module   - Rate and review drivers");
        System.out.println("12. Ride History Module      - View ride history");
        System.out.println("13. Promo Code Module        - Manage promo codes");
        System.out.println("14. SOS Emergency Module     - Emergency handling");
        System.out.println("15. Admin Dashboard Module   - Admin panel");
        System.out.println("0.  Exit");
        System.out.print("\nEnter module number to run: ");

        Scanner scanner = new Scanner(System.in);
        try {
            int choice = scanner.nextInt();
            scanner.close();

            switch (choice) {
                case 1:
                    System.out.println("\n");
                    User.main(new String[]{});
                    break;
                case 2:
                    System.out.println("\n");
                    Driver.main(new String[]{});
                    break;
                case 3:
                    System.out.println("\n");
                    Vehicle.main(new String[]{});
                    break;
                case 4:
                    System.out.println("\n");
                    RideBooking.main(new String[]{});
                    break;
                case 5:
                    System.out.println("\n");
                    RideMatching.main(new String[]{});
                    break;
                case 6:
                    System.out.println("\n");
                    FareCalculator.main(new String[]{});
                    break;
                case 7:
                    System.out.println("\n");
                    Wallet.main(new String[]{});
                    break;
                case 8:
                    System.out.println("\n");
                    Payment.main(new String[]{});
                    break;
                case 9:
                    System.out.println("\n");
                    Notification.main(new String[]{});
                    break;
                case 10:
                    System.out.println("\n");
                    RideTracker.main(new String[]{});
                    break;
                case 11:
                    System.out.println("\n");
                    RatingReview.main(new String[]{});
                    break;
                case 12:
                    System.out.println("\n");
                    RideHistory.main(new String[]{});
                    break;
                case 13:
                    System.out.println("\n");
                    PromoCode.main(new String[]{});
                    break;
                case 14:
                    System.out.println("\n");
                    SOSEmergency.main(new String[]{});
                    break;
                case 15:
                    System.out.println("\n");
                    AdminDashboard.main(new String[]{});
                    break;
                case 0:
                    System.out.println("\nExiting Module Runner...");
                    System.exit(0);
                default:
                    System.out.println("\nInvalid choice. Please run again and select a valid module.");
            }
        } catch (Exception e) {
            System.out.println("\nError: " + e.getMessage());
        }
    }
}
