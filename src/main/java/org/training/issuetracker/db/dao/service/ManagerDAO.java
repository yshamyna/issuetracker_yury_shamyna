package org.training.issuetracker.db.dao.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.training.issuetracker.db.beans.Manager;
import org.training.issuetracker.db.util.DBManager;

public class ManagerDAO {

	public List<Manager> getAll() throws Exception {
		Connection connection = null;
		Statement st = null;
		ResultSet rs = null;
		try {
			connection = DBManager.getConnection();
			st = connection.createStatement();
			st.execute("select id, firstName, lastName from managers");
			rs = st.getResultSet();
			List<Manager> managers = new ArrayList<Manager>();
			Manager manager = null;
			while (rs.next()) {
				manager = new Manager();
				manager.setId(rs.getInt("id"));
				manager.setFirstName(rs.getString("firstName"));
				manager.setLastName(rs.getString("lastName"));
				managers.add(manager);
			}
			return managers;
		} finally {
			DBManager.closeResultSets(rs);
			DBManager.closeStatements(st);
			DBManager.closeConnection(connection);
		}
	}

	public Manager getById(Connection connection, long id) throws SQLException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
	        ps = connection.prepareStatement("select firstName, lastName from managers where id=?");
	        ps.setLong(1, id);
	        rs = ps.executeQuery();
	        if (rs.next()) {
	        	Manager manager = new Manager();
	        	manager.setId(id);
	        	manager.setFirstName(rs.getString("firstName"));
	        	manager.setLastName(rs.getString("lastName"));
	        	return manager;
	        }
		} finally {
			DBManager.closeResultSets(rs);
			DBManager.closeStatements(ps);
		}
		return null;
	}
}
