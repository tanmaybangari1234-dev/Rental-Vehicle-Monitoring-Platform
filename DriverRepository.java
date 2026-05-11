import java.sql.*;
import java.util.*;

/**
 * Driver Repository - Handles all driver database operations
 * Template for managing driver persistence
 */
public class DriverRepository {

    /**
     * Save or update driver in database
     */
    public static boolean saveDriver(Driver driver) {
        String query = "INSERT OR REPLACE INTO drivers " +
                      "(driver_id, name, phone, license_number, is_available, is_verified, rating, total_trips, current_location) " +
                      "VALUES ('" + driver.getDriverId() + "', '" + driver.getName() + "', '" + driver.getPhone() + 
                      "', '" + driver.getLicenseNumber() + "', " + (driver.isAvailable() ? 1 : 0) + ", " +
                      (driver.isVerified() ? 1 : 0) + ", " + driver.getRating() + ", " + driver.getTotalTrips() + 
                      ", '" + driver.getCurrentLocation() + "')";
        
        return DatabaseManager.executeUpdate(query);
    }

    /**
     * Get driver by ID
     */
    public static Driver getDriverById(String driverId) {
        String query = "SELECT * FROM drivers WHERE driver_id = '" + driverId + "'";
        try (ResultSet rs = DatabaseManager.executeQuery(query)) {
            if (rs != null && rs.next()) {
                return createDriverFromResultSet(rs);
            }
        } catch (SQLException e) {
            System.out.println("[GoPro ERROR] Error retrieving driver: " + e.getMessage());
        }
        return null;
    }

    /**
     * Get all drivers
     */
    public static List<Driver> getAllDrivers() {
        List<Driver> drivers = new ArrayList<>();
        String query = "SELECT * FROM drivers";
        try (ResultSet rs = DatabaseManager.executeQuery(query)) {
            if (rs != null) {
                while (rs.next()) {
                    drivers.add(createDriverFromResultSet(rs));
                }
            }
        } catch (SQLException e) {
            System.out.println("[GoPro ERROR] Error retrieving drivers: " + e.getMessage());
        }
        return drivers;
    }

    /**
     * Get available drivers
     */
    public static List<Driver> getAvailableDrivers() {
        List<Driver> drivers = new ArrayList<>();
        String query = "SELECT * FROM drivers WHERE is_available = 1 AND is_verified = 1";
        try (ResultSet rs = DatabaseManager.executeQuery(query)) {
            if (rs != null) {
                while (rs.next()) {
                    drivers.add(createDriverFromResultSet(rs));
                }
            }
        } catch (SQLException e) {
            System.out.println("[GoPro ERROR] Error retrieving available drivers: " + e.getMessage());
        }
        return drivers;
    }

    /**
     * Update driver availability
     */
    public static boolean updateDriverAvailability(String driverId, boolean isAvailable) {
        String query = "UPDATE drivers SET is_available = " + (isAvailable ? 1 : 0) + 
                      " WHERE driver_id = '" + driverId + "'";
        return DatabaseManager.executeUpdate(query);
    }

    /**
     * Update driver location
     */
    public static boolean updateDriverLocation(String driverId, String location) {
        String query = "UPDATE drivers SET current_location = '" + location + 
                      "' WHERE driver_id = '" + driverId + "'";
        return DatabaseManager.executeUpdate(query);
    }

    /**
     * Update driver rating
     */
    public static boolean updateDriverRating(String driverId, double newRating, int totalTrips) {
        String query = "UPDATE drivers SET rating = " + newRating + ", total_trips = " + totalTrips + 
                      " WHERE driver_id = '" + driverId + "'";
        return DatabaseManager.executeUpdate(query);
    }

    /**
     * Delete driver
     */
    public static boolean deleteDriver(String driverId) {
        String query = "DELETE FROM drivers WHERE driver_id = '" + driverId + "'";
        return DatabaseManager.executeUpdate(query);
    }

    /**
     * Helper method to create Driver object from ResultSet
     */
    private static Driver createDriverFromResultSet(ResultSet rs) throws SQLException {
        Driver driver = new Driver(
            rs.getString("driver_id"),
            rs.getString("name"),
            rs.getString("phone"),
            rs.getString("license_number")
        );
        driver.setAvailable(rs.getInt("is_available") == 1);
        driver.setVerified(rs.getInt("is_verified") == 1);
        driver.setRating(rs.getDouble("rating"));
        driver.setTotalTrips(rs.getInt("total_trips"));
        driver.setCurrentLocation(rs.getString("current_location"));
        return driver;
    }

    public static void main(String[] args) {
        System.out.println("╔════════════════════════════════════════════╗");
        System.out.println("║   GoPro Driver Repository Demo             ║");
        System.out.println("╚════════════════════════════════════════════╝\n");

        DatabaseManager.initialize();

        // Test: Save driver
        System.out.println("--- Test 1: Save Driver ---");
        Driver driver1 = new Driver("D001", "Raj Kumar", "9876543210", "DL123456");
        boolean saved = DriverRepository.saveDriver(driver1);
        System.out.println("Driver saved: " + saved + "\n");

        // Test: Get all drivers
        System.out.println("--- Test 2: Get All Drivers ---");
        List<Driver> allDrivers = DriverRepository.getAllDrivers();
        System.out.println("Total drivers: " + allDrivers.size());

        DatabaseManager.closeConnection();
    }
}
