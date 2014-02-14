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
import org.training.issuetracker.db.dao.service.constants.FieldsConstans;
import org.training.issuetracker.db.dao.service.constants.QueriesConstants;
import org.training.issuetracker.db.util.DBManager;

public class UserDAO {

	public List<User> all(Connection connection) throws SQLException {
		Statement st = null;
		ResultSet rs = null;
		try {
			st = connection.createStatement();
			st.execute(QueriesConstants.USER_SELECT_ALL);
			rs = st.getResultSet();
			List<User> users = new ArrayList<User>();
			User user = null;
			RoleDAO roleDAO = new RoleDAO();
			while (rs.next()) {
				user = new User();
				
	        	user.setId(rs.getLong(FieldsConstans.ID));
	        	user.setEmailAddress(rs.getString(FieldsConstans.EMAIL));
	        	user.setPassword(rs.getString(FieldsConstans.PASSWORD));
	        	user.setFirstName(rs.getString(FieldsConstans.FIRST_NAME));
	        	user.setLastName(rs.getString(FieldsConstans.LAST_NAME));
	        	user.setRole(roleDAO.getById(connection, 
	        			rs.getInt(FieldsConstans.ROLE_ID)));
	        	
				users.add(user);
			}
			return users;
		} finally {
			DBManager.closeResultSets(rs);
			DBManager.closeStatements(st);
		}
	}

	public User getById(Connection connection, long id) 
							throws SQLException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		User user = null;
		try {
	        ps = connection.prepareStatement(QueriesConstants.
	        			USER_SELECT_BY_ID);
	        ps.setLong(1, id);
	        rs = ps.executeQuery();
	        if (rs.next()) {
	        	user = new User();
	        	user.setId(id);
	        	user.setEmailAddress(rs.getString(FieldsConstans.EMAIL));
	        	user.setPassword(rs.getString(FieldsConstans.PASSWORD));
	        	user.setFirstName(rs.getString(FieldsConstans.FIRST_NAME));
	        	user.setLastName(rs.getString(FieldsConstans.LAST_NAME));
	        	RoleDAO roleDAO = new RoleDAO();
	        	UserRole role = roleDAO.getById(connection, 
	        				rs.getInt(FieldsConstans.ROLE_ID));
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
			ps = connection.prepareStatement(QueriesConstants.USER_ADD);
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
	        ps = connection.prepareStatement(QueriesConstants.
	        				USER_SELECT_BY_EMAIL_AND_PASSWORD);
	        ps.setString(1, email);
	        ps.setString(2, password);
	        rs = ps.executeQuery();
	        if (rs.next()) {
	        	user = new User();
	        	user.setId(rs.getLong(FieldsConstans.USER_ID_ALIAS));
	        	user.setEmailAddress(email);
	        	user.setPassword(password);
	        	user.setFirstName(rs.getString(FieldsConstans.FIRST_NAME));
	        	user.setLastName(rs.getString(FieldsConstans.LAST_NAME));
	        	
	        	UserRole role = new UserRole();
	        	role.setId(rs.getLong(FieldsConstans.ROLE_ID));
	        	role.setName(rs.getString(FieldsConstans.ROLE));
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
			ps = connection.prepareStatement(QueriesConstants.
						USER_UPDATE_PASSWORD);
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
			ps = connection.prepareStatement(QueriesConstants.USER_UPDATE);
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
