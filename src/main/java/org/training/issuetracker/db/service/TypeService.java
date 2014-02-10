package org.training.issuetracker.db.service;

import java.sql.Connection;
import java.sql.SQLException;

import org.training.issuetracker.db.beans.Type;
import org.training.issuetracker.db.beans.User;
import org.training.issuetracker.db.dao.service.TypeDAO;
import org.training.issuetracker.db.enums.Role;
import org.training.issuetracker.db.util.DBManager;

public class TypeService {
	private Connection connection;
	
	public TypeService(User user) 
			throws ClassNotFoundException, SQLException {
		Role role = Role.valueOf(user.getRole().getValue());
		connection = DBManager.getConnection(role);
	}
	

	public void add(Type type) throws Exception {
		try {
			TypeDAO typeDAO = new TypeDAO();
			typeDAO.add(connection, type);	
		} finally {
			DBManager.closeConnection(connection);	
		}
	}

	public void update(Type type) throws Exception {
		try {
			TypeDAO typeDAO = new TypeDAO();
			typeDAO.update(connection, type);	
		} finally {
			DBManager.closeConnection(connection);	
		}
	}
}
