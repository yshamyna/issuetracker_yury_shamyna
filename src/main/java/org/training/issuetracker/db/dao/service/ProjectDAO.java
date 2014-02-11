package org.training.issuetracker.db.dao.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.training.issuetracker.db.beans.Manager;
import org.training.issuetracker.db.beans.Project;
import org.training.issuetracker.db.util.DBManager;

public class ProjectDAO {

	public List<Project> all(Connection connection) throws SQLException {
		Statement st = null;
		ResultSet rs = null;
		try {
			st = connection.createStatement();
			st.execute("select id, name, managerId, description from projects");
			rs = st.getResultSet();
			List<Project> projects = new ArrayList<Project>();
			Project project = null;
			ManagerDAO managerDAO = new ManagerDAO();
			while (rs.next()) {
				project = new Project();
				project.setId(rs.getInt("id"));
				project.setName(rs.getString("name"));
				project.setDescription(rs.getString("description"));
				project.setManager(managerDAO.getById(connection, 
										rs.getInt("managerId")));
				projects.add(project);
			}
			return projects;
		} finally {
			DBManager.closeResultSets(rs);
			DBManager.closeStatements(st);
		}
	}

	public Project getById(Connection connection, long id) throws SQLException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
	        ps = connection.prepareStatement("select name, description, managerId from projects where id=?");
	        ps.setLong(1, id);
	        rs = ps.executeQuery();
	        if (rs.next()) {
	        	Project project = new Project();
	        	project.setId(id);
	        	project.setName(rs.getString("name"));
	        	project.setDescription(rs.getString("description"));
	        	int managerId = rs.getInt("managerId");
	        	ManagerDAO managerDAO = new ManagerDAO();
	        	Manager manager = managerDAO.getById(connection, managerId);
	        	project.setManager(manager);
	        	return project;
	        }
		} finally {
			DBManager.closeResultSets(rs);
			DBManager.closeStatements(ps);
		}
		return null;
	}

	public long add(Connection connection, Project project) throws SQLException {
		PreparedStatement ps = null;
		Statement st = null;
		ResultSet rs = null;
		try {
			ps = connection.prepareStatement("insert into projects(name, description, managerId) values(?, ?, ?)");
			ps.setString(1, project.getName());
			ps.setString(2, project.getDescription());
			ps.setLong(3, project.getManager().getId());
			ps.executeUpdate();
			
			st = connection.createStatement();
			st.execute("select max(id) as lastProjectId from projects");
			rs = st.getResultSet();
			if (rs.next()) {
				return rs.getLong("lastProjectId");
			}
		} finally {
			DBManager.closeResultSets(rs);
			DBManager.closeStatements(st, ps);
		}
		return -1;
	}

	public void update(Connection connection, Project project) throws SQLException {
		PreparedStatement ps = null;
		try {
			ps = connection.prepareStatement("update projects set name=?, description=?, managerId=? where id=?");
			ps.setString(1, project.getName());
			ps.setString(2, project.getDescription());
			ps.setLong(3, project.getManager().getId());
			ps.setLong(4, project.getId());
			ps.executeUpdate();
		} finally {
			DBManager.closeStatements(ps);
		}
	}

	public List<Project> getNRecordsFromPageY(long recordsPerPage,
			long pageNumber) throws Exception {
		Connection connection = null;
		PreparedStatement ps = null;
		Statement st = null;
		ResultSet rs = null;
		try {
			connection = DBManager.getConnection();
			st = connection.createStatement();
			rs = st.executeQuery("select count(*) as cnt from projects");
			long recordsNumber = 0;
			if (rs.next()) {
				recordsNumber = rs.getLong("cnt");
			}
			
			long offset = (pageNumber - 1) * recordsPerPage;
			if (offset < 0) {
				offset = 0;
			}
			if (offset >= recordsNumber) {
				offset = ((long) java.lang.Math.ceil((double)recordsNumber / recordsPerPage) - 1) * recordsPerPage;
			}
			
			ps = connection.prepareStatement("select projects.id as pid, name, description, managerId, firstName, lastName from projects, managers where managerId=managers.id limit ? offset ?");
			ps.setLong(1, recordsPerPage);
			ps.setLong(2, offset);
			rs = ps.executeQuery();
			List<Project> projects = new ArrayList<Project>();
			Project project = null;
			Manager manager = null;
			while (rs.next()) {
				manager = new Manager();
				manager.setId(rs.getLong("managerId"));
				manager.setFirstName(rs.getString("firstName"));
				manager.setLastName(rs.getString("lastName"));
				
				project = new Project();
				project.setId(rs.getLong("pid"));
				project.setName(rs.getString("name"));
				project.setDescription(rs.getString("description"));
				project.setManager(manager);
				
				projects.add(project);
			}
			return projects;
		} finally {
			DBManager.closeResultSets(rs);
			DBManager.closeStatements(ps, st);
			DBManager.closeConnection(connection);
		}
	}

	public long getQuantityPages(long recordsPerPage) throws Exception {
		Connection connection = null;
		Statement st = null;
		ResultSet rs = null;
		try {
			connection = DBManager.getConnection();
			st = connection.createStatement();
			rs = st.executeQuery("select count(*) as cnt from projects");
			long count = 0;
			if (rs.next()) {
				count = rs.getLong("cnt");
			}
			long div = count / recordsPerPage;
			long mod = count % recordsPerPage;
			if (mod != 0) {
				div++;
			}
			return div;
		} finally {
			DBManager.closeResultSets(rs);
			DBManager.closeStatements(st);
			DBManager.closeConnection(connection);
		}
	}
}
