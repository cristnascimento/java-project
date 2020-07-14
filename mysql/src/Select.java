import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.PreparedStatement;

public class Select {

  public static void main (String[] args) throws ClassNotFoundException, SQLException {

    Connection conn = null;

    String dbClassName = "com.mysql.jdbc.Driver";
    Class.forName(dbClassName);
    conn = DriverManager.getConnection("jdbc:mysql://localhost/javadb?user=java&password=java");

    Statement stmt = null;
    ResultSet rs = null;
    
    try {    
      // Select All
      stmt = conn.createStatement();
      rs = stmt.executeQuery("SELECT * FROM workers");

      while (rs.next()) {
        String name = rs.getString("name");
        String category = rs.getString("category");
        float salary = rs.getFloat("salary");
        System.out.println("{name: " + name + ", category: " +category+" ,salary: "+salary);
        System.out.println("---------------------------------------");
      }

      // Select One

      PreparedStatement preparedStatement = 
      conn.prepareStatement("SELECT * FROM workers WHERE name = ?");
      preparedStatement.setString(1, "Mark");
      rs = preparedStatement.executeQuery();

      while (rs.next()) {
        String name = rs.getString("name");
        String category = rs.getString("category");
        float salary = rs.getFloat("salary");
        System.out.println("{name: " + name + ", category: " +category+", salary: "+salary);
        System.out.println("---------------------------------------");
      }
    }
    catch (SQLException ex){
      // handle any errors
      System.out.println("SQLException: " + ex.getMessage());
      System.out.println("SQLState: " + ex.getSQLState());
      System.out.println("VendorError: " + ex.getErrorCode());
    }
  }
}
