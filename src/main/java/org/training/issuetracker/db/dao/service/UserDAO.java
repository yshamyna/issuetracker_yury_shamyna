package org.training.issuetracker.db.dao.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

import org.training.issuetracker.db.beans.User;
import org.training.issuetracker.db.beans.UserRole;
import org.training.issuetracker.db.dao.interfaces.IRoleDAO;
import org.training.issuetracker.db.dao.interfaces.IUserDAO;
import org.training.issuetracker.db.util.DBManager;

public class UserDAO implements IUserDAO {

	@Override
	public List<User> getAll() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public User getById(long id) throws Exception {
		Connection connection = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		User user = null;
		try {
			connection = DBManager.getConnection();
	        ps = connection.prepareStatement("select id, firstName, lastName, roleId, email, password from users where id=?");
	        ps.setLong(1, id);
	        rs = ps.executeQuery();
	        if (rs.next()) {
	        	user = new User();
	        	user.setId(id);
	        	user.setEmailAddress(rs.getString("email"));
	        	user.setPassword(rs.getString("password"));
	        	user.setFirstName(rs.getString("firstName"));
	        	user.setLastName(rs.getString("lastName"));
	        	IRoleDAO roleDAO = new RoleDAO();
	        	UserRole role = roleDAO.getById(rs.getInt("roleId"));
	        	user.setRole(role);
	        }
		} finally {
			DBManager.closeResultSets(rs);
			DBManager.closeStatements(ps);
			DBManager.closeConnection(connection);
		}
		return user;
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
			connection = DBManager.getConnection();
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
			DBManager.closeResultSets(rs);
			DBManager.closeStatements(ps);
			DBManager.closeConnection(connection);
		}
		return user;
	}
}
