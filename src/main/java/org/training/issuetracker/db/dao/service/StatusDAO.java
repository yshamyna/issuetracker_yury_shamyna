package org.training.issuetracker.db.dao.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.training.issuetracker.db.beans.Status;
import org.training.issuetracker.db.util.DBManager;

public class StatusDAO {

	public List<Status> getAll() throws Exception {
		Connection connection = null;
		Statement st = null;
		ResultSet rs = null;
		try {
			connection = DBManager.getConnection();
			st = connection.createStatement();
			st.execute("select id, name from statuses");
			rs = st.getResultSet();
			List<Status> statuses = new ArrayList<Status>();
			Status status = null;
			while (rs.next()) {
				status = new Status();
				status.setId(rs.getInt("id"));
				status.setValue(rs.getString("name"));
				statuses.add(status);
			}
			return statuses;
		} finally {
			DBManager.closeResultSets(rs);
			DBManager.closeStatements(st);
			DBManager.closeConnection(connection);
		}
	}

	public Status getById(long id) throws Exception {
		Connection connection = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			connection = DBManager.getConnection();
			ps = connection.prepareStatement("select name from statuses where id=?");
			ps.setLong(1, id);
			rs = ps.executeQuery();
			if (rs.next()) {
				Status status = new Status();
				status.setId(id);
				status.setValue(rs.getString("name"));
				return status;
			}
		} finally {
			DBManager.closeResultSets(rs);
			DBManager.closeStatements(ps);
			DBManager.closeConnection(connection);
		}
		return null;
	}

	public void add(Connection connection, Status status) throws Exception {
		PreparedStatement ps = null;
		try {
			ps = connection.prepareStatement("insert into statuses(name) values(?)");
			ps.setString(1, status.getValue());
			ps.executeUpdate();
		} finally {
			DBManager.closeStatements(ps);
		}
	}
	
	public void update(Connection connection, Status status) throws Exception {
		PreparedStatement ps = null;
		try {
			ps = connection.prepareStatement("update statuses set name=? where id=?");
			ps.setString(1, status.getValue());
			ps.setLong(2, status.getId());
			ps.executeUpdate();
		} finally {
			DBManager.closeStatements(ps);
		}
	}

}
