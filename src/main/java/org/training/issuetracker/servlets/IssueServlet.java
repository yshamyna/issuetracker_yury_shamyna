package org.training.issuetracker.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.util.List;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.training.issuetracker.beans.Build;
import org.training.issuetracker.beans.Issue;
import org.training.issuetracker.beans.IssuePriority;
import org.training.issuetracker.beans.IssueStatus;
import org.training.issuetracker.beans.IssueType;
import org.training.issuetracker.beans.Project;
import org.training.issuetracker.beans.User;
import org.training.issuetracker.dao.interfaces.IIssueDAO;
import org.training.issuetracker.dao.interfaces.IPriorityDAO;
import org.training.issuetracker.dao.interfaces.IProjectDAO;
import org.training.issuetracker.dao.interfaces.IStatusDAO;
import org.training.issuetracker.dao.interfaces.ITypeDAO;
import org.training.issuetracker.dao.interfaces.IUserDAO;
import org.training.issuetracker.dao.xml.parsers.Parser;
import org.training.issuetracker.dao.xml.service.IssueDAO;
import org.training.issuetracker.dao.xml.service.PriorityDAO;
import org.training.issuetracker.dao.xml.service.ProjectDAO;
import org.training.issuetracker.dao.xml.service.StatusDAO;
import org.training.issuetracker.dao.xml.service.TypeDAO;
import org.training.issuetracker.dao.xml.service.UserDAO;

/**
 * Servlet implementation class IssueServlet
 */
public class IssueServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private ServletConfig servletConfig;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public IssueServlet() {
        super();
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

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		task(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		task(request, response);
	}
	
