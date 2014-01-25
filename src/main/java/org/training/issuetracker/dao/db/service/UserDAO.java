package org.training.issuetracker.dao.db.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

import org.training.issuetracker.beans.User;
import org.training.issuetracker.beans.UserRole;
import org.training.issuetracker.dao.db.DBManager;
import org.training.issuetracker.dao.interfaces.IUserDAO;

public class UserDAO implements IUserDAO {

	@Override
	public List<User> getAll() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public User getById(int id) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void add(User user) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public User getByEmailAndPassword(String email, String password)
			throws Exception {
		Connection connection = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		User user = null;
		try {
			connection = DBManager.getConnsection();
	        ps = connection.prepareStatement("select users.id, firstName, lastName, name as role, roleId from users, roles where email=? and password=? and roleId=roles.id");
	        ps.setString(1, email);
	        ps.setString(2, password);
	        rs = ps.executeQuery();
	        if (rs.next()) {
	        	user = new User();
	        	user.setId(rs.getLong("id"));
	        	user.setEmailAddress(email);
	        	user.setPassword(password);
	        	user.setFirstName(rs.getString("firstName"));
	        	user.setLastName(rs.getString("lastName"));
	        	UserRole role = new UserRole();
	        	role.setId(rs.getLong("roleId"));
	        	role.setValue(rs.getString("role"));
	        	user.setRole(role);
	        }
		} finally {
			DBManager.closeConnection(connection);
			DBManager.closeStatements(ps);
			DBManager.closeResultSets(rs);
		}
		return user;
	}
}
