package org.training.issuetracker.db.dao.interfaces;

import java.util.List;

import org.training.issuetracker.db.beans.Comment;
import org.training.issuetracker.db.beans.User;

public interface ICommentDAO {
	public List<Comment> getCommentsByIssueId(long issueId) throws Exception;
	public void addComment(Comment comment) throws Exception;
}
