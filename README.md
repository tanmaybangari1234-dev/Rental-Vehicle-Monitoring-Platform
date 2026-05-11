# GoPro Ride-Sharing Application

A comprehensive Java-based ride-sharing platform with 15 integrated modules, featuring user management, driver coordination, fare calculation, payment processing, and real-time tracking.

---

## 📋 Table of Contents

- [Project Overview](#project-overview)
- [Features](#features)
- [Project Structure](#project-structure)
- [System Requirements](#system-requirements)
- [Compilation Instructions](#compilation-instructions)
- [Running the Application](#running-the-application)
- [Module Details](#module-details)
- [Architecture](#architecture)
- [Usage Examples](#usage-examples)

---

## 🎯 Project Overview

GoPro is a full-featured ride-sharing application built in Java that simulates a modern mobility platform similar to Uber or Ola. It encompasses all critical functionalities required for a production-grade ride-sharing ecosystem.

**Current Version:** 1.0  
**Language:** Java  
**Last Updated:** May 8, 2026

---

## ✨ Features

### Core Functionalities

- **User Management** - Registration, OTP verification, login, profile management
- **Driver Management** - Driver verification, online/offline status, rating system
- **Vehicle Management** - Multiple vehicle types (BIKE, AUTO, MINI, SEDAN, SUV)
- **Ride Booking** - Book, confirm, track, complete, and cancel rides
- **Intelligent Ride Matching** - Match riders with nearest available drivers
- **Dynamic Fare Calculation** - Base fare + per-km rate with surge pricing
- **Secure Payments** - Multiple payment methods (UPI, CARD, WALLET, CASH)
- **Digital Wallet** - Add/deduct funds, transaction history
- **Real-time Tracking** - GPS-based live ride tracking with ETA
- **Rating & Reviews** - Star ratings and comments from users and drivers
- **Ride History** - Complete ride records and spending analytics
- **Promo Codes** - Discount coupons with usage limits and expiry dates
- **SOS Emergency** - Emergency alerts and location sharing
- **Notifications** - Real-time ride updates, payment confirmations, offers
- **Admin Dashboard** - Revenue reports, driver/user management, analytics

---

## 📁 Project Structure

```
GoPro/
├── Module Files (15 total)
│   ├── User.java                 # User registration & authentication
│   ├── Driver.java               # Driver management
│   ├── Vehicle.java              # Vehicle details
│   ├── RideBooking.java          # Ride booking lifecycle
│   ├── RideMatching.java         # Driver-rider matching algorithm
│   ├── FareCalculator.java       # Fare computation
│   ├── Wallet.java               # Digital wallet management
│   ├── Payment.java              # Payment processing
│   ├── Notification.java         # Notification service
│   ├── RideTracker.java          # Live tracking
│   ├── RatingReview.java         # Review system
│   ├── RideHistory.java          # Ride history
│   ├── PromoCode.java            # Promo code management
│   ├── SOSEmergency.java         # Emergency service
│   ├── AdminDashboard.java       # Admin panel
│   └── FareCalculatorTest.java   # Fare calculator demo
│
├── Main Application
│   ├── GoProApp.java             # Full integrated application demo
│   ├── GoProGUI.java             # GUI interface (optional)
│   ├── ModuleRunner.java         # Interactive module selector
│   └── Driver.java               # Command-line driver
│
├── Documentation
│   ├── README.md                 # This file
│   ├── dfd level 0.png           # Data Flow Diagram
│   ├── dfd level 1.png           # Detailed DFD
│   ├── er.png                    # Entity-Relationship Diagram
│   └── use case diagram.png      # Use Case Diagram
│
└── Compiled Classes (.class files)
```

---

## 💻 System Requirements

- **Java Development Kit (JDK)** - Version 8 or higher
- **Java Runtime Environment (JRE)** - Version 8 or higher
- **Operating System** - Windows, macOS, or Linux
- **RAM** - Minimum 512MB
- **Disk Space** - 50MB for source and compiled files

---

## 🔧 Compilation Instructions

### Compile All Modules

```bash
cd c:\Users\user\OneDrive\Desktop\GoPro
javac User.java Driver.java Vehicle.java RideBooking.java RideMatching.java Wallet.java Payment.java Notification.java RideTracker.java RatingReview.java RideHistory.java PromoCode.java SOSEmergency.java AdminDashboard.java FareCalculator.java ModuleRunner.java GoProApp.java
```

### Compile Individual Module

```bash
javac User.java
javac Driver.java
javac Wallet.java
# ... and so on
```

### Compile with Warnings/Errors

```bash
javac *.java 2>&1
```

---

## 🚀 Running the Application

### Option 1: Run Full Application Demo

```bash
java -cp "c:\Users\user\OneDrive\Desktop\GoPro" GoProApp
```

**Output:** Complete demonstration of all 15 modules with sample data.

### Option 2: Run Individual Modules

Each module can run independently with its own demo:

```bash
# User Module
java -cp . User

# Driver Module
java -cp . Driver

# Vehicle Module
java -cp . Vehicle

# Ride Booking Module
java -cp . RideBooking

# Fare Calculator
java -cp . FareCalculator

# Wallet Management
java -cp . Wallet

# Payment Processing
java -cp . Payment

# Notifications
java -cp . Notification

# Ride Tracker
java -cp . RideTracker

# Ratings & Reviews
java -cp . RatingReview

# Ride History
java -cp . RideHistory

# Promo Codes
java -cp . PromoCode

# SOS Emergency
java -cp . SOSEmergency

# Admin Dashboard
java -cp . AdminDashboard

# Ride Matching
java -cp . RideMatching
```

### Option 3: Interactive Module Runner

```bash
java -cp . ModuleRunner
```

**Features:**
- Menu-driven interface
- Select any module to run
- Interactive demonstrations

### Option 4: Run with GUI (if available)

```bash
java -cp . GoProGUI
```

---

## 📦 Module Details

### 1. User Module (User.java)
**Description:** Handles user registration, authentication, and profile management  
**Key Methods:**
- `login(email, password)` - User authentication
- `verifyOTP(otp)` - OTP verification for registration
- `updateProfile(name, phone)` - Update user information
- `logout()` - User logout

**Example:**
```java
User user = new User("U001", "Rahul", "rahul@email.com", "9876543210", "pass123");
user.verifyOTP("123456");
user.login("rahul@email.com", "pass123");
```

### 2. Driver Module (Driver.java)
**Description:** Manages driver registration, verification, and availability  
**Key Methods:**
- `verifyDriver()` - Verify driver license
- `goOnline(location)` - Driver goes online
- `goOffline()` - Driver goes offline
- `updateRating(rating)` - Update driver rating

### 3. Vehicle Module (Vehicle.java)
**Description:** Manages vehicle information and status  
**Key Methods:**
- `activateVehicle()` - Activate vehicle
- `deactivateVehicle()` - Deactivate vehicle
- `updateVehicleDetails(model, color)` - Update vehicle info

### 4. Ride Booking Module (RideBooking.java)
**Description:** Manages ride lifecycle from booking to completion  
**Key Methods:**
- `confirmRide(driverId, fare)` - Confirm driver assignment
- `startRide()` - Start the ride
- `completeRide()` - Mark ride as completed
- `cancelRide(reason)` - Cancel ride with reason

### 5. Ride Matching Module (RideMatching.java)
**Description:** Algorithm to match riders with nearest available drivers  
**Key Methods:**
- `addDriver(driver)` - Add driver to available pool
- `findNearestDriver(rideType, location)` - Find best matching driver
- `displayAvailableDrivers()` - List all available drivers

### 6. Fare Calculator Module (FareCalculator.java)
**Description:** Dynamic fare calculation with surge pricing  
**Supported Vehicle Types:** BIKE, AUTO, MINI, SEDAN, SUV  
**Key Methods:**
- `calculateFare(rideType, distanceKm)` - Calculate fare
- `setSurge(multiplier)` - Enable surge pricing
- `removeSurge()` - Disable surge pricing
- `showEstimates(distanceKm)` - Show fare estimates

**Pricing:**
```
BIKE: Base Rs.20 + Rs.8/km
AUTO: Base Rs.30 + Rs.12/km
MINI: Base Rs.50 + Rs.15/km
SEDAN: Base Rs.80 + Rs.20/km
SUV: Base Rs.120 + Rs.25/km
```

### 7. Wallet Module (Wallet.java)
**Description:** Digital wallet for fund management  
**Key Methods:**
- `addMoney(amount)` - Add funds to wallet
- `deductMoney(amount)` - Deduct funds from wallet
- `getBalance()` - Get current balance
- `showTransactionHistory()` - View all transactions

### 8. Payment Module (Payment.java)
**Description:** Handles payment processing and refunds  
**Supported Methods:** UPI, CARD, WALLET, CASH  
**Key Methods:**
- `processPayment()` - Process payment
- `refundPayment()` - Refund processed payment
- `failPayment(reason)` - Mark payment as failed

### 9. Notification Module (Notification.java)
**Description:** Sends real-time notifications to users  
**Notification Types:** RIDE_UPDATE, PAYMENT, PROMO, SAFETY, GENERAL  
**Key Methods:**
- `sendRideUpdate(userId, bookingId, status)` - Send ride updates
- `sendPaymentNotification(userId, amount, status)` - Payment notification
- `sendPromoNotification(userId, promoCode, discount)` - Promo offer
- `showNotifications(userId)` - View user notifications

### 10. Ride Tracker Module (RideTracker.java)
**Description:** Real-time GPS tracking with live location updates  
**Key Methods:**
- `startTracking()` - Start live tracking
- `updateDriverLocation(location, kmCovered)` - Update location
- `stopTracking()` - Stop tracking
- `getDistanceCovered()` - Get progress

### 11. Rating & Review Module (RatingReview.java)
**Description:** Rating system for users and drivers  
**Rating Scale:** 1-5 stars  
**Key Methods:**
- `submitReview()` - Submit rating and comment
- `getAverageRating(personId)` - Get average rating
- `showReviewsFor(personId)` - View all reviews

### 12. Ride History Module (RideHistory.java)
**Description:** Maintains complete ride records and analytics  
**Key Methods:**
- `addRide(ride)` - Add completed ride to history
- `showAllRides()` - View all rides
- `showRidesByStatus(status)` - Filter by status
- `getTotalSpent()` - Calculate total spending

### 13. Promo Code Module (PromoCode.java)
**Description:** Manage promotional codes and discounts  
**Key Methods:**
- `addPromo()` - Create new promo code
- `applyPromo(fareAmount)` - Apply promo to fare
- `deactivate()` - Deactivate promo code
- `showAllPromos()` - View all available promos

### 14. SOS Emergency Module (SOSEmergency.java)
**Description:** Emergency service for user safety  
**Key Methods:**
- `triggerSOS(bookingId, location)` - Trigger emergency alert
- `shareLocation(bookingId, location)` - Share live location
- `acknowledgeSOS()` - Acknowledge SOS by safety team
- `resolveSOS(resolution)` - Resolve emergency

### 15. Admin Dashboard Module (AdminDashboard.java)
**Description:** Administrative interface for platform management  
**Key Methods:**
- `showDashboard(users, drivers, rides, activeRides, revenue)` - Display KPIs
- `viewAllDrivers(drivers)` - View driver list
- `viewAllUsers(users)` - View user list
- `generateRevenueReport(daily, weekly, monthly)` - Revenue analytics

---

## 🏗️ Architecture

### Design Patterns Used
- **Model-View-Controller (MVC)** - Separation of concerns
- **Singleton Pattern** - Notification, PromoCode static storage
- **Factory Pattern** - User/Driver creation
- **Observer Pattern** - Notification system

### Data Flow
```
User Booking → Ride Matching → Driver Assignment → 
Fare Calculation → Payment Processing → Tracking → 
Completion → Rating → History Storage → Notification
```

### Key Interactions
1. User books a ride
2. System matches with nearest available driver
3. Driver confirms and picks up rider
4. Real-time tracking during ride
5. Payment processing upon completion
6. Rating and review submission
7. Data stored in ride history
8. Admin views analytics

---

## 💡 Usage Examples

### Example 1: Complete Ride Flow

```java
// 1. User registration
User user = new User("U001", "Rahul", "rahul@email.com", "9876543210", "pass123");
user.verifyOTP("123456");
user.login("rahul@email.com", "pass123");

// 2. Driver setup
Driver driver = new Driver("D001", "Vijay", "9123456789", "DL-2026-001");
driver.verifyDriver();
driver.goOnline("Connaught Place");

// 3. Book ride
RideBooking booking = new RideBooking("R001", "U001", "Delhi", "Airport", "SEDAN");

// 4. Calculate fare
FareCalculator calc = new FareCalculator();
double fare = calc.calculateFare("SEDAN", 25.5);

// 5. Confirm ride
booking.confirmRide("D001", fare);

// 6. Start ride
booking.startRide();

// 7. Track ride
RideTracker tracker = new RideTracker("R001", "Delhi", "Airport", 25.5);
tracker.startTracking();
tracker.updateDriverLocation("Midway", 12.5);

// 8. Complete ride
booking.completeRide();
tracker.stopTracking();

// 9. Process payment
Payment payment = new Payment("P001", "R001", "U001", fare, "UPI");
payment.processPayment();

// 10. Submit rating
RatingReview review = new RatingReview("REV001", "R001", "U001", "D001", 5, "Great driver!");
review.submitReview();
```

### Example 2: Fare Estimation

```java
FareCalculator calculator = new FareCalculator();

// Show estimates for 10 km
calculator.showEstimates(10.0);

// Enable surge pricing
calculator.setSurge(1.5);

// Calculate with surge
double suvFare = calculator.calculateFare("SUV", 10.0);

// Remove surge
calculator.removeSurge();
```

### Example 3: Wallet Operations

```java
Wallet wallet = new Wallet("W001", "U001");

// Add money
wallet.addMoney(1000);
wallet.addMoney(500);

// Deduct for ride
wallet.deductMoney(290);

// View history
wallet.showTransactionHistory();

// Check balance
System.out.println("Current: Rs." + wallet.getBalance());
```

### Example 4: Apply Promo Code

```java
// Create promo
PromoCode promo = new PromoCode("GOPRO50", 50, 100, 100, 10, 
    LocalDateTime.now().plusDays(30));
promo.addPromo();

// Apply to fare
double discount = promo.applyPromo(290.0);
```

---

## 📊 Sample Output

### Full Application Demo
```
╔══════════════════════════════════════════════════╗
║          Welcome to GO PRO Ride App!            ║
║            Your Ride, Your Way!                 ║
╚══════════════════════════════════════════════════╝

===== MODULE 1: User Registration =====
[GoPro] Rahul Sharma verified via OTP.
[GoPro] Rahul Sharma logged in successfully.

===== MODULE 2: Driver Registration =====
[GoPro] Driver Vijay Kumar verified successfully.
[GoPro] Driver Vijay Kumar is now ONLINE at Connaught Place, Delhi

===== MODULE 6: Fare Calculation =====
[GoPro] ===== Fare Estimates for 10.5 km =====
[GoPro] Fare for BIKE (10.5 km): Rs.104.00
[GoPro] Fare for AUTO (10.5 km): Rs.156.00
[GoPro] Fare for SEDAN (10.5 km): Rs.290.00
...
```

---

## 🔐 Security Considerations

- Password storage (currently plain text - upgrade to hashing in production)
- OTP validation for user registration
- Emergency contact verification
- Payment method encryption (recommended for production)
- Admin authentication (recommended for production)

---

## 🚀 Future Enhancements

- [ ] Database integration (MySQL/PostgreSQL)
- [ ] Web UI/Mobile app frontend
- [ ] Real GPS integration
- [ ] Machine learning for ride matching optimization
- [ ] Advanced analytics and reporting
- [ ] Customer support chatbot
- [ ] Insurance integration
- [ ] Multi-language support
- [ ] Payment gateway integration (Razorpay, Stripe)
- [ ] Surge pricing algorithm optimization

---

## 📝 Notes

- All modules include standalone `main()` methods for testing
- Use `ModuleRunner.java` for interactive exploration
- Each module demonstrates all key features
- Complete integration demo available in `GoProApp.java`
- Sample data is pre-populated for demonstrations

---

## 👨‍💻 Developer Information

**Project:** GoPro Ride-Sharing Platform  
**Version:** 1.0  
**Last Updated:** May 8, 2026  
**Java Version:** 8+  
**Lines of Code:** 2000+

---

## 📄 License

This project is for educational and demonstration purposes.

---

## 📞 Support

For issues, questions, or contributions:
- Review the module documentation above
- Run individual modules for isolated testing
- Check GoProApp.java for integration examples
- Refer to diagram files for architecture understanding

---

## Quick Start Checklist

- ✅ Java 8+ installed
- ✅ All `.java` files compiled
- ✅ Run `java GoProApp` for full demo
- ✅ Or run individual modules with `java -cp . ModuleName`
- ✅ Use `ModuleRunner` for interactive menu

---

**Happy Riding with GoPro! 🚗**
