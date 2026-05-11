# GoPro - Quick Start Integration Guide

## Summary of New Files Created

| File | Purpose |
|------|---------|
| `DatabaseManager.java` | Database connection and initialization |
| `UserRepository.java` | User data persistence |
| `DriverRepository.java` | Driver data persistence |
| `VehicleRepository.java` | Vehicle data persistence |
| `RideBookingRepository.java` | Ride booking persistence |
| `OTPService.java` | OTP generation and sending |

---

## Step 1: Add SQLite Driver

**Download**: https://github.com/xerial/sqlite-jdbc/releases

Choose the latest version (e.g., `sqlite-jdbc-3.44.0.0.jar`)

**Add to your project**:
- IntelliJ: File → Project Structure → Libraries → + → Add JAR
- Eclipse: Right-click Project → Build Path → Add External Archive
- VS Code: Update your classpath/build configuration

---

## Step 2: Modify GoProGUI Constructor

Add database initialization at the start of your `GoProGUI()` constructor:

```java
public GoProGUI() {
    super("GoPro - Your Ride, Your Way!");
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    // ... existing code ...

    // ✨ NEW: Initialize database
    DatabaseManager.initialize();

    // ✨ NEW: Load persisted data from database
    List<User> persistedUsers = UserRepository.getAllUsers();
    users.addAll(persistedUsers);
    
    List<Driver> persistedDrivers = DriverRepository.getAllDrivers();
    drivers.addAll(persistedDrivers);
    
    List<Vehicle> persistedVehicles = VehicleRepository.getAllVehicles();
    vehicles.addAll(persistedVehicles);

    // ... rest of GUI initialization ...

    // ✨ NEW: Close database on app shutdown
    Runtime.getRuntime().addShutdownHook(new Thread(() -> {
        DatabaseManager.closeConnection();
        System.out.println("[GoPro] Application closed. Data saved to database.");
    }));
}
```

---

## Step 3: Update User Registration in GUI

In your `createUserPanel()` method, update the registration button's action:

### Before (In-memory only):
```java
// Old approach
User newUser = new User(userId, name, email, phone, password);
users.add(newUser);
```

### After (With persistence & OTP):
```java
// New approach with database and OTP
String userId = "U" + (userCounter++);
String name = nameField.getText().trim();
String email = emailField.getText().trim();
String phone = phoneField.getText().trim();
String password = passwordField.getText().trim();

// Check if email already exists
if (UserRepository.userExists(email)) {
    JOptionPane.showMessageDialog(this, 
        "Email already registered! Please login instead.",
        "Registration Error", JOptionPane.ERROR_MESSAGE);
    return;
}

// Validate inputs
if (name.isEmpty() || email.isEmpty() || phone.isEmpty() || password.isEmpty()) {
    JOptionPane.showMessageDialog(this, 
        "All fields are required!",
        "Validation Error", JOptionPane.ERROR_MESSAGE);
    return;
}

try {
    // Create user object
    User newUser = new User(userId, name, email, phone, password);

    // Send OTP via email
    String otp = OTPService.generateAndSendOTP(email, "email", name);
    
    if (otp != null) {
        JOptionPane.showMessageDialog(this, 
            "OTP sent to your email: " + email + "\nCheck your inbox (and spam folder)",
            "OTP Sent", JOptionPane.INFORMATION_MESSAGE);

        // Prompt user to enter OTP
        String userOTP = JOptionPane.showInputDialog(this, 
            "Enter the 6-digit OTP sent to your email:");
        
        if (userOTP != null && !userOTP.trim().isEmpty()) {
            // Verify OTP
            if (OTPService.verifyOTP(email, userOTP.trim())) {
                // Mark as verified and save to database
                newUser.setVerified(true);
                UserRepository.saveUser(newUser);
                users.add(newUser);

                // Clear form
                nameField.setText("");
                emailField.setText("");
                phoneField.setText("");
                passwordField.setText("");

                JOptionPane.showMessageDialog(this, 
                    "Registration Successful! You can now login.",
                    "Success", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, 
                    "Invalid OTP! Registration cancelled.",
                    "OTP Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, 
                "OTP entry cancelled. Registration cancelled.",
                "Cancelled", JOptionPane.WARNING_MESSAGE);
        }
    } else {
        JOptionPane.showMessageDialog(this, 
            "Failed to send OTP. Please check:\n" +
            "1. Email address is correct\n" +
            "2. Update Gmail credentials in OTPService.java\n" +
            "3. Enable App Passwords for your Gmail account",
            "Email Error", JOptionPane.ERROR_MESSAGE);
    }

} catch (Exception e) {
    System.out.println("[GoPro ERROR] Registration failed: " + e.getMessage());
    e.printStackTrace();
    JOptionPane.showMessageDialog(this, 
        "Registration failed: " + e.getMessage(),
        "Error", JOptionPane.ERROR_MESSAGE);
}
```

