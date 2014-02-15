package org.training.issuetracker.web.servlets.user;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.training.issuetracker.db.beans.User;
import org.training.issuetracker.db.beans.UserRole;
import org.training.issuetracker.db.service.RoleService;
import org.training.issuetracker.db.service.UserService;
import org.training.issuetracker.web.constants.MessageConstants;
import org.training.issuetracker.web.constants.ParameterConstants;
import org.training.issuetracker.web.constants.URLConstants;

public class EditUserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public EditUserServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String id = request.getParameter(ParameterConstants.ID);
		if (id == null) {
			getServletContext().getRequestDispatcher(URLConstants.USERS_URL).
					forward(request, response);
		} else {
			try {
				User user = (User) request.getSession().
						getAttribute(ParameterConstants.USER);
				
				long userId = Integer.parseInt(id);
				UserService uService = new UserService(user);
				User usr = uService.getUserById(userId);
				
				if (usr == null) {
					getServletContext().getRequestDispatcher(URLConstants.USERS_URL).
							forward(request, response);
				} else {
					request.setAttribute(ParameterConstants.USR, usr);
					
					RoleService rService = new RoleService(user);
					List<UserRole> roles = rService.getRoles();
					request.setAttribute(ParameterConstants.ROLES, roles);
					
					getServletContext().getRequestDispatcher(URLConstants.EDIT_USER_JSP).
							forward(request, response);	
				}
			} catch(NumberFormatException e) {
				getServletContext().getRequestDispatcher(URLConstants.USERS_URL).
					forward(request, response);
			} catch(Exception e) {
				response.getWriter().println(MessageConstants.SORRY_MESSAGE);
			}
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			User user = (User) request.getSession().getAttribute(ParameterConstants.USER);
			
			User u = new User();
			u.setId(Long.parseLong(request.getParameter(ParameterConstants.ID)));
			u.setFirstName(request.getParameter(ParameterConstants.FIRST_NAME));
			u.setLastName(request.getParameter(ParameterConstants.LAST_NAME));
			u.setEmailAddress(request.getParameter(ParameterConstants.EMAIL));
			
			UserRole role = new UserRole();
			role.setId(Integer.parseInt(request.getParameter(ParameterConstants.ROLE_ID)));
			u.setRole(role);
			
			UserService service = new UserService(user);
			service.update(u);
			
			response.getWriter().println(MessageConstants.USER_UPDATED);
		} catch (Exception e) {
			response.getWriter().println(MessageConstants.SORRY_MESSAGE);
		}
	}

}
