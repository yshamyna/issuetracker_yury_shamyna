package org.training.issuetracker.db.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import org.training.issuetracker.db.beans.Issue;
import org.training.issuetracker.db.beans.User;
import org.training.issuetracker.db.dao.service.IssueDAO;
import org.training.issuetracker.db.enums.Role;
import org.training.issuetracker.db.util.DBManager;

public class IssueService {
	private Connection connection;
	
	public IssueService(User user) 
			throws ClassNotFoundException, SQLException {
		Role role;
		if (user == null) {
			role = Role.GUEST;
		} else {
			role = Role.valueOf(user.getRole().getName().toUpperCase());	
		}
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
	
	public Issue getIssueById(long id) throws SQLException {
		IssueDAO issueDAO = new IssueDAO();
		return issueDAO.getById(connection, id);
	}
	
	public List<Issue> getLastNIssues(long n) throws SQLException {
		try {
			IssueDAO issueDAO = new IssueDAO();
			return issueDAO.getLastNRecords(connection, n);	
		} finally {
			DBManager.closeConnection(connection);	
		}
	}
	
	public List<Issue> getIssues(User user, long pageNumber,
						long recordsPerPage) throws SQLException {
		try {
			IssueDAO issueDAO = new IssueDAO();
			return issueDAO.getNRecordsFromPageM(connection, 
					user, pageNumber, recordsPerPage);	
		} finally {
			DBManager.closeConnection(connection);	
		}
	}
	
	public long getQuantityPages(long userId, long recordsPerPage) 
			throws SQLException {
		try {
			IssueDAO issueDAO = new IssueDAO();
			return issueDAO.getQuantityPages(connection, userId, recordsPerPage);	
		} finally {
			DBManager.closeConnection(connection);	
		}
	}
}
