package org.training.issuetracker.db.dao.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.training.issuetracker.db.beans.Attachment;
import org.training.issuetracker.db.beans.User;
import org.training.issuetracker.db.util.DBManager;

public class AttachmentDAO  {

	public List<Attachment> allByIssueId(Connection connection, long issueId) 
					throws SQLException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = connection.prepareStatement("select attachments.id as aid, addedBy, firstName, lastName, addDate, filename from attachments, users where addedBy=users.id and issueId=?");
			ps.setLong(1, issueId);
			rs = ps.executeQuery();
			List<Attachment> attachments = new ArrayList<Attachment>();
			Attachment attachment = null;
			User addedBy = null;
			while (rs.next()) {
				addedBy = new User();
				addedBy.setFirstName(rs.getString("firstName"));
				addedBy.setLastName(rs.getString("lastName"));
				addedBy.setId(rs.getLong("addedBy"));
				
				attachment = new Attachment();
				attachment.setId(rs.getLong("aid"));
				attachment.setFilename(rs.getString("filename"));
				attachment.setIssueId(issueId);
				attachment.setAddedBy(addedBy);
				attachment.setAddDate(rs.getTimestamp("addDate"));
				
				attachments.add(attachment);
			}
			return attachments;
		} finally {
			DBManager.closeResultSets(rs);
			DBManager.closeStatements(ps);
		}
	}

	public void add(Connection connection, Attachment attachment) 
				throws SQLException {
		PreparedStatement ps = null;
		try {
			ps = connection.prepareStatement("insert into attachments(addedBy, addDate, filename, issueId) values(?,?,?,?)");
			ps.setLong(1, attachment.getAddedBy().getId());
			ps.setTimestamp(2, attachment.getAddDate());
			ps.setString(3, attachment.getFilename());
			ps.setLong(4, attachment.getIssueId());
			
			ps.executeUpdate();
		} finally {
			DBManager.closeStatements(ps);
		}
	}
}
