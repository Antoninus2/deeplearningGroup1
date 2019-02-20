// Name: Timothy Mitchell
// Project: Essay Grading System - SE 300
// Group Members: Jimmy, Steven, Nino, Lauren, and Gurvir
// Date: 02/19/19
// Credit: Microsoft SQL Server Tutorial


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class SQLConnection {
    // Connect to the egs database.
    public static void main(String[] args) {
        String connectionUrl =
                "jdbc:sqlserver://egs.database.windows.net:1433;"
                        + "database=egs;"
                        + "user=egs;"
                        + "password=Grading2019!;"
                        + "encrypt=true;"
                        + "trustServerCertificate=false;"
                        + "loginTimeout=30;";
        
        ResultSet resultSet = null;

        try (Connection connection = DriverManager.getConnection(connectionUrl);
        	Statement statement = connection.createStatement();) {

            // Do a select statement that shows the entire table
            String selectSql = "SELECT * FROM dbo.Essays";
        	resultSet = statement.executeQuery(selectSql);
        
        	// Print results from select statement for all five columns
        	while (resultSet.next()) {
            	System.out.println(resultSet.getString(1) + " " + resultSet.getString(2) + " " + 
            		resultSet.getString(3) + " " + resultSet.getString(4) + " " + resultSet.getString(5));
        	}
        	
        }
        // Handle any errors that may have occurred.
        catch (SQLException e) {
            e.printStackTrace();
        }
    }
}