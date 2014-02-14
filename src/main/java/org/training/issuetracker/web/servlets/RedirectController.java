package org.training.issuetracker.web.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.training.issuetracker.web.actions.factories.WebActionFactory;
import org.training.issuetracker.web.actions.interfaces.WebAction;
import org.training.issuetracker.web.constants.ParameterConstants;

public class RedirectController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public RedirectController() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		execute(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		execute(request, response);
	}

	private void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action = request.getParameter(ParameterConstants.ACTION);
		WebAction webAction = WebActionFactory.getWebActionFromFactory(action);
		webAction.execute(request, response);
	}
}
