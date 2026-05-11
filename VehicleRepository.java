import java.sql.*;
import java.util.*;

/**
 * Vehicle Repository - Handles all vehicle database operations
 */
public class VehicleRepository {

    /**
     * Save or update vehicle in database
     */
    public static boolean saveVehicle(Vehicle vehicle) {
        String query = "INSERT OR REPLACE INTO vehicles " +
                      "(vehicle_id, driver_id, vehicle_type, registration_number, model_name, color, is_verified) " +
                      "VALUES ('" + vehicle.getVehicleId() + "', '" + vehicle.getDriverId() + "', '" + 
                      vehicle.getVehicleType() + "', '" + vehicle.getRegistrationNumber() + "', '" + 
                      vehicle.getModelName() + "', '" + vehicle.getColor() + "', " + 
                      (vehicle.isVerified() ? 1 : 0) + ")";
        
        return DatabaseManager.executeUpdate(query);
    }

    /**
     * Get vehicle by ID
     */
    public static Vehicle getVehicleById(String vehicleId) {
        String query = "SELECT * FROM vehicles WHERE vehicle_id = '" + vehicleId + "'";
        try (ResultSet rs = DatabaseManager.executeQuery(query)) {
            if (rs != null && rs.next()) {
                return createVehicleFromResultSet(rs);
            }
        } catch (SQLException e) {
            System.out.println("[GoPro ERROR] Error retrieving vehicle: " + e.getMessage());
        }
        return null;
    }

    /**
     * Get all vehicles by driver
     */
    public static List<Vehicle> getVehiclesByDriverId(String driverId) {
        List<Vehicle> vehicles = new ArrayList<>();
        String query = "SELECT * FROM vehicles WHERE driver_id = '" + driverId + "'";
        try (ResultSet rs = DatabaseManager.executeQuery(query)) {
            if (rs != null) {
                while (rs.next()) {
                    vehicles.add(createVehicleFromResultSet(rs));
                }
            }
        } catch (SQLException e) {
            System.out.println("[GoPro ERROR] Error retrieving vehicles: " + e.getMessage());
        }
        return vehicles;
    }

    /**
     * Get all vehicles
     */
    public static List<Vehicle> getAllVehicles() {
        List<Vehicle> vehicles = new ArrayList<>();
        String query = "SELECT * FROM vehicles WHERE is_verified = 1";
        try (ResultSet rs = DatabaseManager.executeQuery(query)) {
            if (rs != null) {
                while (rs.next()) {
                    vehicles.add(createVehicleFromResultSet(rs));
                }
            }
        } catch (SQLException e) {
            System.out.println("[GoPro ERROR] Error retrieving vehicles: " + e.getMessage());
        }
        return vehicles;
    }

    /**
     * Delete vehicle
     */
    public static boolean deleteVehicle(String vehicleId) {
        String query = "DELETE FROM vehicles WHERE vehicle_id = '" + vehicleId + "'";
        return DatabaseManager.executeUpdate(query);
    }

    /**
     * Helper method to create Vehicle object from ResultSet
     */
    private static Vehicle createVehicleFromResultSet(ResultSet rs) throws SQLException {
        Vehicle vehicle = new Vehicle(
            rs.getString("vehicle_id"),
            rs.getString("driver_id"),
            rs.getString("vehicle_type"),
            rs.getString("registration_number"),
            rs.getString("model_name"),
            rs.getString("color")
        );
        vehicle.setVerified(rs.getInt("is_verified") == 1);
        return vehicle;
    }

    public static void main(String[] args) {
        System.out.println("╔════════════════════════════════════════════╗");
        System.out.println("║   GoPro Vehicle Repository Demo            ║");
        System.out.println("╚════════════════════════════════════════════╝\n");

        DatabaseManager.initialize();

        // Test: Save vehicle
        System.out.println("--- Test 1: Save Vehicle ---");
        Vehicle vehicle1 = new Vehicle("V001", "D001", "SUV", "DL-01-AB-1234", "Toyota Fortuner", "Black");
        boolean saved = VehicleRepository.saveVehicle(vehicle1);
        System.out.println("Vehicle saved: " + saved + "\n");

        // Test: Get vehicles by driver
        System.out.println("--- Test 2: Get Vehicles by Driver ---");
        List<Vehicle> driverVehicles = VehicleRepository.getVehiclesByDriverId("D001");
        System.out.println("Driver D001 vehicles: " + driverVehicles.size());

        DatabaseManager.closeConnection();
    }
}
