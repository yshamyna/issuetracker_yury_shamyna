package org.training.issuetracker.db.service.actions;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.h2.jdbc.JdbcSQLException;
import org.training.issuetracker.db.beans.Priority;
import org.training.issuetracker.db.beans.User;
import org.training.issuetracker.db.interfaces.Action;
import org.training.issuetracker.db.service.PriorityService;

public class AddPriorityAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			User user = (User) request.getSession().getAttribute("user");
			
			Priority priority  = new Priority();
			priority.setValue(request.getParameter("name"));
			
			PriorityService service = new PriorityService(user);
			service.add(priority);
			
			response.getWriter().println("Issue priority was added successfully.");
		} catch (JdbcSQLException e) {
			response.getWriter().println("Already exists value '" 
						+ request.getParameter("name") + "'");
		} catch (Exception e) {
			response.getWriter().
				println("Sorry, but current service is not available... Please try later.");
		}
	}

}
