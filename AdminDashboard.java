import java.util.*;

// Module 15: Admin Dashboard
public class AdminDashboard {
    private String adminId;
    private String adminName;

    public AdminDashboard(String adminId, String adminName) {
        this.adminId = adminId;
        this.adminName = adminName;
    }

    public void showDashboard(int totalUsers, int totalDrivers, int totalRides, int activeRides, double totalRevenue) {
        System.out.println("╔══════════════════════════════════════════════════╗");
        System.out.println("║         GoPro ADMIN DASHBOARD                   ║");
        System.out.println("║         Admin: " + padRight(adminName, 34) + "║");
        System.out.println("╠══════════════════════════════════════════════════╣");
        System.out.println("║  Total Users       : " + padRight(String.valueOf(totalUsers), 28) + "║");
        System.out.println("║  Total Drivers     : " + padRight(String.valueOf(totalDrivers), 28) + "║");
        System.out.println("║  Total Rides       : " + padRight(String.valueOf(totalRides), 28) + "║");
        System.out.println("║  Active Rides      : " + padRight(String.valueOf(activeRides), 28) + "║");
        System.out.println("║  Total Revenue     : " + padRight("Rs." + String.format("%.2f", totalRevenue), 28) + "║");
        System.out.println("╚══════════════════════════════════════════════════╝");
    }

    public void viewAllDrivers(List<Driver> drivers) {
        System.out.println("[GoPro Admin] ===== All Drivers (" + drivers.size() + ") =====");
        for (Driver d : drivers) {
            System.out.println("  " + d);
        }
    }

    public void viewAllUsers(List<User> users) {
        System.out.println("[GoPro Admin] ===== All Users (" + users.size() + ") =====");
        for (User u : users) {
            System.out.println("  " + u);
        }
    }

    public void blockDriver(Driver driver, String reason) {
        driver.goOffline();
        System.out.println("[GoPro Admin] Driver " + driver.getName() + " BLOCKED. Reason: " + reason);
    }

    public void approveDriver(Driver driver) {
        driver.verifyDriver();
        System.out.println("[GoPro Admin] Driver " + driver.getName() + " APPROVED.");
    }

    public void generateRevenueReport(double dailyRevenue, double weeklyRevenue, double monthlyRevenue) {
        System.out.println("[GoPro Admin] ===== Revenue Report =====");
        System.out.println("  Today  : Rs." + String.format("%.2f", dailyRevenue));
        System.out.println("  Week   : Rs." + String.format("%.2f", weeklyRevenue));
        System.out.println("  Month  : Rs." + String.format("%.2f", monthlyRevenue));
    }

    private String padRight(String s, int length) {
        if (s.length() >= length) return s.substring(0, length);
        return s + " ".repeat(length - s.length());
    }

    public static void main(String[] args) {
        System.out.println("╔════════════════════════════════════════════╗");
        System.out.println("║   GoPro Admin Dashboard Module Demo        ║");
        System.out.println("╚════════════════════════════════════════════╝\n");

        // Create admin dashboard
        AdminDashboard dashboard = new AdminDashboard("ADMIN001", "Super Admin");

        // Test 1: Display dashboard
        System.out.println("--- Test 1: Display Dashboard ---");
        dashboard.showDashboard(5, 10, 45, 3, 12500.00);

        // Test 2: Create users and drivers
        System.out.println("\n--- Test 2: View All Users ---");
        List<User> users = new ArrayList<>();
        users.add(new User("U001", "Rahul Sharma", "rahul@email.com", "9876543210", "pass123"));
        users.add(new User("U002", "Priya Singh", "priya@email.com", "9988776655", "pass456"));
        dashboard.viewAllUsers(users);

        System.out.println("\n--- Test 3: View All Drivers ---");
        List<Driver> drivers = new ArrayList<>();
        Driver d1 = new Driver("D001", "Vijay Kumar", "9123456789", "DL-2026-001");
        Driver d2 = new Driver("D002", "Amit Singh", "9987654321", "KA-2026-002");
        d1.verifyDriver();
        d2.verifyDriver();
        drivers.add(d1);
        drivers.add(d2);
        dashboard.viewAllDrivers(drivers);

        // Test 4: Generate revenue report
        System.out.println("\n--- Test 4: Revenue Report ---");
        dashboard.generateRevenueReport(1250.00, 8750.00, 38500.00);

        System.out.println("\n╔════════════════════════════════════════════╗");
        System.out.println("║   Admin Dashboard Demo Complete!           ║");
        System.out.println("╚════════════════════════════════════════════╝");
    }
}
