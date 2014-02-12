package org.training.issuetracker.web.servlets.resolution;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.training.issuetracker.db.beans.Resolution;
import org.training.issuetracker.db.beans.User;
import org.training.issuetracker.db.service.ResolutionService;

/**
 * Servlet implementation class ResolutionReaderServlet
 */
public class ResolutionsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ResolutionsServlet() {
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

	private void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException  {
		try {
			User user = (User) request.getSession().getAttribute("user");
			
			ResolutionService service = new ResolutionService(user);
			List<Resolution> resolutions = service.getResolutions();
			
			request.setAttribute("resolutions", resolutions);
			
			getServletContext().getRequestDispatcher("/resolutions.jsp").
				forward(request, response);
		}  catch (Exception e) {
			response.getWriter().
				println("Sorry, but current page is not available... Please try later.");
		} 
	}
}
