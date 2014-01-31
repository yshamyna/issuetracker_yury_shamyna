package org.training.issuetracker.web.servlets.resolution;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.training.issuetracker.db.beans.IssueResolution;
import org.training.issuetracker.db.dao.interfaces.IResolutionDAO;
import org.training.issuetracker.db.dao.service.ResolutionDAO;

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
				long resolutionId = Integer.parseInt(id);
				IResolutionDAO dao = new ResolutionDAO();
				IssueResolution resolution = dao.getById(resolutionId);
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
			String id = request.getParameter("id");
			String name = request.getParameter("entityName");
			
			IssueResolution resolution = new IssueResolution();
			resolution.setId(Integer.parseInt(id));
			resolution.setValue(name);	
			
			IResolutionDAO resolutionDAO = new ResolutionDAO();
			resolutionDAO.updateName(resolution);
			
			response.getWriter().println("Issue resolution was updated successfully.");
		} catch (Exception e) {
			response.getWriter().println("Sorry, but current service is not available... Please try later.");
		}
	}

}
