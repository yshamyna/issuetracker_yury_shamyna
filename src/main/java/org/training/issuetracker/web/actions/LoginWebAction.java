package org.training.issuetracker.web.actions;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.training.issuetracker.db.beans.User;
import org.training.issuetracker.db.service.UserService;
import org.training.issuetracker.web.actions.interfaces.IWebAction;

public class LoginWebAction implements IWebAction {

	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		try {
			UserService service = new UserService(null);
			User user = service.getUser(email, password);
			if (user == null) {
				response.getWriter().println("Email or password is incorrect.");
				return null;
			} 
			request.getSession().setAttribute("user", user);
			response.sendRedirect("dashboard");
		} catch (Exception e) {
			response.getWriter().
				println("Sorry, but current service is not available... Please try later.");
		}
		return null;
	}

}
