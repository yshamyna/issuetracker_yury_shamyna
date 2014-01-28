package org.training.issuetracker.web.servlets.priority;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.h2.jdbc.JdbcSQLException;
import org.training.issuetracker.db.beans.IssuePriority;
import org.training.issuetracker.db.dao.interfaces.IPriorityDAO;
import org.training.issuetracker.db.dao.service.PriorityDAO;

/**
 * Servlet implementation class AddPriorityServlet
 */
public class AddPriorityServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AddPriorityServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		getServletContext().getRequestDispatcher("/addPriority.jsp").
			forward(request, response);	
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			IssuePriority priority = new IssuePriority();
			priority.setValue(request.getParameter("entityName"));
			IPriorityDAO priorityDAO = new PriorityDAO();
			priorityDAO.add(priority);
		} catch (JdbcSQLException e) {
			request.setAttribute("errMsg", "Already exists value '" 
							+ request.getParameter("entityName") + "'");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			doGet(request, response);	
		}
	}

}
