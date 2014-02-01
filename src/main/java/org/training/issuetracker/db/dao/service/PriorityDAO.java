package org.training.issuetracker.db.dao.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.training.issuetracker.db.beans.IssuePriority;
import org.training.issuetracker.db.dao.interfaces.IPriorityDAO;
import org.training.issuetracker.db.util.DBManager;

public class PriorityDAO implements IPriorityDAO {

	@Override
	public List<IssuePriority> getAll() throws Exception {
		Connection connection = null;
		Statement st = null;
		ResultSet rs = null;
		try {
			connection = DBManager.getConnection();
			st = connection.createStatement();
			st.execute("select id, name from priorities");
			rs = st.getResultSet();
			List<IssuePriority> priotities = new ArrayList<IssuePriority>();
			IssuePriority priority = null;
			while (rs.next()) {
				priority = new IssuePriority();
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

	@Override
	public IssuePriority getById(long id) throws Exception {
		Connection connection = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			connection = DBManager.getConnection();
			ps = connection.prepareStatement("select name from priorities where id=?");
			ps.setLong(1, id);
			rs = ps.executeQuery();
			if (rs.next()) {
				IssuePriority priority = new IssuePriority();
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

	@Override
	public void add(IssuePriority priority) throws Exception {
		Connection connection = null;
		PreparedStatement ps = null;
		try {
			connection = DBManager.getConnection();
			ps = connection.prepareStatement("insert into priorities(name) values(?)");
			ps.setString(1, priority.getValue());
			ps.executeUpdate();
		} finally {
			DBManager.closeStatements(ps);
			DBManager.closeConnection(connection);
		}
	}

	@Override
	public void update(IssuePriority priotity) throws Exception {
		Connection connection = null;
		PreparedStatement ps = null;
		try {
			connection = DBManager.getConnection();
			ps = connection.prepareStatement("update priorities set name=? where id=?");
			ps.setString(1, priotity.getValue());
			ps.setLong(2, priotity.getId());
			ps.executeUpdate();
		} finally {
			DBManager.closeStatements(ps);
			DBManager.closeConnection(connection);
		}
	}

}
