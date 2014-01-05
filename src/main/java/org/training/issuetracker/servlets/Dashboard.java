package org.training.issuetracker.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.util.List;

import javax.servlet.Servlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.training.issuetracker.beans.Issue;
import org.training.issuetracker.beans.User;
import org.training.issuetracker.dao.interfaces.IIssueDAO;
import org.training.issuetracker.dao.xml.parsers.Parser;
import org.training.issuetracker.dao.xml.service.IssueDAO;

public class Dashboard implements Servlet {
	
	private ServletConfig servletConfig;

	@Override
	public void destroy() {
	}

	@Override
	public ServletConfig getServletConfig() {
		return servletConfig;
	}

	@Override
	public String getServletInfo() {
		return null;
	}

	@Override
	public void init(ServletConfig config) throws ServletException {
		servletConfig = config;
		try {
			Parser.setURL(config.getServletContext().getResource("issuetracker.xml"));
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void service(ServletRequest request, ServletResponse response)
			throws ServletException, IOException {
		HttpServletRequest req = (HttpServletRequest) request;
		User user = (User) req.getSession().getAttribute("user");
		PrintWriter htmlWriter = response.getWriter();
		htmlWriter.append("<!DOCTYPE html>");
		htmlWriter.append("<html style=\"font-family:arial;\">");
		if (user != null && "administrator".equals(user.getRole().getValue())) {
			htmlWriter.append("<head><title>Issuetracker</title><link rel=\"stylesheet\" type=\"text/css\" href=\"adminMenu.css\"></head>");
		}
		htmlWriter.append("<body>");
		htmlWriter.append("<table border=\"1\" style=\"width:100%\">");
		htmlWriter.append("<tr>");
		htmlWriter.append("<td>");
		
		if (user == null) {
			htmlWriter.append("<form method=\"post\" action=\"dashboard\">");
			htmlWriter.append("Email address: <input type=\"text\" name=\"emailAddress\"/>&nbsp;");
			htmlWriter.append("Password: <input type=\"password\" name=\"password\"/>");
			htmlWriter.append("<input type=\"submit\" name=\"loginBtn\" value=\"Login\"/>");
			htmlWriter.append("</form>");
			String msg = (String)request.getAttribute("errorMessage");
			if (msg != null) {
				htmlWriter.append("<div style=\"font-size:10pt;color:red;\">" + msg + "</div>");
			}
			htmlWriter.append("<div style=\"float:left;height:26px;\"><form action=\"dashboard\">");
			htmlWriter.append("<input type=\"submit\" name=\"searchIssue\" value=\"Search\"/>");
			htmlWriter.append("</form></div>");
		} else {
			if (user.getRole().getValue().equals("user")) {
				htmlWriter.append("<div><b>User: " + user.getFirstName() + " " + user.getLastName() + "</b></div>");
				htmlWriter.append("<div style=\"float:left;\"><a href=\"#\" onclick=\"return(false)\">Edit</a></div>");
				htmlWriter.append("<div style=\"float:left;height:26px;\"><form method=\"post\" action=\"dashboard\">");
				htmlWriter.append("<input type=\"submit\" name=\"logoutBtn\" value=\"Logout\">");
				htmlWriter.append("</form></div>");
				htmlWriter.append("<div style=\"float:left;height:26px;\"><form method=\"post\" action=\"dashboard\">");
				htmlWriter.append("<input type=\"submit\" name=\"addIssue\" value=\"Submit issue\">");
				htmlWriter.append("</form></div>");
				htmlWriter.append("<div style=\"float:left;height:26px;\"><form action=\"dashboard\">");
				htmlWriter.append("<input type=\"submit\" name=\"searchIssue\" value=\"Search\"/>");
				htmlWriter.append("</form></div>");
			} else if (user.getRole().getValue().equals("administrator")) {
				htmlWriter.append("<div class=\"color-menu\"><div>User: " 
								+ user.getFirstName() + " " + user.getLastName() 
								+ "</div></div>");
				htmlWriter.append("<ul class=\"color-menu\">");
				htmlWriter.append("<li><a href=\"\">Edit</a></li>");
				htmlWriter.append("<li><a href=\"\">Search issue</a></li>")	;
				htmlWriter.append("<li><a href=\"\">Submit issue</a></li>");
				htmlWriter.append("<li><a href=\"\">Review</a>");
				htmlWriter.append("<ul>");	
				htmlWriter.append("<li><a href=\"\">Projects</a></li>");
				htmlWriter.append("<li><a href=\"\">Statuses</a></li>");		
				htmlWriter.append("<li><a href=\"\">Resolutions</a></li>");			
				htmlWriter.append("<li><a href=\"\">Priorities</a></li>");			
				htmlWriter.append("<li><a href=\"\">Types</a></li>");
				htmlWriter.append("</ul></li>");
				htmlWriter.append("<li><a href=\"\">Search user</a></li>");
				htmlWriter.append("<li><a href=\"\">Add</a>");
				htmlWriter.append("<ul>");
				htmlWriter.append("<li><a href=\"\">Project</a></li>");
				htmlWriter.append("<li><a href=\"\">User</a></li>");		
				htmlWriter.append("<li><a href=\"\">Resolution</a></li>");		
				htmlWriter.append("<li><a href=\"\">Priority</a></li>");			
				htmlWriter.append("<li><a href=\"\">Type</a></li>");			
				htmlWriter.append("</ul>");			
				htmlWriter.append("</li>");			
				htmlWriter.append("<li><a href=\"dashboard?action=logout\">Logout</a></li></ul>");		
			}
		}
		htmlWriter.append("</td>");
		htmlWriter.append("</tr>");
		htmlWriter.append("<tr>");
		htmlWriter.append("<td>");
		htmlWriter.append("<table border=\"1\" style=\"width:100%\">");
		htmlWriter.append("<th>Issue</th>");
		htmlWriter.append("<th>Status</th>");
		htmlWriter.append("<th>Type</th>");
		htmlWriter.append("<th>Priority</th>");
		htmlWriter.append("<th>Assignee</th>");
		//---------------------------	
		IIssueDAO issueDAO = new IssueDAO();
		try {
			List<Issue> issues = issueDAO.getAll();
			for (Issue issue : issues) {
				htmlWriter.append("<tr>");
				if (user == null) {
					htmlWriter.append("<td>" + issue.getSummary() + "</td>");	
				} else {
					htmlWriter.append("<td><a href=\"#\" onclick=\"return(false)\">" + issue.getSummary() + "</a></td>");
				}
				htmlWriter.append("<td>" + issue.getStatus().getValue() + "</td>");
				htmlWriter.append("<td>" + issue.getType().getValue() + "</td>");
				htmlWriter.append("<td>" + issue.getPriority().getValue() + "</td><td>");
				User assignee = issue.getAssignee();
				if (assignee == null) {
					htmlWriter.append("---");
				} else {
					htmlWriter.append(assignee.getFirstName() + " " + assignee.getLastName());
				}
				htmlWriter.append("</td></tr>");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} 
		//---------------------------
		htmlWriter.append("</table>");
		htmlWriter.append("</td>");
		htmlWriter.append("</tr>");
		htmlWriter.append("</table>");
		htmlWriter.append("</body>");
		htmlWriter.append("</html>");
	}
}
