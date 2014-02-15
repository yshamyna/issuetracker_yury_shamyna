package org.training.issuetracker.web.servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.training.issuetracker.db.beans.User;
import org.training.issuetracker.db.service.UserService;
import org.training.issuetracker.web.constants.MessageConstants;
import org.training.issuetracker.web.constants.ParameterConstants;
import org.training.issuetracker.web.constants.URLConstants;

public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public Login() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.getRequestDispatcher(URLConstants.DASHBOARD_JSP).
					forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String email = request.getParameter(ParameterConstants.EMAIL);
		String password = request.getParameter(ParameterConstants.PASSWORD);
		try {
			UserService service = new UserService(null);
			User user = service.getUser(email, password);
			if (user == null) {
				request.setAttribute(ParameterConstants.MSG, 
								ParameterConstants.MSG_VALUE);
				request.getRequestDispatcher(URLConstants.DASHBOARD_URL).
					forward(request, response);
				return;
			} 
			request.getSession().setAttribute(ParameterConstants.USER, user);
			response.sendRedirect(URLConstants.DASHBOARD);
			return;
		} catch (Exception e) {
			response.getWriter().println(MessageConstants.SORRY_MESSAGE);
		}
	}

}
