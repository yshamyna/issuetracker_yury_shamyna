package org.training.issuetracker.dao.interfaces;

import java.util.List;

import org.training.issuetracker.beans.User;

public interface IUserDAO {
	public List<User> getAll() throws Exception;
	public User getById(int id) throws Exception;
	public void add(User user) throws Exception;
	public User getByEmailAndPassword(String email, String password) throws Exception;
}
