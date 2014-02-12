package org.training.issuetracker.db.dao.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.training.issuetracker.db.beans.UserRole;
import org.training.issuetracker.db.util.DBManager;

public class RoleDAO {

	public List<UserRole> all(Connection connection) throws SQLException {
		Statement st = null;
		ResultSet rs = null;
		try {
			st = connection.createStatement();
			st.execute("select id, name from roles");
			rs = st.getResultSet();
			List<UserRole> roles = new ArrayList<UserRole>();
			UserRole role = null;
			while (rs.next()) {
				role = new UserRole();
				role.setId(rs.getInt("id"));
				role.setValue(rs.getString("name"));
				roles.add(role);
			}
			return roles;
		} finally {
			DBManager.closeResultSets(rs);
			DBManager.closeStatements(st);
		}
	}

	public UserRole getById(Connection connection, long id) throws SQLException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = connection.prepareStatement("select name from roles where id=?");
			ps.setLong(1, id);
			rs = ps.executeQuery();
			if (rs.next()) {
				UserRole role = new UserRole();
				role.setId(id);
				role.setValue(rs.getString("name"));
				return role;
			}
		} finally {
			DBManager.closeResultSets(rs);
			DBManager.closeStatements(ps);
		}
		return null;
	}
}
