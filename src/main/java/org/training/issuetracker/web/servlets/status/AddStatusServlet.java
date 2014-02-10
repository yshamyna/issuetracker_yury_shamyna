package org.training.issuetracker.web.servlets.status;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.h2.jdbc.JdbcSQLException;
import org.training.issuetracker.db.beans.Status;
import org.training.issuetracker.db.dao.interfaces.IStatusDAO;
import org.training.issuetracker.db.dao.service.StatusDAO;

/**
 * Servlet implementation class AddStatusServlet
 */
public class AddStatusServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AddStatusServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		getServletContext().getRequestDispatcher("/addStatus.jsp").
		forward(request, response);	
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			Status status = new Status();
			status.setValue(request.getParameter("entityName"));
			IStatusDAO statusDAO = new StatusDAO();
			statusDAO.add(status);
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
