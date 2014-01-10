package org.training.issuetracker.servlets.service.contents;

import java.util.List;

import org.training.issuetracker.beans.Issue;
import org.training.issuetracker.beans.User;
import org.training.issuetracker.servlets.service.intefaces.IContent;

public class DashboardContent implements IContent {
	
	private List<Issue> issues;
	private int currentPage;
	private int allPages;
	
	public DashboardContent(List<Issue> issues, int currentPage, int allPages) {
		this.issues = issues;
		this.currentPage = currentPage;
		this.allPages = allPages;
	}

	@Override
	public StringBuilder getValue() {
		StringBuilder content = new StringBuilder();
		content.append(getTable(issues));
		content.append(getPageNavigationForm());
		return content;
	}
	
	private StringBuilder getTable(List<Issue> issues) {
		if (issues == null) {
			return new StringBuilder("<i>Error reading issue.</i>");
		}
		if (issues.size() == 0) {
			return new StringBuilder("Issues not found.");
		}
		StringBuilder table = new StringBuilder();
		table.append("<table border=\"1\" style=\"width:100%\">");
		table.append("<caption>Issues</caption>");
		table.append("<th>id <a href=\"dashboard?column=id_asc&currentPage=" 
				+currentPage + "\">Asc</a>&nbsp;<a href=\"dashboard?column=id_desc&currentPage="
				+currentPage + "\">Desc</a></th>");
		table.append("<th>priority <a href=\"dashboard?column=priority_asc&currentPage=" 
				+currentPage + "\">Asc</a>&nbsp;<a href=\"dashboard?column=priority_desc&currentPage="
				+currentPage + "\">Desc</a></th>");
		table.append("<th>assignee <a href=\"dashboard?column=assignee_asc&currentPage=" 
				+currentPage + "\">Asc</a>&nbsp;<a href=\"dashboard?column=assignee_desc&currentPage="
				+currentPage + "\">Desc</a></th>");
		table.append("<th>type <a href=\"dashboard?column=type_asc&currentPage=" 
				+currentPage + "\">Asc</a>&nbsp;<a href=\"dashboard?column=type_desc&currentPage="
				+currentPage + "\">Desc</a></th>");
		table.append("<th>status <a href=\"dashboard?column=status_asc&currentPage=" 
				+currentPage + "\">Asc</a>&nbsp;<a href=\"dashboard?column=status_desc&currentPage="
				+currentPage + "\">Desc</a></th>");
		table.append("<th>summary <a href=\"dashboard?column=summary_asc&currentPage=" 
				+currentPage + "\">Asc</a>&nbsp;<a href=\"dashboard?column=summary_desc&currentPage="
				+currentPage + "\">Desc</a></th>");
		User assignee = null;
		for (Issue issue : issues) {
			table.append("<tr>");
			table.append("<td><a href=\"editissue?id=" + issue.getId() +"\">" + issue.getId() + "</a></td>");
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
		navigator.append("<form method=\"post\" action=\"dashboard?action=previous_page&currentPage=" 
							+ currentPage + "\">");
		navigator.append("<input type=\"submit\" name=\"previousPage\" value=\"Previous\"/>");
		navigator.append("</form>");
		navigator.append("<form method=\"post\" action=\"dashboard?action=next_page&currentPage=" 
							+ currentPage + "\">");
		navigator.append("<input type=\"submit\" name=\"nextPage\" value=\"Next\"/>");
		navigator.append("</form>");
		return navigator;
	}

}
