package org.training.issuetracker.db.dao.interfaces;

import java.util.List;

import org.training.issuetracker.db.beans.Build;

public interface IBuildDAO {
	public List<Build> getAll() throws Exception;
	public Build getById(long id) throws Exception;
	public void add(Build build) throws Exception;
	public List<Build> getByProjectId(long id) throws Exception;
	public void changeVersion(Build build) throws Exception;
}
