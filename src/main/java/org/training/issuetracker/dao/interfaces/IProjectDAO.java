package org.training.issuetracker.dao.interfaces;

import java.util.List;

import org.training.issuetracker.beans.Project;

public interface IProjectDAO {
	public List<Project> getAll() throws Exception;
	public Project getById(int id) throws Exception;
	public void add(Project project) throws Exception;
}
