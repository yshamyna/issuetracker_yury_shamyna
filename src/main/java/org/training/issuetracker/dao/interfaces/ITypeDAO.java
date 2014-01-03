package org.training.issuetracker.dao.interfaces;

import java.util.List;

import org.training.issuetracker.beans.IssueType;

public interface ITypeDAO {
	public List<IssueType> getAll() throws Exception;
	public IssueType getById(int id) throws Exception;
	public void add(IssueType type) throws Exception;
}
