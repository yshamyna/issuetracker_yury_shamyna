package org.training.issuetracker.db.service.actions;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.training.issuetracker.db.beans.Status;
import org.training.issuetracker.db.beans.User;
import org.training.issuetracker.db.interfaces.Action;
import org.training.issuetracker.db.service.StatusService;

public class UpdateStatusAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			User user = (User) request.getSession().getAttribute("user");
			
			Status status = new Status();
			status.setId(Long.parseLong(request.getParameter("id")));
			status.setValue(request.getParameter("name"));	
			
			StatusService service = new StatusService(user);
			service.update(status);
			
			response.getWriter().println("Issue status was updated successfully.");
		} catch (Exception e) {
			response.getWriter().
				println("Sorry, but current service is not available... Please try later.");
		}
	}

}
