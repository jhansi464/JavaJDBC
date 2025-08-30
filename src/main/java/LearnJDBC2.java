import java.math.BigDecimal;
import java.sql.*;
public class LearnJDBC2 {
    public static void main(String[] args) {
        String url="jdbc:mysql://localhost:3306/Raji2";
        String user="root";
        String password="Raji666";
        Connection c= null;
        Statement st=null;
        ResultSet rs=null;
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            c=DriverManager.getConnection(url,user,password);
            System.out.println("Database Connected successfully!!!");
            st=c.createStatement();
            String query="Select * from Students1";
            rs=st.executeQuery(query);
            System.out.println("Employee Data:");
            System.out.println("---------------------------------------------------");
            while(rs.next()){
                int id=rs.getInt("id");
                String name=rs.getString("name");
                int age=rs.getInt("age");
                BigDecimal salary=rs.getBigDecimal("salary");
                System.out.println(id+" |  "+name+"  |  "+age+"  |  "+salary);
            }
        }catch(Exception e){
            e.printStackTrace();
        }finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (st != null) {
                    st.close();
                }
                if (c != null) {
                    c.close();
                }
            }catch (SQLException se){
                se.printStackTrace();
            }
        }
    }
}
