package org.training.issuetracker.servlets.service;

import java.util.List;

import org.training.issuetracker.beans.Issue;

public class GuestPage extends Page {
	private String msg;
	
	public void setMessage(String message) {
		msg = message;
	}
	
	public GuestPage(Issue issue) {
		super();
		setIssue(issue);
	}
	
	public GuestPage(List<Issue> issues, int currentPage, int allPages) {
		super();
		setIssues(issues);
		setCurrentPage(currentPage);
		setAllPages(allPages);
	}
	
	protected StringBuilder getMenu() {
		StringBuilder form = new StringBuilder();
		form.append("<form method=\"post\" action=\"dashboard\">");
		form.append("Email address: <input type=\"text\" name=\"emailAddress\"/>&nbsp;");
		form.append("Password: <input type=\"password\" name=\"password\"/>");
		form.append("<input type=\"submit\" name=\"loginBtn\" value=\"Login\"/>");
		form.append("</form>");
		if (msg != null) {
			form.append("<div style=\"font-size:10pt;color:red;\">" + msg + "</div>");
		}
		form.append("<div style=\"float:left;height:26px;\"><form action=\"dashboard\">");
		form.append("<input type=\"submit\" name=\"searchIssue\" value=\"Search\"/>");
		form.append("</form></div>");
		return form;
	}
}
