package org.training.issuetracker.db.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import org.training.issuetracker.db.beans.User;
import org.training.issuetracker.db.beans.UserRole;
import org.training.issuetracker.db.dao.service.RoleDAO;
import org.training.issuetracker.db.enums.Role;
import org.training.issuetracker.db.util.DBManager;

public class RoleService {

	private Connection connection;
	
	public RoleService(User user) 
			throws ClassNotFoundException, SQLException {
		Role role;
		if (user == null) {
			role = Role.GUEST;
		} else {
			role = Role.valueOf(user.getRole().getName().toUpperCase());	
		}
		connection = DBManager.getConnection(role);
	}
	
	public List<UserRole> getRoles() throws SQLException {
		try {
			RoleDAO roleDAO = new RoleDAO();
			return roleDAO.all(connection);
		} finally {
			DBManager.closeConnection(connection);
		}
	}
}
