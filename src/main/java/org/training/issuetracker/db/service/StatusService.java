package org.training.issuetracker.db.service;

import java.sql.Connection;
import java.sql.SQLException;

import org.training.issuetracker.db.beans.Status;
import org.training.issuetracker.db.beans.User;
import org.training.issuetracker.db.dao.service.StatusDAO;
import org.training.issuetracker.db.enums.Role;
import org.training.issuetracker.db.util.DBManager;

public class StatusService {
	private Connection connection;
	
	public StatusService(User user) 
			throws ClassNotFoundException, SQLException {
		Role role = Role.valueOf(user.getRole().getValue());
		connection = DBManager.getConnection(role);
	}
	

	public void add(Status status) throws Exception {
		try {
			StatusDAO statusDAO = new StatusDAO();
			statusDAO.add(connection, status);	
		} finally {
			DBManager.closeConnection(connection);
		}
	}

	public void update(Status status) throws Exception {
		try {
			StatusDAO statusDAO = new StatusDAO();
			statusDAO.update(connection, status);	
		} finally {
			DBManager.closeConnection(connection);
		}
	}


}
