import java.util.*;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import javax.mail.*;
import javax.mail.internet.*;

/**
 * OTP Service - Handles OTP generation, sending via Email and SMS
 */
public class OTPService {
    private static final int OTP_LENGTH = 6;
    private static final int OTP_VALIDITY_MINUTES = 10;
    private static final Map<String, OTPData> otpStore = new HashMap<>();

    // OTP Data class to store OTP with timestamp
    private static class OTPData {
        String otp;
        LocalDateTime generatedTime;
        int attempts;

        OTPData(String otp) {
            this.otp = otp;
            this.generatedTime = LocalDateTime.now();
            this.attempts = 0;
        }

        boolean isExpired() {
            return ChronoUnit.MINUTES.between(generatedTime, LocalDateTime.now()) > OTP_VALIDITY_MINUTES;
        }
    }

    /**
     * Generate a random 6-digit OTP
     */
    public static String generateOTP() {
        Random random = new Random();
        int otp = 100000 + random.nextInt(900000);
        return String.valueOf(otp);
    }

    /**
     * Send OTP via Email
     */
    public static boolean sendOTPViaEmail(String email, String otp, String recipientName) {
        // Gmail SMTP Configuration (using App Password for security)
        String sender = "your-email@gmail.com";  // CHANGE THIS
        String appPassword = "your-app-password";  // CHANGE THIS (16-char app-specific password)
        
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.starttls.required", "true");
        props.put("mail.smtp.connectiontimeout", "5000");
        props.put("mail.smtp.timeout", "5000");

        try {
            Session session = Session.getInstance(props, new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(sender, appPassword);
                }
            });

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(sender, "GoPro Support"));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(email));
            message.setSubject("GoPro - Your OTP Verification Code");

            // Email body with HTML formatting
            String emailBody = "<html><body style='font-family: Arial, sans-serif;'>" +
                "<h2 style='color: #e94560;'>GoPro - Your Ride, Your Way</h2>" +
                "<p>Hello " + recipientName + ",</p>" +
                "<p>Your OTP (One-Time Password) for GoPro verification is:</p>" +
                "<h1 style='color: #00c97b; text-align: center; font-size: 36px; letter-spacing: 5px;'>" + otp + "</h1>" +
                "<p><strong>This OTP is valid for 10 minutes.</strong></p>" +
                "<p>Please do not share this code with anyone.</p>" +
                "<p>If you didn't request this OTP, please ignore this email.</p>" +
                "<hr style='border: none; border-top: 1px solid #ddd;'/>" +
                "<p style='color: #666; font-size: 12px;'>© 2026 GoPro. All rights reserved.</p>" +
                "</body></html>";

            message.setContent(emailBody, "text/html; charset=utf-8");

            Transport.send(message);
            System.out.println("[GoPro] ✓ OTP sent successfully to: " + email);
            return true;

        } catch (Exception e) {
            System.out.println("[GoPro ERROR] Failed to send OTP email: " + e.getMessage());
            System.out.println("[GoPro INFO] Update Gmail credentials in OTPService.java lines 42-43");
            return false;
        }
    }

    /**
     * Send OTP via SMS (using Twilio or similar service)
     * For this demo, we'll show a placeholder for Twilio integration
     */
    public static boolean sendOTPViaSMS(String phoneNumber, String otp, String recipientName) {
        // NOTE: To use real SMS, sign up for Twilio (https://www.twilio.com/)
        // and add the Twilio SDK to your project
        
        String accountSid = "your-twilio-account-sid";     // CHANGE THIS
        String authToken = "your-twilio-auth-token";       // CHANGE THIS
        String fromNumber = "+1234567890";                 // CHANGE THIS (Your Twilio number)

        try {
            // Uncomment this section when you have Twilio SDK
            /*
            Twilio.init(accountSid, authToken);
            Message message = Message.creator(
                new PhoneNumber(phoneNumber),  // To number
                new PhoneNumber(fromNumber),   // From number
                "Your GoPro OTP is: " + otp + ". Valid for 10 minutes. Do not share this code."
            ).create();

            System.out.println("[GoPro] ✓ OTP sent successfully to: " + phoneNumber);
            return true;
            */

            // DEMO MODE: Just print to console (for testing without Twilio)
            System.out.println("[GoPro] [SMS DEMO] OTP would be sent to " + phoneNumber + ": " + otp);
            return true;

        } catch (Exception e) {
            System.out.println("[GoPro ERROR] Failed to send OTP SMS: " + e.getMessage());
            System.out.println("[GoPro INFO] To enable SMS, sign up for Twilio and update credentials");
            return false;
        }
    }

    /**
     * Generate and send OTP to user
     */
    public static String generateAndSendOTP(String contact, String contactType, String recipientName) {
        String otp = generateOTP();
        boolean sent = false;

        if ("email".equalsIgnoreCase(contactType)) {
            sent = sendOTPViaEmail(contact, otp, recipientName);
        } else if ("sms".equalsIgnoreCase(contactType)) {
            sent = sendOTPViaSMS(contact, otp, recipientName);
        }

        if (sent) {
            otpStore.put(contact, new OTPData(otp));
            return otp;  // Return for development/testing purposes
        }
        return null;
    }

    /**
     * Verify OTP
     */
    public static boolean verifyOTP(String contact, String providedOTP) {
        if (!otpStore.containsKey(contact)) {
            System.out.println("[GoPro] OTP not found for: " + contact);
            return false;
        }

        OTPData otpData = otpStore.get(contact);

        // Check if OTP is expired
        if (otpData.isExpired()) {
            System.out.println("[GoPro] OTP expired for: " + contact);
            otpStore.remove(contact);
            return false;
        }

        // Check if OTP matches (limit attempts to 3)
        if (otpData.attempts >= 3) {
            System.out.println("[GoPro] Maximum OTP attempts exceeded for: " + contact);
            otpStore.remove(contact);
            return false;
        }

        if (otpData.otp.equals(providedOTP)) {
            System.out.println("[GoPro] ✓ OTP verified successfully for: " + contact);
            otpStore.remove(contact);  // OTP used, remove from store
            return true;
        } else {
            otpData.attempts++;
            System.out.println("[GoPro] Invalid OTP. Attempts remaining: " + (3 - otpData.attempts));
            return false;
        }
    }

    /**
     * Get OTP validity status
     */
    public static int getOTPExpiryMinutes(String contact) {
        if (!otpStore.containsKey(contact)) {
            return -1;
        }

        OTPData otpData = otpStore.get(contact);
        long minutesElapsed = ChronoUnit.MINUTES.between(otpData.generatedTime, LocalDateTime.now());
        return (int) (OTP_VALIDITY_MINUTES - minutesElapsed);
    }

    public static void main(String[] args) {
        System.out.println("╔════════════════════════════════════════════╗");
        System.out.println("║   GoPro OTP Service Demo                   ║");
        System.out.println("╚════════════════════════════════════════════╝\n");

        // Demo: Generate and send OTP
        System.out.println("--- Test 1: Generate and Send OTP via Email ---");
        String email = "user@example.com";
        String otp = OTPService.generateAndSendOTP(email, "email", "Rahul Sharma");
        System.out.println("Generated OTP: " + otp + "\n");

        // Demo: Verify OTP
        System.out.println("--- Test 2: Verify OTP ---");
        boolean verified = OTPService.verifyOTP(email, otp);
        System.out.println("OTP Verified: " + verified + "\n");

        // Demo: Send OTP via SMS
        System.out.println("--- Test 3: Generate and Send OTP via SMS ---");
        String phone = "+919876543210";
        OTPService.generateAndSendOTP(phone, "sms", "Priya Singh");
    }
}
