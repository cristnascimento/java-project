import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.PreparedStatement;

public class Insert {

  public static void main (String[] args) throws ClassNotFoundException, SQLException {

    Connection conn = null;

    String dbClassName = "com.mysql.jdbc.Driver";
    Class.forName(dbClassName);
    conn = DriverManager.getConnection("jdbc:mysql://localhost/javadb?user=java&password=java");

    ResultSet rs = null;
    
    try {    
      PreparedStatement preparedStatement = 
      conn.prepareStatement("INSERT INTO workers VALUES (?, ?, ?)");

      preparedStatement.setString(1, "Ben");
      preparedStatement.setString(2, "C++");
      preparedStatement.setFloat(3, 13000);
      preparedStatement.executeUpdate();

      preparedStatement.setString(1, "William");
      preparedStatement.setString(2, "Perl");
      preparedStatement.setFloat(3, 11800);
      preparedStatement.executeUpdate();

      preparedStatement.setString(1, "Tiffany");
      preparedStatement.setString(2, "C++");
      preparedStatement.setFloat(3, 14000);
      preparedStatement.executeUpdate();

      preparedStatement.setString(1, "Viola");
      preparedStatement.setString(2, "C++");
      preparedStatement.setFloat(3, 13400);
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