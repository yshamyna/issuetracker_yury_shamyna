package org.training.issuetracker.db.dao.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.training.issuetracker.db.beans.Manager;
import org.training.issuetracker.db.beans.Project;
import org.training.issuetracker.db.dao.interfaces.IManagerDAO;
import org.training.issuetracker.db.dao.interfaces.IProjectDAO;
import org.training.issuetracker.db.util.DBManager;

public class ProjectDAO implements IProjectDAO {

	@Override
	public List<Project> getAll() throws Exception {
		Connection connection = null;
		Statement st = null;
		ResultSet rs = null;
		try {
			connection = DBManager.getConnection();
			st = connection.createStatement();
			st.execute("select id, name, managerId, description from projects");
			rs = st.getResultSet();
			List<Project> projects = new ArrayList<Project>();
			Project project = null;
			IManagerDAO managerDAO = new ManagerDAO();
			while (rs.next()) {
				project = new Project();
				project.setId(rs.getInt("id"));
				project.setName(rs.getString("name"));
				project.setDescription(rs.getString("description"));
				project.setManager(managerDAO.getById(rs.getInt("managerId")));
				projects.add(project);
			}
			return projects;
		} finally {
			DBManager.closeResultSets(rs);
			DBManager.closeStatements(st);
			DBManager.closeConnection(connection);
		}
	}

	@Override
	public Project getById(int id) throws Exception {
		Connection connection = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			connection = DBManager.getConnection();
	        ps = connection.prepareStatement("select name, description, managerId from projects where id=?");
	        ps.setInt(1, id);
	        rs = ps.executeQuery();
	        if (rs.next()) {
	        	Project project = new Project();
	        	project.setId(id);
	        	project.setName(rs.getString("name"));
	        	project.setDescription(rs.getString("description"));
	        	int managerId = rs.getInt("managerId");
	        	IManagerDAO managerDAO = new ManagerDAO();
	        	Manager manager = managerDAO.getById(managerId);
	        	project.setManager(manager);
	        	return project;
	        }
		} finally {
			DBManager.closeResultSets(rs);
			DBManager.closeStatements(ps);
			DBManager.closeConnection(connection);
		}
		return null;
	}

	@Override
	public void add(Project project) throws Exception {
		// TODO Auto-generated method stub

	}

}
