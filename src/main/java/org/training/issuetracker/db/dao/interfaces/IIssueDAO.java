package org.training.issuetracker.db.dao.interfaces;

import java.util.List;

import org.training.issuetracker.db.beans.Issue;

public interface IIssueDAO {
	public List<Issue> getAll() throws Exception;
	public Issue getById(long id) throws Exception;
	public void add(Issue issue) throws Exception;
	public List<Issue> getAllByUserId(long id) throws Exception;
}
