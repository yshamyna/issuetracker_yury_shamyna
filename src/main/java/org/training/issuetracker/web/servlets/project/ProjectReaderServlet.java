package org.training.issuetracker.web.servlets.project;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.training.issuetracker.db.beans.Project;
import org.training.issuetracker.db.dao.interfaces.IProjectDAO;
import org.training.issuetracker.db.dao.service.ProjectDAO;

/**
 * Servlet implementation class ProjectReaderServlet
 */
public class ProjectReaderServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ProjectReaderServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		execute(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		execute(request, response);
	}
	
	private void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			String page = request.getParameter("page");
			long pageNumber = 1;
			if (page != null) {
				pageNumber = Integer.parseInt(page);
			}
			
			IProjectDAO projectDAO = new ProjectDAO();
			List<Project> projects = projectDAO.getNRecordsFromPageY(10, pageNumber);
			
			long maxPage = projectDAO.getQuantityPages(10);
			pageNumber = pageNumber > maxPage ? maxPage : pageNumber;
			pageNumber = pageNumber < 1 ? 1 : pageNumber;
			
			request.setAttribute("projects", projects);
			request.setAttribute("page", pageNumber);
			request.setAttribute("maxPage", maxPage);
		}  catch (Exception e) {
			e.printStackTrace();
		} finally {
			getServletContext().getRequestDispatcher("/projects.jsp").
				forward(request, response);
		}
	}

}
