package org.training.issuetracker.db.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

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

	public void add(Priority priority) throws SQLException {
		try {
			PriorityDAO priorityDAO = new PriorityDAO();
			priorityDAO.add(connection, priority);
		} finally {
			DBManager.closeConnection(connection);
		}
	}

	public void update(Priority priority) throws SQLException {
		try {
			PriorityDAO priorityDAO = new PriorityDAO();
			priorityDAO.update(connection, priority);
		} finally {
			DBManager.closeConnection(connection);
		}
	}
	
	public List<Priority> getPriorities() throws SQLException {
		try {
			PriorityDAO priorityDAO = new PriorityDAO();
			return priorityDAO.all(connection);	
		} finally {
			DBManager.closeConnection(connection);	
		}
	}
	
	public Priority getPriorityById(long priorityId) throws SQLException {
		try {
			PriorityDAO priorityDAO = new PriorityDAO();
			return priorityDAO.getById(connection, priorityId);	
		} finally {
			DBManager.closeConnection(connection);	
		}
	}
}
