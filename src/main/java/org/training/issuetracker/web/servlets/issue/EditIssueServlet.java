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
import org.training.issuetracker.web.constants.MessageConstants;
import org.training.issuetracker.web.constants.ParameterConstants;
import org.training.issuetracker.web.constants.URLConstants;

public class EditIssueServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public EditIssueServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String id = request.getParameter(ParameterConstants.ID);
		if (id == null) {
			getServletContext().getRequestDispatcher(URLConstants.DASHBOARD_URL).
					forward(request, response);
		} else {
			try {
				User user = (User) request.getSession().
									getAttribute(ParameterConstants.USER);
				
				long issueId = Integer.parseInt(id);
				
				IssueService iService = new IssueService(user);
				Issue issue = iService.getIssueById(issueId);
				
				if (issue == null) {
					getServletContext().getRequestDispatcher(URLConstants.DASHBOARD_URL).
							forward(request, response);
				} else {
					request.setAttribute(ParameterConstants.ISSUE, issue);
					
					StatusService sService = new StatusService(user);
					List<Status> statuses = sService.getStatuses();
					request.setAttribute(ParameterConstants.STATUSES, statuses);
					
					TypeService tService = new TypeService(user);
					List<Type> types = tService.getTypes();
					request.setAttribute(ParameterConstants.TYPES, types);
					
					PriorityService pService = new PriorityService(user);
					List<Priority> priorities = pService.getPriorities();
					request.setAttribute(ParameterConstants.PRIORITIES, priorities);

					ProjectService prService = new ProjectService(user);
					List<Project> projects = prService.getProjects();
					request.setAttribute(ParameterConstants.PROJECTS, projects);
					
					BuildService bService = new BuildService(user);
					List<Build> builds = bService.getBuildsByProjectId(issue.getProject().getId());
					request.setAttribute(ParameterConstants.BUILDS, builds);
					
					UserService uService = new UserService(user);
					List<User> users = uService.getUsers();
					request.setAttribute(ParameterConstants.ASSIGNEES, users);
					
					CommentService cService = new CommentService(user);
					List<Comment> comments = cService.getCommentsByIssueId(issueId);
					request.setAttribute(ParameterConstants.COMMENTS, comments);
					
					AttachmentService aService = new AttachmentService(user);
					List<Attachment> attachments = aService.getAttachmentsByIssueId(issueId);
					request.setAttribute(ParameterConstants.ATTACHMENTS, attachments);
					
					ResolutionService rService = new ResolutionService(user);
					List<Resolution> resolutions = rService.getResolutions();
					request.setAttribute(ParameterConstants.RESOLUTIONS, resolutions);
					
					getServletContext().getRequestDispatcher(URLConstants.EDIT_ISSUE_JSP).
							forward(request, response);	
				}
			} catch(NumberFormatException e) {
				getServletContext().getRequestDispatcher(URLConstants.DASHBOARD_URL).
					forward(request, response);
			} catch(Exception e) {
				response.getWriter().println(MessageConstants.SORRY_MESSAGE);
			}
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			User user = (User) request.getSession().getAttribute(ParameterConstants.USER);
			
			long id = Integer.parseInt(request.getParameter(ParameterConstants.ID));
			Issue issue = new Issue();
			issue.setId(id);
			
			Timestamp modifyDate = new Timestamp(System.currentTimeMillis());
			User modifyBy = user;
			
			issue.setModifyDate(modifyDate);
			issue.setModifyBy(modifyBy);
			
			issue.setSummary(request.getParameter(ParameterConstants.SUMMARY));
			issue.setDescription(request.getParameter(ParameterConstants.DESCRIPTION));
			
			Status status = new Status();
			status.setId(Integer.parseInt(request.getParameter(ParameterConstants.STATUS_ID)));
			issue.setStatus(status);
			
			Type type = new Type();
			type.setId(Integer.parseInt(request.getParameter(ParameterConstants.TYPE_ID)));
			issue.setType(type);
			
			Priority priority = new Priority();
			priority.setId(Integer.parseInt(request.getParameter(ParameterConstants.PRIORITY_ID)));
			issue.setPriority(priority);
			
			Project project = new Project();
			project.setId(Integer.parseInt(request.getParameter(ParameterConstants.PROJECT_ID)));
			issue.setProject(project);
			
			Build build = new Build();
			build.setId(Integer.parseInt(request.getParameter(ParameterConstants.BUILD_ID)));
			issue.setBuildFound(build);
			
			long userId = Integer.parseInt(request.getParameter(ParameterConstants.ASSIGNEE_ID));
			if (userId == -1) {
				issue.setAssignee(null);
			} else {
				User assignee = new User();
				assignee.setId(userId);
				issue.setAssignee(assignee);
			}
			
			IssueService service = new IssueService(user);
			service.update(issue);
			
			long resolutionId = Integer.parseInt(request.getParameter(ParameterConstants.RESOLUTION_ID));
			if (resolutionId == -1) {
				issue.setResolution(null);
			} else {
				Resolution resolution = new Resolution();
				resolution.setId(resolutionId);
				issue.setResolution(resolution);
			}
			
			response.getWriter().println(MessageConstants.ISSUE_UPDATED);
		} catch (Exception e) {
			response.getWriter().println(MessageConstants.SORRY_MESSAGE);
		}
	}

}
