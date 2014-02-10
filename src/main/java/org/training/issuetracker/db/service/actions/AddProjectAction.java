package org.training.issuetracker.db.service.actions;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.h2.jdbc.JdbcSQLException;
import org.training.issuetracker.db.beans.Build;
import org.training.issuetracker.db.beans.Manager;
import org.training.issuetracker.db.beans.Project;
import org.training.issuetracker.db.beans.User;
import org.training.issuetracker.db.interfaces.Action;
import org.training.issuetracker.db.service.ProjectService;

public class AddProjectAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			User user = (User) request.getSession().getAttribute("user");
			
			ProjectService service = new ProjectService(user);

			Project project = new Project();
			project.setName(request.getParameter("name"));
			project.setDescription(request.getParameter("description"));
			
			Manager manager = new Manager();
			manager.setId(Integer.parseInt(request.getParameter("managerId")));
			project.setManager(manager);
			
			Build build = new Build();
			build.setCurrent(true);
			build.setVersion(request.getParameter("version"));
			
			service.add(project, build);
			
			response.getWriter().println("Project was added successfully.");
		} catch (JdbcSQLException e) {
			response.getWriter().println("Already exists project with name '" 
					+ request.getParameter("name") + "'");
		} catch (Exception e) {
			response.getWriter().
				println("Sorry, but current service is not available... Please try later.");
		}
	}

}
