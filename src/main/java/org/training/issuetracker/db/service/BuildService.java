package org.training.issuetracker.db.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import org.training.issuetracker.db.beans.Build;
import org.training.issuetracker.db.beans.User;
import org.training.issuetracker.db.dao.service.BuildDAO;
import org.training.issuetracker.db.enums.Role;
import org.training.issuetracker.db.util.DBManager;

public class BuildService {
	
	private Connection connection;
	public BuildService(User user) 
			throws ClassNotFoundException, SQLException {
		Role role = Role.valueOf(user.getRole().getValue());
		connection = DBManager.getConnection(role);
	}


	public void add(Build build) throws SQLException {
		try {
			BuildDAO buildDAO = new BuildDAO();
			buildDAO.add(connection, build);
		} finally {
			DBManager.closeConnection(connection);
		}
	}

	public List<Build> getBuildsByProjectId(long projectId) throws SQLException {
		try {
			BuildDAO buildDAO = new BuildDAO();
			return buildDAO.allByProjectId(connection, projectId);	
		} finally {
			DBManager.closeConnection(connection);	
		}
	}

}
