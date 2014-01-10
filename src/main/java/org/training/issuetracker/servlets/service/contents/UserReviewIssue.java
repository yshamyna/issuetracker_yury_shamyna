package org.training.issuetracker.servlets.service.contents;

import java.util.List;

import org.training.issuetracker.beans.Build;
import org.training.issuetracker.beans.Issue;
import org.training.issuetracker.beans.IssuePriority;
import org.training.issuetracker.beans.IssueStatus;
import org.training.issuetracker.beans.IssueType;
import org.training.issuetracker.beans.Project;
import org.training.issuetracker.beans.User;
import org.training.issuetracker.dao.interfaces.IPriorityDAO;
import org.training.issuetracker.dao.interfaces.IProjectDAO;
import org.training.issuetracker.dao.interfaces.IStatusDAO;
import org.training.issuetracker.dao.interfaces.ITypeDAO;
import org.training.issuetracker.dao.interfaces.IUserDAO;
import org.training.issuetracker.dao.xml.service.PriorityDAO;
import org.training.issuetracker.dao.xml.service.ProjectDAO;
import org.training.issuetracker.dao.xml.service.StatusDAO;
import org.training.issuetracker.dao.xml.service.TypeDAO;
import org.training.issuetracker.dao.xml.service.UserDAO;
import org.training.issuetracker.servlets.service.constants.MessagesConstants;
import org.training.issuetracker.servlets.service.intefaces.IContent;

public class UserReviewIssue implements IContent {
	
	private Issue issue;
	
	public UserReviewIssue(Issue issue) {
		this.issue = issue;
	}

	@Override
	public StringBuilder getValue() {
		StringBuilder content = new StringBuilder();
		try {
			content.append("<ul>");
			content.append("<li style=\"list-style:none;\">Create date: " 
						+ issue.getFormatCreatedDate() + "</li>");
			content.append("<li style=\"list-style:none;\">Created by: " 
						+ issue.getCreatedBy().getFirstName() + " " 
						+ issue.getCreatedBy().getLastName() + "</li>");
			content.append("<li style=\"list-style:none;\">Modify date: " 
						+ issue.getFormatModifyDate() + "</li>");
			content.append("<li style=\"list-style:none;\">Created by: " 
						+ issue.getModifyBy().getFirstName() + " " 
					+ issue.getModifyBy().getLastName() + "</li>");
			content.append(getStatusHTML());
			content.append(getTypesHTML());
			content.append(getPriorityHTML());
			content.append(getProjectsAndBuildsHTML());
			content.append(getUsersHTML());
			content.append("</ul>");
		} catch (Exception e) {
			content = new StringBuilder(MessagesConstants.READ_ERROR);
		}
		return content;
	}
	
	private StringBuilder getStatusHTML() throws Exception {
		StringBuilder statusesHTML = new StringBuilder();
		IStatusDAO statusDAO = new StatusDAO();
		List<IssueStatus> statuses = statusDAO.getAll();
		statusesHTML.append("<li style=\"list-style:none;\">Status: <select name=\"statuses\">");
		for (IssueStatus status : statuses) {
			if (issue.getStatus().getId() == status.getId()) {
				statusesHTML.append("<option value=\"" + status.getValue() 
						+ "\" selected>" + status.getValue() 
						+ "</option>");
			} else {
				statusesHTML.append("<option value=\""+ status.getValue() + "\">" + status.getValue() 
						+ "</option>");
			}
		}
		statusesHTML.append("</select></li>");
		return statusesHTML;
	}
	
	private StringBuilder getTypesHTML() throws Exception {
		StringBuilder typesHTML = new StringBuilder();
		ITypeDAO typeDAO = new TypeDAO();
		List<IssueType> types = typeDAO.getAll();
		typesHTML.append("<li style=\"list-style:none;\">Type: <select name=\"types\">");
		for (IssueType type : types) {
			if (issue.getType().getId() == type.getId()) {
				typesHTML.append("<option value=\"" + type.getValue() 
						+ "\" selected>" + type.getValue() 
						+ "</option>");
			} else {
				typesHTML.append("<option value=\""+ type.getValue() + "\">" 
							+ type.getValue() + "</option>");
			}
		}
		typesHTML.append("</select></li>");
		return typesHTML;
	}
	
	private StringBuilder getPriorityHTML() throws Exception {
		StringBuilder prioritiesHTML = new StringBuilder();
		IPriorityDAO priorityDAO = new PriorityDAO();
		List<IssuePriority> priorities = priorityDAO.getAll();
		prioritiesHTML.append("<li style=\"list-style:none;\">Priority: <select name=\"priorities\">");
		for (IssuePriority priority : priorities) {
			if (issue.getPriority().getId() == priority.getId()) {
				prioritiesHTML.append("<option value=\"" + priority.getValue() 
						+ "\" selected>" + priority.getValue() 
						+ "</option>");
			} else {
				prioritiesHTML.append("<option value=\""+ priority.getValue() + "\">" 
							+ priority.getValue() + "</option>");
			}
		}
		prioritiesHTML.append("</select></li>");
		return prioritiesHTML;
	}
	
	private StringBuilder getProjectsAndBuildsHTML() throws Exception {
		StringBuilder projectsAndBuildsHTML = new StringBuilder();
		IProjectDAO projectDAO = new ProjectDAO();
		List<Project> projects = projectDAO.getAll();
		Project currentProject = null;
		projectsAndBuildsHTML.append("<li style=\"list-style:none;\">Project: <select name=\"projects\">");
		for (Project project : projects) {
			if (issue.getProject().getId() == project.getId()) {
				projectsAndBuildsHTML.append("<option value=\"" + project.getName() 
						+ "\" selected>" + project.getName() 
						+ "</option>");
				currentProject = project;
			} else {
				projectsAndBuildsHTML.append("<option value=\""+ project.getName() + "\">" 
							+ project.getName() + "</option>");
			}
		}
		projectsAndBuildsHTML.append("</select></li>");
		List<Build> builds = currentProject.getBuilds();
		projectsAndBuildsHTML.append("<li style=\"list-style:none;\">Build found: <select name=\"builds\">");
		for (Build build : builds) {
			if (currentProject.getBuild().getId() == build.getId()) {
				projectsAndBuildsHTML.append("<option value=\"" + build.getVersion() 
						+ "\" selected>" + build.getVersion() 
						+ "</option>");
			} else {
				projectsAndBuildsHTML.append("<option value=\""+ build.getVersion() + "\">" 
							+ build.getVersion() + "</option>");
			}
		}
		projectsAndBuildsHTML.append("</select></li>");
		return projectsAndBuildsHTML;
	}
	
	private StringBuilder getUsersHTML() throws Exception {
		StringBuilder usersHTML = new StringBuilder();
		IUserDAO userDAO = new UserDAO();
		List<User> users = userDAO.getAll();
		usersHTML.append("<li style=\"list-style:none;\">User: <select name=\"users\">");
		User assignee = issue.getAssignee();
		if (assignee == null) {
			usersHTML.append("<option value=\"none\" selected>none</option>");
		}
		for (User u : users) {
			if (assignee.getId() == u.getId()) {
				usersHTML.append("<option value=\"" + u.getFirstName() + "_" + u.getLastName() 
						+ "\" selected>" + u.getFirstName() + " " + u.getLastName() 
						+ "</option>");
			} else {
				usersHTML.append("<option value=\""+ u.getFirstName() + "_" + u.getLastName() + "\">" 
							+ u.getFirstName() + " " + u.getLastName() + "</option>");
			}
		}
		usersHTML.append("</select></li>");
		return usersHTML;
	}

}
