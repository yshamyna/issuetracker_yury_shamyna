package org.training.issuetracker.db.dao.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

import org.training.issuetracker.db.beans.UserRole;
import org.training.issuetracker.db.dao.interfaces.IRoleDAO;
import org.training.issuetracker.db.util.DBManager;

public class RoleDAO implements IRoleDAO {

	@Override
	public List<UserRole> getAll() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public UserRole getById(int id) throws Exception {
		Connection connection = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			connection = DBManager.getConnection();
			ps = connection.prepareStatement("select name from roles where id=?");
			ps.setInt(1, id);
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
			DBManager.closeConnection(connection);
		}
		return null;
	}

	@Override
	public void add(UserRole role) throws Exception {
		// TODO Auto-generated method stub

	}

}
