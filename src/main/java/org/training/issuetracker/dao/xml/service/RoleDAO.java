package org.training.issuetracker.dao.xml.service;

import java.util.List;

import org.training.issuetracker.beans.UserRole;
import org.training.issuetracker.dao.interfaces.IRoleDAO;
import org.training.issuetracker.dao.xml.parsers.Parser;
import org.training.issuetracker.dao.xml.parsers.RoleParser;

public class RoleDAO implements IRoleDAO {

	@Override
	public List<UserRole> getAll() throws Exception {
		RoleParser roleParser = new RoleParser();
		Parser.parse(roleParser);
		return roleParser.getRoles();
	}

	@Override
	public UserRole getById(int id) throws Exception {
		List<UserRole> roles = getAll();
		for (UserRole role : roles) {
			if (id == role.getId()) {
				return role;
			}
		}
		return null;
	}

	@Override
	public void add(UserRole role) throws Exception {
		// nothing
	}

}
