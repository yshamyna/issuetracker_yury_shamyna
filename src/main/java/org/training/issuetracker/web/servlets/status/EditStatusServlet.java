package org.training.issuetracker.web.servlets.status;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.training.issuetracker.db.beans.IssueStatus;
import org.training.issuetracker.db.dao.interfaces.IStatusDAO;
import org.training.issuetracker.db.dao.service.StatusDAO;

/**
 * Servlet implementation class EditStatusServlet
 */
public class EditStatusServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public EditStatusServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String id = request.getParameter("id");
		if (id == null) {
			getServletContext().getRequestDispatcher("/resolutions").
					forward(request, response);
		} else {
			try {
				long statusId = Integer.parseInt(id);
				IStatusDAO dao = new StatusDAO();
				IssueStatus status = dao.getById(statusId);
				if (status == null) {
					getServletContext().getRequestDispatcher("/statuses").
							forward(request, response);
				} else {
					request.setAttribute("status", status);
					getServletContext().getRequestDispatcher("/editStatus.jsp").
							forward(request, response);	
				}
			} catch(NumberFormatException e) {
				getServletContext().getRequestDispatcher("/statuses").
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
			String id = request.getParameter("id");
			String name = request.getParameter("entityName");
			
			IssueStatus status = new IssueStatus();
			status.setId(Integer.parseInt(id));
			status.setValue(name);	
			
			IStatusDAO statusDAO = new StatusDAO();
			statusDAO.updateName(status);
			
			response.getWriter().println("Issue status was updated successfully.");
		} catch (Exception e) {
			response.getWriter().println("Sorry, but current service is not available... Please try later.");
		}
	}

}
