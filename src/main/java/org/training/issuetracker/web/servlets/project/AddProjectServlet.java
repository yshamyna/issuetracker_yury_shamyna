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
import org.training.issuetracker.db.dao.interfaces.IBuildDAO;
import org.training.issuetracker.db.dao.interfaces.IManagerDAO;
import org.training.issuetracker.db.dao.interfaces.IProjectDAO;
import org.training.issuetracker.db.dao.service.BuildDAO;
import org.training.issuetracker.db.dao.service.ManagerDAO;
import org.training.issuetracker.db.dao.service.ProjectDAO;

/**
 * Servlet implementation class AddProjectServlet
 */
public class AddProjectServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AddProjectServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			IManagerDAO managerDAO = new ManagerDAO();
			List<Manager> managers = managerDAO.getAll();
			request.setAttribute("managers", managers);
			getServletContext().getRequestDispatcher("/addProject.jsp").
			forward(request, response);	
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			Project project = new Project();
			project.setName(request.getParameter("projectName"));
			project.setDescription(request.getParameter("projectDescription"));
			Manager manager = new Manager();
			manager.setId(Integer.parseInt(request.getParameter("managers")));
			project.setManager(manager);
			IProjectDAO projectDAO = new ProjectDAO();
			long projectId = projectDAO.add(project);
			
			Build build = new Build();
			build.setVersion(request.getParameter("projectVersion"));
			build.setCurrent(true);
			build.setProjectId(projectId);
			IBuildDAO buildDAO = new BuildDAO();
			buildDAO.add(build);
		} catch (JdbcSQLException e) {
			request.setAttribute("errMsg", "Already exists project with name '" 
							+ request.getParameter("projectName") + "'");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			doGet(request, response);	
		}
	}

}
