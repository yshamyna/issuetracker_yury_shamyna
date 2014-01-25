package org.training.issuetracker.db.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DBManager {
	public static Connection getConnsection() 
			throws ClassNotFoundException, SQLException {
		Class.forName("org.h2.Driver");
        Connection connection = DriverManager.
        		getConnection("jdbc:h2:issuetracker", "guest", 
        				"Issue_tracker_administrator");
		return connection;
	}
	
	public static void closeConnection(Connection connection) throws SQLException{
		if (connection != null) {
			connection.close();
		}
	}
	
	public static void closeStatements(Statement... statements) throws SQLException {
		for (Statement statement : statements) {
			if (statement != null) {
				statement.close();
			}
		}
	}
	
	public static void closeResultSets(ResultSet...  resultSets) throws SQLException {
		for (ResultSet rs : resultSets) {
			if (rs != null) {
				rs.close();
			}
		}
	}
}
