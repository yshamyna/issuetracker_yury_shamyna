package org.training.issuetracker.servlets.service.contents;

import org.training.issuetracker.beans.Issue;
import org.training.issuetracker.beans.User;
import org.training.issuetracker.servlets.service.intefaces.IContent;

public class GuestReviewIssue implements IContent {
	private Issue issue;
	
	public GuestReviewIssue(Issue issue) {
		this.issue = issue;
	}

	@Override
	public StringBuilder getValue() {
		StringBuilder content = new StringBuilder();
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
