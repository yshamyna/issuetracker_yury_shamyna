package org.training.issuetracker.db.dao.interfaces;

import java.util.List;

import org.training.issuetracker.db.beans.Manager;

public interface IManagerDAO {
	public List<Manager> getAll() throws Exception;
	public Manager getById(int id) throws Exception;
	public void add(Manager manager) throws Exception;
}
