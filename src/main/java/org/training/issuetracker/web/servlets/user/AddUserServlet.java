package org.training.issuetracker.web.servlets.user;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.h2.jdbc.JdbcSQLException;
import org.training.issuetracker.db.beans.User;
import org.training.issuetracker.db.beans.UserRole;
import org.training.issuetracker.db.service.RoleService;
import org.training.issuetracker.db.service.UserService;
import org.training.issuetracker.web.constants.GeneralConsants;
import org.training.issuetracker.web.constants.MessageConstants;
import org.training.issuetracker.web.constants.ParameterConstants;
import org.training.issuetracker.web.constants.URLConstants;

public class AddUserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public AddUserServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			User user = (User) request.getSession().
						getAttribute(ParameterConstants.USER);
			
			RoleService service = new RoleService(user);
			List<UserRole> roles = service.getRoles();
			request.setAttribute(ParameterConstants.ROLES, roles);
			
			getServletContext().getRequestDispatcher(URLConstants.ADD_USER_JSP).
					forward(request, response);	
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			User user = (User) request.getSession().
					getAttribute(ParameterConstants.USER);
			
			User u = new User();
			u.setFirstName(request.getParameter(ParameterConstants.FIRST_NAME));
			u.setLastName(request.getParameter(ParameterConstants.LAST_NAME));
			u.setEmailAddress(request.getParameter(ParameterConstants.EMAIL));
			u.setPassword(request.getParameter(ParameterConstants.PASSWORD));
			
			UserRole role = new UserRole();
			role.setId(Integer.parseInt(request.
									getParameter(ParameterConstants.ROLE_ID)));
			u.setRole(role);
			
			UserService service = new UserService(user);
			service.add(u);
			
			response.getWriter().println(MessageConstants.USER_ADDED);
		} catch (JdbcSQLException e) {
			response.getWriter().println(MessageConstants.USER_EXIST 
					+ request.getParameter(ParameterConstants.EMAIL) 
					+ GeneralConsants.SINGLE_QUOTE);
		} catch (Exception e) {
			response.getWriter().println(MessageConstants.SORRY_MESSAGE);
		}
	}

}
