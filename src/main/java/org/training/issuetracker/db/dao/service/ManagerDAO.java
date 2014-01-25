package org.training.issuetracker.db.dao.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

import org.training.issuetracker.db.beans.Manager;
import org.training.issuetracker.db.dao.interfaces.IManagerDAO;
import org.training.issuetracker.db.util.DBManager;

public class ManagerDAO implements IManagerDAO {

	@Override
	public List<Manager> getAll() throws Exception {
		// TODO Auto-generated method stub
		return null;
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
