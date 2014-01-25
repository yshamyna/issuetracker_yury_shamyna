package org.training.issuetracker.db.dao.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

import org.training.issuetracker.db.beans.Build;
import org.training.issuetracker.db.dao.interfaces.IBuildDAO;
import org.training.issuetracker.db.util.DBManager;

public class BuildDAO implements IBuildDAO {

	@Override
	public List<Build> getAll() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Build getById(long id) throws Exception {
		Connection connection = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			connection = DBManager.getConnection();
	        ps = connection.prepareStatement("select projectId, version, isCurrent from builds where id=?");
	        ps.setLong(1, id);
	        rs = ps.executeQuery();
	        if (rs.next()) {
	        	Build build = new Build();
	        	build.setId(id);
	        	build.setVersion(rs.getString("version"));
	        	build.setProjectId(rs.getLong("projectId"));
	        	build.setCurrent(rs.getBoolean("isCurrent"));
	        	return build;
	        }
		} finally {
			DBManager.closeResultSets(rs);
			DBManager.closeStatements(ps);
			DBManager.closeConnection(connection);
		}
		return null;
	}

	@Override
	public void add(Build build) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public List<Build> getByProjectId(long id) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

}
