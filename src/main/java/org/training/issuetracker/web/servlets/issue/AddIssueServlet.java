package org.training.issuetracker.web.servlets.issue;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.h2.jdbc.JdbcSQLException;
import org.training.issuetracker.db.beans.Build;
import org.training.issuetracker.db.beans.Issue;
import org.training.issuetracker.db.beans.IssuePriority;
import org.training.issuetracker.db.beans.IssueStatus;
import org.training.issuetracker.db.beans.IssueType;
import org.training.issuetracker.db.beans.Project;
import org.training.issuetracker.db.beans.User;
import org.training.issuetracker.db.dao.interfaces.IBuildDAO;
import org.training.issuetracker.db.dao.interfaces.IIssueDAO;
import org.training.issuetracker.db.dao.interfaces.IPriorityDAO;
import org.training.issuetracker.db.dao.interfaces.IProjectDAO;
import org.training.issuetracker.db.dao.interfaces.IStatusDAO;
import org.training.issuetracker.db.dao.interfaces.ITypeDAO;
import org.training.issuetracker.db.dao.interfaces.IUserDAO;
import org.training.issuetracker.db.dao.service.BuildDAO;
import org.training.issuetracker.db.dao.service.IssueDAO;
import org.training.issuetracker.db.dao.service.PriorityDAO;
import org.training.issuetracker.db.dao.service.ProjectDAO;
import org.training.issuetracker.db.dao.service.StatusDAO;
import org.training.issuetracker.db.dao.service.TypeDAO;
import org.training.issuetracker.db.dao.service.UserDAO;

/**
 * Servlet implementation class AddIssueServlet
 */
public class AddIssueServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AddIssueServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			IStatusDAO statusDAO = new StatusDAO();
			List<IssueStatus> statuses = statusDAO.getAll();
			request.setAttribute("statuses", statuses);
			
			ITypeDAO typeDAO = new TypeDAO();
			List<IssueType> types = typeDAO.getAll();
			request.setAttribute("types", types);
			
			IPriorityDAO priorityDAO = new PriorityDAO();
			List<IssuePriority> priorities = priorityDAO.getAll();
			request.setAttribute("priorities", priorities);
			
			IProjectDAO projectDAO = new ProjectDAO();
			List<Project> projects = projectDAO.getAll();
			request.setAttribute("projects", projects);
			
			IBuildDAO buildDAO = new BuildDAO();
			List<Build> builds = buildDAO.getByProjectId(projects.get(0).getId());
			request.setAttribute("builds", builds);
			
			IUserDAO userDAO = new UserDAO();
			List<User> assignee = userDAO.getAll();
			request.setAttribute("assignees", assignee);
			
			getServletContext().getRequestDispatcher("/addIssue.jsp").
				forward(request, response);	
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			Issue issue = new Issue();
			Timestamp createDate = new Timestamp(System.currentTimeMillis());
			User createBy = (User)request.getSession().getAttribute("user");
			
			issue.setCreateDate(createDate);
			issue.setCreatedBy(createBy);
			issue.setModifyDate(createDate);
			issue.setModifyBy(createBy);
			
			issue.setSummary(request.getParameter("summary"));
			issue.setDescription(request.getParameter("description"));
			
			IssueStatus status = new IssueStatus();
			status.setId(Integer.parseInt(request.getParameter("status")));
			issue.setStatus(status);
			
			IssueType type = new IssueType();
			type.setId(Integer.parseInt(request.getParameter("type")));
			issue.setType(type);
			
			IssuePriority priority = new IssuePriority();
			priority.setId(Integer.parseInt(request.getParameter("priority")));
			issue.setPriority(priority);
			
			Project project = new Project();
			project.setId(Integer.parseInt(request.getParameter("project")));
			issue.setProject(project);
			
			Build build = new Build();
			build.setId(Integer.parseInt(request.getParameter("build")));
			issue.setBuildFound(build);
			
			long userId = Integer.parseInt(request.getParameter("assignee"));
			if (userId == -1) {
				issue.setAssignee(null);
			} else {
				User assignee = new User();
				assignee.setId(userId);
				issue.setAssignee(assignee);
			}
			
			IIssueDAO issueDAO = new IssueDAO();
			issueDAO.add(issue);
		} catch (JdbcSQLException e) {
			request.setAttribute("errMsg", "Undefined error.");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			doGet(request, response);	
		}
	}

}
