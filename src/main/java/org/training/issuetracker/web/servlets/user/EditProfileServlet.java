package org.training.issuetracker.web.servlets.user;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.h2.jdbc.JdbcSQLException;
import org.training.issuetracker.db.beans.User;
import org.training.issuetracker.db.service.UserService;
import org.training.issuetracker.web.constants.GeneralConsants;
import org.training.issuetracker.web.constants.MessageConstants;
import org.training.issuetracker.web.constants.ParameterConstants;
import org.training.issuetracker.web.constants.URLConstants;

public class EditProfileServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public EditProfileServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		getServletContext().getRequestDispatcher(URLConstants.EDIT_PROFILE_JSP).
			forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		try {
			User user = (User) request.getSession().
						getAttribute(ParameterConstants.USER);
			
			String firstName = request.getParameter(ParameterConstants.FIRST_NAME);
			String lastName = request.getParameter(ParameterConstants.LAST_NAME);
			String email = request.getParameter(ParameterConstants.EMAIL);
			
			user.setFirstName(firstName);
			user.setLastName(lastName);
			user.setEmailAddress(email);
			
			UserService service = new UserService(user);
			service.update(user);
			
			response.getWriter().println(MessageConstants.PROFILE_UPDATED);
		} catch (JdbcSQLException e) {
			response.getWriter().println(MessageConstants.USER_EXIST 
					+ request.getParameter(ParameterConstants.EMAIL) 
					+ GeneralConsants.SINGLE_QUOTE);
		} catch(Exception e) {
			response.getWriter().println(MessageConstants.SORRY_MESSAGE);
		}
	}

}
