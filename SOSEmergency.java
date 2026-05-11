import java.time.LocalDateTime;

// Module 14: SOS / Emergency Service
public class SOSEmergency {
    private String sosId;
    private String userId;
    private String bookingId;
    private String emergencyContact;
    private String location;
    private String status; // TRIGGERED, ACKNOWLEDGED, RESOLVED
    private LocalDateTime triggeredAt;

    public SOSEmergency(String userId, String emergencyContact) {
        this.userId = userId;
        this.emergencyContact = emergencyContact;
        this.status = "IDLE";
    }

    public void triggerSOS(String bookingId, String location) {
        this.sosId = "SOS" + System.currentTimeMillis();
        this.bookingId = bookingId;
        this.location = location;
        this.status = "TRIGGERED";
        this.triggeredAt = LocalDateTime.now();
        System.out.println("[GoPro] !!!! SOS TRIGGERED !!!!");
        System.out.println("[GoPro] User: " + userId + " | Ride: " + bookingId);
        System.out.println("[GoPro] Location: " + location);
        System.out.println("[GoPro] Emergency contact " + emergencyContact + " has been notified.");
        System.out.println("[GoPro] GoPro Safety Team alerted. Help is on the way!");
    }

    public void acknowledgeSOS() {
        if ("TRIGGERED".equals(status)) {
            this.status = "ACKNOWLEDGED";
            System.out.println("[GoPro] SOS " + sosId + " acknowledged by safety team.");
        }
    }

    public void resolveSOS(String resolution) {
        this.status = "RESOLVED";
        System.out.println("[GoPro] SOS " + sosId + " RESOLVED. " + resolution);
    }

    public void shareLocation(String bookingId, String location) {
        System.out.println("[GoPro] Live location shared with " + emergencyContact + " for ride " + bookingId);
        System.out.println("[GoPro] Current location: " + location);
    }

    public void updateEmergencyContact(String newContact) {
        this.emergencyContact = newContact;
        System.out.println("[GoPro] Emergency contact updated to " + newContact);
    }

    public String getStatus() { return status; }
    public String getEmergencyContact() { return emergencyContact; }

    @Override
    public String toString() {
        return "SOSEmergency{user='" + userId + "', status='" + status + "', contact='" + emergencyContact + "'}";
    }

    public static void main(String[] args) {
        System.out.println("╔════════════════════════════════════════════╗");
        System.out.println("║   GoPro SOS Emergency Module Demo          ║");
        System.out.println("╚════════════════════════════════════════════╝\n");

        // Test 1: Create SOS emergency
        System.out.println("--- Test 1: Create SOS Emergency ---");
        SOSEmergency sos = new SOSEmergency("U001", "9111222333");

        // Test 2: Share location
        System.out.println("\n--- Test 2: Share Location ---");
        sos.shareLocation("R001", "Rajpath, Delhi");

        // Test 3: Trigger SOS
        System.out.println("\n--- Test 3: Trigger SOS ---");
        sos.triggerSOS("R001", "Rajpath, Delhi");

        // Test 4: Acknowledge SOS
        System.out.println("\n--- Test 4: Acknowledge SOS ---");
        sos.acknowledgeSOS();

        // Test 5: Resolve SOS
        System.out.println("\n--- Test 5: Resolve SOS ---");
        sos.resolveSOS("User confirmed safe. False alarm.");

        System.out.println("\n╔════════════════════════════════════════════╗");
        System.out.println("║   SOS Emergency Demo Complete!             ║");
        System.out.println("╚════════════════════════════════════════════╝");
    }
}