---

## Step 4: Update User Login in GUI

In your login section, update to use database lookup:

### Before:
```java
// Old approach - search in memory
User user = null;
for (User u : users) {
    if (u.getEmail().equals(email) && u.getPassword().equals(password)) {
        user = u;
        break;
    }
}
```

### After:
```java
// New approach - lookup from database
User user = UserRepository.getUserByEmail(email);

if (user != null && user.login(email, password)) {
    if (user.isVerified()) {
        JOptionPane.showMessageDialog(this, 
            "Welcome, " + user.getName() + "!",
            "Login Successful", JOptionPane.INFORMATION_MESSAGE);
        // Navigate to dashboard
    } else {
        JOptionPane.showMessageDialog(this, 
            "Please verify your email first!",
            "Verification Required", JOptionPane.ERROR_MESSAGE);
    }
} else {
    JOptionPane.showMessageDialog(this, 
        "Invalid email or password!",
        "Login Failed", JOptionPane.ERROR_MESSAGE);
}
```

---

## Step 5: Update Driver Registration

Similarly update driver registration to use `DriverRepository`:

```java
// When registering a new driver
Driver newDriver = new Driver(driverId, name, phone, licenseNumber);

// Verify driver
newDriver.verifyDriver();

// Save to database
DriverRepository.saveDriver(newDriver);
drivers.add(newDriver);

JOptionPane.showMessageDialog(this, 
    "Driver registered successfully!",
    "Success", JOptionPane.INFORMATION_MESSAGE);
```

---

## Step 6: Update Ride Booking

When booking a ride, save to database:

```java
// When creating a ride booking
RideBooking booking = new RideBooking(bookingId, userId, pickup, dropoff);

// Save to database
RideBookingRepository.saveBooking(booking);
bookings.add(booking);

// Notify user
JOptionPane.showMessageDialog(this, 
    "Ride booked! Finding drivers...",
    "Booking Confirmed", JOptionPane.INFORMATION_MESSAGE);
```

When ride status changes:

```java
// Update ride status in database
RideBookingRepository.updateBookingStatus(bookingId, "IN_PROGRESS");

// Also update in-memory list
for (RideBooking b : bookings) {
    if (b.getBookingId().equals(bookingId)) {
        b.setBookingStatus("IN_PROGRESS");
        break;
    }
}
```

---

## Step 7: Gmail Setup (Required for OTP)

1. Go to: https://myaccount.google.com/security

2. Enable **2-Step Verification** (if not already enabled)

3. Go to: https://myaccount.google.com/apppasswords

4. Select: **Mail** → **Windows Computer** → Generate

5. Copy the 16-character password (format: `xxxx xxxx xxxx xxxx`)

6. Update `OTPService.java` lines 42-43:
```java
String sender = "your-email@gmail.com";         // Your Gmail address
String appPassword = "xxxx xxxx xxxx xxxx";     // Your generated app password
```

