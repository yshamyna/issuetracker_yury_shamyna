package org.training.issuetracker.db.dao.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

import org.training.issuetracker.db.beans.IssuePriority;
import org.training.issuetracker.db.dao.interfaces.IPriorityDAO;
import org.training.issuetracker.db.util.DBManager;

public class PriorityDAO implements IPriorityDAO {

	@Override
	public List<IssuePriority> getAll() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IssuePriority getById(int id) throws Exception {
		Connection connection = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			connection = DBManager.getConnection();
			ps = connection.prepareStatement("select name from priorities where id=?");
			ps.setInt(1, id);
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
		// TODO Auto-generated method stub

	}

}
