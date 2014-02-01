package org.training.issuetracker.db.dao.interfaces;

import java.util.List;

import org.training.issuetracker.db.beans.IssuePriority;

public interface IPriorityDAO {
	public List<IssuePriority> getAll() throws Exception;
	public IssuePriority getById(long id) throws Exception;
	public void add(IssuePriority priority) throws Exception;
	public void update(IssuePriority priotity) throws Exception;
}
