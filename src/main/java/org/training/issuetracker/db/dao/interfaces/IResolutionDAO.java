package org.training.issuetracker.db.dao.interfaces;

import java.util.List;

import org.training.issuetracker.db.beans.IssueResolution;

public interface IResolutionDAO {
	public List<IssueResolution> getAll() throws Exception;
	public IssueResolution getById(long id) throws Exception;
	public void add(IssueResolution resolution) throws Exception;
	public void updateName(IssueResolution resolution) throws Exception;
}
