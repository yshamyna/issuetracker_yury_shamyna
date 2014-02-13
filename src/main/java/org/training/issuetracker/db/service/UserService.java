package org.training.issuetracker.db.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import org.training.issuetracker.db.beans.User;
import org.training.issuetracker.db.dao.service.UserDAO;
import org.training.issuetracker.db.enums.Role;
import org.training.issuetracker.db.util.DBManager;

public class UserService {
	private Connection connection;

	public UserService(User user) 
			throws ClassNotFoundException, SQLException {
		Role role;
		if (user == null) {
			role = Role.GUEST;
		} else {
			role = Role.valueOf(user.getRole().getName().toUpperCase());	
		}
		connection = DBManager.getConnection(role);
	}
	
	public void add(User user) throws Exception {
		try {
			UserDAO userDAO = new UserDAO();
			userDAO.add(connection, user);
		} finally {
			connection.close();
		}
	}

	public void update(User user) throws SQLException {
		try {
			UserDAO userDAO = new UserDAO();
			userDAO.update(connection, user);
		} finally {
			connection.close();
		}
	}
	
	public List<User> getUsers() throws SQLException {
		try {
			UserDAO userDAO = new UserDAO();
			return userDAO.all(connection);	
		} finally {
			DBManager.closeConnection(connection);	
		}
	}
	
	public User getUserById(long userId) throws SQLException {
		try {
			UserDAO userDAO = new UserDAO();
			return userDAO.getById(connection, userId);
		} finally {
			DBManager.closeConnection(connection);
		}
	}
	
	public void changePassword(User user, String password) throws SQLException {
		try {
			UserDAO userDAO = new UserDAO();
			userDAO.updatePassword(connection, user, password);
		} finally {
			DBManager.closeConnection(connection);
		}
	}
	
	public User getUser(String email, String password) throws SQLException {
		try {
			UserDAO userDAO = new UserDAO();
			return userDAO.getByEmailAndPassword(connection, email, password);
		} finally {
			DBManager.closeConnection(connection);
		}
	}
}
