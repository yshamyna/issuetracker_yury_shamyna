package org.training.issuetracker.db.dao.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.training.issuetracker.db.beans.User;
import org.training.issuetracker.db.beans.UserRole;
import org.training.issuetracker.db.util.DBManager;

public class UserDAO {

	public List<User> all(Connection connection) throws SQLException {
		Statement st = null;
		ResultSet rs = null;
		try {
			st = connection.createStatement();
			st.execute("select id, firstName, lastName, email, password, roleId from users");
			rs = st.getResultSet();
			List<User> users = new ArrayList<User>();
			User user = null;
			RoleDAO roleDAO = new RoleDAO();
			while (rs.next()) {
				user = new User();
				
	        	user.setId(rs.getLong("id"));
	        	user.setEmailAddress(rs.getString("email"));
	        	user.setPassword(rs.getString("password"));
	        	user.setFirstName(rs.getString("firstName"));
	        	user.setLastName(rs.getString("lastName"));
	        	user.setRole(roleDAO.getById(connection, rs.getInt("roleId")));
	        	
				users.add(user);
			}
			return users;
		} finally {
			DBManager.closeResultSets(rs);
			DBManager.closeStatements(st);
		}
	}

	public User getById(Connection connection, long id) throws SQLException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		User user = null;
		try {
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
	        	UserRole role = roleDAO.getById(connection, rs.getInt("roleId"));
	        	user.setRole(role);
	        }
		} finally {
			DBManager.closeResultSets(rs);
			DBManager.closeStatements(ps);
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

	public User getByEmailAndPassword(Connection connection, 
			String email, String password) throws SQLException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		User user = null;
		try {
	        ps = connection.prepareStatement("select users.id as uid, firstName, lastName, name as role, roleId from users, roles where email=? and password=? and roleId=roles.id");
	        ps.setString(1, email);
	        ps.setString(2, password);
	        rs = ps.executeQuery();
	        if (rs.next()) {
	        	user = new User();
	        	user.setId(rs.getLong("uid"));
	        	user.setEmailAddress(email);
	        	user.setPassword(password);
	        	user.setFirstName(rs.getString("firstName"));
	        	user.setLastName(rs.getString("lastName"));
	        	
	        	UserRole role = new UserRole();
	        	role.setId(rs.getLong("roleId"));
	        	role.setName(rs.getString("role"));
	        	user.setRole(role);
	        }
		} finally {
			DBManager.closeResultSets(rs);
			DBManager.closeStatements(ps);
		}
		return user;
	}

	public void updatePassword(Connection connection, User user, 
			String password) throws SQLException {
		PreparedStatement ps = null;
		try {
			ps = connection.prepareStatement("update users set password=? where id=?");
			ps.setString(1, password);
			ps.setLong(2, user.getId());
			ps.executeUpdate();
		} finally {
			DBManager.closeStatements(ps);
		}
	}

	public void update(Connection connection, User user) throws SQLException {
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
