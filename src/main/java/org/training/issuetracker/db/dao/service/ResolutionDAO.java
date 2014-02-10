package org.training.issuetracker.db.dao.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.training.issuetracker.db.beans.Resolution;
import org.training.issuetracker.db.util.DBManager;

public class ResolutionDAO {

	public List<Resolution> getAll() throws Exception {
		Connection connection = null;
		Statement st = null;
		ResultSet rs = null;
		try {
			connection = DBManager.getConnection();
			st = connection.createStatement();
			st.execute("select id, name from resolutions");
			rs = st.getResultSet();
			List<Resolution> resolutions = new ArrayList<Resolution>();
			Resolution resolution = null;
			while (rs.next()) {
				resolution = new Resolution();
				resolution.setId(rs.getInt("id"));
				resolution.setValue(rs.getString("name"));
				resolutions.add(resolution);
			}
			return resolutions;
		} finally {
			DBManager.closeResultSets(rs);
			DBManager.closeStatements(st);
			DBManager.closeConnection(connection);
		}
	}

	public Resolution getById(long id) throws Exception {
		Connection connection = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			connection = DBManager.getConnection();
			ps = connection.prepareStatement("select name from resolutions where id=?");
			ps.setLong(1, id);
			rs = ps.executeQuery();
			if (rs.next()) {
				Resolution resolution = new Resolution();
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

	public void add(Connection connection, Resolution resolution) 
			throws Exception {
		PreparedStatement ps = null;
		try {
			ps = connection.prepareStatement("insert into resolutions(name) values(?)");
			ps.setString(1, resolution.getValue());
			ps.executeUpdate();
		} finally {
			DBManager.closeStatements(ps);
		}
	}

	public void update(Connection connection, Resolution resolution) throws Exception {
		PreparedStatement ps = null;
		try {
			ps = connection.prepareStatement("update resolutions set name=? where id=?");
			ps.setString(1, resolution.getValue());
			ps.setLong(2, resolution.getId());
			ps.executeUpdate();
		} finally {
			DBManager.closeStatements(ps);
		}
	}

}
