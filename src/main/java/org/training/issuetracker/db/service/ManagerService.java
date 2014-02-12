package org.training.issuetracker.db.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import org.training.issuetracker.db.beans.Manager;
import org.training.issuetracker.db.beans.User;
import org.training.issuetracker.db.dao.service.ManagerDAO;
import org.training.issuetracker.db.enums.Role;
import org.training.issuetracker.db.util.DBManager;

public class ManagerService {

	private Connection connection;
	
	public ManagerService(User user) 
			throws ClassNotFoundException, SQLException {
		Role role = Role.valueOf(user.getRole().getValue());
		connection = DBManager.getConnection(role);
	}
	
	public List<Manager> getManagers() throws SQLException {
		try {
			ManagerDAO managerDAO = new ManagerDAO();
			return managerDAO.all(connection);
		} finally {
			DBManager.closeConnection(connection);
		}
	}
}
