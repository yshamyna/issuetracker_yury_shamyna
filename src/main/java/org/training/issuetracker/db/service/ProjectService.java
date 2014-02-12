package org.training.issuetracker.db.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import org.training.issuetracker.db.beans.Build;
import org.training.issuetracker.db.beans.Project;
import org.training.issuetracker.db.beans.User;
import org.training.issuetracker.db.dao.service.BuildDAO;
import org.training.issuetracker.db.dao.service.ProjectDAO;
import org.training.issuetracker.db.enums.Role;
import org.training.issuetracker.db.util.DBManager;

public class ProjectService {
	private Connection connection;
	
	public ProjectService(User user) 
			throws ClassNotFoundException, SQLException {
		Role role = Role.valueOf(user.getRole().getValue());
		connection = DBManager.getConnection(role);
	}
	
	public void add(Project project, Build build) throws SQLException {
		try {
			ProjectDAO projectDAO = new ProjectDAO();
			long projectId = projectDAO.add(connection, project);	

			build.setProjectId(projectId);
			
			BuildDAO buildDAO = new BuildDAO();
			buildDAO.add(connection, build);
		} finally {
			DBManager.closeConnection(connection);
		}
	}

	public void update(Project project, Build newBuild) throws SQLException {
		try {
			ProjectDAO projectDAO = new ProjectDAO();
			projectDAO.update(connection, project);	
			
			BuildDAO buildDAO = new BuildDAO();
			Build currentBuild = buildDAO.getCurrentBuild(connection, 
					project.getId());
			if (currentBuild.getId() != newBuild.getId()) {
				buildDAO.changeVersion(connection, currentBuild.getId(), 
						newBuild.getId());
			}
		} finally {
			DBManager.closeConnection(connection);
		}
	}
	
	public List<Project> getProjects() throws SQLException {
		try {
			ProjectDAO projectDAO = new ProjectDAO();
			return projectDAO.all(connection);	
		} finally {
			DBManager.closeConnection(connection);	
		}
	}
	
	public Project getProjectById(long projectId) throws SQLException {
		try {
			ProjectDAO projectDAO = new ProjectDAO();
			return projectDAO.getById(connection, projectId);	
		} finally {
			DBManager.closeConnection(connection);	
		}
	}
	
	public List<Project> getProjects(long pageNumber, long recordsPerPage) 
								throws SQLException {
		try {
			ProjectDAO projectDAO = new ProjectDAO();
			return projectDAO.getNRecordsFromPageM(connection, 
									pageNumber, recordsPerPage);	
		} finally {
			DBManager.closeConnection(connection);	
		}
	}
	
	public long getQuantityPages(long recordsPerPage) throws SQLException {
		try {
			ProjectDAO projectDAO = new ProjectDAO();
			return projectDAO.getQuantityPages(connection, recordsPerPage);	
		} finally {
			DBManager.closeConnection(connection);	
		}
	}

}
