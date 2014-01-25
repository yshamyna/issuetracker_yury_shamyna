package org.training.issuetracker.db.dao.interfaces;

import java.util.List;

import org.training.issuetracker.db.beans.IssuePriority;

public interface IPriorityDAO {
	public List<IssuePriority> getAll() throws Exception;
	public IssuePriority getById(int id) throws Exception;
	public void add(IssuePriority priority) throws Exception;
}
