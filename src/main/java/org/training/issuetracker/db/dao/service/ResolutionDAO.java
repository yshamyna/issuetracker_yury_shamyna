package org.training.issuetracker.db.dao.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

import org.training.issuetracker.db.beans.IssueResolution;
import org.training.issuetracker.db.dao.interfaces.IResolutionDAO;
import org.training.issuetracker.db.util.DBManager;

public class ResolutionDAO implements IResolutionDAO {

	@Override
	public List<IssueResolution> getAll() throws Exception {
		return null;
	}

	@Override
	public IssueResolution getById(int id) throws Exception {
		Connection connection = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			connection = DBManager.getConnection();
			ps = connection.prepareStatement("select name from resolutions where id=?");
			ps.setInt(1, id);
			rs = ps.executeQuery();
			if (rs.next()) {
				IssueResolution resolution = new IssueResolution();
				resolution.setId(id);
				resolution.setValue(rs.getString("name"));
				return resolution;
			}
		} finally {
			DBManager.closeResultSets(rs);
			DBManager.closeStatements(ps);
			DBManager.closeConnection(connection);
		}
		return null;
	}

	@Override
	public void add(IssueResolution resolution) throws Exception {
	}

}
