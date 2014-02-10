package org.training.issuetracker.db.service.actions;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.training.issuetracker.db.beans.User;
import org.training.issuetracker.db.beans.UserRole;
import org.training.issuetracker.db.interfaces.Action;
import org.training.issuetracker.db.service.UserService;

public class UpdateUserAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			User user = (User) request.getSession().getAttribute("user");
			
			User u = new User();
			u.setId(Long.parseLong(request.getParameter("id")));
			u.setFirstName(request.getParameter("firstName"));
			u.setLastName(request.getParameter("lastName"));
			u.setEmailAddress(request.getParameter("email"));
			
			UserRole role = new UserRole();
			role.setId(Integer.parseInt(request.getParameter("roleId")));
			u.setRole(role);
			
			UserService service = new UserService(user);
			service.update(user);
			
			response.getWriter().println("User data was updated successfully.");
		} catch (Exception e) {
			response.getWriter().
				println("Sorry, but current service is not available... Please try later.");
		}
	}

}
