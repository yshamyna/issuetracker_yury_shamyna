package org.training.issuetracker.servlets.service;

import java.util.List;

import org.training.issuetracker.beans.Issue;
import org.training.issuetracker.beans.User;

public class AdminPage extends Page {
	private User user;
	
	public AdminPage(User user, List<Issue> issues, int currentPage, int allPages) {
		super();
		this.user = user;
		setIssues(issues);
		setCurrentPage(currentPage);
		setAllPages(allPages);
	}
	
	protected String getLink() {
		return "<link rel=\"stylesheet\" type=\"text/css\" href=\"menu.css\">";
	}
	
	protected StringBuilder getForm() {
		StringBuilder form = new StringBuilder();
		form.append("<div class=\"color-menu\"><div>User: " 
				+ user.getFirstName() + " " + user.getLastName() 
				+ "</div></div>");
		form.append("<ul class=\"color-menu\">");
		form.append("<li><a href=\"\">Edit</a></li>");
		form.append("<li><a href=\"\">Search issue</a></li>")	;
		form.append("<li><a href=\"\">Submit issue</a></li>");
		form.append("<li><a href=\"\">Review</a>");
		form.append("<ul>");	
		form.append("<li><a href=\"\">Projects</a></li>");
		form.append("<li><a href=\"\">Statuses</a></li>");		
		form.append("<li><a href=\"\">Resolutions</a></li>");			
		form.append("<li><a href=\"\">Priorities</a></li>");			
		form.append("<li><a href=\"\">Types</a></li>");
		form.append("</ul></li>");
		form.append("<li><a href=\"\">Search user</a></li>");
		form.append("<li><a href=\"\">Add</a>");
		form.append("<ul>");
		form.append("<li><a href=\"\">Project</a></li>");
		form.append("<li><a href=\"\">User</a></li>");		
		form.append("<li><a href=\"\">Resolution</a></li>");		
		form.append("<li><a href=\"\">Priority</a></li>");			
		form.append("<li><a href=\"\">Type</a></li>");			
		form.append("</ul>");			
		form.append("</li>");			
		form.append("<li><a href=\"dashboard?action=logout\">Logout</a></li></ul>");
		return form;
	}
}
