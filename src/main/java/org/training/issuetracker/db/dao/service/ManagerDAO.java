package org.training.issuetracker.db.dao.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.training.issuetracker.db.beans.Manager;
import org.training.issuetracker.db.dao.service.constants.FieldsConstans;
import org.training.issuetracker.db.dao.service.constants.QueriesConstants;
import org.training.issuetracker.db.util.DBManager;

public class ManagerDAO {

	public List<Manager> all(Connection connection) throws SQLException {
		Statement st = null;
		ResultSet rs = null;
		try {
			st = connection.createStatement();
			st.execute(QueriesConstants.MANAGER_SELECT_ALL);
			rs = st.getResultSet();
			List<Manager> managers = new ArrayList<Manager>();
			Manager manager = null;
			while (rs.next()) {
				manager = new Manager();
				manager.setId(rs.getInt(FieldsConstans.ID));
				manager.setFirstName(rs.getString(FieldsConstans.FIRST_NAME));
				manager.setLastName(rs.getString(FieldsConstans.LAST_NAME));
				managers.add(manager);
			}
			return managers;
		} finally {
			DBManager.closeResultSets(rs);
			DBManager.closeStatements(st);
		}
	}

	public Manager getById(Connection connection, long id) throws SQLException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
	        ps = connection.prepareStatement(QueriesConstants.
	        				MANAGER_SELECT_BY_ID);
	        ps.setLong(1, id);
	        rs = ps.executeQuery();
	        if (rs.next()) {
	        	Manager manager = new Manager();
	        	manager.setId(id);
	        	manager.setFirstName(rs.getString(FieldsConstans.FIRST_NAME));
	        	manager.setLastName(rs.getString(FieldsConstans.LAST_NAME));
	        	return manager;
	        }
		} finally {
			DBManager.closeResultSets(rs);
			DBManager.closeStatements(ps);
		}
		return null;
	}
}
