package org.training.issuetracker.servlets.service;

import org.training.issuetracker.beans.Issue;
import org.training.issuetracker.beans.User;

public class UserIssuePage extends Page {
	private User user;
	
	public UserIssuePage(User user, Issue issue) {
		super();
		this.user = user;
		setIssue(issue);
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
	
	protected StringBuilder getBaseContent() {
		Issue issue = getIssue();
		StringBuilder content = new StringBuilder();
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
		try {
			content.append(getStatusHTML());
			content.append(getTypesHTML());
			content.append(getPriorityHTML());
			content.append(getProjectsAndBuildsHTML());
			content.append(getUsersHTML());
		} catch (Exception e) {
			e.printStackTrace();
		}
		content.append("</ul>");
		return content;
	}
}
