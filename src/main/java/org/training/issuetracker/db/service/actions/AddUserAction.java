package org.training.issuetracker.db.service.actions;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.h2.jdbc.JdbcSQLException;
import org.training.issuetracker.db.beans.User;
import org.training.issuetracker.db.beans.UserRole;
import org.training.issuetracker.db.interfaces.Action;
import org.training.issuetracker.db.service.UserService;

public class AddUserAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			User user = (User) request.getSession().getAttribute("user");
			
			User u = new User();
			u.setFirstName(request.getParameter("firstName"));
			u.setLastName(request.getParameter("lastName"));
			u.setEmailAddress(request.getParameter("email"));
			
			UserRole role = new UserRole();
			role.setId(Integer.parseInt(request.getParameter("roleId")));
			u.setRole(role);
			
			UserService service = new UserService(user);
			service.add(user);
			
			response.getWriter().println("User was added successfully.");
		} catch (JdbcSQLException e) {
			response.getWriter().println("Already exists user with e-mail '" 
					+ request.getParameter("email") + "'");
		} catch (Exception e) {
			response.getWriter().
				println("Sorry, but current service is not available... Please try later.");
		}
	}

}
