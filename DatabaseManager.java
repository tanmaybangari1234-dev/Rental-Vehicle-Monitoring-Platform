import java.sql.*;

/**
 * Database Manager for GoPro - Handles all database operations
 * Uses SQLite for lightweight, file-based persistence
 */
public class DatabaseManager {
    private static final String DB_URL = "jdbc:sqlite:gopro_database.db";
    private static Connection connection;

    // Initialize database and create tables
    public static void initialize() {
        try {
            // Load SQLite driver
            Class.forName("org.sqlite.JDBC");
            
            // Create connection
            connection = DriverManager.getConnection(DB_URL);
            System.out.println("[GoPro] Database connected successfully!");
            
            // Create tables if they don't exist
            createTables();
        } catch (ClassNotFoundException e) {
            System.out.println("[GoPro ERROR] SQLite driver not found. Please add sqlite-jdbc JAR file.");
            System.out.println("Download from: https://github.com/xerial/sqlite-jdbc/releases");
            e.printStackTrace();
        } catch (SQLException e) {
            System.out.println("[GoPro ERROR] Database connection failed!");
            e.printStackTrace();
        }
    }

    private static void createTables() throws SQLException {
        String[] createTableQueries = {
            // Users Table
            "CREATE TABLE IF NOT EXISTS users (" +
            "    user_id TEXT PRIMARY KEY," +
            "    name TEXT NOT NULL," +
            "    email TEXT UNIQUE NOT NULL," +
            "    phone TEXT NOT NULL," +
            "    password TEXT NOT NULL," +
            "    is_verified INTEGER DEFAULT 0," +
            "    otp_code TEXT," +
            "    otp_generated_time TIMESTAMP," +
            "    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP" +
            ");",

            // Drivers Table
            "CREATE TABLE IF NOT EXISTS drivers (" +
            "    driver_id TEXT PRIMARY KEY," +
            "    name TEXT NOT NULL," +
            "    phone TEXT NOT NULL," +
            "    license_number TEXT UNIQUE NOT NULL," +
            "    is_available INTEGER DEFAULT 0," +
            "    is_verified INTEGER DEFAULT 0," +
            "    rating REAL DEFAULT 5.0," +
            "    total_trips INTEGER DEFAULT 0," +
            "    current_location TEXT," +
            "    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP" +
            ");",

            // Vehicles Table
            "CREATE TABLE IF NOT EXISTS vehicles (" +
            "    vehicle_id TEXT PRIMARY KEY," +
            "    driver_id TEXT NOT NULL," +
            "    vehicle_type TEXT NOT NULL," +
            "    registration_number TEXT UNIQUE NOT NULL," +
            "    model_name TEXT NOT NULL," +
            "    color TEXT NOT NULL," +
            "    is_verified INTEGER DEFAULT 0," +
            "    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP," +
            "    FOREIGN KEY(driver_id) REFERENCES drivers(driver_id)" +
            ");",

            // Ride Bookings Table
            "CREATE TABLE IF NOT EXISTS ride_bookings (" +
            "    booking_id TEXT PRIMARY KEY," +
            "    user_id TEXT NOT NULL," +
            "    driver_id TEXT," +
            "    pickup_location TEXT NOT NULL," +
            "    dropoff_location TEXT NOT NULL," +
            "    booking_status TEXT DEFAULT 'REQUESTED'," +
            "    fare_amount REAL," +
            "    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP," +
            "    completed_at TIMESTAMP," +
            "    FOREIGN KEY(user_id) REFERENCES users(user_id)," +
            "    FOREIGN KEY(driver_id) REFERENCES drivers(driver_id)" +
            ");",

            // Payments Table
            "CREATE TABLE IF NOT EXISTS payments (" +
            "    payment_id TEXT PRIMARY KEY," +
            "    booking_id TEXT NOT NULL," +
            "    user_id TEXT NOT NULL," +
            "    amount REAL NOT NULL," +
            "    payment_method TEXT NOT NULL," +
            "    payment_status TEXT DEFAULT 'PENDING'," +
            "    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP," +
            "    FOREIGN KEY(booking_id) REFERENCES ride_bookings(booking_id)," +
            "    FOREIGN KEY(user_id) REFERENCES users(user_id)" +
            ");",

            // Ratings and Reviews Table
            "CREATE TABLE IF NOT EXISTS ratings_reviews (" +
            "    review_id TEXT PRIMARY KEY," +
            "    booking_id TEXT NOT NULL," +
            "    user_id TEXT NOT NULL," +
            "    driver_id TEXT NOT NULL," +
            "    rating REAL NOT NULL," +
            "    review_text TEXT," +
            "    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP," +
            "    FOREIGN KEY(booking_id) REFERENCES ride_bookings(booking_id)," +
            "    FOREIGN KEY(user_id) REFERENCES users(user_id)," +
            "    FOREIGN KEY(driver_id) REFERENCES drivers(driver_id)" +
            ");",

            // Wallet Table
            "CREATE TABLE IF NOT EXISTS wallets (" +
            "    wallet_id TEXT PRIMARY KEY," +
            "    user_id TEXT UNIQUE NOT NULL," +
            "    balance REAL DEFAULT 0.0," +
            "    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP," +
            "    FOREIGN KEY(user_id) REFERENCES users(user_id)" +
            ");",

            // Promo Codes Table
            "CREATE TABLE IF NOT EXISTS promo_codes (" +
            "    promo_id TEXT PRIMARY KEY," +
            "    code TEXT UNIQUE NOT NULL," +
            "    discount_percentage INTEGER NOT NULL," +
            "    max_uses INTEGER," +
            "    current_uses INTEGER DEFAULT 0," +
            "    is_active INTEGER DEFAULT 1," +
            "    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP" +
            ");"
        };

        try (Statement stmt = connection.createStatement()) {
            for (String query : createTableQueries) {
                stmt.execute(query);
            }
            System.out.println("[GoPro] Database tables created/verified successfully!");
        }
    }

    // Get connection
    public static Connection getConnection() {
        if (connection == null) {
            initialize();
        }
        return connection;
    }

    // Close database connection
    public static void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
                System.out.println("[GoPro] Database connection closed!");
            }
        } catch (SQLException e) {
            System.out.println("[GoPro ERROR] Error closing database connection!");
            e.printStackTrace();
        }
    }

    // Execute query (for INSERT, UPDATE, DELETE)
    public static boolean executeUpdate(String query) {
        try (Statement stmt = connection.createStatement()) {
            stmt.executeUpdate(query);
            return true;
        } catch (SQLException e) {
            System.out.println("[GoPro ERROR] Execute update failed: " + e.getMessage());
            return false;
        }
    }

    // Execute query with ResultSet (for SELECT)
    public static ResultSet executeQuery(String query) {
        try {
            Statement stmt = connection.createStatement();
            return stmt.executeQuery(query);
        } catch (SQLException e) {
            System.out.println("[GoPro ERROR] Execute query failed: " + e.getMessage());
            return null;
        }
    }

    public static void main(String[] args) {
        System.out.println("╔════════════════════════════════════════════╗");
        System.out.println("║   GoPro Database Manager Demo              ║");
        System.out.println("╚════════════════════════════════════════════╝\n");

        // Initialize database
        DatabaseManager.initialize();

        // Close connection when done
        DatabaseManager.closeConnection();
    }
}
