package org.training.issuetracker.servlets.service;

import java.util.List;

import org.training.issuetracker.beans.Issue;
import org.training.issuetracker.beans.User;

public class UserPage extends Page {
	private User user;
	
	public UserPage(Issue issue) {
		super();
		setIssue(issue);
	}
	
	public UserPage(User user, List<Issue> issues, int currentPage, int allPages) {
		super();
		this.user = user;
		setIssues(issues);
		setCurrentPage(currentPage);
		setAllPages(allPages);
	}
	
	protected String getLink() {
		return "<link rel=\"stylesheet\" type=\"text/css\" href=\"menu.css\">";
	}
	
	protected StringBuilder getMenu() {
		StringBuilder form = new StringBuilder();
		form.append("<div class=\"color-menu\"><div>User: " 
				+ user.getFirstName() + " " + user.getLastName() 
				+ "</div></div>");
		form.append("<ul class=\"color-menu\">");
		form.append("<li><a href=\"\">Edit</a></li>");
		form.append("<li><a href=\"\">Search issue</a></li>")	;
		form.append("<li><a href=\"\">Submit issue</a></li>");
		form.append("<li><a href=\"dashboard?action=logout\">Logout</a></li></ul>");
		return form;
	}
}
