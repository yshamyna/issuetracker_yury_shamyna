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

public class AddProjectServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public AddProjectServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			User user = (User) request.getSession().getAttribute("user");
			
			ManagerService service = new ManagerService(user);
			List<Manager> managers = service.getManagers();
			
			request.setAttribute("managers", managers);
			
			getServletContext().getRequestDispatcher("/addProject.jsp").
				forward(request, response);	
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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
