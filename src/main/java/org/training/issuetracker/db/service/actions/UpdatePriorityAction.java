package org.training.issuetracker.db.service.actions;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.training.issuetracker.db.beans.Priority;
import org.training.issuetracker.db.beans.User;
import org.training.issuetracker.db.interfaces.Action;
import org.training.issuetracker.db.service.PriorityService;

public class UpdatePriorityAction implements Action {

	@Override
	public void execute(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		try {
			User user = (User) request.getSession().getAttribute("user");
			
			Priority priority  = new Priority();
			priority.setId(Long.parseLong(request.getParameter("id")));
			priority.setValue(request.getParameter("name"));
			
			PriorityService service = new PriorityService(user);
			service.update(priority);
			
			response.getWriter().println("Issue priority was updated successfully.");
		} catch (Exception e) {
			response.getWriter().
				println("Sorry, but current service is not available... Please try later.");
		}
	}

}
