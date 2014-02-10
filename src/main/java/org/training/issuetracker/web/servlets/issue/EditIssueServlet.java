package org.training.issuetracker.web.servlets.issue;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.training.issuetracker.db.beans.Attachment;
import org.training.issuetracker.db.beans.Build;
import org.training.issuetracker.db.beans.Comment;
import org.training.issuetracker.db.beans.Issue;
import org.training.issuetracker.db.beans.Priority;
import org.training.issuetracker.db.beans.Status;
import org.training.issuetracker.db.beans.Type;
import org.training.issuetracker.db.beans.Project;
import org.training.issuetracker.db.beans.User;
import org.training.issuetracker.db.dao.interfaces.IAttachmentDAO;
import org.training.issuetracker.db.dao.interfaces.IBuildDAO;
import org.training.issuetracker.db.dao.interfaces.ICommentDAO;
import org.training.issuetracker.db.dao.interfaces.IIssueDAO;
import org.training.issuetracker.db.dao.interfaces.IPriorityDAO;
import org.training.issuetracker.db.dao.interfaces.IProjectDAO;
import org.training.issuetracker.db.dao.interfaces.IStatusDAO;
import org.training.issuetracker.db.dao.interfaces.ITypeDAO;
import org.training.issuetracker.db.dao.interfaces.IUserDAO;
import org.training.issuetracker.db.dao.service.AttachmentDAO;
import org.training.issuetracker.db.dao.service.BuildDAO;
import org.training.issuetracker.db.dao.service.CommentDAO;
import org.training.issuetracker.db.dao.service.IssueDAO;
import org.training.issuetracker.db.dao.service.PriorityDAO;
import org.training.issuetracker.db.dao.service.ProjectDAO;
import org.training.issuetracker.db.dao.service.StatusDAO;
import org.training.issuetracker.db.dao.service.TypeDAO;
import org.training.issuetracker.db.dao.service.UserDAO;

/**
 * Servlet implementation class EditIssueServlet
 */
public class EditIssueServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public EditIssueServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String id = request.getParameter("id");
		if (id == null) {
			getServletContext().getRequestDispatcher("/dashboard").
					forward(request, response);
		} else {
			try {
				long issueId = Integer.parseInt(id);
				IIssueDAO dao = new IssueDAO();
				Issue issue = dao.getById(issueId);
				if (issue == null) {
					getServletContext().getRequestDispatcher("/dashboard").
							forward(request, response);
				} else {
					request.setAttribute("issue", issue);
					
					IStatusDAO statusDAO = new StatusDAO();
					List<Status> statuses = statusDAO.getAll();
					request.setAttribute("statuses", statuses);
					
					ITypeDAO typeDAO = new TypeDAO();
					List<Type> types = typeDAO.getAll();
					request.setAttribute("types", types);
					
					IPriorityDAO priorityDAO = new PriorityDAO();
					List<Priority> priorities = priorityDAO.getAll();
					request.setAttribute("priorities", priorities);

					IProjectDAO projectDAO = new ProjectDAO();
					List<Project> projects = projectDAO.getAll();
					request.setAttribute("projects", projects);
					
					IBuildDAO buildDAO = new BuildDAO();
					List<Build> builds = buildDAO.getByProjectId(issue.getProject().getId());
					request.setAttribute("builds", builds);
					
					IUserDAO userDAO = new UserDAO();
					List<User> users = userDAO.getAll();
					request.setAttribute("assignees", users);
					
					ICommentDAO commentDAO = new CommentDAO();
					List<Comment> comments = commentDAO.getCommentsByIssueId(issueId);
					request.setAttribute("comments", comments);
					
					IAttachmentDAO attachmentDAO = new AttachmentDAO();
					List<Attachment> attachments = attachmentDAO.getListByIssueId(issueId);
					request.setAttribute("attachments", attachments);
					
					getServletContext().getRequestDispatcher("/editIssue.jsp").
							forward(request, response);	
				}
			} catch(NumberFormatException e) {
				getServletContext().getRequestDispatcher("/dashboard").
					forward(request, response);
			} catch(Exception e) {
				e.printStackTrace();
				response.getWriter().println("Sorry, but current service is not available... Please try later.");
			}
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			long id = Integer.parseInt(request.getParameter("id"));
			IIssueDAO issueDAO = new IssueDAO();
			Issue issue = issueDAO.getById(id);
			
			Timestamp modifyDate = new Timestamp(System.currentTimeMillis());
			User modifyBy = (User)request.getSession().getAttribute("user");
			
			issue.setModifyDate(modifyDate);
			issue.setModifyBy(modifyBy);
			
			issue.setSummary(request.getParameter("summary"));
			issue.setDescription(request.getParameter("description"));
			
			Status status = new Status();
			status.setId(Integer.parseInt(request.getParameter("statusId")));
			issue.setStatus(status);
			
			Type type = new Type();
			type.setId(Integer.parseInt(request.getParameter("typeId")));
			issue.setType(type);
			
			Priority priority = new Priority();
			priority.setId(Integer.parseInt(request.getParameter("priorityId")));
			issue.setPriority(priority);
			
			Project project = new Project();
			project.setId(Integer.parseInt(request.getParameter("projectId")));
			issue.setProject(project);
			
			Build build = new Build();
			build.setId(Integer.parseInt(request.getParameter("buildId")));
			issue.setBuildFound(build);
			
			long userId = Integer.parseInt(request.getParameter("assigneeId"));
			if (userId == -1) {
				issue.setAssignee(null);
			} else {
				User assignee = new User();
				assignee.setId(userId);
				issue.setAssignee(assignee);
			}
			
			issueDAO.update(issue);
			response.getWriter().println("Issue was changed successfully.");
		} catch(Exception e) {
			e.printStackTrace();
			response.getWriter().println("Sorry, but current service is not available... Please try later.");
		}
	}

}
