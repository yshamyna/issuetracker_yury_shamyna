package org.training.issuetracker.db.dao.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.training.issuetracker.db.beans.Manager;
import org.training.issuetracker.db.dao.interfaces.IManagerDAO;
import org.training.issuetracker.db.util.DBManager;

public class ManagerDAO implements IManagerDAO {

	@Override
	public List<Manager> getAll() throws Exception {
		Connection connection = null;
		Statement st = null;
		ResultSet rs = null;
		try {
			connection = DBManager.getConnection();
			st = connection.createStatement();
			st.execute("select id, firstName, lastName from managers");
			rs = st.getResultSet();
			List<Manager> managers = new ArrayList<Manager>();
			Manager manager = null;
			while (rs.next()) {
				manager = new Manager();
				manager.setId(rs.getInt("id"));
				manager.setFirstName(rs.getString("firstName"));
				manager.setLastName(rs.getString("lastName"));
				managers.add(manager);
			}
			return managers;
		} finally {
			DBManager.closeResultSets(rs);
			DBManager.closeStatements(st);
			DBManager.closeConnection(connection);
		}
	}

	@Override
	public Manager getById(int id) throws Exception {
		Connection connection = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			connection = DBManager.getConnection();
	        ps = connection.prepareStatement("select firstName, lastName from managers where id=?");
	        ps.setInt(1, id);
	        rs = ps.executeQuery();
	        if (rs.next()) {
	        	Manager manager = new Manager();
	        	manager.setId(id);
	        	manager.setFirstName(rs.getString("firstName"));
	        	manager.setLastName(rs.getString("lastName"));
	        	return manager;
	        }
		} finally {
			DBManager.closeResultSets(rs);
			DBManager.closeStatements(ps);
			DBManager.closeConnection(connection);
		}
		return null;
	}

	@Override
	public void add(Manager manager) throws Exception {
		// TODO Auto-generated method stub

	}

}