	private void task(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		out.println("<!DOCTYPE html>");
		out.println("<html>");
		out.println("<head>");
		out.println("<title>Issue</title>");
		out.println("</head>");
		
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");
		int issueId = Integer.parseInt(request.getParameter("id"));
		try {
			Parser.setURL(servletConfig.getServletContext().getResource("issuetracker.xml"));
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		IIssueDAO iDAO = new IssueDAO();
		try {
			Issue issue = iDAO.getById(issueId);
				if (user != null && "administrator".equals(user.getRole().getValue())) {
					out.append("<head><title>Issuetracker</title><link rel=\"stylesheet\" type=\"text/css\" href=\"adminMenu.css\"></head>");
				}
				out.append("<body>");
				out.append("<table border=\"1\" style=\"width:100%\">");
				out.append("<tr>");
				out.append("<td>");
				
				if (user == null) {
					out.append("<form method=\"post\" action=\"dashboard\">");
					out.append("Email address: <input type=\"text\" name=\"emailAddress\"/>&nbsp;");
					out.append("Password: <input type=\"password\" name=\"password\"/>");
					out.append("<input type=\"submit\" name=\"loginBtn\" value=\"Login\"/>");
					out.append("</form>");
					String msg = (String)request.getAttribute("errorMessage");
					if (msg != null) {
						out.append("<div style=\"font-size:10pt;color:red;\">" + msg + "</div>");
					}
					out.append("<div style=\"float:left;height:26px;\"><form action=\"dashboard\">");
					out.append("<input type=\"submit\" name=\"searchIssue\" value=\"Search\"/>");
					out.append("</form></div>");
					
					out.append("</td>");
					out.append("</tr>");
					out.println("<tr><td>");
					out.println("<ul>");
					out.println("<li style=\"list-style:none;\">Create date: " + issue.getFormatCreatedDate() + "</li>");
					out.println("<li style=\"list-style:none;\">Created by: " + issue.getCreatedBy().getFirstName() + " " + issue.getCreatedBy().getLastName() + "</li>");
					out.println("<li style=\"list-style:none;\">Modify date: " + issue.getFormatModifyDate() + "</li>");
					out.println("<li style=\"list-style:none;\">Created by: " + issue.getModifyBy().getFirstName() + " " + issue.getModifyBy().getLastName() + "</li>");
					out.println("<li style=\"list-style:none;\">Id: " + issue.getId() + "</li>");
					out.println("<li style=\"list-style:none;\">Summary: " + issue.getSummary() + "</li>");
					out.println("<li style=\"list-style:none;\">Description: " + issue.getDescription() + "</li>");
					out.println("<li style=\"list-style:none;\">Status: " + issue.getStatus().getValue() + "</li>");
					out.println("<li style=\"list-style:none;\">Type:" + issue.getType().getValue() + "</li>");
					out.println("<li style=\"list-style:none;\">Priority: " + issue.getPriority().getValue() + "</li>");
					out.println("<li style=\"list-style:none;\">Project: " + issue.getProject().getName() + "</li>");
					out.println("<li style=\"list-style:none;\">Build: " + issue.getBuildFound().getVersion() + "</li>");
					User assignee = issue.getAssignee();
					if (assignee == null) {
						out.println("<li style=\"list-style:none;\">Assignee: none</li>");
					} else {
						out.println("<li style=\"list-style:none;\">Assignee: " + assignee.getFirstName() 
								+ " " + assignee.getLastName() + "</li>");
						out.println("</ul>");	
					}
					
				} else {
					if (user.getRole().getValue().equals("user")) {
						out.append("<div><b>User: " + user.getFirstName() + " " + user.getLastName() + "</b></div>");
						out.append("<div style=\"float:left;\"><a href=\"#\" onclick=\"return(false)\">Edit</a></div>");
						out.append("<div style=\"float:left;height:26px;\"><form method=\"post\" action=\"dashboard\">");
						out.append("<input type=\"submit\" name=\"logoutBtn\" value=\"Logout\">");
						out.append("</form></div>");
						out.append("<div style=\"float:left;height:26px;\"><form method=\"post\" action=\"dashboard\">");
						out.append("<input type=\"submit\" name=\"addIssue\" value=\"Submit issue\">");
						out.append("</form></div>");
						out.append("<div style=\"float:left;height:26px;\"><form action=\"dashboard\">");
						out.append("<input type=\"submit\" name=\"searchIssue\" value=\"Search\"/>");
						out.append("</form></div>");
					} else if (user.getRole().getValue().equals("administrator")) {
						out.append("<div class=\"color-menu\"><div>User: " 
										+ user.getFirstName() + " " + user.getLastName() 
										+ "</div></div>");
						out.append("<ul class=\"color-menu\">");
						out.append("<li><a href=\"\">Edit</a></li>");
						out.append("<li><a href=\"\">Search issue</a></li>")	;
						out.append("<li><a href=\"\">Submit issue</a></li>");
						out.append("<li><a href=\"\">Review</a>");
						out.append("<ul>");	
						out.append("<li><a href=\"\">Projects</a></li>");
						out.append("<li><a href=\"\">Statuses</a></li>");		
						out.append("<li><a href=\"\">Resolutions</a></li>");			
						out.append("<li><a href=\"\">Priorities</a></li>");			
						out.append("<li><a href=\"\">Types</a></li>");
						out.append("</ul></li>");
						out.append("<li><a href=\"\">Search user</a></li>");
						out.append("<li><a href=\"\">Add</a>");
						out.append("<ul>");
						out.append("<li><a href=\"\">Project</a></li>");
						out.append("<li><a href=\"\">User</a></li>");		
						out.append("<li><a href=\"\">Resolution</a></li>");		
						out.append("<li><a href=\"\">Priority</a></li>");			
						out.append("<li><a href=\"\">Type</a></li>");			
						out.append("</ul>");			
						out.append("</li>");			
						out.append("<li><a href=\"dashboard?action=logout\">Logout</a></li></ul>");		
					}
					out.append("</td>");
					out.append("</tr>");
					out.append("<tr>");
					out.append("<td>");
					// issue -------
					out.println("<ul>");
					out.println("<li style=\"list-style:none;\">Create date: " + issue.getFormatCreatedDate() + "</li>");
					out.println("<li style=\"list-style:none;\">Created by: " + issue.getCreatedBy().getFirstName() + " " + issue.getCreatedBy().getLastName() + "</li>");
					out.println("<li style=\"list-style:none;\">Modify date: " + issue.getFormatModifyDate() + "</li>");
					out.println("<li style=\"list-style:none;\">Modify by: " + issue.getModifyBy().getFirstName() + " " + issue.getModifyBy().getLastName() + "</li>");
					out.println("<li style=\"list-style:none;\">Id: " + issue.getId() + "</li>");
					out.println("<li style=\"list-style:none;\">Summary: " + issue.getSummary() + "</li>");
					out.println("<li style=\"list-style:none;\">Description: " + issue.getDescription() + "</li>");
					IStatusDAO statusDAO = new StatusDAO();
					List<IssueStatus> statuses = statusDAO.getAll();
					out.println("<li style=\"list-style:none;\">Status: <select name=\"statuses\">");
					for (IssueStatus status : statuses) {
						if (issue.getStatus().getId() == status.getId()) {
							out.println("<option value=\"" + status.getValue() 
									+ "\" selected>" + status.getValue() 
									+ "</option>");
						} else {
							out.println("<option value=\""+ status.getValue() + "\">" + status.getValue() + "</option>");
						}
					}
					out.println("</select></li>");
					ITypeDAO typeDAO = new TypeDAO();
					List<IssueType> types = typeDAO.getAll();
					out.println("<li style=\"list-style:none;\">Type: <select name=\"types\">");
					for (IssueType type : types) {
						if (issue.getType().getId() == type.getId()) {
							out.println("<option value=\"" + type.getValue() 
									+ "\" selected>" + type.getValue() 
									+ "</option>");
						} else {
							out.println("<option value=\""+ type.getValue() + "\">" 
										+ type.getValue() + "</option>");
						}
					}
					out.println("</select></li>");
					IPriorityDAO priorityDAO = new PriorityDAO();
					List<IssuePriority> priorities = priorityDAO.getAll();
					out.println("<li style=\"list-style:none;\">Priority: <select name=\"priorities\">");
					for (IssuePriority priority : priorities) {
						if (issue.getPriority().getId() == priority.getId()) {
							out.println("<option value=\"" + priority.getValue() 
									+ "\" selected>" + priority.getValue() 
									+ "</option>");
						} else {
							out.println("<option value=\""+ priority.getValue() + "\">" 
										+ priority.getValue() + "</option>");
						}
					}
					out.println("</select></li>");
					
					IProjectDAO projectDAO = new ProjectDAO();
					List<Project> projects = projectDAO.getAll();
					Project currentProject = null;
					out.println("<li style=\"list-style:none;\">Project: <select name=\"projects\">");
					for (Project project : projects) {
						if (issue.getProject().getId() == project.getId()) {
							out.println("<option value=\"" + project.getName() 
									+ "\" selected>" + project.getName() 
									+ "</option>");
							currentProject = project;
						} else {
							out.println("<option value=\""+ project.getName() + "\">" 
										+ project.getName() + "</option>");
						}
					}
					out.println("</select></li>");
					
					List<Build> builds = currentProject.getBuilds();
					out.println("<li style=\"list-style:none;\">Build found: <select name=\"builds\">");
					for (Build build : builds) {
						if (currentProject.getBuild().getId() == build.getId()) {
							out.println("<option value=\"" + build.getVersion() 
									+ "\" selected>" + build.getVersion() 
									+ "</option>");
						} else {
							out.println("<option value=\""+ build.getVersion() + "\">" 
										+ build.getVersion() + "</option>");
						}
					}
					out.println("</select></li>");
					
					IUserDAO userDAO = new UserDAO();
					List<User> users = userDAO.getAll();
					out.println("<li style=\"list-style:none;\">User: <select name=\"users\">");
					User assignee = issue.getAssignee();
					if (assignee == null) {
						out.println("<option value=\"none\" selected>none</option>");
						for (User u : users) {
							out.println("<option value=\""+ u.getFirstName() + "_" + u.getLastName() + "\">" 
										+ u.getFirstName() + " " + u.getLastName() + "</option>");
						}
					} else {
						for (User u : users) {
							if (assignee.getId() == u.getId()) {
								out.println("<option value=\"" + u.getFirstName() + "_" + u.getLastName() 
										+ "\" selected>" + u.getFirstName() + " " + u.getLastName() 
										+ "</option>");
							} else {
								out.println("<option value=\""+ u.getFirstName() + "_" + u.getLastName() + "\">" 
											+ u.getFirstName() + " " + u.getLastName() + "</option>");
							}
						}
					}
					out.println("</select></li>");
					out.println("</ul>");
					out.append("</td>");
					out.append("</tr>");
					//--------------
				}
		} catch (Exception e) {
			System.err.println("Issue eror.");
			e.printStackTrace();
		}
		out.println("</body>");
		out.println("</html>");
	}

}
