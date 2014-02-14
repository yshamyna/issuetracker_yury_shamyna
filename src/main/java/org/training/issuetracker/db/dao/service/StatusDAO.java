package org.training.issuetracker.db.dao.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.training.issuetracker.db.beans.Status;
import org.training.issuetracker.db.dao.service.constants.FieldsConstans;
import org.training.issuetracker.db.dao.service.constants.QueriesConstants;
import org.training.issuetracker.db.util.DBManager;

public class StatusDAO {

	public List<Status> all(Connection connection) throws SQLException {
		Statement st = null;
		ResultSet rs = null;
		try {
			st = connection.createStatement();
			st.execute(QueriesConstants.STATUS_SELECT_ALL);
			rs = st.getResultSet();
			List<Status> statuses = new ArrayList<Status>();
			Status status = null;
			while (rs.next()) {
				status = new Status();
				status.setId(rs.getInt(FieldsConstans.ID));
				status.setName(rs.getString(FieldsConstans.NAME));
				statuses.add(status);
			}
			return statuses;
		} finally {
			DBManager.closeResultSets(rs);
			DBManager.closeStatements(st);
		}
	}

	public Status getById(Connection connection, long id) 
				throws SQLException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = connection.prepareStatement(QueriesConstants.
						STATUS_SELECT_BY_ID);
			ps.setLong(1, id);
			rs = ps.executeQuery();
			if (rs.next()) {
				Status status = new Status();
				status.setId(id);
				status.setName(rs.getString(FieldsConstans.NAME));
				return status;
			}
		} finally {
			DBManager.closeResultSets(rs);
			DBManager.closeStatements(ps);
		}
		return null;
	}

	public void add(Connection connection, Status status) throws SQLException {
		PreparedStatement ps = null;
		try {
			ps = connection.prepareStatement(QueriesConstants.STATUS_ADD);
			ps.setString(1, status.getName());
			ps.executeUpdate();
		} finally {
			DBManager.closeStatements(ps);
		}
	}
	
	public void update(Connection connection, Status status) throws SQLException {
		PreparedStatement ps = null;
		try {
			ps = connection.prepareStatement(QueriesConstants.STATUS_UPDATE);
			ps.setString(1, status.getName());
			ps.setLong(2, status.getId());
			ps.executeUpdate();
		} finally {
			DBManager.closeStatements(ps);
		}
	}

}
