# GoPro - Data Persistence & OTP Implementation Guide

## Overview
This guide explains how to integrate database persistence and OTP sending functionality into your GoPro application.

---

## Part 1: Database Setup

### 1.1 What's New?
- **DatabaseManager.java** - Handles SQLite connection and table creation
- **UserRepository.java** - Manages all user database operations
- SQLite database file: `gopro_database.db` (auto-created in project folder)

### 1.2 Setup Steps

#### Step 1: Add SQLite JDBC Driver
1. Download: https://github.com/xerial/sqlite-jdbc/releases
2. Download the latest JAR file (e.g., `sqlite-jdbc-3.44.0.0.jar`)
3. Add to your project's classpath:
   - If using IDE: Right-click project → Build Path → Add External Archive
   - If using command line: Include in CLASSPATH

#### Step 2: Initialize Database in Your Application
Add this to your main method or GUI constructor:

```java
// Initialize database on startup
DatabaseManager.initialize();

// Load existing users from database
List<User> loadedUsers = UserRepository.getAllUsers();
users.addAll(loadedUsers);  // Add to your users list

// Close database on app shutdown
Runtime.getRuntime().addShutdownHook(new Thread(() -> {
    DatabaseManager.closeConnection();
}));
```

#### Step 3: Use Repository Methods When Creating/Updating Users
Instead of just adding to ArrayList:

```java
// When registering a new user:
User newUser = new User(userId, name, email, phone, password);
users.add(newUser);  // Add to in-memory list
UserRepository.saveUser(newUser);  // PERSIST to database

// When logging in:
User user = UserRepository.getUserByEmail(email);
if (user != null && user.login(email, password)) {
    // Login successful
}

// When updating user verification:
UserRepository.updateUserVerification(userId, true);
```

### 1.3 Database Schema
The following tables are created automatically:
- `users` - User accounts and verification status
- `drivers` - Driver information
- `vehicles` - Vehicle details
- `ride_bookings` - Ride booking records
- `payments` - Payment transactions
- `ratings_reviews` - User and driver reviews
- `wallets` - User wallet balance
- `promo_codes` - Promotional codes

---

## Part 2: OTP Service Implementation

### 2.1 What's New?
- **OTPService.java** - Generates, stores, and verifies OTPs
- Email sending via Gmail SMTP
- SMS sending via Twilio (placeholder - optional)

### 2.2 Email OTP Setup (Gmail)

#### Step 1: Enable 2-Factor Authentication on Gmail
1. Go to: https://myaccount.google.com/security
2. Enable "2-Step Verification"

#### Step 2: Generate App Password
1. Go to: https://myaccount.google.com/apppasswords
2. Select "Mail" and "Windows Computer"
3. Copy the 16-character password

#### Step 3: Update OTPService.java
Open `OTPService.java` and update lines 42-43:

```java
String sender = "your-email@gmail.com";        // Your Gmail address
String appPassword = "xxxx xxxx xxxx xxxx";    // Your 16-char app password
```

#### Step 4: Use OTP Service in Registration
```java
// During registration, generate and send OTP
String otp = OTPService.generateAndSendOTP(userEmail, "email", userName);

// User receives email with OTP

// During verification, verify OTP
boolean verified = OTPService.verifyOTP(userEmail, userProvidedOTP);
if (verified) {
    user.setVerified(true);
    UserRepository.updateUserVerification(userId, true);
}
```

### 2.3 SMS OTP Setup (Optional - Twilio)

To enable SMS OTP:

1. Sign up for Twilio: https://www.twilio.com/
2. Get your Account SID, Auth Token, and Phone Number
3. Add Twilio SDK to your project:
   - Maven: `com.twilio.sdk:twilio:9.2.0`
   - Manual: Download from https://www.twilio.com/docs/libraries/java
4. Update `OTPService.java` lines 75-77:

```java
String accountSid = "your-account-sid";
String authToken = "your-auth-token";
String fromNumber = "+1234567890";  // Your Twilio number
```

5. Uncomment the Twilio code block (lines 79-87)

### 2.4 OTP Features
- **6-digit random OTP** generated
- **10-minute expiry** automatically enforced
- **3 attempt limit** before lockout
- **Email and SMS delivery**
- **Validation and error handling**

---

## Part 3: Integration with GoProGUI

### 3.1 Update GUI Constructor

```java
public GoProGUI() {
    super("GoPro - Your Ride, Your Way!");
    // ... existing setup code ...

    // ✓ NEW: Initialize database
    DatabaseManager.initialize();

    // ✓ NEW: Load existing data from database
    List<User> persistedUsers = UserRepository.getAllUsers();
    users.addAll(persistedUsers);

    // ... rest of GUI initialization ...

    // ✓ NEW: Add shutdown hook to save data
    Runtime.getRuntime().addShutdownHook(new Thread(() -> {
        DatabaseManager.closeConnection();
        System.out.println("[GoPro] Application closed. Data saved.");
    }));
}
```

### 3.2 Update User Registration Panel

In your `createUserPanel()` method, update the registration logic:

```java
// When user submits registration form:
String userId = "U" + userCounter++;
String name = nameField.getText();
String email = emailField.getText();
String phone = phoneField.getText();
String password = passwordField.getText();

// Check if user already exists
if (UserRepository.userExists(email)) {
    JOptionPane.showMessageDialog(null, "Email already registered!");
    return;
}

// Create user object
User newUser = new User(userId, name, email, phone, password);

// ✓ NEW: Send OTP via Email
String otp = OTPService.generateAndSendOTP(email, "email", name);
if (otp != null) {
    JOptionPane.showMessageDialog(null, "OTP sent to your email!");
    
    // ✓ NEW: Ask for OTP verification
    String userOTP = JOptionPane.showInputDialog("Enter 6-digit OTP from your email:");
    
    if (OTPService.verifyOTP(email, userOTP)) {
        newUser.setVerified(true);
        
        // ✓ NEW: Save to database
        UserRepository.saveUser(newUser);
        users.add(newUser);
        
        JOptionPane.showMessageDialog(null, "Registration successful!");
    } else {
        JOptionPane.showMessageDialog(null, "Invalid OTP!");
    }
} else {
    JOptionPane.showMessageDialog(null, "Failed to send OTP. Check email credentials.");
}
```

