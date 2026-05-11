# GoPro Implementation - Quick Reference Card

## 🚀 Setup Checklist

### ☐ 1. Download SQLite Driver (5 min)
- URL: https://github.com/xerial/sqlite-jdbc/releases
- File: sqlite-jdbc-3.44.0.0.jar
- Add to classpath

### ☐ 2. Gmail Setup (5 min)
- Go to: https://myaccount.google.com/apppasswords
- Generate App Password
- Copy 16-char password

### ☐ 3. Update OTPService.java (2 min)
```java
// Line 42-43
String sender = "your-email@gmail.com";
String appPassword = "xxxx xxxx xxxx xxxx";
```

### ☐ 4. Update GoProGUI Constructor (5 min)
```java
// Add at start of constructor
DatabaseManager.initialize();
List<User> users = UserRepository.getAllUsers();
users.addAll(users);

// Add at end of constructor
Runtime.getRuntime().addShutdownHook(new Thread(() -> {
    DatabaseManager.closeConnection();
}));
```

### ☐ 5. Update Registration Panel (15 min)
Replace:
```java
User newUser = new User(...);
users.add(newUser);
```

With:
```java
User newUser = new User(...);
String otp = OTPService.generateAndSendOTP(email, "email", name);
if (OTPService.verifyOTP(email, userEnteredOTP)) {
    newUser.setVerified(true);
    UserRepository.saveUser(newUser);
    users.add(newUser);
}
```

### ☐ 6. Update Login Panel (5 min)
Replace:
```java
User user = users.stream()...
```

With:
```java
User user = UserRepository.getUserByEmail(email);
```

### ☐ 7. Test (30 min)
```
1. Register new user
2. Verify OTP email received
3. Close app
4. Reopen app
5. Login with same credentials
6. Verify user data persists
```

---

## 📊 Key Method References

### User Operations
```java
// Save user
UserRepository.saveUser(user);

// Load user
User user = UserRepository.getUserByEmail(email);

// Load all users
List<User> allUsers = UserRepository.getAllUsers();

// Check if exists
boolean exists = UserRepository.userExists(email);

// Update verification
UserRepository.updateUserVerification(userId, true);
```

### Driver Operations
```java
// Save driver
DriverRepository.saveDriver(driver);

// Get driver
Driver driver = DriverRepository.getDriverById(driverId);

// Get available drivers
List<Driver> available = DriverRepository.getAvailableDrivers();

// Update location
DriverRepository.updateDriverLocation(driverId, location);
```

### Ride Booking Operations
```java
// Save booking
RideBookingRepository.saveBooking(booking);

// Get booking
RideBooking booking = RideBookingRepository.getBookingById(bookingId);

// Get user bookings
List<RideBooking> bookings = RideBookingRepository.getBookingsByUserId(userId);

// Update status
RideBookingRepository.updateBookingStatus(bookingId, "COMPLETED");

// Assign driver
RideBookingRepository.assignDriver(bookingId, driverId);
```

### OTP Operations
```java
// Send OTP
String otp = OTPService.generateAndSendOTP(email, "email", name);

// Verify OTP
boolean verified = OTPService.verifyOTP(email, userOTP);

// Check expiry
int minutesLeft = OTPService.getOTPExpiryMinutes(email);
```

### Database Operations
```java
// Initialize
DatabaseManager.initialize();

// Get connection
Connection conn = DatabaseManager.getConnection();

// Execute update
DatabaseManager.executeUpdate(query);

// Execute query
ResultSet rs = DatabaseManager.executeQuery(query);

// Close
DatabaseManager.closeConnection();
```

---

## 🔧 Common Patterns

### Pattern 1: Create & Save User
```java
User user = new User(id, name, email, phone, password);
UserRepository.saveUser(user);
users.add(user);
```

### Pattern 2: OTP Verification
```java
String otp = OTPService.generateAndSendOTP(email, "email", name);
if (OTPService.verifyOTP(email, userOTP)) {
    // Proceed
}
```

### Pattern 3: Load & Use Existing User
```java
User user = UserRepository.getUserByEmail(email);
if (user != null) {
    // User found
}
```

### Pattern 4: Create & Save Booking
```java
RideBooking booking = new RideBooking(id, userId, pickup, dropoff);
RideBookingRepository.saveBooking(booking);
bookings.add(booking);
```

