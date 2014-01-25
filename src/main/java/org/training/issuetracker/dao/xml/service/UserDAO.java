package org.training.issuetracker.dao.xml.service;

import java.util.List;

import org.training.issuetracker.beans.User;
import org.training.issuetracker.dao.interfaces.IUserDAO;
import org.training.issuetracker.dao.xml.parsers.Parser;
import org.training.issuetracker.dao.xml.parsers.UserParser;

public class UserDAO implements IUserDAO {

	@Override
	public List<User> getAll() throws Exception {
		UserParser userParser = new UserParser();
		Parser.parse(userParser);
		return userParser.getUsers();
	}

	@Override
	public User getById(int id) throws Exception {
		List<User> users = getAll();
		for (User user : users) {
			if (id == user.getId()) {
				return user;
			}
		}
		return null;
	}

	@Override
	public void add(User user) throws Exception {
		// nothing
	}

	@Override
	public User getByEmailAndPassword(String email, String password)
			throws Exception {
		return null;
	}

}
