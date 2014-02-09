package org.training.issuetracker.web.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.training.issuetracker.db.interfaces.Action;
import org.training.issuetracker.db.interfaces.ActionFactory;
import org.training.issuetracker.db.service.factories.Factory;

/**
 * Servlet implementation class ActionControllerServlet
 */
public class ActionControllerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ActionControllerServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			String actionType = request.getParameter("action");
			ActionFactory actionFactory = Factory.getActionFactory(actionType);
			String entityType = request.getParameter("entity");
			Action action = actionFactory.getActionFromFactory(entityType);
			action.execute(request, response);
		} catch (Exception e) {
			response.getWriter().println("Sorry, but current service is not available... Please try later.");
		}
	}

}
