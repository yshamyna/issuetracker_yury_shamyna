package org.training.issuetracker.db.dao.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.training.issuetracker.db.beans.Build;
import org.training.issuetracker.db.dao.service.constants.FieldsConstans;
import org.training.issuetracker.db.dao.service.constants.QueriesConstants;
import org.training.issuetracker.db.util.DBManager;

public class BuildDAO {


	public Build getById(Connection connection, long id) throws SQLException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
	        ps = connection.prepareStatement(QueriesConstants.BUID_SELECT_BY_ID);
	        ps.setLong(1, id);
	        rs = ps.executeQuery();
	        if (rs.next()) {
	        	Build build = new Build();
	        	build.setId(id);
	        	build.setVersion(rs.getString(FieldsConstans.VERSION));
	        	build.setProjectId(rs.getLong(FieldsConstans.PROJECT_ID));
	        	build.setCurrent(rs.getBoolean(FieldsConstans.IS_CURRENT));
	        	return build;
	        }
		} finally {
			DBManager.closeResultSets(rs);
			DBManager.closeStatements(ps);
		}
		return null;
	}

	public void add(Connection connection, Build build) throws SQLException {
		PreparedStatement ps = null;
		try {
			ps = connection.prepareStatement(QueriesConstants.BUID_ADD);
			ps.setLong(1, build.getProjectId());
			ps.setString(2, build.getVersion());
			ps.setBoolean(3, build.getIsCurrent());
			ps.executeUpdate();
		} finally {
			DBManager.closeStatements(ps);
		}

	}

	public List<Build> allByProjectId(Connection connection, long id) 
					throws SQLException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
	        ps = connection.prepareStatement(QueriesConstants.
	        			BUID_SELECT_BY_PROJECT_ID);
	        ps.setLong(1, id);
	        rs = ps.executeQuery();
	        List<Build> builds = new ArrayList<Build>();
	        Build build = null;
	        while (rs.next()) {
	        	build = new Build();
	        	build.setId(rs.getLong(FieldsConstans.ID));
	        	build.setVersion(rs.getString(FieldsConstans.VERSION));
	        	build.setProjectId(id);
	        	build.setCurrent(rs.getBoolean(FieldsConstans.IS_CURRENT));
	        	builds.add(build);
	        }
	        return builds;
		} finally {
			DBManager.closeResultSets(rs);
			DBManager.closeStatements(ps);
		}
	}

	public void changeVersion(Connection connection, long oldBuildId, 
			long newBuildId) throws SQLException {
		PreparedStatement ps = null;
		try {
			ps = connection.prepareStatement(QueriesConstants.BUILD_CHANGE_VERSION_PART_ONE);
			ps.addBatch(QueriesConstants.BUILD_CHANGE_VERSION_PART_TWO);
			ps.setLong(1, oldBuildId);
			ps.setLong(2, newBuildId);
			ps.executeBatch();
		} finally {
			DBManager.closeStatements(ps);
		}
		
	}
	
	public Build getCurrentBuild(Connection connection, long projectId) throws SQLException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = connection.prepareStatement(QueriesConstants.BUILD_CURRENT);
			ps.setLong(1, projectId);
			rs = ps.executeQuery();
			if (rs.next()) {
				Build build = new Build();
				build.setId(rs.getLong(FieldsConstans.ID));
				build.setVersion(rs.getString(FieldsConstans.VERSION));
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
