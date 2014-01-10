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
import org.training.issuetracker.dao.xml.parsers.enums.XMLTag;
import org.training.issuetracker.dao.xml.service.PriorityDAO;
import org.training.issuetracker.dao.xml.service.ProjectDAO;
import org.training.issuetracker.dao.xml.service.StatusDAO;
import org.training.issuetracker.dao.xml.service.TypeDAO;
import org.training.issuetracker.dao.xml.service.UserDAO;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class IssueParser extends DefaultHandler {
	private final static String DATE_FORMAT = "yyyy-m-d h:mm a";
	private List<Issue> issues;
	private Issue issue;
	private boolean insideTagIssues = false;
	private XMLTag tag;
	
	public List<Issue> getIssues() {
		return issues;
	}
	
	@Override
	public void characters(char[] ch, int start, int length)
			throws SAXException {
		if (insideTagIssues) {
			try {
				if (new String(ch, start, length).trim().length() == 0) {
					return;
				}
				switch (tag) {
					case SUMMARY:
						issue.setSummary(new String(ch, start, length));
						break;
					case DESCRIPTION:
						issue.setDescription(new String(ch, start, length));
						break;
					case STATUS:
						int statusId = Integer.parseInt(new String(ch, start, length));
						IStatusDAO sDAO = new StatusDAO();
						issue.setStatus(sDAO.getById(statusId));
						break;
					case TYPE:
						int typeId = Integer.parseInt(new String(ch, start, length));
						ITypeDAO tDAO = new TypeDAO();
						issue.setType(tDAO.getById(typeId));
						break;
					case PRIORITY:
						int priorityId = Integer.parseInt(new String(ch, start, length));
						IPriorityDAO pDAO = new PriorityDAO();
						issue.setPriority(pDAO.getById(priorityId));
						break;
					case PROJECT:
						int projectId = Integer.parseInt(new String(ch, start, length));
						IProjectDAO projectDAO = new ProjectDAO();
						issue.setProject(projectDAO.getById(projectId));
						break;
					case BUILD_FOUND:
						List<Build> builds = issue.getProject().getBuilds();
						int buildId = Integer.parseInt(new String(ch, start, length));
						Build build = null;
						for (Build b : builds) {
							if (buildId == b.getId()) {
								build = b;
							}
						}
						issue.setBuildFound(build);
						break;
					case ASSIGNEE:
						int userId = Integer.parseInt(new String(ch, start, length));
						IUserDAO uDAO = new UserDAO();
						issue.setAssignee(uDAO.getById(userId));
						break;
					case CREATED_BY:
						userId = Integer.parseInt(new String(ch, start, length));
						uDAO = new UserDAO();
						issue.setCreatedBy((uDAO.getById(userId)));
						break;
					case MODIFY_BY:
						userId = Integer.parseInt(new String(ch, start, length));
						uDAO = new UserDAO();
						issue.setModifyBy((uDAO.getById(userId)));
						break;
					case CREATE_DATE:
						String date = new String(ch, start, length);
						SimpleDateFormat format = new SimpleDateFormat(DATE_FORMAT);
						issue.setCreateDate(format.parse(date));
						break;
					case MODIFY_DATE:
						date = new String(ch, start, length);
						format = new SimpleDateFormat(DATE_FORMAT);
						issue.setModifyDate(format.parse(date));
						break;
					default:
						break;
				}
			} catch (Exception e) {
				throw new SAXException(e);
			}
		}
	}

	@Override
	public void endElement(String uri, String localName, String qName)
			throws SAXException {
		if (insideTagIssues) {
			if (TagConstants.TNS_ISSUETRACKER.equals(qName)) {
				tag = XMLTag.ISSUETRACKER;
			} else {
				tag = XMLTag.valueOf(qName.toUpperCase());	
			}
			switch (tag) {
			case ISSUES:
				insideTagIssues = false;
				break;
			case ISSUE:
				issues.add(issue);
				break;
			default:
				break;
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
		if (TagConstants.TNS_ISSUETRACKER.equals(qName)) {
			tag = XMLTag.ISSUETRACKER;
		} else {
			tag = XMLTag.valueOf(qName.toUpperCase());	
		}
		switch (tag) {
			case ISSUES:
				insideTagIssues = true;
				break;
			case ISSUE:
				if (insideTagIssues) {
					int id = Integer.parseInt(attributes.getValue(AttrsConstants.ID));
					issue = new Issue();
					issue.setId(id);
				}
				break;
			default: 
				break;
		}
	}
}
