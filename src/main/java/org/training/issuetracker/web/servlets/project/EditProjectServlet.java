package org.training.issuetracker.web.servlets.project;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.training.issuetracker.db.beans.Build;
import org.training.issuetracker.db.beans.IssueStatus;
import org.training.issuetracker.db.beans.Manager;
import org.training.issuetracker.db.beans.Project;
import org.training.issuetracker.db.dao.interfaces.IBuildDAO;
import org.training.issuetracker.db.dao.interfaces.IManagerDAO;
import org.training.issuetracker.db.dao.interfaces.IProjectDAO;
import org.training.issuetracker.db.dao.interfaces.IStatusDAO;
import org.training.issuetracker.db.dao.service.BuildDAO;
import org.training.issuetracker.db.dao.service.ManagerDAO;
import org.training.issuetracker.db.dao.service.ProjectDAO;
import org.training.issuetracker.db.dao.service.StatusDAO;

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
				long projectId = Integer.parseInt(id);
				IProjectDAO dao = new ProjectDAO();
				Project project = dao.getById(projectId);
				if (project == null) {
					getServletContext().getRequestDispatcher("/projects").
							forward(request, response);
				} else {
					request.setAttribute("project", project);
					
					IBuildDAO buildDAO = new BuildDAO();
					List<Build> builds = buildDAO.getByProjectId(projectId);
					request.setAttribute("builds", builds);

					IManagerDAO managerDAO = new ManagerDAO();
					List<Manager> managers = managerDAO.getAll();
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
//		try {
//			String id = request.getParameter("id");
//			String name = request.getParameter("name");
//			
//			IssueStatus status = new IssueStatus();
//			status.setId(Integer.parseInt(id));
//			status.setValue(name);	
//			
//			IStatusDAO statusDAO = new StatusDAO();
//			statusDAO.updateName(status);
//			
//			response.getWriter().println("Issue status was updated successfully.");
//		} catch (Exception e) {
//			response.getWriter().println("Sorry, but current service is not available... Please try later.");
//		}
	}

}
