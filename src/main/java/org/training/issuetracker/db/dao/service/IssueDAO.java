package org.training.issuetracker.db.dao.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import org.training.issuetracker.db.beans.Issue;
import org.training.issuetracker.db.beans.User;
import org.training.issuetracker.db.util.DBManager;

public class IssueDAO {
	
	private static final String GET_ALL_ISSUES_SQL = 
			"SELECT id, createDate, createBy, modifyDate, modifyBy, summary, description, statusId, typeId, priorityId, projectId, buildId, assignee, resolutionId from issues";
	private static final String GET_ALL_ISSUES_BY_ASSIGNEE_SQL = 
			"SELECT id, createDate, createBy, modifyDate, modifyBy, summary, description, statusId, typeId, priorityId, projectId, buildId, resolutionId from issues where assignee=?";

	public Issue getById(Connection connection, long id) throws SQLException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
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
				issue.setAssignee(userDAO.getById(connection, rs.getInt("assignee")));
				issue.setCreateBy(userDAO.getById(connection, rs.getInt("createBy")));
				issue.setModifyBy(userDAO.getById(connection, rs.getInt("modifyBy")));
				
				StatusDAO statusDAO = new StatusDAO();
				issue.setStatus(statusDAO.getById(connection, rs.getInt("statusId")));
				
				TypeDAO typeDAO = new TypeDAO();
				issue.setType(typeDAO.getById(connection, rs.getInt("typeId")));
				
				PriorityDAO priorityDAO = new PriorityDAO();
				issue.setPriority(priorityDAO.getById(connection, rs.getInt("priorityId")));
				
				ProjectDAO projectDAO = new ProjectDAO();
				issue.setProject(projectDAO.getById(connection, rs.getInt("projectId")));
				
				BuildDAO buildDAO = new BuildDAO();
				issue.setBuildFound(buildDAO.getById(connection, rs.getInt("buildId")));
				
				ResolutionDAO resolutionDAO = new ResolutionDAO();
				issue.setResolution(resolutionDAO.getById(connection, rs.getInt("resolutionId")));
				
