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
import org.training.issuetracker.web.constants.GeneralConsants;
import org.training.issuetracker.web.constants.MessageConstants;
import org.training.issuetracker.web.constants.ParameterConstants;
import org.training.issuetracker.web.constants.URLConstants;

public class ProjectsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public ProjectsServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		execute(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		execute(request, response);
	}
	
	private void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			String page = request.getParameter(ParameterConstants.PAGE);
			long pageNumber = 1;
			if (page != null) {
				pageNumber = Integer.parseInt(page);
			}
			
			User user = (User) request.getSession().getAttribute(ParameterConstants.USER);
			
			ProjectService service = new ProjectService(user);
			List<Project> projects = service.getProjects(pageNumber, 
								GeneralConsants.RECORDS_PER_PAGE);
			
			service = new ProjectService(user);
			long maxPage = service.getQuantityPages(GeneralConsants.RECORDS_PER_PAGE);
			pageNumber = pageNumber > maxPage ? maxPage : pageNumber;
			pageNumber = pageNumber < 1 ? 1 : pageNumber;
			
			request.setAttribute(ParameterConstants.PROJECTS, projects);
			request.setAttribute(ParameterConstants.PAGE, pageNumber);
			request.setAttribute(ParameterConstants.MAX_PAGE, maxPage);
			
			getServletContext().getRequestDispatcher(URLConstants.PROJECTS_JSP).
				forward(request, response);
		}  catch (Exception e) {
			response.getWriter().
				println(MessageConstants.SORRY_MESSAGE);
		} 
	}

}
