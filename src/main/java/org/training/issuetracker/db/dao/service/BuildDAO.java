package org.training.issuetracker.db.dao.service;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.training.issuetracker.db.beans.Build;
import org.training.issuetracker.db.dao.interfaces.IBuildDAO;
import org.training.issuetracker.db.util.DBManager;

public class BuildDAO implements IBuildDAO {

	@Override
	public List<Build> getAll() throws Exception {
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
		Connection connection = null;
		PreparedStatement ps = null;
		try {
			connection = DBManager.getConnection();
			ps = connection.prepareStatement("insert into builds(projectId, version, isCurrent) values(?, ?, ?)");
			ps.setLong(1, build.getProjectId());
			ps.setString(2, build.getVersion());
			ps.setBoolean(3, build.getIsCurrent());
			ps.executeUpdate();
		} finally {
			DBManager.closeStatements(ps);
			DBManager.closeConnection(connection);
		}

	}

	@Override
	public List<Build> getByProjectId(long id) throws Exception {
		Connection connection = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			connection = DBManager.getConnection();
	        ps = connection.prepareStatement("select id, version, isCurrent from builds where projectId=?");
	        ps.setLong(1, id);
	        rs = ps.executeQuery();
	        List<Build> builds = new ArrayList<Build>();
	        Build build = null;
	        while (rs.next()) {
	        	build = new Build();
	        	build.setId(rs.getLong("id"));
	        	build.setVersion(rs.getString("version"));
	        	build.setProjectId(id);
	        	build.setCurrent(rs.getBoolean("isCurrent"));
	        	builds.add(build);
	        }
	        return builds;
		} finally {
			DBManager.closeResultSets(rs);
			DBManager.closeStatements(ps);
			DBManager.closeConnection(connection);
		}
	}

	@Override
	public void changeVersion(Build build) throws Exception {
		Connection connection = null;
		CallableStatement cs = null;
		try {
			connection = DBManager.getConnection();
			cs = connection.prepareCall("call updateBuildVersionOfProject(?, ?)");
			cs.setLong(1, build.getId());
			cs.setLong(2, build.getProjectId());
			cs.executeUpdate();
		} finally {
			DBManager.closeStatements(cs);
			DBManager.closeConnection(connection);
		}
		
	}

}
