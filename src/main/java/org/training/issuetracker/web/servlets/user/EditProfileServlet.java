package org.training.issuetracker.web.servlets.user;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.training.issuetracker.db.beans.User;
import org.training.issuetracker.db.service.UserService;

public class EditProfileServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public EditProfileServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		getServletContext().getRequestDispatcher("/editProfile.jsp").
			forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			User user = (User) request.getSession().getAttribute("user");
			
			String firstName = request.getParameter("firstName");
			String lastName = request.getParameter("lastName");
			String email = request.getParameter("email");
			
			user.setFirstName(firstName);
			user.setLastName(lastName);
			user.setEmailAddress(email);
			
			UserService service = new UserService(user);
			service.update(user);
			
			response.getWriter().println("Profile was changed successfully.");
		} catch(Exception e) {
			response.getWriter().println("Sorry, but current service is not available... Please try later.");
		}
	}

}
