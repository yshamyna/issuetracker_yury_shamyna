package org.training.issuetracker.db.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import org.training.issuetracker.db.beans.Comment;
import org.training.issuetracker.db.beans.User;
import org.training.issuetracker.db.dao.service.CommentDAO;
import org.training.issuetracker.db.enums.Role;
import org.training.issuetracker.db.util.DBManager;

public class CommentService {
	private Connection connection;
	
	public CommentService(User user) 
			throws ClassNotFoundException, SQLException {
		Role role;
		if (user == null) {
			role = Role.GUEST;
		} else {
			role = Role.valueOf(user.getRole().getName().toUpperCase());	
		}
		connection = DBManager.getConnection(role);
	}

	public void add(Comment comment) throws SQLException {
		try {
			CommentDAO commentDAO = new CommentDAO();
			commentDAO.add(connection, comment);
		} finally {
			DBManager.closeConnection(connection);
		}
	}
	
	public List<Comment> getCommentsByIssueId(long issueId) throws SQLException {
		try {
			CommentDAO commentDAO = new CommentDAO();
			return commentDAO.allByIssueId(connection, issueId);	
		} finally {
			DBManager.closeConnection(connection);	
		}
	}

}
