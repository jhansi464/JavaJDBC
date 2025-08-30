import java.sql.*;

public class LearnJDBC {
    public static void main(String[] args) {
        // JDBC URL, username and password of MySQL server
        String url = "jdbc:mysql://localhost:3306/Raji1"; // DB name = Raji1
        String user = "root";  // your MySQL username
        String password = "Raji666"; // your MySQL password

        // JDBC variables
        Connection con = null;
        Statement stmt = null;
        ResultSet rs = null;

        try {
            // 1. Load and register JDBC driver (optional for latest versions)
            Class.forName("com.mysql.cj.jdbc.Driver");

            // 2. Open a connection
            con = DriverManager.getConnection(url, user, password);
            System.out.println("✅ Connected to database successfully!");

            // 3. Create statement
            stmt = con.createStatement();

            // 4. Execute query
            String query = "SELECT * FROM employees";  // <-- table name
            rs = stmt.executeQuery(query);

            // 5. Process the result set
            System.out.println("Employee Data:");
            System.out.println("-------------------------------------------------------");
            while (rs.next()) {
                int id = rs.getInt("emp_id");
                String fname = rs.getString("first_name");
                String lname = rs.getString("last_name");
                String email = rs.getString("email");
                String dept = rs.getString("department");
                double salary = rs.getDouble("salary");

                System.out.println(id + " | " + fname + " " + lname + " | " + email + " | " + dept + " | " + salary);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 6. Close resources
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
                if (con != null) con.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
    }
}
