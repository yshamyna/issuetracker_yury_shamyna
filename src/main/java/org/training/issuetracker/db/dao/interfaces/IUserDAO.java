package org.training.issuetracker.db.dao.interfaces;

import java.util.List;

import org.training.issuetracker.db.beans.User;

public interface IUserDAO {
	public List<User> getAll() throws Exception;
	public User getById(long id) throws Exception;
	public void add(User user) throws Exception;
	public User getByEmailAndPassword(String email, String password) throws Exception;
	public void changePassword(User user) throws Exception;
	public void update(User user) throws Exception;
}
