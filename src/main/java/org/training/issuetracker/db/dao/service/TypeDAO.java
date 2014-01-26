package org.training.issuetracker.db.dao.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.training.issuetracker.db.beans.IssueType;
import org.training.issuetracker.db.dao.interfaces.ITypeDAO;
import org.training.issuetracker.db.util.DBManager;

public class TypeDAO implements ITypeDAO {

	@Override
	public List<IssueType> getAll() throws Exception {
		Connection connection = null;
		Statement st = null;
		ResultSet rs = null;
		try {
			connection = DBManager.getConnection();
			st = connection.createStatement();
			st.execute("select id, name from types");
			rs = st.getResultSet();
			List<IssueType> types = new ArrayList<IssueType>();
			IssueType type = null;
			while (rs.next()) {
				type = new IssueType();
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

	@Override
	public IssueType getById(int id) throws Exception {
		Connection connection = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			connection = DBManager.getConnection();
			ps = connection.prepareStatement("select name from types where id=?");
			ps.setInt(1, id);
			rs = ps.executeQuery();
			if (rs.next()) {
				IssueType type = new IssueType();
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

	@Override
	public void add(IssueType type) throws Exception {
		// TODO Auto-generated method stub

	}

}
