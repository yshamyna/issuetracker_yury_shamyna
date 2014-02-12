package org.training.issuetracker.db.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import org.training.issuetracker.db.beans.Resolution;
import org.training.issuetracker.db.beans.User;
import org.training.issuetracker.db.dao.service.ResolutionDAO;
import org.training.issuetracker.db.enums.Role;
import org.training.issuetracker.db.util.DBManager;

public class ResolutionService {
	private Connection connection;
	
	public ResolutionService(User user) 
			throws ClassNotFoundException, SQLException {
		Role role;
		if (user == null) {
			role = Role.GUEST;
		} else {
			role = Role.valueOf(user.getRole().getName().toUpperCase());	
		}
		connection = DBManager.getConnection(role);
	}

	public void add(Resolution resolution) throws SQLException {
		try {
			ResolutionDAO resolutionDAO = new ResolutionDAO();
			resolutionDAO.add(connection, resolution);
		} finally {
			DBManager.closeConnection(connection);
		}
	}

	public void update(Resolution resolution) throws SQLException {
		try {
			ResolutionDAO resolutionDAO = new ResolutionDAO();
			resolutionDAO.update(connection, resolution);
		} finally {
			DBManager.closeConnection(connection);
		}
	}
	
	public Resolution getResolutionById(long resolutionId) throws SQLException {
		try {
			ResolutionDAO resolutionDAO = new ResolutionDAO();
			return resolutionDAO.getById(connection, resolutionId);	
		} finally {
			DBManager.closeConnection(connection);	
		}
	}
	
	public List<Resolution> getResolutions() throws SQLException {
		try {
			ResolutionDAO resolutionDAO = new ResolutionDAO();
			return resolutionDAO.all(connection);	
		} finally {
			DBManager.closeConnection(connection);	
		}
	}
}
