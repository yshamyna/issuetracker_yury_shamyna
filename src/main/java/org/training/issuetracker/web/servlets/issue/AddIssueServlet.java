package org.training.issuetracker.web.servlets.issue;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.training.issuetracker.db.beans.Build;
import org.training.issuetracker.db.beans.Issue;
import org.training.issuetracker.db.beans.Priority;
import org.training.issuetracker.db.beans.Project;
import org.training.issuetracker.db.beans.Status;
import org.training.issuetracker.db.beans.Type;
import org.training.issuetracker.db.beans.User;
import org.training.issuetracker.db.service.BuildService;
import org.training.issuetracker.db.service.IssueService;
import org.training.issuetracker.db.service.PriorityService;
import org.training.issuetracker.db.service.ProjectService;
import org.training.issuetracker.db.service.StatusService;
import org.training.issuetracker.db.service.TypeService;
import org.training.issuetracker.db.service.UserService;
import org.training.issuetracker.web.constants.MessageConstants;
import org.training.issuetracker.web.constants.ParameterConstants;
import org.training.issuetracker.web.constants.URLConstants;

public class AddIssueServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public AddIssueServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			User user = (User) request.getSession().
								getAttribute(ParameterConstants.USER);
			
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
			List<Build> builds = bService.getBuildsByProjectId(projects.get(0).getId());
			request.setAttribute(ParameterConstants.BUILDS, builds);
			
			UserService uService = new UserService(user);
			List<User> users = uService.getUsers();
			request.setAttribute(ParameterConstants.ASSIGNEES, users);
			
			getServletContext().getRequestDispatcher(URLConstants.ADD_ISSUE_JSP).
				forward(request, response);	
		} catch (Exception e) {
			response.getWriter().println(MessageConstants.SORRY_MESSAGE);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			User user = (User) request.getSession().
									getAttribute(ParameterConstants.USER);
			
			Issue issue = new Issue();
			
			Timestamp createDate = new Timestamp(System.currentTimeMillis());
			Timestamp modifyDate = new Timestamp(System.currentTimeMillis());
			
			issue.setCreateBy(user);
			issue.setCreateDate(createDate);
			issue.setModifyDate(modifyDate);
			issue.setModifyBy(user);
			issue.setSummary(request.
					getParameter(ParameterConstants.SUMMARY));
			issue.setDescription(request.
					getParameter(ParameterConstants.DESCRIPTION));
			
			Status status = new Status();
			status.setId(Integer.parseInt(request.
					getParameter(ParameterConstants.STATUS_ID)));
			issue.setStatus(status);
			
			Type type = new Type();
			type.setId(Integer.parseInt(request.
					getParameter(ParameterConstants.TYPE_ID)));
			issue.setType(type);
			
			Priority priority = new Priority();
			priority.setId(Integer.parseInt(request.
					getParameter(ParameterConstants.PRIORITY_ID)));
			issue.setPriority(priority);
			
			Project project = new Project();
			project.setId(Integer.parseInt(request.
					getParameter(ParameterConstants.PROJECT_ID)));
			issue.setProject(project);
			
			Build build = new Build();
			build.setId(Integer.parseInt(request.
					getParameter(ParameterConstants.BUILD_ID)));
			issue.setBuildFound(build);
			
			long userId = Integer.parseInt(request.
					getParameter(ParameterConstants.ASSIGNEE_ID));
			if (userId == -1) {
				issue.setAssignee(null);
			} else {
				User assignee = new User();
				assignee.setId(userId);
				issue.setAssignee(assignee);
			}
			
			IssueService service = new IssueService(user);
			service.add(issue);
			
			response.getWriter().println(MessageConstants.ISSUE_ADDED);
		} catch (Exception e) {
			e.printStackTrace();
			response.getWriter().println(MessageConstants.SORRY_MESSAGE);
		}
	}

}
