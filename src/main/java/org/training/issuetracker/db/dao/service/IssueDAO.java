package org.training.issuetracker.db.dao.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import org.training.issuetracker.db.beans.Issue;
import org.training.issuetracker.db.dao.interfaces.IBuildDAO;
import org.training.issuetracker.db.dao.interfaces.IIssueDAO;
import org.training.issuetracker.db.dao.interfaces.IPriorityDAO;
import org.training.issuetracker.db.dao.interfaces.IProjectDAO;
import org.training.issuetracker.db.dao.interfaces.IResolutionDAO;
import org.training.issuetracker.db.dao.interfaces.IStatusDAO;
import org.training.issuetracker.db.dao.interfaces.ITypeDAO;
import org.training.issuetracker.db.dao.interfaces.IUserDAO;
import org.training.issuetracker.db.util.DBManager;

public class IssueDAO implements IIssueDAO {
	
	private static final String GET_ALL_ISSUES_SQL = 
			"SELECT id, createDate, createBy, modifyDate, modifyBy, summary, description, statusId, typeId, priorityId, projectId, buildId, assignee, resolutionId from issues";
	private static final String GET_ALL_ISSUES_BY_ASSIGNEE_SQL = 
			"SELECT id, createDate, createBy, modifyDate, modifyBy, summary, description, statusId, typeId, priorityId, projectId, buildId, resolutionId from issues where assignee=?";

	@Override
	public List<Issue> getAll() throws Exception {
		Connection connection = null;
		Statement st = null;
		ResultSet rs = null;
		try {
			connection = DBManager.getConnection();
			st = connection.createStatement();
			st.execute(GET_ALL_ISSUES_SQL);
			rs = st.getResultSet();
			List<Issue> issues = new ArrayList<Issue>();
			Issue issue = null;
			while (rs.next()) {
				issue = new Issue();
				
				issue.setId(rs.getLong("id"));
				issue.setCreateDate(rs.getTimestamp("createDate"));
				issue.setModifyDate(rs.getTimestamp("modifyDate"));
				issue.setSummary(rs.getString("summary"));
				issue.setDescription(rs.getString("description"));
				
				IUserDAO userDAO = new UserDAO();
				issue.setAssignee(userDAO.getById(rs.getInt("assignee")));
				issue.setCreatedBy(userDAO.getById(rs.getInt("createBy")));
				issue.setModifyBy(userDAO.getById(rs.getInt("modifyBy")));
				
				IStatusDAO statusDAO = new StatusDAO();
				issue.setStatus(statusDAO.getById(rs.getInt("statusId")));
				
				ITypeDAO typeDAO = new TypeDAO();
				issue.setType(typeDAO.getById(rs.getInt("typeId")));
				
				IPriorityDAO priorityDAO = new PriorityDAO();
				issue.setPriority(priorityDAO.getById(rs.getInt("priorityId")));
				
				IProjectDAO projectDAO = new ProjectDAO();
				issue.setProject(projectDAO.getById(rs.getInt("projectId")));
				
				IBuildDAO buildDAO = new BuildDAO();
				issue.setBuildFound(buildDAO.getById(rs.getInt("buildId")));
				
				IResolutionDAO resolutionDAO = new ResolutionDAO();
				issue.setResolution(resolutionDAO.getById(rs.getInt("resolutionId")));
				
				issues.add(issue);
			}
			return issues;
		} finally {
			DBManager.closeResultSets(rs);
			DBManager.closeStatements(st);
			DBManager.closeConnection(connection);
		} 
	}

	@Override
	public Issue getById(long id) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void add(Issue issue) throws Exception {
		Connection connection = null;
		PreparedStatement ps = null;
		Statement st = null;
		ResultSet rs = null;
		try {
			connection = DBManager.getConnection();
			ps = connection.prepareStatement("insert into issues(createDate, createBy, modifyDate, modifyBy, summary, description, statusId, typeId, priorityId, projectId, buildId, assignee, resolutionId) values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
			ps.setTimestamp(1, issue.getCreateDate());
			ps.setLong(2, issue.getCreatedBy().getId());
			ps.setTimestamp(3, issue.getModifyDate());
			ps.setLong(4, issue.getModifyBy().getId());
			ps.setString(5, issue.getSummary());
			ps.setString(6, issue.getDescription());
			ps.setLong(7, issue.getStatus().getId());
			ps.setLong(8, issue.getType().getId());
			ps.setLong(9, issue.getPriority().getId());
			ps.setLong(10, issue.getProject().getId());
			ps.setLong(11, issue.getBuildFound().getId());
			if (issue.getAssignee() == null) {
				ps.setNull(12, Types.INTEGER);
			} else {
				ps.setLong(12, issue.getAssignee().getId());
			}
			ps.setNull(13, Types.INTEGER);
			ps.executeUpdate();
		} finally {
			DBManager.closeResultSets(rs);
			DBManager.closeStatements(st, ps);
			DBManager.closeConnection(connection);
		}
	}

	@Override
	public List<Issue> getAllByUserId(long id) throws Exception {
		Connection connection = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			connection = DBManager.getConnection();
			ps = connection.prepareStatement(GET_ALL_ISSUES_BY_ASSIGNEE_SQL);
			ps.setLong(1, id);
			rs = ps.executeQuery();
			List<Issue> issues = new ArrayList<Issue>();
			Issue issue = null;
			while (rs.next()) {
				issue = new Issue();
				
				issue.setId(rs.getLong("id"));
				issue.setCreateDate(rs.getTimestamp("createDate"));
				issue.setModifyDate(rs.getTimestamp("modifyDate"));
				issue.setSummary(rs.getString("summary"));
				issue.setDescription(rs.getString("description"));
				
				IUserDAO userDAO = new UserDAO();
				issue.setAssignee(userDAO.getById(id));
				issue.setCreatedBy(userDAO.getById(rs.getInt("createBy")));
				issue.setModifyBy(userDAO.getById(rs.getInt("modifyBy")));
				
				IStatusDAO statusDAO = new StatusDAO();
				issue.setStatus(statusDAO.getById(rs.getInt("statusId")));
				
				ITypeDAO typeDAO = new TypeDAO();
				issue.setType(typeDAO.getById(rs.getInt("typeId")));
				
				IPriorityDAO priorityDAO = new PriorityDAO();
				issue.setPriority(priorityDAO.getById(rs.getInt("priorityId")));
				
				IProjectDAO projectDAO = new ProjectDAO();
				issue.setProject(projectDAO.getById(rs.getInt("projectId")));
				
				IBuildDAO buildDAO = new BuildDAO();
				issue.setBuildFound(buildDAO.getById(rs.getInt("buildId")));
				
				IResolutionDAO resolutionDAO = new ResolutionDAO();
				issue.setResolution(resolutionDAO.getById(rs.getInt("resolutionId")));
				
				issues.add(issue);
			}
			return issues;
		} finally {
			DBManager.closeResultSets(rs);
			DBManager.closeStatements(ps);
			DBManager.closeConnection(connection);
		} 
	}

}
