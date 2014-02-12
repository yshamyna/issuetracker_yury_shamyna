package org.training.issuetracker.web.actions;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.training.issuetracker.db.beans.User;
import org.training.issuetracker.db.service.UserService;
import org.training.issuetracker.web.actions.interfaces.WebAction;

public class LoginWebAction implements WebAction {

	@Override
	public void execute(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String email = request.getParameter("E-mail");
		String password = request.getParameter("Password");
		try {
			UserService service = new UserService(null);
			User user = service.getUser(email, password);
			if (user == null) {
				request.setAttribute("msg", "Email or password is incorrect.");
				request.getRequestDispatcher("/dashboard").
					forward(request, response);
				return;
			} 
			request.getSession().setAttribute("user", user);
			response.sendRedirect("dashboard");
			return;
		} catch (Exception e) {
			response.getWriter().
				println("Sorry, but current service is not available... Please try later.");
		}
	}

}
