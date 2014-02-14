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
import org.training.issuetracker.db.dao.service.constants.FieldsConstans;
import org.training.issuetracker.db.dao.service.constants.QueriesConstants;
import org.training.issuetracker.db.util.DBManager;

public class ProjectDAO {

	public List<Project> all(Connection connection) throws SQLException {
		Statement st = null;
		ResultSet rs = null;
		try {
			st = connection.createStatement();
			st.execute(QueriesConstants.PROJECT_SELECT_ALL);
			rs = st.getResultSet();
			List<Project> projects = new ArrayList<Project>();
			Project project = null;
			ManagerDAO managerDAO = new ManagerDAO();
			while (rs.next()) {
				project = new Project();
				project.setId(rs.getInt(FieldsConstans.ID));
				project.setName(rs.getString(FieldsConstans.NAME));
				project.setDescription(rs.getString(FieldsConstans.DESCRIPTION));
				project.setManager(managerDAO.getById(connection, 
										rs.getInt(FieldsConstans.MANAGER_ID)));
				projects.add(project);
			}
			return projects;
		} finally {
			DBManager.closeResultSets(rs);
			DBManager.closeStatements(st);
		}
	}

	public Project getById(Connection connection, long id) 
				throws SQLException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
	        ps = connection.prepareStatement(QueriesConstants.
	        			PROJECT_SELECT_BY_ID);
	        ps.setLong(1, id);
	        rs = ps.executeQuery();
	        if (rs.next()) {
	        	Project project = new Project();
	        	project.setId(id);
	        	project.setName(rs.getString(FieldsConstans.NAME));
	        	project.setDescription(rs.getString(FieldsConstans.DESCRIPTION));
	        	int managerId = rs.getInt(FieldsConstans.MANAGER_ID);
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

	public long add(Connection connection, Project project) 
					throws SQLException {
		PreparedStatement ps = null;
		Statement st = null;
		ResultSet rs = null;
		try {
			ps = connection.prepareStatement(QueriesConstants.PROJECT_ADD);
			ps.setString(1, project.getName());
			ps.setString(2, project.getDescription());
			ps.setLong(3, project.getManager().getId());
			ps.executeUpdate();
			
			st = connection.createStatement();
			st.execute(QueriesConstants.PROJECT_GET_MAX_ID);
			rs = st.getResultSet();
			if (rs.next()) {
				return rs.getLong(FieldsConstans.PROJECT_MAX_ID_ALIAS);
			}
		} finally {
			DBManager.closeResultSets(rs);
			DBManager.closeStatements(st, ps);
		}
		return -1;
	}

	public void update(Connection connection, Project project) 
						throws SQLException {
		PreparedStatement ps = null;
		try {
			ps = connection.prepareStatement(QueriesConstants.PROJECT_UPDATE);
			ps.setString(1, project.getName());
			ps.setString(2, project.getDescription());
			ps.setLong(3, project.getManager().getId());
			ps.setLong(4, project.getId());
			ps.executeUpdate();
		} finally {
			DBManager.closeStatements(ps);
		}
	}

	public List<Project> getNRecordsFromPageM(Connection connection, 
			long pageNumber, long recordsPerPage) throws SQLException {
		PreparedStatement ps = null;
		Statement st = null;
		ResultSet rs = null;
		try {
			st = connection.createStatement();
			rs = st.executeQuery(QueriesConstants.PROJECT_COUNT);
			long recordsNumber = 0;
			if (rs.next()) {
				recordsNumber = rs.getLong(FieldsConstans.COUNT_ALIAS);
			}
			
			long offset = (pageNumber - 1) * recordsPerPage;
			if (offset < 0) {
				offset = 0;
			}
			if (offset >= recordsNumber) {
				offset = ((long) Math.ceil((double)recordsNumber 
								/ recordsPerPage) - 1) * recordsPerPage;
			}
			
			ps = connection.prepareStatement(QueriesConstants.
							PROJECT_N_RECORDS_FROM_PAGE_M);
			ps.setLong(1, recordsPerPage);
			ps.setLong(2, offset);
			rs = ps.executeQuery();
			List<Project> projects = new ArrayList<Project>();
			Project project = null;
			Manager manager = null;
			while (rs.next()) {
				manager = new Manager();
				manager.setId(rs.getLong(FieldsConstans.MANAGER_ID));
				manager.setFirstName(rs.getString(FieldsConstans.FIRST_NAME));
				manager.setLastName(rs.getString(FieldsConstans.LAST_NAME));
				
				project = new Project();
				project.setId(rs.getLong(FieldsConstans.PROJECT_ID_ALIAS));
				project.setName(rs.getString(FieldsConstans.NAME));
				project.setDescription(rs.getString(FieldsConstans.DESCRIPTION));
				project.setManager(manager);
				
				projects.add(project);
			}
			return projects;
		} finally {
			DBManager.closeResultSets(rs);
			DBManager.closeStatements(ps, st);
		}
	}

	public long getQuantityPages(Connection connection, 
					long recordsPerPage) throws SQLException {
		Statement st = null;
		ResultSet rs = null;
		try {
			st = connection.createStatement();
			rs = st.executeQuery(QueriesConstants.PROJECT_ALL_COUNT);
			
			long count = 0;
			if (rs.next()) {
				count = rs.getLong(FieldsConstans.COUNT_ALIAS);
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
		}
	}
}