	        	return issue;
	        }
		} finally {
			DBManager.closeResultSets(rs);
			DBManager.closeStatements(ps);
		}
		return null;
	}

	public void add(Connection connection, Issue issue) throws Exception {
		PreparedStatement ps = null;
		try {
			ps = connection.prepareStatement("insert into issues(createDate, createBy, modifyDate, modifyBy, summary, description, statusId, typeId, priorityId, projectId, buildId, assignee, resolutionId) values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
			ps.setTimestamp(1, issue.getCreateDate());
			ps.setLong(2, issue.getCreateBy().getId());
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

	public List<Issue> allByUserId(Connection connection, long userId) 
				throws SQLException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = connection.prepareStatement(GET_ALL_ISSUES_BY_ASSIGNEE_SQL);
			ps.setLong(1, userId);
			rs = ps.executeQuery();
			
			UserDAO uDAO = new UserDAO();
			StatusDAO sDAO = new StatusDAO();
			TypeDAO tDAO = new TypeDAO();
			PriorityDAO pDAO = new PriorityDAO();
			ProjectDAO prDAO = new ProjectDAO();
			BuildDAO bDAO = new BuildDAO();
			ResolutionDAO rDAO = new ResolutionDAO();
			
			List<Issue> issues = new ArrayList<Issue>();
			Issue issue = null;
			while (rs.next()) {
				issue = new Issue();
				
				issue.setId(rs.getLong("id"));
				issue.setCreateDate(rs.getTimestamp("createDate"));
				issue.setModifyDate(rs.getTimestamp("modifyDate"));
				issue.setSummary(rs.getString("summary"));
				issue.setDescription(rs.getString("description"));
				issue.setAssignee(uDAO.getById(connection, rs.getInt("assignee")));
				issue.setCreateBy(uDAO.getById(connection, rs.getInt("createBy")));
				issue.setModifyBy(uDAO.getById(connection, rs.getInt("modifyBy")));
				issue.setStatus(sDAO.getById(connection, rs.getInt("statusId")));
				issue.setType(tDAO.getById(connection, rs.getInt("typeId")));
				issue.setPriority(pDAO.getById(connection, rs.getInt("priorityId")));
				issue.setProject(prDAO.getById(connection, rs.getInt("projectId")));
				issue.setBuildFound(bDAO.getById(connection, rs.getInt("buildId")));
				issue.setResolution(rDAO.getById(connection, rs.getInt("resolutionId")));
				
				issues.add(issue);
			}
			return issues;
		} finally {
			DBManager.closeResultSets(rs);
			DBManager.closeStatements(ps);
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

	public List<Issue> getNRecordsFromPageM(Connection connection, 
			User user, long pageNumber, long recordsPerPage)
								throws SQLException {
		Statement st = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			st = connection.createStatement();
			st.execute("select count(*) as cnt from issues where assignee=?");
			rs = st.getResultSet();
			long recordsNumber = 0;
			if (rs.next()) {
				recordsNumber = rs.getLong("cnt");
			}
			
			long offset = (pageNumber - 1) * recordsPerPage;
			if (offset < 0) {
				offset = 0;
			}
			if (offset >= recordsNumber) {
				offset = ((long) Math.ceil((double)recordsNumber 
								/ recordsPerPage) - 1) * recordsPerPage;
			}
			
			ps = connection.prepareStatement("select id, createDate, createBy, modifyDate, modifyBy, summary, description, statusId, typeId, priorityId, projectId, buildId, resolutionId from issues where assignee=? order by id desc limit ? offset ?");
			ps.setLong(1, user.getId());
			ps.setLong(2, recordsPerPage);
			ps.setLong(3, offset);
			rs = ps.executeQuery();
			
			UserDAO uDAO = new UserDAO();
			StatusDAO sDAO = new StatusDAO();
			TypeDAO tDAO = new TypeDAO();
			PriorityDAO pDAO = new PriorityDAO();
			ProjectDAO prDAO = new ProjectDAO();
			BuildDAO bDAO = new BuildDAO();
			ResolutionDAO rDAO = new ResolutionDAO();
			
			List<Issue> issues = new ArrayList<Issue>();
			Issue issue = null;
			while(rs.next()) {
				issue = new Issue();
				
				issue.setId(rs.getLong("id"));
				issue.setCreateDate(rs.getTimestamp("createDate"));
				issue.setModifyDate(rs.getTimestamp("modifyDate"));
				issue.setSummary(rs.getString("summary"));
				issue.setDescription(rs.getString("description"));
				issue.setAssignee(uDAO.getById(connection, rs.getInt("assignee")));
				issue.setCreateBy(uDAO.getById(connection, rs.getInt("createBy")));
				issue.setModifyBy(uDAO.getById(connection, rs.getInt("modifyBy")));
				issue.setStatus(sDAO.getById(connection, rs.getInt("statusId")));
				issue.setType(tDAO.getById(connection, rs.getInt("typeId")));
				issue.setPriority(pDAO.getById(connection, rs.getInt("priorityId")));
				issue.setProject(prDAO.getById(connection, rs.getInt("projectId")));
				issue.setBuildFound(bDAO.getById(connection, rs.getInt("buildId")));
				issue.setResolution(rDAO.getById(connection, rs.getInt("resolutionId")));
				
				issues.add(issue);
			}
			return issues;
		} finally {
			DBManager.closeResultSets(rs);
			DBManager.closeStatements(ps, st);
		}
	}

	public List<Issue> getLastNRecords(Connection connection, long n) 
					throws SQLException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = connection.prepareStatement("SELECT id, createDate, createBy, modifyDate, modifyBy, summary, description, statusId, typeId, priorityId, projectId, buildId, assignee, resolutionId from issues order by id desc limit ?");
			ps.setLong(1, n);
			rs = ps.executeQuery();
			
			UserDAO uDAO = new UserDAO();
			StatusDAO sDAO = new StatusDAO();
			TypeDAO tDAO = new TypeDAO();
			PriorityDAO pDAO = new PriorityDAO();
			ProjectDAO prDAO = new ProjectDAO();
			BuildDAO bDAO = new BuildDAO();
			ResolutionDAO rDAO = new ResolutionDAO();
			
			List<Issue> issues = new ArrayList<Issue>();
			Issue issue = null;
			
			while (rs.next()) {
				issue = new Issue();
				
				issue.setId(rs.getLong("id"));
				issue.setCreateDate(rs.getTimestamp("createDate"));
				issue.setModifyDate(rs.getTimestamp("modifyDate"));
				issue.setSummary(rs.getString("summary"));
				issue.setDescription(rs.getString("description"));
				issue.setAssignee(uDAO.getById(connection, rs.getInt("assignee")));
				issue.setCreateBy(uDAO.getById(connection, rs.getInt("createBy")));
				issue.setModifyBy(uDAO.getById(connection, rs.getInt("modifyBy")));
				issue.setStatus(sDAO.getById(connection, rs.getInt("statusId")));
				issue.setType(tDAO.getById(connection, rs.getInt("typeId")));
				issue.setPriority(pDAO.getById(connection, rs.getInt("priorityId")));
				issue.setProject(prDAO.getById(connection, rs.getInt("projectId")));
				issue.setBuildFound(bDAO.getById(connection, rs.getInt("buildId")));
				issue.setResolution(rDAO.getById(connection, rs.getInt("resolutionId")));
				
				issues.add(issue);
			}
			return issues;
		} finally {
			DBManager.closeResultSets(rs);
			DBManager.closeStatements(ps);
		} 
	}

	public long getQuantityPages(Connection connection, long userId, long recordsPerPage) 
			throws SQLException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = connection.prepareStatement("select count(*) as cnt from issues where assignee=?");
			ps.setLong(1, userId);
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
		}
	}
}
