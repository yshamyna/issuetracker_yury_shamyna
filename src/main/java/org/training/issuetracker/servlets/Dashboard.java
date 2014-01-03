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

import org.training.issuetracker.beans.IssuePriority;
import org.training.issuetracker.beans.IssueResolution;
import org.training.issuetracker.beans.IssueStatus;
import org.training.issuetracker.beans.IssueType;
import org.training.issuetracker.beans.User;
import org.training.issuetracker.beans.UserRole;
import org.training.issuetracker.dao.interfaces.IPriorityDAO;
import org.training.issuetracker.dao.interfaces.IResolutionDAO;
import org.training.issuetracker.dao.interfaces.IRoleDAO;
import org.training.issuetracker.dao.interfaces.IStatusDAO;
import org.training.issuetracker.dao.interfaces.ITypeDAO;
import org.training.issuetracker.dao.interfaces.IUserDAO;
import org.training.issuetracker.dao.xml.parsers.Parser;
import org.training.issuetracker.dao.xml.service.PriorityDAO;
import org.training.issuetracker.dao.xml.service.ResolutionDAO;
import org.training.issuetracker.dao.xml.service.RoleDAO;
import org.training.issuetracker.dao.xml.service.StatusDAO;
import org.training.issuetracker.dao.xml.service.TypeDAO;
import org.training.issuetracker.dao.xml.service.UserDAO;

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
		htmlWriter.append("List issue");
		//---------------------------
		// getListIssue
		IStatusDAO statusDAO = new StatusDAO();
		IRoleDAO roleDAO = new RoleDAO();
		IResolutionDAO resolutionDAO = new ResolutionDAO();
		IPriorityDAO priorityDAO = new PriorityDAO();
		IUserDAO userDAO = new UserDAO();
		ITypeDAO typeDAO = new TypeDAO();
		try {
			List<IssueStatus> s = statusDAO.getAll();
			for (IssueStatus ss : s) {
				System.out.println(ss.getId() + " " + ss.getValue());
			}
			List<UserRole> roles = roleDAO.getAll();
			for (UserRole role : roles) {
				System.out.println(role.getId() + " " + role.getValue());
			}
			List<IssueResolution> resolutions = resolutionDAO.getAll();
			for (IssueResolution resolution : resolutions) {
				System.out.println(resolution.getId() + " " + resolution.getValue());
			}
			System.out.println(resolutionDAO.getById(1).getValue());
			List<IssuePriority> priorities = priorityDAO.getAll();
			for (IssuePriority p : priorities) {
				System.out.println(p.getId() + " " + p.getValue());
			}
			List<User> users = userDAO.getAll();
			for (User user : users) {
				System.out.println(user);
			}
			List<IssueType> types = typeDAO.getAll();
			for (IssueType type : types) {
				System.out.println(type.getId() + " " + type.getValue());
			}
		} catch (Exception e) {
			e.printStackTrace();
		} 
		//---------------------------
		htmlWriter.append("</td>");
		htmlWriter.append("</tr>");
		htmlWriter.append("</table>");
		htmlWriter.append("</body>");
		htmlWriter.append("</html>");
	}

}
