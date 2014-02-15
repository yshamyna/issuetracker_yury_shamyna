package org.training.issuetracker.db.dao.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import org.training.issuetracker.db.beans.Issue;
import org.training.issuetracker.db.beans.User;
import org.training.issuetracker.db.dao.service.constants.FieldsConstans;
import org.training.issuetracker.db.dao.service.constants.QueriesConstants;
import org.training.issuetracker.db.util.DBManager;

public class IssueDAO {
	public Issue getById(Connection connection, long id) throws SQLException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
	        ps = connection.prepareStatement(QueriesConstants.ISSUE_SELECT_BY_ID);
	        ps.setLong(1, id);
	        rs = ps.executeQuery();
	        if (rs.next()) {
	        	Issue issue = new Issue();
	        	issue.setId(id);
	        	issue.setCreateDate(rs.getTimestamp(FieldsConstans.CREATE_DATE));
				issue.setModifyDate(rs.getTimestamp(FieldsConstans.MODIFY_DATE));
				issue.setSummary(rs.getString(FieldsConstans.SUMMARY));
				issue.setDescription(rs.getString(FieldsConstans.DESCRIPTION));
				
				UserDAO userDAO = new UserDAO();
				issue.setAssignee(userDAO.getById(connection, 
						rs.getInt(FieldsConstans.ASSIGNEE)));
				issue.setCreateBy(userDAO.getById(connection,
						rs.getInt(FieldsConstans.CREATE_BY)));
				issue.setModifyBy(userDAO.getById(connection,
						rs.getInt(FieldsConstans.MODIFY_BY)));
				
				StatusDAO statusDAO = new StatusDAO();
				issue.setStatus(statusDAO.getById(connection, 
						rs.getInt(FieldsConstans.STATUS_ID)));
				
				TypeDAO typeDAO = new TypeDAO();
				issue.setType(typeDAO.getById(connection, 
						rs.getInt(FieldsConstans.TYPE_ID)));
				
				PriorityDAO priorityDAO = new PriorityDAO();
				issue.setPriority(priorityDAO.getById(connection, 
						rs.getInt(FieldsConstans.PRIORITY_ID)));
				
				ProjectDAO projectDAO = new ProjectDAO();
				issue.setProject(projectDAO.getById(connection, 
						rs.getInt(FieldsConstans.PROJECT_ID)));
				
				BuildDAO buildDAO = new BuildDAO();
				issue.setBuildFound(buildDAO.getById(connection, 
						rs.getInt(FieldsConstans.BUILD_ID)));
				
				ResolutionDAO resolutionDAO = new ResolutionDAO();
				issue.setResolution(resolutionDAO.getById(connection, 
						rs.getInt(FieldsConstans.RESOLUTION_ID)));
				
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
			ps = connection.prepareStatement(QueriesConstants.ISSUE_ADD);
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
			ps = connection.prepareStatement(QueriesConstants.
								ISSUE_SELECT_BY_USER_ID);
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
				
				issue.setId(rs.getLong(FieldsConstans.ID));
				issue.setCreateDate(rs.getTimestamp(FieldsConstans.CREATE_DATE));
				issue.setModifyDate(rs.getTimestamp(FieldsConstans.MODIFY_DATE));
				issue.setSummary(rs.getString(FieldsConstans.SUMMARY));
				issue.setDescription(rs.getString(FieldsConstans.DESCRIPTION));
				issue.setAssignee(uDAO.getById(connection, 
						rs.getInt(FieldsConstans.ASSIGNEE)));
				issue.setCreateBy(uDAO.getById(connection, 
						rs.getInt(FieldsConstans.CREATE_BY)));
				issue.setModifyBy(uDAO.getById(connection,
						rs.getInt(FieldsConstans.MODIFY_BY)));
				issue.setStatus(sDAO.getById(connection, 
						rs.getInt(FieldsConstans.STATUS_ID)));
				issue.setType(tDAO.getById(connection,
						rs.getInt(FieldsConstans.TYPE_ID)));
				issue.setPriority(pDAO.getById(connection, 
						rs.getInt(FieldsConstans.PRIORITY_ID)));
				issue.setProject(prDAO.getById(connection,
						rs.getInt(FieldsConstans.PROJECT_ID)));
				issue.setBuildFound(bDAO.getById(connection, 
						rs.getInt(FieldsConstans.BUILD_ID)));
				issue.setResolution(rDAO.getById(connection, 
						rs.getInt(FieldsConstans.RESOLUTION_ID)));
				
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
			ps = connection.prepareStatement(QueriesConstants.ISSUE_UPDATE);
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
			if (issue.getResolution() == null) {
				ps.setNull(11, Types.INTEGER);
			} else {
				ps.setLong(11, issue.getResolution().getId());
			}
			ps.setLong(12, issue.getId());
			ps.executeUpdate();
		} finally {
			DBManager.closeStatements(ps);
		}
	}

	public List<Issue> getNRecordsFromPageM(Connection connection, 
			User user, long pageNumber, long recordsPerPage)
								throws SQLException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = connection.prepareStatement(QueriesConstants.ISSUE_COUNT);
			ps.setLong(1, user.getId());
			rs = ps.executeQuery();
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
							ISSUE_N_RECORDS_FROM_PAGE_M);
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
				
				issue.setId(rs.getLong(FieldsConstans.ID));
				issue.setCreateDate(rs.getTimestamp(FieldsConstans.CREATE_DATE));
				issue.setModifyDate(rs.getTimestamp(FieldsConstans.MODIFY_DATE));
				issue.setSummary(rs.getString(FieldsConstans.SUMMARY));
				issue.setDescription(rs.getString(FieldsConstans.DESCRIPTION));
				issue.setAssignee(user);
				issue.setCreateBy(uDAO.getById(connection, 
						rs.getInt(FieldsConstans.CREATE_BY)));
				issue.setModifyBy(uDAO.getById(connection,
						rs.getInt(FieldsConstans.MODIFY_BY)));
				issue.setStatus(sDAO.getById(connection, 
						rs.getInt(FieldsConstans.STATUS_ID)));
				issue.setType(tDAO.getById(connection,
						rs.getInt(FieldsConstans.TYPE_ID)));
				issue.setPriority(pDAO.getById(connection, 
						rs.getInt(FieldsConstans.PRIORITY_ID)));
				issue.setProject(prDAO.getById(connection,
						rs.getInt(FieldsConstans.PROJECT_ID)));
				issue.setBuildFound(bDAO.getById(connection, 
						rs.getInt(FieldsConstans.BUILD_ID)));
				issue.setResolution(rDAO.getById(connection, 
						rs.getInt(FieldsConstans.RESOLUTION_ID)));
				
				issues.add(issue);
			}
			return issues;
		} finally {
			DBManager.closeResultSets(rs);
			DBManager.closeStatements(ps);
		}
	}

	public List<Issue> getLastNRecords(Connection connection, long n) 
					throws SQLException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = connection.prepareStatement(QueriesConstants.
							ISSUE_LAST_N_RECORDS);
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
				
				issue.setId(rs.getLong(FieldsConstans.ID));
				issue.setCreateDate(rs.getTimestamp(FieldsConstans.CREATE_DATE));
				issue.setModifyDate(rs.getTimestamp(FieldsConstans.MODIFY_DATE));
				issue.setSummary(rs.getString(FieldsConstans.SUMMARY));
				issue.setDescription(rs.getString(FieldsConstans.DESCRIPTION));
				issue.setAssignee(uDAO.getById(connection, 
						rs.getInt(FieldsConstans.ASSIGNEE)));
				issue.setCreateBy(uDAO.getById(connection, 
						rs.getInt(FieldsConstans.CREATE_BY)));
				issue.setModifyBy(uDAO.getById(connection,
						rs.getInt(FieldsConstans.MODIFY_BY)));
				issue.setStatus(sDAO.getById(connection, 
						rs.getInt(FieldsConstans.STATUS_ID)));
				issue.setType(tDAO.getById(connection,
						rs.getInt(FieldsConstans.TYPE_ID)));
				issue.setPriority(pDAO.getById(connection, 
						rs.getInt(FieldsConstans.PRIORITY_ID)));
				issue.setProject(prDAO.getById(connection,
						rs.getInt(FieldsConstans.PROJECT_ID)));
				issue.setBuildFound(bDAO.getById(connection, 
						rs.getInt(FieldsConstans.BUILD_ID)));
				issue.setResolution(rDAO.getById(connection, 
						rs.getInt(FieldsConstans.RESOLUTION_ID)));
				
				issues.add(issue);
			}
			return issues;
		} finally {
			DBManager.closeResultSets(rs);
			DBManager.closeStatements(ps);
		} 
	}

	public long getQuantityPages(Connection connection, long userId, 
			long recordsPerPage) throws SQLException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = connection.prepareStatement(QueriesConstants.
							ISSUE_ALL_COUNT);
			ps.setLong(1, userId);
			rs = ps.executeQuery();
			
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
			DBManager.closeStatements(ps);
		}
	}
}
