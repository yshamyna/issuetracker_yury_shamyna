package org.training.issuetracker.db.service.actions;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.training.issuetracker.db.beans.Resolution;
import org.training.issuetracker.db.beans.User;
import org.training.issuetracker.db.interfaces.Action;
import org.training.issuetracker.db.service.ResolutionService;

public class UpdateResolutionAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			User user = (User) request.getSession().getAttribute("user");
			
			Resolution resolution = new Resolution();
			resolution.setId(Long.parseLong(request.getParameter("id")));
			resolution.setValue(request.getParameter("name"));
			
			ResolutionService service = new ResolutionService(user);
			service.update(resolution);
			
			response.getWriter().println("Issue resolution was updated successfully.");
		} catch (Exception e) {
			response.getWriter().
				println("Sorry, but current service is not available... Please try later.");
		}
	}

}
