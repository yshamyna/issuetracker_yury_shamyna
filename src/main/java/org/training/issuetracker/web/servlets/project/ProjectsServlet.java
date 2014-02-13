package org.training.issuetracker.web.servlets.project;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.training.issuetracker.db.beans.Project;
import org.training.issuetracker.db.beans.User;
import org.training.issuetracker.db.service.ProjectService;

/**
 * Servlet implementation class ProjectReaderServlet
 */
public class ProjectsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ProjectsServlet() {
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
			
			User user = (User) request.getSession().getAttribute("user");
			
			ProjectService service = new ProjectService(user);
			List<Project> projects = service.getProjects(pageNumber, 10);
			
			service = new ProjectService(user);
			long maxPage = service.getQuantityPages(10);
			pageNumber = pageNumber > maxPage ? maxPage : pageNumber;
			pageNumber = pageNumber < 1 ? 1 : pageNumber;
			
			request.setAttribute("projects", projects);
			request.setAttribute("page", pageNumber);
			request.setAttribute("maxPage", maxPage);
			
			getServletContext().getRequestDispatcher("/projects.jsp").
				forward(request, response);
		}  catch (Exception e) {
			response.getWriter().
				println("Sorry, but current page is not available... Please try later.");
		} 
	}

}
