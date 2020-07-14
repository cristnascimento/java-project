# Java with JDBC MySQL Connector

## Description 

In this tutorial we will show how to connect to a MySQL database and execute simple CRUD operations.

## Dependencies

* Ubuntu 18.04
* Java 8
* MySQL
* MySQL Connector

## Install MySQL Connector

```
$ sudo apt-get install libmysql-java
```

## Set `CLASSPATH`

edit `/etc/environment` and add current dir and mysql connetor to the classpath

```
CLASSPATH=".:/usr/share/java/mysql-connector-java-<version>.jar"
```

## Create a database

```sql
mysql> CREATE DATABASE javadb;
```

## Create a user

```sql
mysql> CREATE USER 'java'@'localhost' IDENTIFIED BY 'java';
mysql> GRANT ALL PRIVILEGES ON javadb . * TO 'java'@'localhost';
mysql> FLUSH PRIVILEGES;
```
## Create table
```sql
mysql> use javadb;
```

```sql
mysql> CREATE TABLE workers (name VARCHAR(50), category VARCHAR(10), salary FLOAT);
```

## Load data into table

select database
```sql
mysql> use javadb;
```

Load data from csv file

```sql
mysql> LOAD DATA LOCAL INFILE '/path/workers.txt' INTO TABLE workers FIELDS TERMINATED BY ',';
```

workers.txt
```txt
John,Python,10500
Peter,Javascript,11200
Mark,Ruby,13000
Anne,PHP,11400
Sammy,Javascript,12800
Alicia,Ruby,17000
Mary,Python,11400
Tina,PHP,9500
Bob,PHP,8000
Kirst,Python,12000
```

or
```sql
mysql> INSERT INTO workers VALUES ('John', 'Python', 10500);
mysql> INSERT INTO workers VALUES ('Peter', 'Javascript', 11200);
mysql> INSERT INTO workers VALUES ('Mark', 'Ruby', 13000);
mysql> INSERT INTO workers VALUES ('Anne', 'PHP', 11400);
mysql> INSERT INTO workers VALUES ('Sammy', 'Javascript', 12800);
mysql> INSERT INTO workers VALUES ('Alicia', 'Ruby', 17000);
mysql> INSERT INTO workers VALUES ('Mary', 'Python', 11400);
mysql> INSERT INTO workers VALUES ('Tina', 'PHP', 9500);
mysql> INSERT INTO workers VALUES ('Bob', 'PHP', 8000);
mysql> INSERT INTO workers VALUES ('Kirst', 'Python', 12000);
```

## Connect

Connect.java
```java
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;

public class Connect {

  public static void main (String[] args) throws ClassNotFoundException, SQLException {

    Connection conn = null;

    String dbClassName = "com.mysql.jdbc.Driver";
    Class.forName(dbClassName);
    conn = DriverManager.getConnection("jdbc:mysql://localhost/javadb?user=java&password=java");

    System.out.println("Connected!");
  }
}

```
compile
```
$ javac Connect.java
```
run
```
$ java Connect
```

## Select

Select.java
```java
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
```

## Insert

Insert.java
```java
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
```

## Update

Update.java
```java
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.PreparedStatement;

public class Update {

  public static void main (String[] args) throws ClassNotFoundException, SQLException {

    Connection conn = null;

    String dbClassName = "com.mysql.jdbc.Driver";
    Class.forName(dbClassName);
    conn = DriverManager.getConnection("jdbc:mysql://localhost/javadb?user=java&password=java");

    ResultSet rs = null;
    
    try {    
      PreparedStatement preparedStatement = 
      conn.prepareStatement("UPDATE workers SET category = ?, salary = ? WHERE name = ?");

      preparedStatement.setString(1, "C++");
      preparedStatement.setFloat(2, 13000);
      preparedStatement.setString(3, "Peter");
      int rowsMatched = preparedStatement.executeUpdate();
      System.out.println("Last udpate: " + rowsMatched);
    }
    catch (SQLException ex){
      // handle any errors
      System.out.println("SQLException: " + ex.getMessage());
      System.out.println("SQLState: " + ex.getSQLState());
      System.out.println("VendorError: " + ex.getErrorCode());
    }
  }
}
```

## Delete

Delete.java
```java
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.PreparedStatement;

public class Update {

  public static void main (String[] args) throws ClassNotFoundException, SQLException {

    Connection conn = null;

    String dbClassName = "com.mysql.jdbc.Driver";
    Class.forName(dbClassName);
    conn = DriverManager.getConnection("jdbc:mysql://localhost/javadb?user=java&password=java");

    ResultSet rs = null;
    
    try {    
      PreparedStatement preparedStatement = 
      conn.prepareStatement("UPDATE workers SET category = ?, salary = ? WHERE name = ?");

      preparedStatement.setString(1, "C++");
      preparedStatement.setFloat(2, 13000);
      preparedStatement.setString(3, "Peter");
      int rowsMatched = preparedStatement.executeUpdate();
      System.out.println("Last udpate: " + rowsMatched);
    }
    catch (SQLException ex){
      // handle any errors
      System.out.println("SQLException: " + ex.getMessage());
      System.out.println("SQLState: " + ex.getSQLState());
      System.out.println("VendorError: " + ex.getErrorCode());
    }
  }
}
```
## References

* [Connector/J Documentation](https://dev.mysql.com/doc/connector-j/5.1/en/connector-j-overview.html)
* [Simple set up and example](https://gist.github.com/craigvantonder/98391eb72f0e23525377ddbd89d607af)

* [Drive Manager](https://dev.mysql.com/doc/connector-j/5.1/en/connector-j-usagenotes-connect-drivermanager.html)

* [About classpath](https://dev.mysql.com/doc/connector-j/5.1/en/connector-j-installing-classpath.html)

* [Hibernate JPA, Spring JPA](https://www.springboottutorial.com/hibernate-jpa-tutorial-with-spring-boot-starter-jpa)
* [Difference between hibernate and spring data jpsa](https://stackoverflow.com/questions/23862994/what-is-the-difference-between-hibernate-and-spring-data-jpa)

* [Java and JDBC](https://www.devmedia.com.br/aprendendo-java-com-jdbc/29116)
