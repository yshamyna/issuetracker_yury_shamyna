package org.training.issuetracker.db.dao.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.training.issuetracker.db.beans.Attachment;
import org.training.issuetracker.db.beans.User;
import org.training.issuetracker.db.dao.service.constants.FieldsConstans;
import org.training.issuetracker.db.dao.service.constants.QueriesConstants;
import org.training.issuetracker.db.util.DBManager;

public class AttachmentDAO  {

	public List<Attachment> allByIssueId(Connection connection, long issueId) 
					throws SQLException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = connection.prepareStatement(QueriesConstants.
										ATTACHMENT_SELECT_BY_ISSUE_ID);
			ps.setLong(1, issueId);
			rs = ps.executeQuery();
			List<Attachment> attachments = new ArrayList<Attachment>();
			Attachment attachment = null;
			User addedBy = null;
			while (rs.next()) {
				addedBy = new User();
				addedBy.setFirstName(rs.getString(FieldsConstans.FIRST_NAME));
				addedBy.setLastName(rs.getString(FieldsConstans.LAST_NAME));
				addedBy.setId(rs.getLong(FieldsConstans.ADDED_BY));
				
				attachment = new Attachment();
				attachment.setId(rs.getLong(FieldsConstans.ATTACHMENT_ID_ALIAS));
				attachment.setFilename(rs.getString(FieldsConstans.FILENAME));
				attachment.setIssueId(issueId);
				attachment.setAddedBy(addedBy);
				attachment.setAddDate(rs.getTimestamp(FieldsConstans.ADD_DATE));
				
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
			ps = connection.prepareStatement(QueriesConstants.ATTACHMENT_ADD);
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
