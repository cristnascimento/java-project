import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.PreparedStatement;

public class Delete {

  public static void main (String[] args) throws ClassNotFoundException, SQLException {

    Connection conn = null;

    String dbClassName = "com.mysql.jdbc.Driver";
    Class.forName(dbClassName);
    conn = DriverManager.getConnection("jdbc:mysql://localhost/javadb?user=java&password=java");

    ResultSet rs = null;
    
    try {    
      PreparedStatement preparedStatement = 
      conn.prepareStatement("DELETE FROM workers WHERE name = ?");

      preparedStatement.setString(1, "Mark");
      int rowsMatched = preparedStatement.executeUpdate();

      System.out.println("Last insert: " + rowsMatched);
    }
    catch (SQLException ex){
      // handle any errors
      System.out.println("SQLException: " + ex.getMessage());
      System.out.println("SQLState: " + ex.getSQLState());
      System.out.println("VendorError: " + ex.getErrorCode());
    }
  }
}