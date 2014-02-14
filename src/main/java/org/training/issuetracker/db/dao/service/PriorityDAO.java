package org.training.issuetracker.db.dao.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.training.issuetracker.db.beans.Priority;
import org.training.issuetracker.db.dao.service.constants.FieldsConstans;
import org.training.issuetracker.db.dao.service.constants.QueriesConstants;
import org.training.issuetracker.db.util.DBManager;

public class PriorityDAO {

	public List<Priority> all(Connection connection) throws SQLException {
		Statement st = null;
		ResultSet rs = null;
		try {
			st = connection.createStatement();
			st.execute(QueriesConstants.PRIORITY_SELECT_ALL);
			rs = st.getResultSet();
			List<Priority> priotities = new ArrayList<Priority>();
			Priority priority = null;
			while (rs.next()) {
				priority = new Priority();
				priority.setId(rs.getInt(FieldsConstans.ID));
				priority.setName(rs.getString(FieldsConstans.NAME));
				priotities.add(priority);
			}
			return priotities;
		} finally {
			DBManager.closeResultSets(rs);
			DBManager.closeStatements(st);
		}
	}

	public Priority getById(Connection connection, long id) 
					throws SQLException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = connection.prepareStatement(QueriesConstants.
								PRIORITY_SELECT_BY_ID);
			ps.setLong(1, id);
			rs = ps.executeQuery();
			if (rs.next()) {
				Priority priority = new Priority();
				priority.setId(id);
				priority.setName(rs.getString(FieldsConstans.NAME));
				return priority;
			}
		} finally {
			DBManager.closeResultSets(rs);
			DBManager.closeStatements(ps);
		}
		return null;
	}

	public void add(Connection connection, Priority priority) 
								throws SQLException {
		PreparedStatement ps = null;
		try {
			ps = connection.prepareStatement(QueriesConstants.PRIORITY_ADD);
			ps.setString(1, priority.getName());
			ps.executeUpdate();
		} finally {
			DBManager.closeStatements(ps);
		}
	}

	public void update(Connection connection, Priority priotity) 
			throws SQLException {
		PreparedStatement ps = null;
		try {
			ps = connection.prepareStatement(QueriesConstants.PRIORITY_UPDATE);
			ps.setString(1, priotity.getName());
			ps.setLong(2, priotity.getId());
			ps.executeUpdate();
		} finally {
			DBManager.closeStatements(ps);
		}
	}

}
