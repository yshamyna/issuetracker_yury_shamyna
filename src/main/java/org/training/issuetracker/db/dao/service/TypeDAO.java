package org.training.issuetracker.db.dao.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.training.issuetracker.db.beans.Type;
import org.training.issuetracker.db.util.DBManager;

public class TypeDAO {

	public List<Type> getAll() throws Exception {
		Connection connection = null;
		Statement st = null;
		ResultSet rs = null;
		try {
			connection = DBManager.getConnection();
			st = connection.createStatement();
			st.execute("select id, name from types");
			rs = st.getResultSet();
			List<Type> types = new ArrayList<Type>();
			Type type = null;
			while (rs.next()) {
				type = new Type();
				type.setId(rs.getInt("id"));
				type.setValue(rs.getString("name"));
				types.add(type);
			}
			return types;
		} finally {
			DBManager.closeResultSets(rs);
			DBManager.closeStatements(st);
			DBManager.closeConnection(connection);
		}
	}

	public Type getById(long id) throws Exception {
		Connection connection = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			connection = DBManager.getConnection();
			ps = connection.prepareStatement("select name from types where id=?");
			ps.setLong(1, id);
			rs = ps.executeQuery();
			if (rs.next()) {
				Type type = new Type();
				type.setId(id);
				type.setValue(rs.getString("name"));
				return type;
			}
		} finally {
			DBManager.closeResultSets(rs);
			DBManager.closeStatements(ps);
			DBManager.closeConnection(connection);
		}
		return null;
	}

	public void add(Connection connection, Type type) throws Exception {
		PreparedStatement ps = null;
		try {
			ps = connection.prepareStatement("insert into types(name) values(?)");
			ps.setString(1, type.getValue());
			ps.executeUpdate();
		} finally {
			DBManager.closeStatements(ps);
		}
	}

	public void update(Connection connection, Type type)
			throws Exception {
		PreparedStatement ps = null;
		try {
			ps = connection.prepareStatement("update types set name=? where id=?");
			ps.setString(1, type.getValue());
			ps.setLong(2, type.getId());
			ps.executeUpdate();
		} finally {
			DBManager.closeStatements(ps);
		}
	}
}
