package org.training.issuetracker.web.servlets.user;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.training.issuetracker.db.beans.User;
import org.training.issuetracker.db.service.UserService;
import org.training.issuetracker.web.constants.MessageConstants;
import org.training.issuetracker.web.constants.ParameterConstants;
import org.training.issuetracker.web.constants.URLConstants;

public class UsersServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public UsersServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		execute(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		execute(request, response);
	}

	private void execute(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		try {
			User user = (User) request.getSession().
								getAttribute(ParameterConstants.USER);
			
			UserService service = new UserService(user);
			List<User> users = service.getUsers();
			
			request.setAttribute(ParameterConstants.USERS, users);
			
			getServletContext().getRequestDispatcher(URLConstants.USERS_JSP).
				forward(request, response);
		}  catch (Exception e) {
			response.getWriter().println(MessageConstants.SORRY_MESSAGE);
		} 
	}
}
