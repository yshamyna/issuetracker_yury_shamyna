package org.training.issuetracker.db.dao.interfaces;

import java.util.List;

import org.training.issuetracker.db.beans.IssueType;

public interface ITypeDAO {
	public List<IssueType> getAll() throws Exception;
	public IssueType getById(long id) throws Exception;
	public void add(IssueType type) throws Exception;
	public void updateName(IssueType newType) throws Exception;
}
