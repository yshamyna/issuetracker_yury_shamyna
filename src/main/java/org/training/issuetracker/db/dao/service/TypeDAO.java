package org.training.issuetracker.db.dao.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.training.issuetracker.db.beans.Type;
import org.training.issuetracker.db.dao.service.constants.FieldsConstans;
import org.training.issuetracker.db.dao.service.constants.QueriesConstants;
import org.training.issuetracker.db.util.DBManager;

public class TypeDAO {

	public List<Type> all(Connection connection) throws SQLException {
		Statement st = null;
		ResultSet rs = null;
		try {
			st = connection.createStatement();
			st.execute(QueriesConstants.TYPE_SELECT_ALL);
			rs = st.getResultSet();
			List<Type> types = new ArrayList<Type>();
			Type type = null;
			while (rs.next()) {
				type = new Type();
				type.setId(rs.getInt(FieldsConstans.ID));
				type.setName(rs.getString(FieldsConstans.NAME));
				types.add(type);
			}
			return types;
		} finally {
			DBManager.closeResultSets(rs);
			DBManager.closeStatements(st);
		}
	}

	public Type getById(Connection connection, long id) 
				throws SQLException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = connection.prepareStatement(QueriesConstants.
						TYPE_SELECT_BY_ID);
			ps.setLong(1, id);
			rs = ps.executeQuery();
			if (rs.next()) {
				Type type = new Type();
				type.setId(id);
				type.setName(rs.getString(FieldsConstans.NAME));
				return type;
			}
		} finally {
			DBManager.closeResultSets(rs);
			DBManager.closeStatements(ps);
		}
		return null;
	}

	public void add(Connection connection, Type type) throws SQLException {
		PreparedStatement ps = null;
		try {
			ps = connection.prepareStatement(QueriesConstants.TYPE_ADD);
			ps.setString(1, type.getName());
			ps.executeUpdate();
		} finally {
			DBManager.closeStatements(ps);
		}
	}

	public void update(Connection connection, Type type)
			throws SQLException {
		PreparedStatement ps = null;
		try {
			ps = connection.prepareStatement(QueriesConstants.TYPE_UPDATE);
			ps.setString(1, type.getName());
			ps.setLong(2, type.getId());
			ps.executeUpdate();
		} finally {
			DBManager.closeStatements(ps);
		}
	}
}
