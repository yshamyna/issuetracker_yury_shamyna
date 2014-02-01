package org.training.issuetracker.web.servlets.priority;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.training.issuetracker.db.beans.IssuePriority;
import org.training.issuetracker.db.dao.interfaces.IPriorityDAO;
import org.training.issuetracker.db.dao.service.PriorityDAO;

/**
 * Servlet implementation class EditPriorityServlet
 */
public class EditPriorityServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public EditPriorityServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String id = request.getParameter("id");
		if (id == null) {
			getServletContext().getRequestDispatcher("/priorities").
					forward(request, response);
		} else {
			try {
				long entityId = Integer.parseInt(id);
				IPriorityDAO dao = new PriorityDAO();
				IssuePriority entity = dao.getById(entityId);
				if (entity == null) {
					getServletContext().getRequestDispatcher("/priorities").
							forward(request, response);
				} else {
					request.setAttribute("priority", entity);
					getServletContext().getRequestDispatcher("/editPriority.jsp").
							forward(request, response);	
				}
			} catch(NumberFormatException e) {
				getServletContext().getRequestDispatcher("/priorities").
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
			
			IssuePriority priority  = new IssuePriority();
			priority.setId(Integer.parseInt(id));
			priority.setValue(name);	
			
			IPriorityDAO priorityDAO = new PriorityDAO();
			priorityDAO.update(priority);
			
			response.getWriter().println("Issue priority was updated successfully.");
		} catch (Exception e) {
			response.getWriter().println("Sorry, but current service is not available... Please try later.");
		}
	}

}
