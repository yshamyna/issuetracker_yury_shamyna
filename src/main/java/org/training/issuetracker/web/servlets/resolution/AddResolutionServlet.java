package org.training.issuetracker.web.servlets.resolution;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.h2.jdbc.JdbcSQLException;
import org.training.issuetracker.db.beans.Resolution;
import org.training.issuetracker.db.beans.User;
import org.training.issuetracker.db.service.ResolutionService;

/**
 * Servlet implementation class AddResolutionServlet
 */
public class AddResolutionServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AddResolutionServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		getServletContext().getRequestDispatcher("/addResolution.jsp").
					forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			User user = (User) request.getSession().getAttribute("user");
			
			Resolution resolution = new Resolution();
			resolution.setName(request.getParameter("name"));
			
			ResolutionService service = new ResolutionService(user);
			service.add(resolution);
			
			response.getWriter().println("Issue resolution was added successfully.");
		} catch (JdbcSQLException e) {
			response.getWriter().println("Already exists value '" 
					+ request.getParameter("name") + "'");
		} catch (Exception e) {
			response.getWriter().
				println("Sorry, but current service is not available... Please try later.");
		}
	}

}
