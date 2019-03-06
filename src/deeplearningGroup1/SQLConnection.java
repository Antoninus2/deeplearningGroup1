package deeplearningGroup1;

/**
 * @author Timothy Mitchell
 *
 */


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class SQLConnection {
	
	public static void main(String[] args) {
		connect();
	}
	
    // Connect to the egs database.
    public static void connect() {
        String connectionUrl =
                "jdbc:sqlserver://database.database.windows.net:1433;"
                        + "database=egs;"
                        + "user=user;"
                        + "password=password;"
                        + "encrypt=true;"
                        + "trustServerCertificate=false;"
                        + "loginTimeout=30;";
        
        ResultSet resultSet = null;

        try (Connection connection = DriverManager.getConnection(connectionUrl);
        	Statement statement = connection.createStatement();) {

            // Do a select statement that shows the entire table
            String selectSql = "SELECT * FROM dbo.User_Info"; //dbo.Essays
        	resultSet = statement.executeQuery(selectSql);
        
        	// Print results from select statement for all five columns
        	while (resultSet.next()) {
            	System.out.println(resultSet.getString(1) + " | " + resultSet.getString(2) + " | " + 
            		resultSet.getString(3) + " | " + resultSet.getString(4) + " | " + resultSet.getString(5));
        	}
        	
        }
        // Handle any errors that may have occurred.
        catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public void saveData (double[][] data, String table) {
    	
    }
}