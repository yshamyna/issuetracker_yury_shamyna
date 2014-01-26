package org.training.issuetracker.db.dao.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.training.issuetracker.db.beans.IssueStatus;
import org.training.issuetracker.db.dao.interfaces.IStatusDAO;
import org.training.issuetracker.db.util.DBManager;

public class StatusDAO implements IStatusDAO {

	@Override
	public List<IssueStatus> getAll() throws Exception {
		Connection connection = null;
		Statement st = null;
		ResultSet rs = null;
		try {
			connection = DBManager.getConnection();
			st = connection.createStatement();
			st.execute("select id, name from statuses");
			rs = st.getResultSet();
			List<IssueStatus> statuses = new ArrayList<IssueStatus>();
			IssueStatus status = null;
			while (rs.next()) {
				status = new IssueStatus();
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

	@Override
	public IssueStatus getById(int id) throws Exception {
		Connection connection = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			connection = DBManager.getConnection();
			ps = connection.prepareStatement("select name from statuses where id=?");
			ps.setInt(1, id);
			rs = ps.executeQuery();
			if (rs.next()) {
				IssueStatus status = new IssueStatus();
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

	@Override
	public void add(IssueStatus status) throws Exception {
		// TODO Auto-generated method stub

	}

}
