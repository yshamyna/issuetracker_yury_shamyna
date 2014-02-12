package org.training.issuetracker.web.servlets.project;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.training.issuetracker.db.beans.Build;
import org.training.issuetracker.db.beans.Manager;
import org.training.issuetracker.db.beans.Project;
import org.training.issuetracker.db.beans.User;
import org.training.issuetracker.db.service.BuildService;
import org.training.issuetracker.db.service.ManagerService;
import org.training.issuetracker.db.service.ProjectService;

/**
 * Servlet implementation class EditProjectServlet
 */
public class EditProjectServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public EditProjectServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String id = request.getParameter("id");
		if (id == null) {
			getServletContext().getRequestDispatcher("/projects").
					forward(request, response);
		} else {
			try {
				User user = (User) request.getSession().getAttribute("user");
				
				long projectId = Integer.parseInt(id);
				
				ProjectService pService = new ProjectService(user);
				Project project = pService.getProjectById(projectId);
				
				if (project == null) {
					getServletContext().getRequestDispatcher("/projects").
							forward(request, response);
				} else {
					request.setAttribute("project", project);
					
					BuildService bService = new BuildService(user);
					List<Build> builds = bService.getBuildsByProjectId(projectId);
					request.setAttribute("builds", builds);

					ManagerService mService = new ManagerService(user);
					List<Manager> managers = mService.getManagers();
					request.setAttribute("managers", managers);
					
					getServletContext().getRequestDispatcher("/editProject.jsp").
							forward(request, response);	
				}
			} catch(NumberFormatException e) {
				getServletContext().getRequestDispatcher("/projects").
					forward(request, response);
			} catch(Exception e) {
				response.getWriter().println("Sorry, but current service is not available... Please try later.");
			}
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			User user = (User) request.getSession().getAttribute("user");
			
			ProjectService service = new ProjectService(user);

			Project project = new Project();
			project.setId(Integer.parseInt(request.getParameter("id")));
			project.setName(request.getParameter("name"));
			project.setDescription(request.getParameter("description"));
			
			Manager manager = new Manager();
			manager.setId(Integer.parseInt(request.getParameter("managerId")));
			project.setManager(manager);
			
			Build build = new Build();
			build.setId(Integer.parseInt(request.getParameter("buildId")));
			build.setProjectId(project.getId());
			
			service.update(project, build);
			
			response.getWriter().println("Project was updated successfully.");
		} catch (Exception e) {
			response.getWriter().
				println("Sorry, but current service is not available... Please try later.");
		}
	}

}
