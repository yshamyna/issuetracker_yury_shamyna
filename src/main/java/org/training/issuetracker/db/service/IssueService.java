package org.training.issuetracker.db.service;

import java.sql.Connection;
import java.sql.SQLException;

import org.training.issuetracker.db.beans.Issue;
import org.training.issuetracker.db.beans.User;
import org.training.issuetracker.db.dao.service.IssueDAO;
import org.training.issuetracker.db.enums.Role;
import org.training.issuetracker.db.util.DBManager;

public class IssueService {
	private Connection connection;
	
	public IssueService(User user) 
			throws ClassNotFoundException, SQLException {
		Role role = Role.valueOf(user.getRole().getValue());
		connection = DBManager.getConnection(role);
	}

	public void add(Issue issue) throws Exception {
		try {
			IssueDAO issueDAO = new IssueDAO();
			issueDAO.add(connection, issue);	
		} finally {
			DBManager.closeConnection(connection);	
		}
	}

	public void update(Issue issue) throws Exception {
		try {
			IssueDAO issueDAO = new IssueDAO();
			issueDAO.update(connection, issue);	
		} finally {
			DBManager.closeConnection(connection);	
		}
	}
}
