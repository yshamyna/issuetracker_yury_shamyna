package org.training.issuetracker.db.dao.service;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import org.training.issuetracker.db.beans.Issue;
import org.training.issuetracker.db.beans.User;
import org.training.issuetracker.db.dao.interfaces.IBuildDAO;
import org.training.issuetracker.db.dao.interfaces.IIssueDAO;
import org.training.issuetracker.db.dao.interfaces.IPriorityDAO;
import org.training.issuetracker.db.dao.interfaces.IProjectDAO;
import org.training.issuetracker.db.dao.interfaces.IResolutionDAO;
import org.training.issuetracker.db.dao.interfaces.IStatusDAO;
import org.training.issuetracker.db.dao.interfaces.ITypeDAO;
import org.training.issuetracker.db.dao.interfaces.IUserDAO;
import org.training.issuetracker.db.util.DBManager;

public class IssueDAO {
	
	private static final String GET_ALL_ISSUES_SQL = 
			"SELECT id, createDate, createBy, modifyDate, modifyBy, summary, description, statusId, typeId, priorityId, projectId, buildId, assignee, resolutionId from issues";
	private static final String GET_ALL_ISSUES_BY_ASSIGNEE_SQL = 
			"SELECT id, createDate, createBy, modifyDate, modifyBy, summary, description, statusId, typeId, priorityId, projectId, buildId, resolutionId from issues where assignee=?";
	
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
				
				UserDAO userDAO = new UserDAO();
				issue.setAssignee(userDAO.getById(rs.getInt("assignee")));
				issue.setCreatedBy(userDAO.getById(rs.getInt("createBy")));
				issue.setModifyBy(userDAO.getById(rs.getInt("modifyBy")));
				
				StatusDAO statusDAO = new StatusDAO();
				issue.setStatus(statusDAO.getById(rs.getInt("statusId")));
				
				TypeDAO typeDAO = new TypeDAO();
				issue.setType(typeDAO.getById(rs.getInt("typeId")));
				
				PriorityDAO priorityDAO = new PriorityDAO();
				issue.setPriority(priorityDAO.getById(rs.getInt("priorityId")));
				
				ProjectDAO projectDAO = new ProjectDAO();
				issue.setProject(projectDAO.getById(rs.getInt("projectId")));
				
				BuildDAO buildDAO = new BuildDAO();
				issue.setBuildFound(buildDAO.getById(rs.getInt("buildId")));
				
				ResolutionDAO resolutionDAO = new ResolutionDAO();
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

