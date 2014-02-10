package org.training.issuetracker.db.service;

import java.sql.Connection;
import java.sql.SQLException;

import org.training.issuetracker.db.beans.Priority;
import org.training.issuetracker.db.beans.User;
import org.training.issuetracker.db.dao.service.PriorityDAO;
import org.training.issuetracker.db.enums.Role;
import org.training.issuetracker.db.util.DBManager;

public class PriorityService {

	private Connection connection;
	
	public PriorityService(User user) 
			throws ClassNotFoundException, SQLException {
		Role role = Role.valueOf(user.getRole().getValue());
		connection = DBManager.getConnection(role);
	}

	public void add(Priority priority) throws Exception {
		try {
			PriorityDAO priorityDAO = new PriorityDAO();
			priorityDAO.add(connection, priority);
		} finally {
			DBManager.closeConnection(connection);
		}
	}

	public void update(Priority priority) throws Exception {
		try {
			PriorityDAO priorityDAO = new PriorityDAO();
			priorityDAO.update(connection, priority);
		} finally {
			DBManager.closeConnection(connection);
		}
	}
}