---

## Step 8: Testing Checklist

- [ ] SQLite JAR file added to project
- [ ] `DatabaseManager.initialize()` called in GoProGUI constructor
- [ ] User registration includes OTP verification
- [ ] User data persists after app restart
- [ ] Driver data persists after app restart
- [ ] Ride bookings are saved to database
- [ ] Login uses database lookup
- [ ] Database file created: `gopro_database.db`

---

## Testing the Setup

### Test 1: Register a User with OTP
```
1. Start the application
2. Go to Users panel
3. Fill in registration form
4. Submit → OTP email sent
5. Enter OTP from email
6. Registration complete
```

### Test 2: Persistence
```
1. Register and close the app
2. Reopen the app
3. Login with same credentials
4. User should be found from database
```

### Test 3: Multiple Users
```
1. Register 3-4 users with different emails
2. Close and restart app
3. All users should be available
4. Each can login with their credentials
```

---

## Troubleshooting

### Problem: "SQLite driver not found"
**Solution**: 
- Ensure sqlite-jdbc JAR is in classpath
- Restart IDE after adding JAR
- Check Build Path in project settings

### Problem: "OTP not received"
**Solution**:
- Check spam folder in Gmail
- Verify email credentials in OTPService.java
- Check if Gmail account has 2-factor enabled
- Check if "Less secure apps" is allowed (for old Gmail accounts)

### Problem: "Database locked" error
**Solution**:
- Close other instances of the application
- Delete `gopro_database.db` and restart (will recreate empty DB)
- Check Task Manager for stray Java processes

### Problem: "Users not persisting"
**Solution**:
- Verify DatabaseManager.initialize() is called first
- Check that UserRepository.saveUser() is called after registration
- Verify database connection is open: `DatabaseManager.getConnection()`

---

## Architecture Diagram

```
┌─────────────────────────────────────────┐
│           GoProGUI                      │
│  (Main UI - Swing)                      │
└──────────────────┬──────────────────────┘
                   │
        ┌──────────┴──────────┐
        │                     │
        ▼                     ▼
    ┌────────────────┐   ┌──────────────┐
    │  Repositories  │   │   OTPService │
    │ (Data Access)  │   │              │
    ├────────────────┤   ├──────────────┤
    │ UserRepository │   │ generateOTP()│
    │DriverRepository
   │   │ sendOTPViaEmail()
    │ VehicleRepository   │ verifyOTP()  │
    │ RideBookingRep.│   └──────────────┘
    └────────┬──────────────┘             │
             │                             │
             ▼                             ▼
        ┌──────────────────────────────────┐
        │    DatabaseManager               │
        │   (SQLite Connection)            │
        └──────────────────┬───────────────┘
                           │
                           ▼
                  ┌──────────────────┐
                  │  gopro_database  │
                  │  (SQLite DB)     │
                  │                  │
                  │ - users          │
                  │ - drivers        │
                  │ - vehicles       │
                  │ - bookings       │
                  │ - payments       │
                  └──────────────────┘
```

---

## Next Steps

1. ✅ Add SQLite JAR to project
2. ✅ Update Gmail credentials
3. ✅ Integrate DatabaseManager initialization
4. ✅ Update registration panel with OTP
5. ✅ Update login panel to use database
6. ✅ Test user registration and persistence
7. ✅ Apply same pattern to Drivers, Vehicles, Rides
8. ✅ Optional: Add password hashing (BCrypt)
9. ✅ Optional: Add SMS via Twilio

---

## Security Reminders

⚠️ **Before Production:**

1. **Hash passwords** - Use BCrypt instead of storing plain text
2. **Use environment variables** - Don't hardcode credentials
3. **Validate all inputs** - Prevent SQL injection
4. **Use HTTPS** - For any external API calls
5. **Secure database** - Use proper authentication

---

Good luck! 🚀 Your GoPro app now has real data persistence and OTP verification!
