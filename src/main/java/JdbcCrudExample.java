import java.sql.*;
import java.util.Scanner;

public class JdbcCrudExample {
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/crud_demo";
        String user = "root";
        String password = "Raji666";

        try (Connection con = DriverManager.getConnection(url, user, password);
             Scanner sc = new Scanner(System.in)) {

            while (true) {
                System.out.println("\n--- JDBC CRUD Menu ---");
                System.out.println("1. Insert");
                System.out.println("2. Read");
                System.out.println("3. Update");
                System.out.println("4. Delete");
                System.out.println("5. Exit");
                System.out.print("Choose an option: ");
                int choice = sc.nextInt();

                switch (choice) {
                    case 1: // INSERT
                        System.out.print("Enter name: ");
                        String name = sc.next();
                        System.out.print("Enter email: ");
                        String email = sc.next();

                        String insertSQL = "INSERT INTO users (name, email) VALUES (?, ?)";
                        try (PreparedStatement ps = con.prepareStatement(insertSQL)) {
                            ps.setString(1, name);
                            ps.setString(2, email);
                            int rows = ps.executeUpdate();
                            System.out.println(rows + " record(s) inserted.");
                        }
                        break;

                    case 2: // READ
                        String selectSQL = "SELECT * FROM users";
                        try (Statement stmt = con.createStatement();
                             ResultSet rs = stmt.executeQuery(selectSQL)) {
                            System.out.println("\nID | Name | Email");
                            System.out.println("--------------------");
                            while (rs.next()) {
                                System.out.println(rs.getInt("id") + " | " +
                                        rs.getString("name") + " | " +
                                        rs.getString("email"));
                            }
                        }
                        break;

                    case 3: // UPDATE
                        System.out.print("Enter user ID to update: ");
                        int uid = sc.nextInt();
                        System.out.print("Enter new email: ");
                        String newEmail = sc.next();

                        String updateSQL = "UPDATE users SET email=? WHERE id=?";
                        try (PreparedStatement ps = con.prepareStatement(updateSQL)) {
                            ps.setString(1, newEmail);
                            ps.setInt(2, uid);
                            int rows = ps.executeUpdate();
                            System.out.println(rows + " record(s) updated.");
                        }
                        break;

                    case 4: // DELETE
                        System.out.print("Enter user ID to delete: ");
                        int did = sc.nextInt();

                        String deleteSQL = "DELETE FROM users WHERE id=?";
                        try (PreparedStatement ps = con.prepareStatement(deleteSQL)) {
                            ps.setInt(1, did);
                            int rows = ps.executeUpdate();
                            System.out.println(rows + " record(s) deleted.");
                        }
                        break;

                    case 5: // EXIT
                        System.out.println("Exiting program...");
                        return;

                    default:
                        System.out.println("Invalid choice. Try again!");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
