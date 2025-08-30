import java.sql.*;
import java.util.Scanner;

public class StudentRecordSystem {
    static final String url = "jdbc:mysql://localhost:3306/StudentsDB";
    static final String user = "root";
    static final String password = "Raji666";

    public static void main(String[] args) {
        try (Connection c = DriverManager.getConnection(url, user, password);
             Scanner sc = new Scanner(System.in)) {  // Scanner inside try block

            System.out.println("Database Connected successfully");
            int choice;
            do {
                System.out.println("------StudentRecordSystem---------");
                System.out.println("1.Add Student");
                System.out.println("2. View All Students");
                System.out.println("3. Update Student");
                System.out.println("4. Delete Student");
                System.out.println("5. Search Student");
                System.out.println("6.Exit");
                System.out.print("Enter your choice: ");
                choice = sc.nextInt();
                sc.nextLine(); // consume leftover newline

                switch (choice) {
                    case 1:
                        addStudent(c, sc);
                        break;
                    case 2:
                        viewStudent(c);
                        break;
                    case 3:
                        updateStudent(c, sc);
                        break;
                    case 4:
                        deleteStudent(c, sc);
                        break;
                    case 5:
                        searchStudent(c, sc);
                        break;
                    case 6:
                        System.out.println("Exiting.....");
                        break;
                    default:
                        System.out.println("Invalid choice!!!");
                        break;
                }
            } while (choice != 6);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void addStudent(Connection c, Scanner sc) throws SQLException {
        System.out.print("Enter name: ");
        String name = sc.nextLine();
        System.out.print("Enter Age: ");
        int age = sc.nextInt();
        sc.nextLine(); // consume leftover newline
        System.out.print("Enter Department: ");
        String dept = sc.nextLine();
        System.out.print("Enter grade: ");
        String grade = sc.nextLine();

        String query = "INSERT INTO Students (name, age, department, grade) VALUES (?, ?, ?, ?)";
        try (PreparedStatement pst = c.prepareStatement(query)) {
            pst.setString(1, name);
            pst.setInt(2, age);
            pst.setString(3, dept);
            pst.setString(4, grade);
            pst.executeUpdate();
            System.out.println("Student added successfully");
        }
    }

    private static void viewStudent(Connection c) throws SQLException {
        String query = "SELECT * FROM Students";
        try (Statement st = c.createStatement();
             ResultSet rs = st.executeQuery(query)) {

            System.out.println("\nID | Name | Age | Department | Grade");
            System.out.println("-----------------------------------------");

            while (rs.next()) {
                System.out.printf("%d | %s | %d | %s | %s%n",
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getInt("age"),
                        rs.getString("department"),
                        rs.getString("grade"));
            }
        }
    }

    private static void updateStudent(Connection c, Scanner sc) throws SQLException {
        System.out.print("Enter Student ID to update: ");
        int id = sc.nextInt();
        sc.nextLine();
        System.out.print("Enter new name: ");
        String name = sc.nextLine();
        System.out.print("Enter age: ");
        int age = sc.nextInt();
        sc.nextLine();
        System.out.print("Enter department: ");
        String dept = sc.nextLine();
        System.out.print("Enter grade: ");
        String grade = sc.nextLine();

        String query = "UPDATE Students SET name=?, age=?, department=?, grade=? WHERE id=?";
        try (PreparedStatement pst = c.prepareStatement(query)) {
            pst.setString(1, name);
            pst.setInt(2, age);
            pst.setString(3, dept);
            pst.setString(4, grade);
            pst.setInt(5, id);
            int rows = pst.executeUpdate();
            if (rows > 0) System.out.println("Student updated successfully");
            else System.out.println("Student ID not found");
        }
    }

    private static void deleteStudent(Connection c, Scanner sc) throws SQLException {
        System.out.print("Enter ID to delete: ");
        int id = sc.nextInt();

        String query = "DELETE FROM Students WHERE id=?";
        try (PreparedStatement pst = c.prepareStatement(query)) {
            pst.setInt(1, id);
            int rows = pst.executeUpdate();
            if (rows > 0) System.out.println("Student ID deleted successfully");
            else System.out.println("Student ID not found");
        }
    }

    private static void searchStudent(Connection c, Scanner sc) throws SQLException {
        System.out.print("Enter Student name to search: ");
        String name = sc.nextLine();

        String query = "SELECT * FROM Students WHERE name LIKE ?";
        try (PreparedStatement pst = c.prepareStatement(query)) {
            pst.setString(1, "%" + name + "%");
            try (ResultSet rs = pst.executeQuery()) {
                System.out.println("\nID | Name | Age | Department | Grade");
                System.out.println("-----------------------------------------");
                boolean found = false;
                while (rs.next()) {
                    found = true;
                    System.out.printf("%d | %s | %d | %s | %s%n",
                            rs.getInt("id"),
                            rs.getString("name"),
                            rs.getInt("age"),
                            rs.getString("department"),
                            rs.getString("grade"));
                }
                if (!found) System.out.println("No Student found with this name.");
            }
        }
    }
}
