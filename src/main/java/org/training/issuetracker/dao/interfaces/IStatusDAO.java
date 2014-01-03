package org.training.issuetracker.dao.interfaces;

import java.util.List;

import org.training.issuetracker.beans.IssueStatus;

public interface IStatusDAO {
	public List<IssueStatus> getAll() throws Exception;
	public IssueStatus getById(int id) throws Exception;
	public void add(IssueStatus status) throws Exception;
}
