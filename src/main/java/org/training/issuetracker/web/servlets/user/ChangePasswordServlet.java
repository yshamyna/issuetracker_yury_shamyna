package org.training.issuetracker.web.servlets.user;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.training.issuetracker.db.beans.User;
import org.training.issuetracker.db.service.UserService;

public class ChangePasswordServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public ChangePasswordServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		getServletContext().getRequestDispatcher("/changePassword.jsp").
			forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			User user = (User) request.getSession().getAttribute("user");
			
			String password = request.getParameter("password");

			UserService service = new UserService(user);
			service.changePassword(user, password);
			
			response.getWriter().println("Password was changed successfully.");
		} catch(Exception e) {
			response.getWriter().println("Sorry, but current service is not available... Please try later.");
		}
	}

}
