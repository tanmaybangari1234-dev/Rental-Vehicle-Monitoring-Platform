public class User {
    private String userId;
    private String name;
    private String email;
    private String phone;
    private String password;
    private boolean isVerified;

    public User(String userId, String name, String email, String phone, String password) {
        this.userId = userId;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.password = password;
        this.isVerified = false;
    }

    public boolean login(String email, String password) {
        if (this.email.equals(email) && this.password.equals(password)) {
            System.out.println("[GoPro] " + name + " logged in successfully.");
            return true;
        }
        System.out.println("[GoPro] Login failed. Invalid credentials.");
        return false;
    }

    public void verifyOTP(String otp) {
        if (otp != null && otp.length() == 6) {
            this.isVerified = true;
            System.out.println("[GoPro] " + name + " verified via OTP.");
        } else {
            System.out.println("[GoPro] Invalid OTP.");
        }
    }

    public void updateProfile(String name, String phone) {
        this.name = name;
        this.phone = phone;
        System.out.println("[GoPro] Profile updated for " + this.name);
    }

    public void logout() {
        System.out.println("[GoPro] " + name + " logged out.");
    }

    // Getters
    public String getUserId() { return userId; }
    public String getName() { return name; }
    public String getEmail() { return email; }
    public String getPhone() { return phone; }
    public String getPassword() { return password; }
    public boolean isVerified() { return isVerified; }

    // Setters
    public void setVerified(boolean isVerified) { this.isVerified = isVerified; }

    @Override
    public String toString() {
        return "User{name='" + name + "', email='" + email + "', phone='" + phone + "', verified=" + isVerified + "}";
    }

    public static void main(String[] args) {
        System.out.println("╔════════════════════════════════════════════╗");
        System.out.println("║   GoPro User Module Demo                   ║");
        System.out.println("╚════════════════════════════════════════════╝\n");

        // Test 1: Create users
        System.out.println("--- Test 1: User Registration ---");
        User user1 = new User("U001", "Rahul Sharma", "rahul@email.com", "9876543210", "pass123");
        User user2 = new User("U002", "Priya Singh", "priya@email.com", "9988776655", "pass456");

        // Test 2: OTP verification
        System.out.println("\n--- Test 2: OTP Verification ---");
        user1.verifyOTP("123456");
        user2.verifyOTP("654321");

        // Test 3: Login
        System.out.println("\n--- Test 3: User Login ---");
        user1.login("rahul@email.com", "pass123");
        user1.login("rahul@email.com", "wrongpass");

        // Test 4: Profile update
        System.out.println("\n--- Test 4: Profile Update ---");
        user1.updateProfile("Rahul Sharma Updated", "9111222333");

        // Test 5: Logout
        System.out.println("\n--- Test 5: Logout ---");
        user1.logout();

        System.out.println("\n╔════════════════════════════════════════════╗");
        System.out.println("║   User Module Demo Complete!               ║");
        System.out.println("╚════════════════════════════════════════════╝");
    }
}
