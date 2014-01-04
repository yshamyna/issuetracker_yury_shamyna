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
		PrintWriter htmlWriter = response.getWriter();
		htmlWriter.append("<html>");
		htmlWriter.append("<body>");
		htmlWriter.append("<table border=\"1\" style=\"width:100%\">");
		htmlWriter.append("<tr>");
		htmlWriter.append("<td>");
		htmlWriter.append("<form method=\"POST\" action=\"login\">");
		htmlWriter.append("Email address: <input type=\"text\" name=\"emailAddress\"/>");
		htmlWriter.append("Password: <input type=\"text\" name=\"password\"/>");
		htmlWriter.append("<input type=\"button\" name=\"loginBtn\" value=\"Login\"/>");
		htmlWriter.append("</form>");
		htmlWriter.append("<form action=\"search-issue\">");
		htmlWriter.append("<input type=\"button\" name=\"searchIssue\" value=\"Search\"/>");
		htmlWriter.append("</form>");
		htmlWriter.append("</td>");
		htmlWriter.append("</tr>");
		htmlWriter.append("<tr>");
		htmlWriter.append("<td>");
		htmlWriter.append("<table border=\"1\">");
		htmlWriter.append("<th>Issue</th>");
		htmlWriter.append("<th>Status</th>");
		htmlWriter.append("<th>Type</th>");
		htmlWriter.append("<th>Priority</th>");
		htmlWriter.append("<th>Project</th>");
		htmlWriter.append("<th>Build</th>");
		htmlWriter.append("<th>Assignee</th>");
		//---------------------------	
		IIssueDAO issueDAO = new IssueDAO();
		try {
			List<Issue> issues = issueDAO.getAll();
			for (Issue issue : issues) {
				htmlWriter.append("<tr>");
				htmlWriter.append("<td>" + issue.getSummary() + "</td>");
				htmlWriter.append("<td>" + issue.getStatus().getValue() + "</td>");
				htmlWriter.append("<td>" + issue.getType().getValue() + "</td>");
				htmlWriter.append("<td>" + issue.getPriority().getValue() + "</td>");
				htmlWriter.append("<td>" + issue.getProject().getName() + "</td>");
				htmlWriter.append("<td>" + issue.getBuildFound().getVersion() + "</td><td>");
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
