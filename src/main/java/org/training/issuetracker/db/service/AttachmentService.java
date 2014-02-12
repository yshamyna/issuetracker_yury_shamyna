package org.training.issuetracker.db.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import org.training.issuetracker.db.beans.Attachment;
import org.training.issuetracker.db.beans.User;
import org.training.issuetracker.db.dao.service.AttachmentDAO;
import org.training.issuetracker.db.enums.Role;
import org.training.issuetracker.db.util.DBManager;

public class AttachmentService {
	private Connection connection;
	
	public AttachmentService(User user) 
			throws ClassNotFoundException, SQLException {
		Role role;
		if (user == null) {
			role = Role.GUEST;
		} else {
			role = Role.valueOf(user.getRole().getName().toUpperCase());	
		}
		connection = DBManager.getConnection(role);
	}

	public void add(Attachment attachment) throws Exception {
		try {
			AttachmentDAO attachmentDAO = new AttachmentDAO();
			attachmentDAO.add(connection, attachment);
		} finally {
			DBManager.closeConnection(connection);
		}
	}
	
	public List<Attachment> getAttachmentsByIssueId(long issueId) throws SQLException {
		try {
			AttachmentDAO attachmentDAO = new AttachmentDAO();
			return attachmentDAO.allByIssueId(connection, issueId);	
		} finally {
			DBManager.closeConnection(connection);	
		}
	}
	
	

}
