package org.training.issuetracker.web.servlets.resolution;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.h2.jdbc.JdbcSQLException;
import org.training.issuetracker.db.beans.Resolution;
import org.training.issuetracker.db.dao.interfaces.IResolutionDAO;
import org.training.issuetracker.db.dao.service.ResolutionDAO;

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
			Resolution resolution = new Resolution();
			resolution.setValue(request.getParameter("entityName"));
			IResolutionDAO resolutionDAO = new ResolutionDAO();
			resolutionDAO.add(resolution);
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
