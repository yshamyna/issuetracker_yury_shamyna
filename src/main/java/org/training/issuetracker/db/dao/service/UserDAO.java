package org.training.issuetracker.db.dao.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.training.issuetracker.db.beans.User;
import org.training.issuetracker.db.beans.UserRole;
import org.training.issuetracker.db.util.DBManager;

public class UserDAO {

	public List<User> getAll() throws Exception {
		Connection connection = null;
		Statement st = null;
		ResultSet rs = null;
		try {
			connection = DBManager.getConnection();
			st = connection.createStatement();
			st.execute("select id, firstName, lastName, email, password, roleId from users");
			rs = st.getResultSet();
			List<User> users = new ArrayList<User>();
			User user = null;
			while (rs.next()) {
				user = new User();
	        	user.setId(rs.getLong("id"));
	        	user.setEmailAddress(rs.getString("email"));
	        	user.setPassword(rs.getString("password"));
	        	user.setFirstName(rs.getString("firstName"));
	        	user.setLastName(rs.getString("lastName"));
	        	RoleDAO roleDAO = new RoleDAO();
	        	UserRole role = roleDAO.getById(connection, rs.getInt("roleId"));
	        	user.setRole(role);
				users.add(user);
			}
			return users;
		} finally {
			DBManager.closeResultSets(rs);
			DBManager.closeStatements(st);
			DBManager.closeConnection(connection);
		}
	}

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
	        	RoleDAO roleDAO = new RoleDAO();
	        	UserRole role = (UserRole) roleDAO.getById(connection, rs.getInt("roleId"));
	        	user.setRole(role);
	        }
		} finally {
			DBManager.closeResultSets(rs);
			DBManager.closeStatements(ps);
			DBManager.closeConnection(connection);
		}
		return user;
	}

	public void add(Connection connection, User user) throws Exception {
		PreparedStatement ps = null;
		try {
			ps = connection.prepareStatement("insert into users(firstName, lastName, email, roleId, password) values(?, ?, ?, ?, ?)");
			ps.setString(1, user.getFirstName());
			ps.setString(2, user.getLastName());
			ps.setString(3, user.getEmailAddress());
			ps.setLong(4, user.getRole().getId());
			ps.setString(5, user.getPassword());
			ps.executeUpdate();
		} finally {
			DBManager.closeStatements(ps);
		}
	}

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

	public void changePassword(User user) throws Exception {
		Connection connection = null;
		PreparedStatement ps = null;
		try {
			connection = DBManager.getConnection();
			ps = connection.prepareStatement("update users set password=? where id=?");
			ps.setString(1, user.getPassword());
			ps.setLong(2, user.getId());
			ps.executeUpdate();
		} finally {
			DBManager.closeStatements(ps);
			DBManager.closeConnection(connection);
		}
	}

	public void update(Connection connection, User user) throws Exception {
		PreparedStatement ps = null;
		try {
			ps = connection.prepareStatement("update users set firstName=?, lastName=?, email=?, roleId=? where id=?");
			ps.setString(1, user.getFirstName());
			ps.setString(2, user.getLastName());
			ps.setString(3, user.getEmailAddress());
			ps.setLong(4, user.getRole().getId());
			ps.setLong(5, user.getId());
			ps.executeUpdate();
		} finally {
			DBManager.closeStatements(ps);
		}
	}
}
