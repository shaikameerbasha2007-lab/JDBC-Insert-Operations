import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseInsert {
    // Database connection details
    private static final String URL = "jdbc:mysql://localhost:3306/testdb";
    private static final String USER = "root";
    private static final String PASSWORD = "password";

    public static void main(String[] args) {
        Connection connection = null;
        Statement statement = null;
        PreparedStatement preparedStatement = null;

        try {
            // Step 1: Load the MySQL JDBC Driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Step 2: Establish the connection
            System.out.println("Connecting to database...");
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("Connection successful!");

            // Step 3: Create table if it doesn't already exist
            String createTableSQL = "CREATE TABLE IF NOT EXISTS students ("
                    + "id INT PRIMARY KEY, "
                    + "name VARCHAR(50), "
                    + "branch VARCHAR(20)"
                    + ")";
            statement = connection.createStatement();
            statement.execute(createTableSQL);
            System.out.println("Table 'students' verified/created.");

            // Step 4: Prepare the parameterized INSERT query
            String insertSQL = "INSERT INTO students (id, name, branch) VALUES (?, ?, ?)";
            preparedStatement = connection.prepareStatement(insertSQL);

            // Record 1
            preparedStatement.setInt(1, 101);
            preparedStatement.setString(2, "Shaik Ameer");
            preparedStatement.setString(3, "CSE");
            int rows1 = preparedStatement.executeUpdate();

            // Record 2
            preparedStatement.setInt(1, 102);
            preparedStatement.setString(2, "Rahul Verma");
            preparedStatement.setString(3, "ECE");
            int rows2 = preparedStatement.executeUpdate();

            System.out.println("Total records inserted: " + (rows1 + rows2));

        } catch (ClassNotFoundException e) {
            System.err.println("JDBC Driver not found. Ensure MySQL Connector JAR is added.");
            e.printStackTrace();
        } catch (SQLException e) {
            System.err.println("Database error: " + e.getMessage());
            e.printStackTrace();
        } finally {
            // Step 5: Close all JDBC resources
            try {
                if (preparedStatement != null) preparedStatement.close();
                if (statement != null) statement.close();
                if (connection != null) connection.close();
                System.out.println("Database resources closed.");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
