package org.training.issuetracker.web.servlets.type;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.h2.jdbc.JdbcSQLException;
import org.training.issuetracker.db.beans.IssueType;
import org.training.issuetracker.db.dao.interfaces.ITypeDAO;
import org.training.issuetracker.db.dao.service.TypeDAO;

/**
 * Servlet implementation class AddTypeServlet
 */
public class AddTypeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AddTypeServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		getServletContext().getRequestDispatcher("/addType.jsp").
				forward(request, response);	
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			IssueType  type = new IssueType();
			type.setValue(request.getParameter("entityName"));
			ITypeDAO typeDAO = new TypeDAO();
			typeDAO.add(type);
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
