package org.training.issuetracker.db.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import org.training.issuetracker.db.beans.Type;
import org.training.issuetracker.db.beans.User;
import org.training.issuetracker.db.dao.service.TypeDAO;
import org.training.issuetracker.db.enums.Role;
import org.training.issuetracker.db.util.DBManager;

public class TypeService {
	private Connection connection;
	
	public TypeService(User user) 
			throws ClassNotFoundException, SQLException {
		Role role;
		if (user == null) {
			role = Role.GUEST;
		} else {
			role = Role.valueOf(user.getRole().getName().toUpperCase());	
		}
		connection = DBManager.getConnection(role);
	}
	

	public void add(Type type) throws SQLException {
		try {
			TypeDAO typeDAO = new TypeDAO();
			typeDAO.add(connection, type);	
		} finally {
			DBManager.closeConnection(connection);	
		}
	}

	public void update(Type type) throws SQLException {
		try {
			TypeDAO typeDAO = new TypeDAO();
			typeDAO.update(connection, type);	
		} finally {
			DBManager.closeConnection(connection);	
		}
	}
	
	public List<Type> getTypes() throws SQLException {
		try {
			TypeDAO typeDAO = new TypeDAO();
			return typeDAO.all(connection);	
		} finally {
			DBManager.closeConnection(connection);	
		}
	}
	
	public Type getTypeById(long typeId) throws SQLException {
		try {
			TypeDAO typeDAO = new TypeDAO();
			return typeDAO.getById(connection, typeId);	
		} finally {
			DBManager.closeConnection(connection);	
		}
	}
}
