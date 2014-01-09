package org.training.issuetracker.servlets.service;

import org.training.issuetracker.beans.Issue;
import org.training.issuetracker.beans.User;

public class GuestIssuePage extends Page {
	
	public GuestIssuePage(Issue issue) {
		super();
		setIssue(issue);
	}
	
	protected StringBuilder getMenu() {
		StringBuilder form = new StringBuilder();
		form.append("<form method=\"post\" action=\"dashboard\">");
		form.append("Email address: <input type=\"text\" name=\"emailAddress\"/>&nbsp;");
		form.append("Password: <input type=\"password\" name=\"password\"/>");
		form.append("<input type=\"submit\" name=\"loginBtn\" value=\"Login\"/>");
		form.append("</form>");
		form.append("<div style=\"float:left;height:26px;\"><form action=\"dashboard\">");
		form.append("<input type=\"submit\" name=\"searchIssue\" value=\"Search\"/>");
		form.append("</form></div>");
		return form;
	}
	
	protected StringBuilder getBaseContent() {
		StringBuilder content = new StringBuilder();
		Issue issue = getIssue();
		content.append("<ul>");
		content.append("<li style=\"list-style:none;\">Create date: " + issue.getFormatCreatedDate() + "</li>");
		content.append("<li style=\"list-style:none;\">Created by: " + issue.getCreatedBy().getFirstName() + " " + issue.getCreatedBy().getLastName() + "</li>");
		content.append("<li style=\"list-style:none;\">Modify date: " + issue.getFormatModifyDate() + "</li>");
		content.append("<li style=\"list-style:none;\">Created by: " + issue.getModifyBy().getFirstName() + " " + issue.getModifyBy().getLastName() + "</li>");
		content.append("<li style=\"list-style:none;\">Id: " + issue.getId() + "</li>");
		content.append("<li style=\"list-style:none;\">Summary: " + issue.getSummary() + "</li>");
		content.append("<li style=\"list-style:none;\">Description: " + issue.getDescription() + "</li>");
		content.append("<li style=\"list-style:none;\">Status: " + issue.getStatus().getValue() + "</li>");
		content.append("<li style=\"list-style:none;\">Type:" + issue.getType().getValue() + "</li>");
		content.append("<li style=\"list-style:none;\">Priority: " + issue.getPriority().getValue() + "</li>");
		content.append("<li style=\"list-style:none;\">Project: " + issue.getProject().getName() + "</li>");
		content.append("<li style=\"list-style:none;\">Build: " + issue.getBuildFound().getVersion() + "</li>");
		User assignee = issue.getAssignee();
		if (assignee == null) {
			content.append("<li style=\"list-style:none;\">Assignee: none</li>");
		} else {
			content.append("<li style=\"list-style:none;\">Assignee: " + assignee.getFirstName() 
					+ " " + assignee.getLastName() + "</li>");
		}
		content.append("</ul>");
		return content;
	}
}
