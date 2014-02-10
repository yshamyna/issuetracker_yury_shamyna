package org.training.issuetracker.db.service;

import java.sql.Connection;
import java.sql.SQLException;

import org.training.issuetracker.db.beans.Comment;
import org.training.issuetracker.db.beans.User;
import org.training.issuetracker.db.dao.service.CommentDAO;
import org.training.issuetracker.db.enums.Role;
import org.training.issuetracker.db.util.DBManager;

public class CommentService {
	private Connection connection;
	
	public CommentService(User user) 
			throws ClassNotFoundException, SQLException {
		Role role = Role.valueOf(user.getRole().getValue());
		connection = DBManager.getConnection(role);
	}

	public void add(Comment comment) throws Exception {
		try {
			CommentDAO commentDAO = new CommentDAO();
			commentDAO.add(connection, comment);
		} finally {
			DBManager.closeConnection(connection);
		}
	}

}
