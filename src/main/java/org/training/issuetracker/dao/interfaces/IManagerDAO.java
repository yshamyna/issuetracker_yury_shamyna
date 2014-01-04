package org.training.issuetracker.dao.interfaces;

import java.util.List;

import org.training.issuetracker.beans.Manager;

public interface IManagerDAO {
	public List<Manager> getAll() throws Exception;
	public Manager getById(int id) throws Exception;
	public void add(Manager manager) throws Exception;
}
