package org.training.issuetracker.web.servlets.resolution;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.training.issuetracker.db.beans.Resolution;
import org.training.issuetracker.db.beans.User;
import org.training.issuetracker.db.service.ResolutionService;

/**
 * Servlet implementation class EditResolutionServlet
 */
public class EditResolutionServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public EditResolutionServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		String id = request.getParameter("id");
		if (id == null) {
			getServletContext().getRequestDispatcher("/resolutions").
					forward(request, response);
		} else {
			try {
				User user = (User) request.getSession().getAttribute("user");
				
				long resolutionId = Integer.parseInt(id);
				
				ResolutionService service = new ResolutionService(user);
				Resolution resolution = service.getResolutionById(resolutionId);
				
				if (resolution == null) {
					getServletContext().getRequestDispatcher("/resolutions").
							forward(request, response);
				} else {
					request.setAttribute("resolution", resolution);
					getServletContext().getRequestDispatcher("/editResolution.jsp").
							forward(request, response);	
				}
			} catch(NumberFormatException e) {
				getServletContext().getRequestDispatcher("/resolutions").
					forward(request, response);
			} catch(Exception e) {
				response.getWriter().println("Sorry, but current service is not available... Please try later.");
			}
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		try {
			User user = (User) request.getSession().getAttribute("user");
			
			Resolution resolution = new Resolution();
			resolution.setId(Long.parseLong(request.getParameter("id")));
			resolution.setValue(request.getParameter("name"));
			
			ResolutionService service = new ResolutionService(user);
			service.update(resolution);
			
			response.getWriter().println("Issue resolution was updated successfully.");
		} catch (Exception e) {
			response.getWriter().
				println("Sorry, but current service is not available... Please try later.");
		}
	}

}
