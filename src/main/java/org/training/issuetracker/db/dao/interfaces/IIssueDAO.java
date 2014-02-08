package org.training.issuetracker.db.dao.interfaces;

import java.util.List;

import org.training.issuetracker.db.beans.Issue;
import org.training.issuetracker.db.beans.User;

public interface IIssueDAO {
	public List<Issue> getAll() throws Exception;
	public Issue getById(long id) throws Exception;
	public void add(Issue issue) throws Exception;
	public List<Issue> getAllByUserId(long id) throws Exception;
	public void update(Issue issue) throws Exception;
	public List<Issue> getNRecordsFromPageY(User user, long recordsPerPage, long pageNumber) throws Exception;
	public List<Issue> getLastNRecords(long n) throws Exception;
	public long getQuantityPages(User user, long recordsPerPage) throws Exception;
}
