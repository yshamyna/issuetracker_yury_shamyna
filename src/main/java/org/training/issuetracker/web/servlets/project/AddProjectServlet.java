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
import org.training.issuetracker.db.service.ManagerService;
import org.training.issuetracker.db.service.ProjectService;
import org.training.issuetracker.web.constants.GeneralConsants;
import org.training.issuetracker.web.constants.MessageConstants;
import org.training.issuetracker.web.constants.ParameterConstants;
import org.training.issuetracker.web.constants.URLConstants;

public class AddProjectServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public AddProjectServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			User user = (User) request.getSession().
								getAttribute(ParameterConstants.USER);
			
			ManagerService service = new ManagerService(user);
			List<Manager> managers = service.getManagers();
			
			request.setAttribute(ParameterConstants.MANAGERS, managers);
			
			getServletContext().getRequestDispatcher(URLConstants.ADD_PROJECT_JSP).
				forward(request, response);	
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			User user = (User) request.getSession().
						getAttribute(ParameterConstants.USER);
			
			ProjectService service = new ProjectService(user);

			Project project = new Project();
			project.setName(request.getParameter(ParameterConstants.NAME));
			project.setDescription(request.getParameter(ParameterConstants.DESCRIPTION));
			
			Manager manager = new Manager();
			manager.setId(Integer.parseInt(request.
									getParameter(ParameterConstants.MANAGER_ID)));
			project.setManager(manager);
			
			Build build = new Build();
			build.setCurrent(true);
			build.setVersion(request.getParameter(ParameterConstants.VERSION));
			
			service.add(project, build);
			
			response.getWriter().println();
		} catch (JdbcSQLException e) {
			response.getWriter().println(MessageConstants.PROJECT_EXIST
					+ request.getParameter(ParameterConstants.NAME) 
					+ GeneralConsants.SINGLE_QUOTE);
		} catch (Exception e) {
			response.getWriter().
				println(MessageConstants.SORRY_MESSAGE);
		}
	}

}
