package org.training.issuetracker.servlets.service;

import java.util.List;

import org.training.issuetracker.beans.Issue;
import org.training.issuetracker.beans.User;

public abstract class Page {
	private StringBuilder page;
	private List<Issue> issues = null;
	private int currentPage;
	private int allPages;

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}
	
	public void setAllPages(int allPages) {
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
		page.append(getForm());
		page.append("</td></tr><tr><td>");
		page.append(getTable(issues));
		page.append(getPageNavigationForm());
		page.append("</td><tr>");
		page.append("</body>");
		page.append("</html>");
		return page;
	}
	
	protected String getLink() {
		return "";
	}
	
	protected StringBuilder getForm() {
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
	
}
