package org.training.issuetracker.db.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.training.issuetracker.db.enums.Role;

public class DBManager {
	public static Connection getConnection(Role role) 
			throws ClassNotFoundException, SQLException {
		Class.forName("org.h2.Driver");
        Connection connection = null;
        switch (role) {
        	case GUEST:
        		connection = DriverManager.
                		getConnection("jdbc:h2:issuetracker", "guest", 
                				"Issue_tracker_guest");
        		break;
        	case USER: 
        		connection = DriverManager.
        		getConnection("jdbc:h2:issuetracker", "guest", 
        				"Issue_tracker_user");
        		break;
        	case ADMINISTRATOR:
        		connection = DriverManager.
        		getConnection("jdbc:h2:issuetracker", "administrator", 
        				"Issue_tracker_administrator");
        		break;
        }
		return connection;
	}
	
	public static Connection getConnection() 
			throws ClassNotFoundException, SQLException {
		Class.forName("org.h2.Driver");
        Connection connection = DriverManager.
        		getConnection("jdbc:h2:issuetracker", "administrator", 
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
