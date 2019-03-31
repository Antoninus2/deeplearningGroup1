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

import com.microsoft.sqlserver.jdbc.SQLServerException;

public class SQLConnection {
	
    // Connect to the egs database.
    public String connect() {
    	String connectionUrl =
                "jdbc:sqlserver://egs.database.windows.net:1433;"
                        + "database=egs;"
                        + "user=egs;"
                        + "password=Grading2019!;"
                        + "encrypt=true;"
                        + "trustServerCertificate=false;"
                        + "loginTimeout=30;";
    	return connectionUrl;
    }
    
    // not done
    public double[][] pullWeights1() {
    	
    	
    	String connectionurl = connect();
    	double[][] Weights1 = new double[4][3];
    	
        ResultSet resultSet;
        try (Connection connection = DriverManager.getConnection(connectionurl);
	        	Statement statement = connection.createStatement();) {

	            // Updates the account with the new password when they request it
	            String selectSql = "select*from dbo.Weights1"; //dbo.Weights1
	        	resultSet = statement.executeQuery(selectSql);
	        	
	        	for (int i=1;resultSet.next();i++) {
	            	String col1 = resultSet.getString(1);
	            	String col2 = resultSet.getString(2);
	            	String col3 = resultSet.getString(3);
	            	Weights1[i][1] = Double.parseDouble(col1);
	            	Weights1[i][2] = Double.parseDouble(col2);
	            	Weights1[i][3] = Double.parseDouble(col3);
	        	}
	        	
        } catch (SQLServerException e) {
            e.printStackTrace();
        } catch (SQLException e) {
    		e.printStackTrace();
    	}
    	
        return Weights1;
    	
    }
    
    public void saveWeights1(double Weights1[][]) {
    	
    	String connectionurl = connect();
    	
        ResultSet resultSet;
        try (Connection connection = DriverManager.getConnection(connectionurl);
	        	Statement statement = connection.createStatement();) {

	            // Updates the account with the new password when they request it
        	for (int i=1;i<5;i++) {
        		String string1 = "'" +Weights1[i][1]+ "'";
        		String string2 = "'" +Weights1[i][2]+ "'";
        		String string3 = "'" +Weights1[i][3]+ "'";
	            String selectSql = "insert into dbo.Weights1(col1,col2,col3) values (" +string1+ "," +string2+ "," +string3+ ")"; //dbo.Weights1
	        	resultSet = statement.executeQuery(selectSql);
        	}
	       
	        	
        } catch (SQLServerException e) {
            e.printStackTrace();
        } catch (SQLException e) {
    		e.printStackTrace();
    	}
        
    }
    
    /*public void saveData (double[][] data, String table) {
    	
    }*/
}