	public Issue getById(long id) throws Exception {
		Connection connection = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			connection = DBManager.getConnection();
	        ps = connection.prepareStatement("select createDate, createBy, modifyDate, modifyBy, summary, description, statusId, typeId, priorityId, projectId, buildId, assignee, resolutionId from issues where id=?");
	        ps.setLong(1, id);
	        rs = ps.executeQuery();
	        if (rs.next()) {
	        	Issue issue = new Issue();
	        	issue.setId(id);
	        	issue.setCreateDate(rs.getTimestamp("createDate"));
				issue.setModifyDate(rs.getTimestamp("modifyDate"));
				issue.setSummary(rs.getString("summary"));
				issue.setDescription(rs.getString("description"));
				
				UserDAO userDAO = new UserDAO();
				issue.setAssignee(userDAO.getById(rs.getInt("assignee")));
				issue.setCreatedBy(userDAO.getById(rs.getInt("createBy")));
				issue.setModifyBy(userDAO.getById(rs.getInt("modifyBy")));
				
				StatusDAO statusDAO = new StatusDAO();
				issue.setStatus(statusDAO.getById(rs.getInt("statusId")));
				
				TypeDAO typeDAO = new TypeDAO();
				issue.setType(typeDAO.getById(rs.getInt("typeId")));
				
				PriorityDAO priorityDAO = new PriorityDAO();
				issue.setPriority(priorityDAO.getById(rs.getInt("priorityId")));
				
				ProjectDAO projectDAO = new ProjectDAO();
				issue.setProject(projectDAO.getById(rs.getInt("projectId")));
				
				BuildDAO buildDAO = new BuildDAO();
				issue.setBuildFound(buildDAO.getById(rs.getInt("buildId")));
				
				ResolutionDAO resolutionDAO = new ResolutionDAO();
				issue.setResolution(resolutionDAO.getById(rs.getInt("resolutionId")));
				
	        	return issue;
	        }
		} finally {
			DBManager.closeResultSets(rs);
			DBManager.closeStatements(ps);
			DBManager.closeConnection(connection);
		}
		return null;
	}

	public void add(Connection connection, Issue issue) throws Exception {
		PreparedStatement ps = null;
		try {
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
			DBManager.closeStatements(ps);
		}
	}

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
				
				UserDAO userDAO = new UserDAO();
				issue.setAssignee(userDAO.getById(id));
				issue.setCreatedBy(userDAO.getById(rs.getInt("createBy")));
				issue.setModifyBy(userDAO.getById(rs.getInt("modifyBy")));
				
				StatusDAO statusDAO = new StatusDAO();
				issue.setStatus(statusDAO.getById(rs.getInt("statusId")));
				
				TypeDAO typeDAO = new TypeDAO();
				issue.setType(typeDAO.getById(rs.getInt("typeId")));
				
				PriorityDAO priorityDAO = new PriorityDAO();
				issue.setPriority(priorityDAO.getById(rs.getInt("priorityId")));
				
				ProjectDAO projectDAO = new ProjectDAO();
				issue.setProject(projectDAO.getById(rs.getInt("projectId")));
				
				BuildDAO buildDAO = new BuildDAO();
				issue.setBuildFound(buildDAO.getById(rs.getInt("buildId")));
				
				ResolutionDAO resolutionDAO = new ResolutionDAO();
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

	public void update(Connection connection, Issue issue) throws Exception {
		PreparedStatement ps = null;
		try {
			ps = connection.prepareStatement("update issues set modifyDate=?, modifyBy=?, summary=?, description=?, statusId=?, typeId=?, priorityId=?, projectId=?, buildId=?, assignee=?, resolutionId=? where id=?");
			ps.setTimestamp(1, issue.getModifyDate());
			ps.setLong(2, issue.getModifyBy().getId());
			ps.setString(3, issue.getSummary());
			ps.setString(4, issue.getDescription());
			ps.setLong(5, issue.getStatus().getId());
			ps.setLong(6, issue.getType().getId());
			ps.setLong(7, issue.getPriority().getId());
			ps.setLong(8, issue.getProject().getId());
			ps.setLong(9, issue.getBuildFound().getId());
			if (issue.getAssignee() == null) {
				ps.setNull(10, Types.INTEGER);
			} else {
				ps.setLong(10, issue.getAssignee().getId());
			}
			ps.setNull(11, Types.INTEGER);
			ps.setLong(12, issue.getId());
			ps.executeUpdate();
		} finally {
			DBManager.closeStatements(ps);
		}
	}

	public List<Issue> getNRecordsFromPageY(User user, long recordsPerPage, long pageNumber)
			throws Exception {
		Connection connection = null;
		CallableStatement cs = null;
		ResultSet rs = null;
		try {
			connection = DBManager.getConnection();
			cs = connection.prepareCall("{call getNIssuesFromPageY(?, ?, ?)}");
			
			cs.setLong(1, user.getId());
			cs.setLong(2, pageNumber);
			cs.setLong(3, recordsPerPage);
			rs = cs.executeQuery();
			
			List<Issue> issues = new ArrayList<Issue>();
			Issue issue = null;
			while(rs.next()) {
				issue = new Issue();
				
				issue.setId(rs.getLong("id"));
				issue.setCreateDate(rs.getTimestamp("createDate"));
				issue.setModifyDate(rs.getTimestamp("modifyDate"));
				issue.setSummary(rs.getString("summary"));
				issue.setDescription(rs.getString("description"));
				
				UserDAO userDAO = new UserDAO();
				//issue.setAssignee(userDAO.getById(id));
				issue.setAssignee(userDAO.getById(user.getId()));
				issue.setCreatedBy(userDAO.getById(rs.getInt("createBy")));
				issue.setModifyBy(userDAO.getById(rs.getInt("modifyBy")));
				
				StatusDAO statusDAO = new StatusDAO();
				issue.setStatus(statusDAO.getById(rs.getInt("statusId")));
				
				TypeDAO typeDAO = new TypeDAO();
				issue.setType(typeDAO.getById(rs.getInt("typeId")));
				
				PriorityDAO priorityDAO = new PriorityDAO();
				issue.setPriority(priorityDAO.getById(rs.getInt("priorityId")));
				
				ProjectDAO projectDAO = new ProjectDAO();
				issue.setProject(projectDAO.getById(rs.getInt("projectId")));
				
				BuildDAO buildDAO = new BuildDAO();
				issue.setBuildFound(buildDAO.getById(rs.getInt("buildId")));
				
				ResolutionDAO resolutionDAO = new ResolutionDAO();
				issue.setResolution(resolutionDAO.getById(rs.getInt("resolutionId")));
				
				issues.add(issue);
			}
			
			return issues;
		} finally {
			DBManager.closeResultSets(rs);
			DBManager.closeStatements(cs);
			DBManager.closeConnection(connection);
		}
	}

	public List<Issue> getLastNRecords(long n) throws Exception {
		Connection connection = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			connection = DBManager.getConnection();
			ps = connection.prepareStatement("SELECT id, createDate, createBy, modifyDate, modifyBy, summary, description, statusId, typeId, priorityId, projectId, buildId, assignee, resolutionId from issues order by id desc limit ?");
			ps.setLong(1, n);
			rs = ps.executeQuery();
//			List<Issue> issues = new ArrayList<Issue>();
//			Issue issue = null;
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
			DBManager.closeStatements(ps);
			DBManager.closeConnection(connection);
		} 
	}

	public long getQuantityPages(User user, long recordsPerPage) throws Exception {
		Connection connection = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			connection = DBManager.getConnection();
			ps = connection.prepareStatement("select count(*) as cnt from issues where assignee=?");
			ps.setLong(1, user.getId());
			rs = ps.executeQuery();
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
			DBManager.closeStatements(ps);
			DBManager.closeConnection(connection);
		}
	}
}
