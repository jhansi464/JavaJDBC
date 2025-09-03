import java.sql.*;

public class JdbcSetupExample {
    public static void main(String[] args) {
        String baseUrl = "jdbc:mysql://localhost:3306/?useSSL=false&serverTimezone=UTC"; // no DB yet
        String dbUrl   = "jdbc:mysql://localhost:3306/cruds_demo?useSSL=false&serverTimezone=UTC"; // with DB
        String user = "root";
        String password = "Raji666";

        try {
            // 1. Connect without DB and create database
            try (Connection con = DriverManager.getConnection(baseUrl, user, password);
                 Statement stmt = con.createStatement()) {
                stmt.executeUpdate("CREATE DATABASE IF NOT EXISTS cruds_demo");
                System.out.println("✅ Database 'cruds_demo' ready.");
            }

            // 2. Connect to the new DB
            try (Connection con = DriverManager.getConnection(dbUrl, user, password);
                 Statement stmt = con.createStatement()) {

                // 3. Create table
                String createTable = "CREATE TABLE IF NOT EXISTS users (" +
                        "id INT AUTO_INCREMENT PRIMARY KEY, " +
                        "name VARCHAR(100) NOT NULL, " +
                        "email VARCHAR(100) NOT NULL UNIQUE)";
                stmt.executeUpdate(createTable);
                System.out.println("✅ Table 'users' ready.");

                // 4. Insert sample data
                String insert = "INSERT INTO users (name, email) VALUES (?, ?)" +"ON DUPLICATE KEY UPDATE name=VALUES(name)";
                try (PreparedStatement ps = con.prepareStatement(insert)) {
                    ps.setString(1, "Ravi");
                    ps.setString(2, "ravi@example.com");
                    ps.executeUpdate();

                    ps.setString(1, "Sita");
                    ps.setString(2, "sita@example.com");
                    ps.executeUpdate();

                    ps.setString(1, "Arjun");
                    ps.setString(2, "arjun@example.com");
                    ps.executeUpdate();
                }
                System.out.println("✅ Sample records inserted.");

                // 5. Read and display records
                String select = "SELECT * FROM users";
                try (ResultSet rs = stmt.executeQuery(select)) {
                    System.out.println("\n--- Users Table ---");
                    while (rs.next()) {
                        System.out.println(rs.getInt("id") + " | " +
                                rs.getString("name") + " | " +
                                rs.getString("email"));
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
