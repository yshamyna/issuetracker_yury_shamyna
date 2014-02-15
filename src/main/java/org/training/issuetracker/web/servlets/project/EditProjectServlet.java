package org.training.issuetracker.web.servlets.project;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.h2.jdbc.JdbcSQLException;
import org.training.issuetracker.db.beans.Build;
import org.training.issuetracker.db.beans.Manager;
import org.training.issuetracker.db.beans.Project;
import org.training.issuetracker.db.beans.User;
import org.training.issuetracker.db.service.BuildService;
import org.training.issuetracker.db.service.ManagerService;
import org.training.issuetracker.db.service.ProjectService;
import org.training.issuetracker.web.constants.GeneralConsants;
import org.training.issuetracker.web.constants.MessageConstants;
import org.training.issuetracker.web.constants.ParameterConstants;
import org.training.issuetracker.web.constants.URLConstants;

public class EditProjectServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public EditProjectServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String id = request.getParameter(ParameterConstants.ID);
		if (id == null) {
			getServletContext().getRequestDispatcher(URLConstants.PROJECTS_URL).
					forward(request, response);
		} else {
			try {
				User user = (User) request.getSession().
									getAttribute(ParameterConstants.USER);
				
				long projectId = Integer.parseInt(id);
				
				ProjectService pService = new ProjectService(user);
				Project project = pService.getProjectById(projectId);
				
				if (project == null) {
					getServletContext().getRequestDispatcher(URLConstants.PROJECTS_URL).
							forward(request, response);
				} else {
					request.setAttribute(ParameterConstants.PROJECT, project);
					
					BuildService bService = new BuildService(user);
					List<Build> builds = bService.getBuildsByProjectId(projectId);
					request.setAttribute(ParameterConstants.BUILDS, builds);

					ManagerService mService = new ManagerService(user);
					List<Manager> managers = mService.getManagers();
					request.setAttribute(ParameterConstants.MANAGERS, managers);
					
					getServletContext().getRequestDispatcher(URLConstants.EDIT_PROJECT_JSP).
							forward(request, response);	
				}
			} catch(NumberFormatException e) {
				getServletContext().getRequestDispatcher(URLConstants.PROJECTS_URL).
					forward(request, response);
			} catch(Exception e) {
				response.getWriter().println(MessageConstants.SORRY_MESSAGE);
			}
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			User user = (User) request.getSession().
						getAttribute(ParameterConstants.USER);
			
			ProjectService service = new ProjectService(user);

			Project project = new Project();
			project.setId(Integer.parseInt(request.getParameter(ParameterConstants.ID)));
			project.setName(request.getParameter(ParameterConstants.NAME));
			project.setDescription(request.getParameter(ParameterConstants.DESCRIPTION));
			
			Manager manager = new Manager();
			manager.setId(Integer.parseInt(request.
							getParameter(ParameterConstants.MANAGER_ID)));
			project.setManager(manager);
			
			Build build = new Build();
			build.setId(Integer.parseInt(request.
							getParameter(ParameterConstants.BUILD_ID)));
			build.setProjectId(project.getId());
			
			service.update(project, build);
			
			response.getWriter().println(MessageConstants.PROJECT_UPDATED);
		} catch (JdbcSQLException e) {
			response.getWriter().println(MessageConstants.PROJECT_EXIST
					+ request.getParameter(ParameterConstants.NAME) 
					+ GeneralConsants.SINGLE_QUOTE);
		} catch (Exception e) {
			response.getWriter().println(MessageConstants.SORRY_MESSAGE);
		}
	}

}
