package org.training.issuetracker.web.servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.training.issuetracker.db.beans.User;
import org.training.issuetracker.db.service.UserService;

public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public Login() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.getRequestDispatcher("/dashboard.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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
			e.printStackTrace();
			response.getWriter().
				println("Sorry, but current service is not available... Please try later.");
		}
	}

}
