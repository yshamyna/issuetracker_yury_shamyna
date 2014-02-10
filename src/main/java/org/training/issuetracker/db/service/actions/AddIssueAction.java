package org.training.issuetracker.db.service.actions;

import java.io.IOException;
import java.sql.Timestamp;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.training.issuetracker.db.beans.Build;
import org.training.issuetracker.db.beans.Issue;
import org.training.issuetracker.db.beans.Priority;
import org.training.issuetracker.db.beans.Project;
import org.training.issuetracker.db.beans.Status;
import org.training.issuetracker.db.beans.Type;
import org.training.issuetracker.db.beans.User;
import org.training.issuetracker.db.interfaces.Action;
import org.training.issuetracker.db.service.IssueService;

public class AddIssueAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			User user = (User) request.getSession().getAttribute("user");
			
			Issue issue = new Issue();
			
			Timestamp createDate = new Timestamp(System.currentTimeMillis());
			Timestamp modifyDate = new Timestamp(System.currentTimeMillis());
			
			issue.setCreatedBy(user);
			issue.setCreateDate(createDate);
			issue.setModifyDate(modifyDate);
			issue.setModifyBy(user);
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
			service.add(issue);
			
			response.getWriter().println("Issue was added successfully.");
		} catch (Exception e) {
			response.getWriter().
				println("Sorry, but current service is not available... Please try later.");
		}
	}

}
