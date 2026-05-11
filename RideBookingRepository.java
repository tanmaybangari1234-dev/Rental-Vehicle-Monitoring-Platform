import java.sql.*;
import java.util.*;
import java.time.LocalDateTime;

/**
 * Ride Booking Repository - Handles all ride booking database operations
 */
public class RideBookingRepository {

    /**
     * Save or update ride booking in database
     */
    public static boolean saveBooking(RideBooking booking) {
        String query = "INSERT OR REPLACE INTO ride_bookings " +
                      "(booking_id, user_id, driver_id, pickup_location, dropoff_location, booking_status, fare_amount) " +
                      "VALUES ('" + booking.getBookingId() + "', '" + booking.getUserId() + "', '" + 
                      (booking.getDriverId() != null ? booking.getDriverId() : "NULL") + "', '" + 
                      booking.getPickupLocation() + "', '" + booking.getDropoffLocation() + "', '" + 
                      booking.getBookingStatus() + "', " + booking.getFareAmount() + ")";
        
        return DatabaseManager.executeUpdate(query);
    }

    /**
     * Get booking by ID
     */
    public static RideBooking getBookingById(String bookingId) {
        String query = "SELECT * FROM ride_bookings WHERE booking_id = '" + bookingId + "'";
        try (ResultSet rs = DatabaseManager.executeQuery(query)) {
            if (rs != null && rs.next()) {
                return createBookingFromResultSet(rs);
            }
        } catch (SQLException e) {
            System.out.println("[GoPro ERROR] Error retrieving booking: " + e.getMessage());
        }
        return null;
    }

    /**
     * Get all bookings for a user
     */
    public static List<RideBooking> getBookingsByUserId(String userId) {
        List<RideBooking> bookings = new ArrayList<>();
        String query = "SELECT * FROM ride_bookings WHERE user_id = '" + userId + "' ORDER BY created_at DESC";
        try (ResultSet rs = DatabaseManager.executeQuery(query)) {
            if (rs != null) {
                while (rs.next()) {
                    bookings.add(createBookingFromResultSet(rs));
                }
            }
        } catch (SQLException e) {
            System.out.println("[GoPro ERROR] Error retrieving bookings: " + e.getMessage());
        }
        return bookings;
    }

    /**
     * Get all active bookings for a driver
     */
    public static List<RideBooking> getActiveBookingsByDriver(String driverId) {
        List<RideBooking> bookings = new ArrayList<>();
        String query = "SELECT * FROM ride_bookings WHERE driver_id = '" + driverId + 
                      "' AND booking_status IN ('ACCEPTED', 'IN_PROGRESS')";
        try (ResultSet rs = DatabaseManager.executeQuery(query)) {
            if (rs != null) {
                while (rs.next()) {
                    bookings.add(createBookingFromResultSet(rs));
                }
            }
        } catch (SQLException e) {
            System.out.println("[GoPro ERROR] Error retrieving active bookings: " + e.getMessage());
        }
        return bookings;
    }

    /**
     * Update booking status
     */
    public static boolean updateBookingStatus(String bookingId, String status) {
        String query = "UPDATE ride_bookings SET booking_status = '" + status + "' WHERE booking_id = '" + bookingId + "'";
        return DatabaseManager.executeUpdate(query);
    }

    /**
     * Assign driver to booking
     */
    public static boolean assignDriver(String bookingId, String driverId) {
        String query = "UPDATE ride_bookings SET driver_id = '" + driverId + "', booking_status = 'ACCEPTED' " +
                      "WHERE booking_id = '" + bookingId + "'";
        return DatabaseManager.executeUpdate(query);
    }

    /**
     * Complete booking
     */
    public static boolean completeBooking(String bookingId, double fareAmount) {
        String query = "UPDATE ride_bookings SET booking_status = 'COMPLETED', fare_amount = " + fareAmount + 
                      ", completed_at = CURRENT_TIMESTAMP WHERE booking_id = '" + bookingId + "'";
        return DatabaseManager.executeUpdate(query);
    }

    /**
     * Get all bookings (admin)
     */
    public static List<RideBooking> getAllBookings() {
        List<RideBooking> bookings = new ArrayList<>();
        String query = "SELECT * FROM ride_bookings ORDER BY created_at DESC";
        try (ResultSet rs = DatabaseManager.executeQuery(query)) {
            if (rs != null) {
                while (rs.next()) {
                    bookings.add(createBookingFromResultSet(rs));
                }
            }
        } catch (SQLException e) {
            System.out.println("[GoPro ERROR] Error retrieving bookings: " + e.getMessage());
        }
        return bookings;
    }

    /**
     * Helper method to create RideBooking object from ResultSet
     */
    private static RideBooking createBookingFromResultSet(ResultSet rs) throws SQLException {
        RideBooking booking = new RideBooking(
            rs.getString("booking_id"),
            rs.getString("user_id"),
            rs.getString("pickup_location"),
            rs.getString("dropoff_location")
        );
        booking.setDriverId(rs.getString("driver_id"));
        booking.setBookingStatus(rs.getString("booking_status"));
        booking.setFareAmount(rs.getDouble("fare_amount"));
        return booking;
    }

    public static void main(String[] args) {
        System.out.println("╔════════════════════════════════════════════╗");
        System.out.println("║   GoPro Ride Booking Repository Demo       ║");
        System.out.println("╚════════════════════════════════════════════╝\n");

        DatabaseManager.initialize();

        // Test: Save booking
        System.out.println("--- Test 1: Save Booking ---");
        RideBooking booking = new RideBooking("B001", "U001", "Delhi Airport", "Connaught Place");
        boolean saved = RideBookingRepository.saveBooking(booking);
        System.out.println("Booking saved: " + saved + "\n");

        // Test: Get bookings by user
        System.out.println("--- Test 2: Get User Bookings ---");
        List<RideBooking> userBookings = RideBookingRepository.getBookingsByUserId("U001");
        System.out.println("User U001 bookings: " + userBookings.size());

        DatabaseManager.closeConnection();
    }
}
