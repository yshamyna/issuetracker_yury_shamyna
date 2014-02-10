package org.training.issuetracker.db.service;

import java.sql.Connection;
import java.sql.SQLException;

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
	
	public void add(Project project, Build build) throws Exception {
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

	public void update(Project project, Build newBuild) throws Exception {
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

}
