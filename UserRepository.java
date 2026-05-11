import java.sql.*;
import java.util.*;

/**
 * User Repository - Handles all user database operations
 */
public class UserRepository {

    /**
     * Save or update user in database
     */
    public static boolean saveUser(User user) {
        String query = "INSERT OR REPLACE INTO users (user_id, name, email, phone, password, is_verified) " +
                      "VALUES ('" + user.getUserId() + "', '" + user.getName() + "', '" + user.getEmail() + 
                      "', '" + user.getPhone() + "', '" + user.getPassword() + "', " + 
                      (user.isVerified() ? 1 : 0) + ")";
        
        return DatabaseManager.executeUpdate(query);
    }

    /**
     * Get user by email
     */
    public static User getUserByEmail(String email) {
        String query = "SELECT * FROM users WHERE email = '" + email + "'";
        try (ResultSet rs = DatabaseManager.executeQuery(query)) {
            if (rs != null && rs.next()) {
                User user = new User(
                    rs.getString("user_id"),
                    rs.getString("name"),
                    rs.getString("email"),
                    rs.getString("phone"),
                    rs.getString("password")
                );
                // Set verification status from database
                user.setVerified(rs.getInt("is_verified") == 1);
                return user;
            }
        } catch (SQLException e) {
            System.out.println("[GoPro ERROR] Error retrieving user: " + e.getMessage());
        }
        return null;
    }

    /**
     * Get user by ID
     */
    public static User getUserById(String userId) {
        String query = "SELECT * FROM users WHERE user_id = '" + userId + "'";
        try (ResultSet rs = DatabaseManager.executeQuery(query)) {
            if (rs != null && rs.next()) {
                User user = new User(
                    rs.getString("user_id"),
                    rs.getString("name"),
                    rs.getString("email"),
                    rs.getString("phone"),
                    rs.getString("password")
                );
                user.setVerified(rs.getInt("is_verified") == 1);
                return user;
            }
        } catch (SQLException e) {
            System.out.println("[GoPro ERROR] Error retrieving user: " + e.getMessage());
        }
        return null;
    }

    /**
     * Get all users
     */
    public static List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        String query = "SELECT * FROM users";
        try (ResultSet rs = DatabaseManager.executeQuery(query)) {
            if (rs != null) {
                while (rs.next()) {
                    User user = new User(
                        rs.getString("user_id"),
                        rs.getString("name"),
                        rs.getString("email"),
                        rs.getString("phone"),
                        rs.getString("password")
                    );
                    user.setVerified(rs.getInt("is_verified") == 1);
                    users.add(user);
                }
            }
        } catch (SQLException e) {
            System.out.println("[GoPro ERROR] Error retrieving users: " + e.getMessage());
        }
        return users;
    }

    /**
     * Delete user
     */
    public static boolean deleteUser(String userId) {
        String query = "DELETE FROM users WHERE user_id = '" + userId + "'";
        return DatabaseManager.executeUpdate(query);
    }

    /**
     * Check if user exists
     */
    public static boolean userExists(String email) {
        String query = "SELECT COUNT(*) FROM users WHERE email = '" + email + "'";
        try (ResultSet rs = DatabaseManager.executeQuery(query)) {
            if (rs != null && rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            System.out.println("[GoPro ERROR] Error checking user existence: " + e.getMessage());
        }
        return false;
    }

    /**
     * Update user verification status
     */
    public static boolean updateUserVerification(String userId, boolean isVerified) {
        String query = "UPDATE users SET is_verified = " + (isVerified ? 1 : 0) + 
                      " WHERE user_id = '" + userId + "'";
        return DatabaseManager.executeUpdate(query);
    }

    /**
     * Store OTP in database
     */
    public static boolean storeOTP(String email, String otp) {
        String query = "UPDATE users SET otp_code = '" + otp + 
                      "', otp_generated_time = CURRENT_TIMESTAMP WHERE email = '" + email + "'";
        return DatabaseManager.executeUpdate(query);
    }

    /**
     * Get stored OTP
     */
    public static String getStoredOTP(String email) {
        String query = "SELECT otp_code FROM users WHERE email = '" + email + "'";
        try (ResultSet rs = DatabaseManager.executeQuery(query)) {
            if (rs != null && rs.next()) {
                return rs.getString("otp_code");
            }
        } catch (SQLException e) {
            System.out.println("[GoPro ERROR] Error retrieving OTP: " + e.getMessage());
        }
        return null;
    }

    public static void main(String[] args) {
        System.out.println("╔════════════════════════════════════════════╗");
        System.out.println("║   GoPro User Repository Demo               ║");
        System.out.println("╚════════════════════════════════════════════╝\n");

        // Initialize database
        DatabaseManager.initialize();

        // Test: Save user
        System.out.println("--- Test 1: Save User ---");
        User user1 = new User("U001", "Rahul Sharma", "rahul@email.com", "9876543210", "pass123");
        boolean saved = UserRepository.saveUser(user1);
        System.out.println("User saved: " + saved + "\n");

        // Test: Retrieve user
        System.out.println("--- Test 2: Retrieve User ---");
        User retrieved = UserRepository.getUserByEmail("rahul@email.com");
        if (retrieved != null) {
            System.out.println("Retrieved: " + retrieved + "\n");
        }

        // Test: Get all users
        System.out.println("--- Test 3: Get All Users ---");
        List<User> allUsers = UserRepository.getAllUsers();
        System.out.println("Total users: " + allUsers.size());

        // Close database
        DatabaseManager.closeConnection();
    }
}
