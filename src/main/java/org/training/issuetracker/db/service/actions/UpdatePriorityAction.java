package org.training.issuetracker.db.service.actions;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.training.issuetracker.db.beans.User;
import org.training.issuetracker.db.interfaces.Action;
import org.training.issuetracker.db.interfaces.Service;
import org.training.issuetracker.db.service.PriorityService;

public class UpdatePriorityAction implements Action {

	@Override
	public void execute(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		try {
			User user = (User) request.getSession().getAttribute("user");
			
			Service service = new PriorityService(user);
			String[] paramNames = service.getParameters();
			
			Map<String, String> values = new HashMap<String, String>();
			for (String paramName : paramNames) {
				values.put(paramName, request.getParameter(paramName));
			}
			
			service.update(values);
			
			response.getWriter().println("Issue priority was updated successfully.");
		} catch (Exception e) {
			response.getWriter().
				println("Sorry, but current service is not available... Please try later.");
		}
	}

}
