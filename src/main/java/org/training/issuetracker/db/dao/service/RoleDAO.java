package org.training.issuetracker.db.dao.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.training.issuetracker.db.beans.UserRole;
import org.training.issuetracker.db.dao.service.constants.FieldsConstans;
import org.training.issuetracker.db.dao.service.constants.QueriesConstants;
import org.training.issuetracker.db.util.DBManager;

public class RoleDAO {

	public List<UserRole> all(Connection connection) throws SQLException {
		Statement st = null;
		ResultSet rs = null;
		try {
			st = connection.createStatement();
			st.execute(QueriesConstants.ROLE_SELECT_ALL);
			rs = st.getResultSet();
			List<UserRole> roles = new ArrayList<UserRole>();
			UserRole role = null;
			while (rs.next()) {
				role = new UserRole();
				role.setId(rs.getInt(FieldsConstans.ID));
				role.setName(rs.getString(FieldsConstans.NAME));
				roles.add(role);
			}
			return roles;
		} finally {
			DBManager.closeResultSets(rs);
			DBManager.closeStatements(st);
		}
	}

	public UserRole getById(Connection connection, long id) 
				throws SQLException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = connection.prepareStatement(QueriesConstants.
					ROLE_SELECT_BY_ID);
			ps.setLong(1, id);
			rs = ps.executeQuery();
			if (rs.next()) {
				UserRole role = new UserRole();
				role.setId(id);
				role.setName(rs.getString(FieldsConstans.NAME));
				return role;
			}
		} finally {
			DBManager.closeResultSets(rs);
			DBManager.closeStatements(ps);
		}
		return null;
	}
}
