package org.training.issuetracker.db.dao.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.training.issuetracker.db.beans.Comment;
import org.training.issuetracker.db.util.DBManager;

public class CommentDAO {

	public List<Comment> allByIssueId(Connection connection, long issueId) 
				throws SQLException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
	        ps = connection.prepareStatement("select id, sender, comment, createDate from comments where issueId=?");
	        ps.setLong(1, issueId);
	        rs = ps.executeQuery();
	        List<Comment> comments = new ArrayList<Comment>();
	        Comment comment = null;
	        UserDAO userDAO = new UserDAO();
	        while (rs.next()) {
	        	comment = new Comment();
	        	comment.setId(rs.getLong("id"));
	        	comment.setComment(rs.getString("comment"));
	        	comment.setIssueId(issueId);
	        	comment.setCreateDate(rs.getTimestamp("createDate"));
	        	comment.setSender(userDAO.getById(connection, rs.getLong("sender")));
	        	comments.add(comment);
	        }
	        return comments;
		} finally {
			DBManager.closeResultSets(rs);
			DBManager.closeStatements(ps);
		}
	}

	public void add(Connection connection, Comment comment) throws SQLException {
		PreparedStatement ps = null;
		try {
	        ps = connection.prepareStatement("insert into comments(sender, createDate, comment, issueId) values(?, ?, ?, ?)");
	        ps.setLong(1, comment.getSender().getId());
	        ps.setTimestamp(2, comment.getCreateDate());
	        ps.setString(3, comment.getComment());
	        ps.setLong(4, comment.getIssueId());
	        ps.executeUpdate();
		} finally {
			DBManager.closeStatements(ps);
		}
	}
}