### 3.3 Update User Login Panel

```java
// When user attempts login:
String email = emailField.getText();
String password = passwordField.getText();

// ✓ NEW: Load user from database
User user = UserRepository.getUserByEmail(email);

if (user != null && user.login(email, password)) {
    if (user.isVerified()) {
        JOptionPane.showMessageDialog(null, "Login successful!");
        // Proceed to dashboard
    } else {
        JOptionPane.showMessageDialog(null, "Please verify your email first!");
    }
} else {
    JOptionPane.showMessageDialog(null, "Invalid credentials!");
}
```

### 3.4 Update Other Operations

Similar changes for:
- **Driver Registration** → `DriverRepository.saveDriver()`
- **Vehicle Registration** → `VehicleRepository.saveVehicle()`
- **Ride Bookings** → `RideBookingRepository.saveBooking()`
- **Payments** → `PaymentRepository.savePayment()`

---

## Part 4: Create Repository Classes for Other Entities

Create similar repository classes:

### DriverRepository.java
```java
public class DriverRepository {
    public static boolean saveDriver(Driver driver) { /* ... */ }
    public static Driver getDriverById(String driverId) { /* ... */ }
    public static List<Driver> getAllDrivers() { /* ... */ }
    // ... more methods
}
```

### VehicleRepository.java
```java
public class VehicleRepository {
    public static boolean saveVehicle(Vehicle vehicle) { /* ... */ }
    public static List<Vehicle> getVehiclesByDriverId(String driverId) { /* ... */ }
    // ... more methods
}
```

### RideBookingRepository.java
```java
public class RideBookingRepository {
    public static boolean saveBooking(RideBooking booking) { /* ... */ }
    public static RideBooking getBookingById(String bookingId) { /* ... */ }
    // ... more methods
}
```

---

## Part 5: Testing

### Test Database Persistence
```java
// Test 1: Create and save user
User testUser = new User("U999", "Test User", "test@email.com", "9999999999", "test123");
UserRepository.saveUser(testUser);

// Test 2: Close and restart app - user should still be there
// Restart application
User retrieved = UserRepository.getUserByEmail("test@email.com");
System.out.println("User retrieved from database: " + retrieved);  // Should not be null
```

### Test OTP Functionality
```java
// Test 1: Send OTP
String otp = OTPService.generateAndSendOTP("your-email@gmail.com", "email", "Test User");
System.out.println("OTP generated: " + otp);

// Test 2: Verify correct OTP
boolean verified = OTPService.verifyOTP("your-email@gmail.com", otp);
System.out.println("OTP verified: " + verified);  // Should be true

// Test 3: Verify incorrect OTP
boolean wrongOTP = OTPService.verifyOTP("your-email@gmail.com", "000000");
System.out.println("Wrong OTP verified: " + wrongOTP);  // Should be false
```

---

## Part 6: Troubleshooting

### Issue: SQLite JAR not found
**Solution**: Ensure sqlite-jdbc JAR is in your classpath. Check build path in your IDE.

### Issue: Emails not sending
**Solution**: 
1. Check Gmail credentials are correct
2. Enable "Less secure app access" (old Gmail) or use App Passwords (recommended)
3. Check if firewall is blocking SMTP port 587

### Issue: Database locked
**Solution**: Ensure only one instance of the app is running at a time.

### Issue: OTP not received
**Solution**: 
1. Check email spam folder
2. Verify email address is correct
3. Check Gmail credentials in OTPService.java

---

## Part 7: Security Best Practices

⚠️ **Important Security Notes:**

1. **Never hardcode passwords in production**
   - Use environment variables for Gmail password
   - Use secure vaults for Twilio credentials

2. **Use HTTPS for API calls** if adding external services

3. **Hash passwords** instead of storing plain text
   - Use BCrypt: `BCryptPasswordEncoder.encode(password)`

4. **Use prepared statements** to prevent SQL injection
   - Update DatabaseManager to use PreparedStatement

5. **Validate all user inputs** before storing in database

6. **Use HTTPS** for email links (add login links in OTP emails)

---

## Summary of Changes

| Component | Before | After |
|-----------|--------|-------|
| Data Storage | In-memory ArrayLists | SQLite Database |
| OTP Verification | Local only, not sent | Sent via Email/SMS |
| User Persistence | Lost on app close | Saved permanently |
| Login | Requires re-registration | Uses saved credentials |
| Scalability | Limited to runtime memory | Can store unlimited data |

---

## Next Steps

1. Add SQLite JDBC driver to your project
2. Update Gmail credentials in OTPService.java
3. Integrate DatabaseManager.initialize() in GoProGUI
4. Create repository classes for other entities
5. Update GUI panels to use repository methods
6. Test registration → OTP → Verification → Login flow
7. Verify data persists after app restart

---

## Support & Resources

- SQLite Documentation: https://www.sqlite.org/docs.html
- Gmail SMTP: https://support.google.com/accounts/answer/185833
- Twilio SMS: https://www.twilio.com/docs/sms
- JavaMail API: https://javaee.github.io/javamail/

Good luck with your GoPro application! 🚀
