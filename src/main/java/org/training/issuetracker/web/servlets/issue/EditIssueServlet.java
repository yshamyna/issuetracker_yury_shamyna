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
import org.training.issuetracker.db.beans.Project;
import org.training.issuetracker.db.beans.Resolution;
import org.training.issuetracker.db.beans.Status;
import org.training.issuetracker.db.beans.Type;
import org.training.issuetracker.db.beans.User;
import org.training.issuetracker.db.service.AttachmentService;
import org.training.issuetracker.db.service.BuildService;
import org.training.issuetracker.db.service.CommentService;
import org.training.issuetracker.db.service.IssueService;
import org.training.issuetracker.db.service.PriorityService;
import org.training.issuetracker.db.service.ProjectService;
import org.training.issuetracker.db.service.ResolutionService;
import org.training.issuetracker.db.service.StatusService;
import org.training.issuetracker.db.service.TypeService;
import org.training.issuetracker.db.service.UserService;

public class EditIssueServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public EditIssueServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String id = request.getParameter("id");
		if (id == null) {
			getServletContext().getRequestDispatcher("/dashboard").
					forward(request, response);
		} else {
			try {
				User user = (User) request.getSession().getAttribute("user");
				
				long issueId = Integer.parseInt(id);
				
				IssueService iService = new IssueService(user);
				Issue issue = iService.getIssueById(issueId);
				
				if (issue == null) {
					getServletContext().getRequestDispatcher("/dashboard").
							forward(request, response);
				} else {
					request.setAttribute("issue", issue);
					
					StatusService sService = new StatusService(user);
					List<Status> statuses = sService.getStatuses();
					request.setAttribute("statuses", statuses);
					
					TypeService tService = new TypeService(user);
					List<Type> types = tService.getTypes();
					request.setAttribute("types", types);
					
					PriorityService pService = new PriorityService(user);
					List<Priority> priorities = pService.getPriorities();
					request.setAttribute("priorities", priorities);

					ProjectService prService = new ProjectService(user);
					List<Project> projects = prService.getProjects();
					request.setAttribute("projects", projects);
					
					BuildService bService = new BuildService(user);
					List<Build> builds = bService.getBuildsByProjectId(issue.getProject().getId());
					request.setAttribute("builds", builds);
					
					UserService uService = new UserService(user);
					List<User> users = uService.getUsers();
					request.setAttribute("assignees", users);
					
					CommentService cService = new CommentService(user);
					List<Comment> comments = cService.getCommentsByIssueId(issueId);
					request.setAttribute("comments", comments);
					
					AttachmentService aService = new AttachmentService(user);
					List<Attachment> attachments = aService.getAttachmentsByIssueId(issueId);
					request.setAttribute("attachments", attachments);
					
					ResolutionService rService = new ResolutionService(user);
					List<Resolution> resolutions = rService.getResolutions();
					request.setAttribute("resolutions", resolutions);
					
					getServletContext().getRequestDispatcher("/editIssue.jsp").
							forward(request, response);	
				}
			} catch(NumberFormatException e) {
				getServletContext().getRequestDispatcher("/dashboard").
					forward(request, response);
			} catch(Exception e) {
				response.getWriter().println("Sorry, but current service is not available... Please try later.");
			}
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			User user = (User) request.getSession().getAttribute("user");
			
			long id = Integer.parseInt(request.getParameter("id"));
			Issue issue = new Issue();
			issue.setId(id);
			
			Timestamp modifyDate = new Timestamp(System.currentTimeMillis());
			User modifyBy = user;
			
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
			
			IssueService service = new IssueService(user);
			service.update(issue);
			
			long resolutionId = Integer.parseInt(request.getParameter("resolutionId"));
			if (resolutionId == -1) {
				issue.setResolution(null);
			} else {
				Resolution resolution = new Resolution();
				resolution.setId(resolutionId);
				issue.setResolution(resolution);
			}
			
			response.getWriter().println("Issue was updated successfully.");
		} catch (Exception e) {
			response.getWriter().
				println("Sorry, but current service is not available... Please try later.");
		}
	}

}
