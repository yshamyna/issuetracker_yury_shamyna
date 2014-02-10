package org.training.issuetracker.db.dao.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.training.issuetracker.db.beans.Build;
import org.training.issuetracker.db.util.DBManager;

public class BuildDAO {


	public Build getById(Connection connection, long id) throws Exception {
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
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
		}
		return null;
	}

	public void add(Connection connection, Build build) throws Exception {
		PreparedStatement ps = null;
		try {
			ps = connection.prepareStatement("insert into builds(projectId, version, isCurrent) values(?, ?, ?)");
			ps.setLong(1, build.getProjectId());
			ps.setString(2, build.getVersion());
			ps.setBoolean(3, build.getIsCurrent());
			ps.executeUpdate();
		} finally {
			DBManager.closeStatements(ps);
		}

	}

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

	public void changeVersion(Connection connection, long oldBuildId, 
			long newBuildId) throws Exception {
		PreparedStatement ps = null;
		try {
			ps = connection.prepareStatement("update builds set isCurrent=false where id=?");
			ps.addBatch("update builds set isCurrent=true where id=?");
			ps.setLong(1, oldBuildId);
			ps.setLong(2, newBuildId);
			ps.executeBatch();
		} finally {
			DBManager.closeStatements(ps);
		}
		
	}
	
	public Build getCurrentBuild(Connection connection, long projectId) throws Exception {
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = connection.prepareStatement("select id, version from builds where projectId=? and isCurrent=true");
			ps.setLong(1, projectId);
			rs = ps.executeQuery();
			if (rs.next()) {
				Build build = new Build();
				build.setId(rs.getLong("id"));
				build.setVersion(rs.getString("version"));
				build.setProjectId(projectId);
				build.setCurrent(true);
				return build;
			}
		} finally {
			DBManager.closeResultSets(rs);
			DBManager.closeStatements(ps);
		}
		return null;
	}

}
