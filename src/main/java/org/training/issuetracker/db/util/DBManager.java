package org.training.issuetracker.db.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.training.issuetracker.db.enums.Role;

public class DBManager {
	private static final String DRIVER = "org.h2.Driver";
	private static final String DB_URL = "jdbc:h2:issuetracker";
	private static final String GUEST = "guest";
	private static final String GUEST_PASSWORD = "Issue_tracker_guest";
	private static final String USER = "User";
	private static final String USER_PASSWORD = "Issue_tracker_user";
	private static final String ADMINISTRATOR = "administrator";
	private static final String ADMINISTRATOR_PASSWORD = 
					"Issue_tracker_administrator";
	
	public static Connection getConnection(Role role) 
			throws ClassNotFoundException, SQLException {
		Class.forName(DRIVER);
        Connection connection = null;
        switch (role) {
        	case GUEST:
        		connection = DriverManager.
                		getConnection(DB_URL, GUEST, GUEST_PASSWORD);
        		break;
        	case USER: 
        		connection = DriverManager.
        				getConnection(DB_URL, USER, USER_PASSWORD);
        		break;
        	case ADMINISTRATOR:
        		connection = DriverManager.
        			getConnection(DB_URL, ADMINISTRATOR, ADMINISTRATOR_PASSWORD);
        		break;
        }
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
