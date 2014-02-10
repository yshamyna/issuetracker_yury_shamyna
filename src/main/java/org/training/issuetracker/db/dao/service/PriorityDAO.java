package org.training.issuetracker.db.dao.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.training.issuetracker.db.beans.Priority;
import org.training.issuetracker.db.dao.interfaces.IPriorityDAO;
import org.training.issuetracker.db.util.DBManager;

public class PriorityDAO {

	public List<Priority> getAll() throws Exception {
		Connection connection = null;
		Statement st = null;
		ResultSet rs = null;
		try {
			connection = DBManager.getConnection();
			st = connection.createStatement();
			st.execute("select id, name from priorities");
			rs = st.getResultSet();
			List<Priority> priotities = new ArrayList<Priority>();
			Priority priority = null;
			while (rs.next()) {
				priority = new Priority();
				priority.setId(rs.getInt("id"));
				priority.setValue(rs.getString("name"));
				priotities.add(priority);
			}
			return priotities;
		} finally {
			DBManager.closeResultSets(rs);
			DBManager.closeStatements(st);
			DBManager.closeConnection(connection);
		}
	}

	public Priority getById(long id) throws Exception {
		Connection connection = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			connection = DBManager.getConnection();
			ps = connection.prepareStatement("select name from priorities where id=?");
			ps.setLong(1, id);
			rs = ps.executeQuery();
			if (rs.next()) {
				Priority priority = new Priority();
				priority.setId(id);
				priority.setValue(rs.getString("name"));
				return priority;
			}
		} finally {
			DBManager.closeResultSets(rs);
			DBManager.closeStatements(ps);
			DBManager.closeConnection(connection);
		}
		return null;
	}

	public void add(Connection connection, Priority priority) throws Exception {
		PreparedStatement ps = null;
		try {
			ps = connection.prepareStatement("insert into priorities(name) values(?)");
			ps.setString(1, priority.getValue());
			ps.executeUpdate();
		} finally {
			DBManager.closeStatements(ps);
		}
	}

	public void update(Connection connection, Priority priotity) 
			throws Exception {
		PreparedStatement ps = null;
		try {
			ps = connection.prepareStatement("update priorities set name=? where id=?");
			ps.setString(1, priotity.getValue());
			ps.setLong(2, priotity.getId());
			ps.executeUpdate();
		} finally {
			DBManager.closeStatements(ps);
		}
	}

}
