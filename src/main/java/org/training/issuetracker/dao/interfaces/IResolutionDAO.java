package org.training.issuetracker.dao.interfaces;

import java.util.List;

import org.training.issuetracker.beans.IssueResolution;

public interface IResolutionDAO {
	public List<IssueResolution> getAll() throws Exception;
	public IssueResolution getById(int id) throws Exception;
	public void add(IssueResolution resolution) throws Exception;
}
