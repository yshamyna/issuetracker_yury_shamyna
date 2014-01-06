package org.training.issuetracker.dao.xml.parsers;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.training.issuetracker.beans.Build;
import org.training.issuetracker.beans.Issue;
import org.training.issuetracker.dao.interfaces.IPriorityDAO;
import org.training.issuetracker.dao.interfaces.IProjectDAO;
import org.training.issuetracker.dao.interfaces.IStatusDAO;
import org.training.issuetracker.dao.interfaces.ITypeDAO;
import org.training.issuetracker.dao.interfaces.IUserDAO;
import org.training.issuetracker.dao.xml.constants.AttrsConstants;
import org.training.issuetracker.dao.xml.constants.TagConstants;
import org.training.issuetracker.dao.xml.service.PriorityDAO;
import org.training.issuetracker.dao.xml.service.ProjectDAO;
import org.training.issuetracker.dao.xml.service.StatusDAO;
import org.training.issuetracker.dao.xml.service.TypeDAO;
import org.training.issuetracker.dao.xml.service.UserDAO;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class IssueParser extends DefaultHandler {
	private List<Issue> issues;
	private Issue issue;
	private boolean insideTagIssues = false;
	private String currentTag;
	
	public List<Issue> getIssues() {
		return issues;
	}
	
	@Override
	public void characters(char[] ch, int start, int length)
			throws SAXException {
		if (insideTagIssues) {
			try {
				if (TagConstants.SUMMARY.equals(currentTag)) {
					issue.setSummary(new String(ch, start, length));
				} else if (TagConstants.DESCRIPTION.equals(currentTag)) {
					issue.setDescription(new String(ch, start, length));
				} else if (TagConstants.STATUS.equals(currentTag)) {
					int statusId = Integer.parseInt(new String(ch, start, length));
					IStatusDAO sDAO = new StatusDAO();
					issue.setStatus(sDAO.getById(statusId));
				} else if (TagConstants.TYPE.equals(currentTag)) {
					int typeId = Integer.parseInt(new String(ch, start, length));
					ITypeDAO tDAO = new TypeDAO();
					issue.setType(tDAO.getById(typeId));
				} else if (TagConstants.PRIORITY.equals(currentTag)) {
					int priorityId = Integer.parseInt(new String(ch, start, length));
					IPriorityDAO pDAO = new PriorityDAO();
					issue.setPriority(pDAO.getById(priorityId));
				} else if (TagConstants.PROJECT.equals(currentTag)) {
					int projectId = Integer.parseInt(new String(ch, start, length));
					IProjectDAO pDAO = new ProjectDAO();
					issue.setProject(pDAO.getById(projectId));
				} else if (TagConstants.BUILD_FOUND.equals(currentTag)) {
					List<Build> builds = issue.getProject().getBuilds();
					int buildId = Integer.parseInt(new String(ch, start, length));
					Build build = null;
					for (Build b : builds) {
						if (buildId == b.getId()) {
							build = b;
						}
					}
					issue.setBuildFound(build);
				} else if (TagConstants.ASSIGNEE.equals(currentTag)) {
					int userId = Integer.parseInt(new String(ch, start, length));
					IUserDAO uDAO = new UserDAO();
					issue.setAssignee(uDAO.getById(userId));
				} else if (TagConstants.CREATE_BY.equals(currentTag)) {
					int userId = Integer.parseInt(new String(ch, start, length));
					IUserDAO uDAO = new UserDAO();
					issue.setCreatedBy((uDAO.getById(userId)));
				} else if (TagConstants.MODIFY_BY.equals(currentTag)) {
					int userId = Integer.parseInt(new String(ch, start, length));
					IUserDAO uDAO = new UserDAO();
					issue.setModifyBy((uDAO.getById(userId)));
				} else if (TagConstants.CREATE_DATE.equals(currentTag)) {
					String date = new String(ch, start, length);
					SimpleDateFormat format = new SimpleDateFormat("yyyy-m-d h:mm a");
					issue.setCreateDate(format.parse(date));
				} else if (TagConstants.MODIFY_DATE.equals(currentTag)) {
					String date = new String(ch, start, length);
					SimpleDateFormat format = new SimpleDateFormat("yyyy-m-d h:mm a");
					issue.setModifyDate(format.parse(date));
				}
			} catch (Exception e) {
				throw new SAXException(e);
			}
			currentTag = null;
		}
	}

	@Override
	public void endElement(String uri, String localName, String qName)
			throws SAXException {
		if (insideTagIssues) {
			if (TagConstants.ISSUES.equals(qName)) {
				insideTagIssues = false;
			} else if (TagConstants.ISSUE.equals(qName)) {
				issues.add(issue);
			}
			
		}
	}

	@Override
	public void startDocument() throws SAXException {
		issues = new ArrayList<Issue>();
	}

	@Override
	public void startElement(String uri, String localName, String qName,
			Attributes attributes) throws SAXException {
		if (TagConstants.ISSUES.equals(qName)) {
			insideTagIssues = true;
		}
		if (TagConstants.ISSUE.equals(qName) && insideTagIssues) {
			int id = Integer.parseInt(attributes.getValue(AttrsConstants.ID));
			issue = new Issue();
			issue.setId(id);
		}
		currentTag = qName;
	}

}
