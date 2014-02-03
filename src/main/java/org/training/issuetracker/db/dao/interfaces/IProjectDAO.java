package org.training.issuetracker.db.dao.interfaces;

import java.util.List;

import org.training.issuetracker.db.beans.Project;

public interface IProjectDAO {
	public List<Project> getAll() throws Exception;
	public Project getById(long id) throws Exception;
	public long add(Project project) throws Exception;
}