### Pattern 5: Update Booking Status
```java
RideBookingRepository.updateBookingStatus(bookingId, "COMPLETED");
// Also update in-memory
booking.setBookingStatus("COMPLETED");
```

---

## 📱 GUI Updates Template

### Registration Form
```java
// 1. Validate input
// 2. Check if email exists: UserRepository.userExists(email)
// 3. Create user object
// 4. Send OTP: OTPService.generateAndSendOTP(...)
// 5. Get user input
// 6. Verify OTP: OTPService.verifyOTP(...)
// 7. Save to DB: UserRepository.saveUser(...)
// 8. Add to list: users.add(user)
// 9. Show success message
```

### Login Form
```java
// 1. Validate input
// 2. Load from DB: UserRepository.getUserByEmail(email)
// 3. Check password: user.login(email, password)
// 4. Check verified: user.isVerified()
// 5. Show appropriate message
```

### Ride Booking Form
```java
// 1. Get form values
// 2. Create booking: new RideBooking(...)
// 3. Save to DB: RideBookingRepository.saveBooking(...)
// 4. Add to list: bookings.add(booking)
// 5. Find driver: DriverRepository.getAvailableDrivers()
// 6. Assign driver: RideBookingRepository.assignDriver(...)
// 7. Show confirmation
```

---

## ⚠️ Critical Must-Do's

1. ⭐ Add SQLite JAR to classpath
2. ⭐ Call DatabaseManager.initialize() first
3. ⭐ Save to repository AND in-memory list
4. ⭐ Load from repository when retrieving
5. ⭐ Update Gmail credentials in OTPService
6. ⭐ Use UserRepository.getUserByEmail() for login
7. ⭐ Call DatabaseManager.closeConnection() on shutdown

---

## 🐛 Debugging Tips

### Problem: SQLite not found
```
→ Check classpath
→ Restart IDE
→ Verify JAR exists
```

### Problem: OTP not sending
```
→ Check email credentials
→ Check firewall (port 587)
→ Check Gmail 2-factor enabled
→ Check spam folder
```

### Problem: Users not persisting
```
→ Verify saveUser() is called
→ Check database file exists
→ Check UserRepository.getAllUsers() returns data
→ Verify initialization happens first
```

### Problem: Database locked
```
→ Close all app instances
→ Check for running Java processes
→ Delete gopro_database.db and restart
```

---

## 📈 Implementation Timeline

| Task | Time | Status |
|------|------|--------|
| Download & Setup SQLite | 10 min | ☐ |
| Gmail Setup | 5 min | ☐ |
| Code Integration | 30 min | ☐ |
| Registration Panel | 15 min | ☐ |
| Login Panel | 10 min | ☐ |
| Driver Registration | 15 min | ☐ |
| Ride Booking | 15 min | ☐ |
| Testing & Debugging | 30 min | ☐ |
| **TOTAL** | **~2.5 hours** | ☐ |

---

## 📚 Documentation Files

| File | Purpose | Read Time |
|------|---------|-----------|
| SOLUTION_SUMMARY.md | Overview | 5 min |
| QUICK_START_INTEGRATION.md | Step-by-step | 10 min |
| COMPLETE_IMPLEMENTATION_EXAMPLE.md | Code examples | 15 min |
| DATA_PERSISTENCE_GUIDE.md | Detailed guide | 20 min |

---

## 🎯 Success Criteria

- [x] SQLite JAR added
- [ ] Database initializes on app start
- [ ] Registration sends OTP email
- [ ] OTP verification works
- [ ] Data persists after app close
- [ ] Login finds user from database
- [ ] All drivers/vehicles/rides saved to DB
- [ ] No data loss on app restart

**When all ☐ are ✓, you're done!**

---

## 📞 Quick Links

- SQLite Downloads: https://github.com/xerial/sqlite-jdbc/releases
- Gmail Settings: https://myaccount.google.com/apppasswords
- JavaMail Docs: https://javaee.github.io/javamail/
- This Solution: Check SOLUTION_SUMMARY.md

---

## Version Info

- Database: SQLite 3
- Java Libraries: JavaMail (for email)
- Optional: Twilio (for SMS)

---

**Start with: Download SQLite JAR → Update OTPService.java → Update GoProGUI → Test**

Good luck! 🚀
