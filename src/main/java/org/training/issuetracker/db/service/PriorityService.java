package org.training.issuetracker.db.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;

import org.training.issuetracker.db.beans.IssuePriority;
import org.training.issuetracker.db.beans.User;
import org.training.issuetracker.db.dao.service.PriorityDAO;
import org.training.issuetracker.db.enums.Role;
import org.training.issuetracker.db.interfaces.Service;
import org.training.issuetracker.db.util.DBManager;

public class PriorityService implements Service {
	private static final String ID_PARAMETER ="id";
	private static final String NAME_PARAMETER ="name";
	
	private String[] parameters = {ID_PARAMETER, NAME_PARAMETER};
	private Connection connection;
	
	public PriorityService(User user) 
			throws ClassNotFoundException, SQLException {
		Role role = Role.valueOf(user.getRole().getValue());
		connection = DBManager.getConnection(role);
	}

	@Override
	public String[] getParameters() {
		return parameters;
	}

	@Override
	public void add(Map<String, String> values) throws Exception {
		
	}

	@Override
	public void update(Map<String, String> values) throws Exception {
		long id = Long.parseLong(values.get(ID_PARAMETER));
		String name = values.get(NAME_PARAMETER);
		
		IssuePriority priority = new IssuePriority();
		priority.setId(id);
		priority.setValue(name);
		
		PriorityDAO priorityDAO = new PriorityDAO();
		priorityDAO.update(connection, priority);
	}
}
