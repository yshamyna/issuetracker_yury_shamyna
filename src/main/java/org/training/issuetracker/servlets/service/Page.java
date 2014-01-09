package org.training.issuetracker.servlets.service;

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

public abstract class Page {
	private StringBuilder page;
	private List<Issue> issues = null;
	private Issue issue = null;
	private int currentPage;
	private int allPages;
	
	protected Issue getIssue() {
		return issue;
	}
	
	protected void setIssue(Issue issue) {
		this.issue = issue;
	}
	
	protected void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}
	
	protected void setAllPages(int allPages) {
		this.allPages = allPages;
	}

	protected void setIssues(List<Issue> issues) {
		this.issues = issues;
	}
	
	public StringBuilder getPage() {
		page = new StringBuilder();
		page.append("<!DOCTYPE html>");
		page.append("<html style=\"font-family:arial;\">");
		page.append("<title>Issue tracker</title>");
		page.append(getLink());
		page.append("<body>");
		page.append("<table border=\"1\" style=\"width:100%\">");
		page.append("<tr><td>");
		page.append(getMenu());
		page.append("</td></tr><tr><td>");
		page.append(getBaseContent());
		page.append("</td><tr></body></html>");
		return page;
	}
	
	protected String getLink() {
		return "";
	}
	
	protected StringBuilder getMenu() {
		return new StringBuilder("");
	}
	
	private StringBuilder getTable(List<Issue> issues) {
		if (issues.size() == 0) {
			return new StringBuilder("Issues not found.");
		}
		StringBuilder table = new StringBuilder();
		table.append("<table border=\"1\" style=\"width:100%\">");
		table.append("<caption>Issues</caption>");
		table.append("<th>id</th><th>priority</th><th>assignee</th><th>type</th><th>status</th><th>summary</th>");
		User assignee = null;
		for (Issue issue : issues) {
			table.append("<tr>");
			table.append("<td><a href=\"issue?id=" + issue.getId() +"\">" + issue.getId() + "</a></td>");
			table.append("<td>" + issue.getPriority().getValue() + "</td>");
			assignee = issue.getAssignee();
			if (assignee == null) {
				table.append("<td>none</td>");
			} else {
				table.append("<td>" + assignee.getFirstName() 
						+ " " + assignee.getLastName() 
						+ "</td>");
			}
			table.append("<td>" + issue.getType().getValue() + "</td>");
			table.append("<td>" + issue.getStatus().getValue() + "</td>");
			table.append("<td>" + issue.getSummary() + "</td>");
			table.append("</tr>");
		}
		table.append("</table>");
		return table;
	}
	
	private StringBuilder getPageNavigationForm() {
		StringBuilder navigator = new StringBuilder();
		navigator.append("<div>Page: "
				+ currentPage 
				+ "/" + allPages + "</div>");
		navigator.append("<form method=\"post\" action=\"dashboard?currentPage=" 
							+ currentPage + "\">");
		navigator.append("<input type=\"submit\" name=\"previousPage\" value=\"Previous\"/>");
		navigator.append("</form>");
		navigator.append("<form method=\"post\" action=\"dashboard?currentPage=" 
							+ currentPage + "\">");
		navigator.append("<input type=\"submit\" name=\"nextPage\" value=\"Next\"/>");
		navigator.append("</form>");
		return navigator;
	}
	
	protected StringBuilder getBaseContent() {
		StringBuilder content = new StringBuilder(getTable(issues));
		content.append(getPageNavigationForm());
		return content;
	}
	
	protected StringBuilder getStatusHTML() throws Exception {
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
	
	
	protected StringBuilder getTypesHTML() throws Exception {
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
	
	protected StringBuilder getPriorityHTML() throws Exception {
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
	
	protected StringBuilder getProjectsAndBuildsHTML() throws Exception {
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
	
	protected StringBuilder getUsersHTML() throws Exception {
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
