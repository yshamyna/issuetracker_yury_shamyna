package org.training.issuetracker.dao.interfaces;

import java.util.List;

import org.training.issuetracker.beans.UserRole;

public interface IRoleDAO {
	public List<UserRole> getAll() throws Exception;
	public UserRole getById(int id) throws Exception;
	public void add(UserRole role) throws Exception;
}
